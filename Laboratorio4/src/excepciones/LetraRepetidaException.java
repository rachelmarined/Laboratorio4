/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author XPS 13
 */
public class LetraRepetidaException extends AhorcadoException {
    private static final long serialVersionUID = 1L;
    private final char letra;

    public LetraRepetidaException(String mensaje, char letra) {
        super(mensaje);
        this.letra = letra;
    }

    public char getLetra() {
        return letra;
    }
}
