package gui;

import gui.sonido.GestorSonido;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modelo.AdministradorPalabras;
import modelo.JuegoAhorcado;

public class VentanaPrincipal extends JFrame{
    
    private static final long serialVersionUID = 1L;
    private static final String CARTA_MENU = "MENU";
    private static final String CARTA_JUEGO = "JUEGO";
    private static final String CARTA_ADMIN = "ADMIN";

    private final CardLayout cartas = new CardLayout();
    private final JPanel contenedor = new JPanel(cartas);
    private final GestorSonido sonido = new GestorSonido();
    private final PanelJuego panelJuego;
    private final PanelAdministracion panelAdministracion;

    public VentanaPrincipal(AdministradorPalabras administrador) {
        super("Juego del Ahorcado");

        PanelMenu panelMenu = new PanelMenu(this, administrador, sonido);
        panelJuego = new PanelJuego(this, sonido);
        panelAdministracion = new PanelAdministracion(this, administrador, sonido);

        contenedor.add(panelMenu, CARTA_MENU);
        contenedor.add(panelJuego, CARTA_JUEGO);
        contenedor.add(panelAdministracion, CARTA_ADMIN);

        add(contenedor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(920, 640);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        mostrarMenu();
    }

    public void mostrarMenu() {
        cartas.show(contenedor, CARTA_MENU);
    }
    
    public void mostrarJuego(JuegoAhorcado juego) {
        panelJuego.iniciar(juego);
        cartas.show(contenedor, CARTA_JUEGO);
    }

    public void mostrarAdministracion() {
        panelAdministracion.actualizar();
        cartas.show(contenedor, CARTA_ADMIN);
    }

    public void salir() {
        System.exit(0);
    }
}
