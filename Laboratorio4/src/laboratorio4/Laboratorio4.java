package laboratorio4;

import gui.VentanaPrincipal;
import javax.swing.SwingUtilities;
import modelo.AdministradorPalabras;

/**
 *
 * @author XPS 13
 */
public class Laboratorio4 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdministradorPalabras administrador = new AdministradorPalabras();
            VentanaPrincipal ventana = new VentanaPrincipal(administrador);
            ventana.setVisible(true);
        });
    }
}
