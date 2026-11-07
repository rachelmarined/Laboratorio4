
package modelo;

import excepciones.PalabraInvalidaException; 
import java.util.Objects;

public class JuegoAlAzar extends JuegoBase{
    
    private static final long serialVersionUID = 1L;
    private final AdministradorPalabras administrador;

    public JuegoAlAzar(AdministradorPalabras administrador) throws PalabraInvalidaException {
        super();
        this.administrador = Objects.requireNonNull(administrador, "administrador");
        reiniciar();
    }

    @Override
    protected String elegirPalabra() {
        return administrador.obtenerAleatoria();
    }

    @Override
    public Modalidad getModalidad() {
        return Modalidad.AL_AZAR;
    }
}
