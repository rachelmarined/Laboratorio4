/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
import excepciones.PalabraInvalidaException;
import java.util.Objects;
import modelo.Modalidad;
import modelo.JuegoBase;
public class JugadorAlAzar extends JuegoBase{
    private static final long serialVersionUID = 1L;
 private final AdministradorPalabras administrador;
    public JugadorAlAzar(AdministradorPalabras administrador) throws PalabraInvalidaException {
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
