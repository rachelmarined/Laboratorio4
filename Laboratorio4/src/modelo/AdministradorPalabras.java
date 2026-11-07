package modelo;
import excepciones.PalabraDuplicadaException;
import excepciones.PalabraInvalidaException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdministradorPalabras implements Serializable {
private static final long serialVersionUID = 1L;

    private static final String[] PALABRAS_INICIALES = {
        "árbol", "java", "herencia", "interfaz", "excepción", "recursividad",
        "polimorfismo", "teclado", "pantalla", "montaña", "español", "compilador"
    };

    private final List<String> palabras = new ArrayList<>();
   
    private transient Random random = new Random();

    public AdministradorPalabras() {
        for (String palabra : PALABRAS_INICIALES) {
            palabras.add(UtilTexto.normalizar(palabra));
        }
    }

    public void agregar(String palabra) throws PalabraInvalidaException, PalabraDuplicadaException {
        String normalizada = UtilTexto.validarPalabra(palabra);
        if (existe(normalizada, 0)) {
            throw new PalabraDuplicadaException("La palabra \"" + normalizada + "\" ya existe en la lista.");
        }
        palabras.add(normalizada);
    }

    public String obtenerAleatoria() {
        if (random == null) {
            random = new Random();
        }
        return palabras.get(random.nextInt(palabras.size()));
    }

   
    private boolean existe(String palabra, int indice) {
        if (indice >= palabras.size()) {
            return false;
        }
        if (palabras.get(indice).equals(palabra)) {
            return true;
        }
        return existe(palabra, indice + 1);
    }

    public int cantidad() {
        return palabras.size();
    }

    public List<String> getPalabras() {
        return Collections.unmodifiableList(new ArrayList<>(palabras));
    }

}
