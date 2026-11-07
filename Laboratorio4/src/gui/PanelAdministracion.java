package gui;
import excepciones.PalabraDuplicadaException;
import excepciones.PalabraInvalidaException;
import gui.sonido.EfectoSonido;
import gui.sonido.GestorSonido;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.AdministradorPalabras;
public class PanelAdministracion extends JPanel{
  private static final long serialVersionUID = 1L;
    private static final Color COLOR_OK = new Color(46, 160, 67);
    private static final Color COLOR_ERROR = new Color(200, 50, 50);

    private final AdministradorPalabras administrador;
    private final GestorSonido sonido;
    private final JTextField txtPalabra = new JTextField(15);
    private final JLabel lblMensaje = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblCantidad = new JLabel();
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modeloLista);

    public PanelAdministracion(VentanaPrincipal ventana, AdministradorPalabras administrador,
                               GestorSonido sonido) {
        this.administrador = administrador;
        this.sonido = sonido;
        construirInterfaz(ventana);
        actualizar();
    }

    private void construirInterfaz(VentanaPrincipal ventana) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Administración de palabras", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.add(lblCantidad, BorderLayout.NORTH);
        centro.add(new JScrollPane(lista), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        JPanel entrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnAgregar = new JButton("Agregar");
        entrada.add(new JLabel("Nueva palabra:"));
        entrada.add(txtPalabra);
        entrada.add(btnAgregar);

        JButton btnVolver = new JButton("Volver al menú");
        JPanel botonVolver = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonVolver.add(btnVolver);

        JPanel sur = new JPanel();
        sur.setLayout(new BoxLayout(sur, BoxLayout.Y_AXIS));
        sur.add(entrada);
        sur.add(lblMensaje);
        sur.add(botonVolver);
        add(sur, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarPalabra());
        txtPalabra.addActionListener(e -> agregarPalabra()); 
        btnVolver.addActionListener(e -> ventana.mostrarMenu());
    }

    private void agregarPalabra() {
        try {
            administrador.agregar(txtPalabra.getText());
            mostrarMensaje("Palabra agregada a la lista.", false);
            sonido.reproducir(EfectoSonido.ACIERTO);
            txtPalabra.setText("");
            actualizar();
        } catch (PalabraDuplicadaException | PalabraInvalidaException ex) {
            mostrarMensaje(ex.getMessage(), true);
            sonido.reproducir(EfectoSonido.ALERTA);
        }
        txtPalabra.requestFocusInWindow();
    }

    private void mostrarMensaje(String texto, boolean error) {
        lblMensaje.setForeground(error ? COLOR_ERROR : COLOR_OK);
        lblMensaje.setText(texto);
    }

   
    public void actualizar() {
        modeloLista.clear();
        for (String palabra : administrador.getPalabras()) {
            modeloLista.addElement(palabra);
        }
        lblCantidad.setText("Palabras registradas: " + administrador.cantidad());
    }

}
