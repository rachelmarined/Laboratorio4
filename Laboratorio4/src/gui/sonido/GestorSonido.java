/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.sonido;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GestorSonido{
    private static final float FRECUENCIA_MUESTREO=44100f;
    private static final double AMPLITUD=0.35 * Short.MAX_VALUE;
    private static final int MUESTRAS_SUAVIZADO=220; 
    private final AudioFormat formato=new AudioFormat(FRECUENCIA_MUESTREO, 16, 1, true, false);
    private final ExecutorService hiloAudio=Executors.newSingleThreadExecutor(tarea -> {
        Thread hilo=new Thread(tarea, "hilo-sonido");
        hilo.setDaemon(true);
        return hilo;
    });
    private volatile boolean silenciado;
    private volatile boolean disponible;
    private volatile Runnable alDesactivarse;
    public GestorSonido(){
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, formato);
        disponible = AudioSystem.isLineSupported(info);
    }
    public void reproducir(EfectoSonido efecto){
        if (silenciado||!disponible){
            return;
        }
        hiloAudio.execute(() -> reproducirBytes(generarPcm(efecto)));
    }
    public void setSilenciado(boolean silenciado){
        this.silenciado = silenciado;
    }
    public boolean isSilenciado(){
        return silenciado;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setAlDesactivarse(Runnable accion){
        this.alDesactivarse = accion;
    }
    private void reproducirBytes(byte[] datos) {
        try (SourceDataLine linea=AudioSystem.getSourceDataLine(formato)){
            linea.open(formato);
            linea.start();
            linea.write(datos, 0, datos.length);
            linea.drain();
        }
        catch (LineUnavailableException|IllegalArgumentException ex){
            desactivar();
        }
    }
    private void desactivar(){
        disponible = false;
        Runnable accion = alDesactivarse;
        if (accion != null) {
            SwingUtilities.invokeLater(accion);
        }
    }
    private byte[] generarPcm(EfectoSonido efecto) {
        double frecuencias[] = efecto.getFrecuencias();
        int duraciones []= efecto.getDuracionesMs();
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        for (int nota = 0; nota < frecuencias.length; nota++) {
            int muestras=(int)(FRECUENCIA_MUESTREO*duraciones[nota]/1000);
            for (int i = 0; i < muestras; i++) {
                double envolvente= Math.min(1.0,Math.min(i, muestras-i)/(double)MUESTRAS_SUAVIZADO);
                double onda =frecuencias[nota] <= 0 ? 0 : Math.sin(2*Math.PI* frecuencias[nota] *i/FRECUENCIA_MUESTREO);
                short valor=(short)(onda*envolvente*AMPLITUD);
                salida.write(valor & 0xFF);       
                salida.write((valor >> 8) & 0xFF); 
            }
        }
        return salida.toByteArray();
    }
}
