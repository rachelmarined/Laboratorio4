/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author XPS 13
 */
public enum Modalidad {
  PALABRA_FIJA("Palabra fija (2 jugadores)"),
    AL_AZAR("Palabra al azar (1 jugador)");

    private final String texto;

    Modalidad(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
    
}
