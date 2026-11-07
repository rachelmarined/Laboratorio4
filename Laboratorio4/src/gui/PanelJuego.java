/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import excepciones.AhorcadoException;
import excepciones.PalabraInvalidaException;
import gui.sonido.GestorSonido;
import modelo.EstadoPartida;
import modelo.JuegoAhorcado;
import modelo.JuegoPalabraFija;
import java.awt.*;
import javax.swing.*;

public class PanelJuego {
    private static final long serialVersionUID = 1L;
 
    private final VentanaPrincipal ventana;
    private JuegoAhorcado juego;

    private final PanelDibujo panelDibujo = new PanelDibujo();

    private final JLabel lblTitulo = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblPalabra = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblIntentos = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblErradas = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblMensaje = new JLabel(" ", SwingConstants.CENTER);

    private final JTextField txtLetra = new JTextField(3);
    private final JButton btnProbar = new JButton("Probar");

    private final JButton btnNueva = new JButton("Jugar de nuevo");
    private final JButton btnMenu = new JButton("Volver al menú");

    public PanelJuego(VentanaPrincipal ventana, GestorSonido sonido) {
        this.ventana = ventana;
 
        setLayout(new BorderLayout());

        add(lblTitulo, BorderLayout.NORTH);
 
        add(panelDibujo, BorderLayout.WEST);

        JPanel centro = new JPanel(new GridLayout(6, 1));
        centro.add(lblPalabra);
        centro.add(lblIntentos);
        centro.add(lblErradas);
 
        JPanel filaLetra = new JPanel(new FlowLayout());
        filaLetra.add(new JLabel("Letra:"));
        filaLetra.add(txtLetra);
        filaLetra.add(btnProbar);
        centro.add(filaLetra);
 
        centro.add(lblMensaje);
        add(centro, BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout());
        abajo.add(btnNueva);
        abajo.add(btnMenu);
        add(abajo, BorderLayout.SOUTH);

        btnProbar.addActionListener(e -> probarLetra());
        txtLetra.addActionListener(e -> probarLetra());
        btnNueva.addActionListener(e -> jugarDeNuevo());
        btnMenu.addActionListener(e -> ventana.mostrarMenu());
    }

    public void iniciar(JuegoAhorcado juego) {
        this.juego = juego;
        lblTitulo.setText(juego.getModalidad().getTexto());
        lblMensaje.setText("Escriba una letra y presione Probar.");
        actualizarPantalla();
    }
 
    private void probarLetra() {
        try {
            juego.ingresarLetra(txtLetra.getText());
            lblMensaje.setText(" ");
        } catch (AhorcadoException ex) {
            lblMensaje.setText(ex.getMessage());
        }
        txtLetra.setText("");
        txtLetra.requestFocusInWindow();
        actualizarPantalla();
    }
 
    private void jugarDeNuevo() {
        try {
            if (juego instanceof JuegoPalabraFija) {
                String nueva = DialogoPalabra.pedir(this);
                if (nueva == null) {
                    return;
                }
                ((JuegoPalabraFija) juego).cambiarPalabra(nueva);
            }
            juego.reiniciar();
            lblMensaje.setText("Escriba una letra y presione Probar.");
            actualizarPantalla();
        } catch (PalabraInvalidaException ex) {
            lblMensaje.setText(ex.getMessage());
        }
    }

    private void actualizarPantalla() {
        lblPalabra.setText(juego.getPalabraMostrada());
        lblIntentos.setText("Intentos restantes: " + juego.getIntentosRestantes());
        lblErradas.setText("Letras erradas: " + juego.getLetrasErradas());
        panelDibujo.setEtapa(juego.getEtapa());
 
        if (juego.getEstado() == EstadoPartida.GANADA) {
            lblMensaje.setText("¡Ganaste!");
        } else if (juego.getEstado() == EstadoPartida.PERDIDA) {
            lblMensaje.setText("Perdiste. La palabra era " + juego.getPalabraSecreta());
        }
 
        boolean enCurso = juego.getEstado() == EstadoPartida.EN_CURSO;
        txtLetra.setEnabled(enCurso);
        btnProbar.setEnabled(enCurso);
    }
}
