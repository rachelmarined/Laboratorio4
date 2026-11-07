/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.sonido;

/**
 *
 * @author marlo
 */
public enum EfectoSonido {
    ACIERTO(new double[] {880}, new int[] {120}),
    ERROR(new double[] {220}, new int[] {250}),
    ALERTA(new double[] {440, 0, 440}, new int[] {80, 60, 80}),
    VICTORIA(new double[] {523, 659, 784}, new int[] {150, 150, 300}),
    DERROTA(new double[] {392, 330, 262}, new int[] {200, 200, 400});
    private final double[] frecuencias;
    private final int[] duracionesMs;
    EfectoSonido(double frecuencias[], int duracionesMs[]) {
        this.frecuencias=frecuencias;
        this.duracionesMs=duracionesMs;
    }
    public double[] getFrecuencias(){
        return frecuencias.clone();
    }
    public int[] getDuracionesMs(){
        return duracionesMs.clone();
    }
}
