/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author marlo
 */
import excepciones.PalabraInvalidaException;
import gui.sonido.EfectoSonido;
import gui.sonido.GestorSonido;
import java.awt.*;
import javax.swing.*;
import modelo.AdministradorPalabras;
import modelo.JuegoAhorcado;
import modelo.JuegoAlAzar;
import modelo.JuegoPalabraFija;
import modelo.Modalidad;

public class PanelMenu extends JPanel{
    private static final long serialVersionUID = 1L;
    private final VentanaPrincipal ventana;
    private final AdministradorPalabras administrador;
    private final GestorSonido sonido;
    private final JLabel lblMensaje = new JLabel(" ", SwingConstants.CENTER);
    public PanelMenu(VentanaPrincipal ventana, AdministradorPalabras administrador,GestorSonido sonido) {
        this.ventana = ventana;
        this.administrador = administrador;
        this.sonido = sonido;
        construirInterfaz();
    }
    private void construirInterfaz() {
        setLayout(new GridBagLayout()); // centra el bloque de botones
        JPanel bloque = new JPanel();
        bloque.setLayout(new BoxLayout(bloque, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Juego del Ahorcado", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 34f));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        bloque.add(titulo);
        bloque.add(Box.createVerticalStrut(25));
 // El texto de cada botón sale del enum Modalidad.
        JButton btnFija = crearBoton(Modalidad.PALABRA_FIJA.getTexto());
        JButton btnAzar = crearBoton(Modalidad.AL_AZAR.getTexto());
        JButton btnAdmin = crearBoton("Administrar palabras");
        JButton btnSalir = crearBoton("Salir");
        bloque.add(btnFija);
        bloque.add(Box.createVerticalStrut(10));
        bloque.add(btnAzar);
        bloque.add(Box.createVerticalStrut(10));
        bloque.add(btnAdmin);
        bloque.add(Box.createVerticalStrut(10));
        bloque.add(btnSalir);
        bloque.add(Box.createVerticalStrut(15));
        lblMensaje.setForeground(new Color(200, 50, 50));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        bloque.add(lblMensaje);
        add(bloque);
        btnFija.addActionListener(e -> iniciarPalabraFija());
        btnAzar.addActionListener(e -> iniciarAlAzar());
        btnAdmin.addActionListener(e -> ventana.mostrarAdministracion());
        btnSalir.addActionListener(e -> ventana.salir());
    }
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(260, 40));
        boton.setMaximumSize(new Dimension(260, 40));
        return boton;
    }
    private void iniciarPalabraFija() {
        String palabra = DialogoPalabra.pedir(this);
        if (palabra == null) {
            return; // el jugador canceló
    }
    try {
        JuegoAhorcado juego = new JuegoPalabraFija(palabra);
        lblMensaje.setText(" ");
        ventana.mostrarJuego(juego);
    } 
    catch (PalabraInvalidaException ex) {
        lblMensaje.setText(ex.getMessage());
        sonido.reproducir(EfectoSonido.ALERTA);
    }
 }
    private void iniciarAlAzar() {
        try {
            JuegoAhorcado juego = new JugadorAlAzar(administrador);
            lblMensaje.setText(" ");
            ventana.mostrarJuego(juego);
        }
        catch (PalabraInvalidaException ex) {
            lblMensaje.setText(ex.getMessage());
            sonido.reproducir(EfectoSonido.ALERTA);
        }
    }
}
