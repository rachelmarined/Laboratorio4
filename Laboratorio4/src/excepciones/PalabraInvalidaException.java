/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author XPS 13
 */
public class PalabraInvalidaException extends AhorcadoException {
    private static final long serialVersionUID = 1L;
    public static final String MENSAJE_ESTANDAR = 
        "La palabra debe tener entre 3 y 15 letras, sin espacios ni números.";

    public PalabraInvalidaException() {
        this(MENSAJE_ESTANDAR);
    }

    public PalabraInvalidaException(String mensaje) {
        super(mensaje);
    }
}
