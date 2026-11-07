package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

final class DialogoPalabra {

    private DialogoPalabra() {
    }

    static String pedir(Component padre) {
        JPasswordField campo = new JPasswordField(15);
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 6));
        panel.add(new JLabel("Escriba la palabra secreta (3 a 15 letras)."));
        panel.add(campo);

        SwingUtilities.invokeLater(campo::requestFocusInWindow);

        int opcion = JOptionPane.showConfirmDialog(padre, panel, "Palabra secreta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        char[] caracteres = campo.getPassword();
        String palabra = (opcion == JOptionPane.OK_OPTION) ? new String(caracteres) : null;
        Arrays.fill(caracteres, ' ');

        return palabra;
    }
}

