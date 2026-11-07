package gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import modelo.EtapaAhorcado;

public class PanelDibujo extends JPanel {
private static final long serialVersionUID = 1L;
    private static final double ANCHO_VIRTUAL = 200.0;
    private static final double ALTO_VIRTUAL = 240.0;

    private EtapaAhorcado etapa = EtapaAhorcado.VACIO;

    public PanelDibujo() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(260, 300));
    }

    public void setEtapa(EtapaAhorcado etapa) {
        this.etapa = etapa;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       
        double escala = Math.min(getWidth() / ANCHO_VIRTUAL, getHeight() / ALTO_VIRTUAL);
        g2.translate((getWidth() - ANCHO_VIRTUAL * escala) / 2,
                (getHeight() - ALTO_VIRTUAL * escala) / 2);
        g2.scale(escala, escala);

        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2.setColor(new Color(110, 70, 30));
        g2.drawLine(20, 225, 140, 225); 
        g2.drawLine(50, 225, 50, 20);  
        g2.drawLine(50, 20, 130, 20);   
        g2.drawLine(50, 55, 85, 20);    
        g2.drawLine(130, 20, 130, 50);  

       
        g2.setColor(Color.DARK_GRAY);
        if (alcanzada(EtapaAhorcado.CABEZA)) {
            g2.drawOval(110, 50, 40, 40);
        }
        if (alcanzada(EtapaAhorcado.TRONCO)) {
            g2.drawLine(130, 90, 130, 150);
        }
        if (alcanzada(EtapaAhorcado.BRAZO_IZQ)) {
            g2.drawLine(130, 105, 105, 135);
        }
        if (alcanzada(EtapaAhorcado.BRAZO_DER)) {
            g2.drawLine(130, 105, 155, 135);
        }
        if (alcanzada(EtapaAhorcado.PIERNA_IZQ)) {
            g2.drawLine(130, 150, 108, 195);
        }
        if (alcanzada(EtapaAhorcado.PIERNA_DER)) {
            g2.drawLine(130, 150, 152, 195);
        }

        g2.dispose();
    }

    private boolean alcanzada(EtapaAhorcado parte) {
        return etapa.ordinal() >= parte.ordinal();
    }
}