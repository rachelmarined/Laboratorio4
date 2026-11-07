/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author XPS 13
 */
import excepciones.PalabraInvalidaException;
import java.text.Normalizer;
import java.util.Locale;
public class UtilTexto {
  public static final int MIN_LETRAS = 3;
    public static final int MAX_LETRAS = 15;

    
    private static final char MARCADOR_ENIE = '\u0001';

    private UtilTexto() {
    }

    
    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String s = Normalizer.normalize(texto, Normalizer.Form.NFC).toUpperCase(Locale.ROOT);
        s = s.replace('Ñ', MARCADOR_ENIE);
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        return s.replace(MARCADOR_ENIE, 'Ñ');
    }

   
    public static boolean esLetra(char c) {
        return (c >= 'A' && c <= 'Z') || c == 'Ñ';
    }

   
    public static String validarPalabra(String palabra) throws PalabraInvalidaException {
        if (palabra == null) {
            throw new PalabraInvalidaException();
        }
        String normalizada = normalizar(palabra);
        if (normalizada.length() < MIN_LETRAS || normalizada.length() > MAX_LETRAS) {
            throw new PalabraInvalidaException();
        }
        for (int i = 0; i < normalizada.length(); i++) {
            if (!esLetra(normalizada.charAt(i))) {
                throw new PalabraInvalidaException();
            }
        }
        return normalizada;
    }

    
}
