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
import modelo.Modalidad;
import modelo.JuegoBase;
public class JuegoPalabraFija extends JuegoBase{
    private static final long serialVersionUID = 1L;
    private String palabraConfigurada;
    public JuegoPalabraFija(String palabra) throws PalabraInvalidaException {
        super();
        establecerPalabraSecreta(palabra);
        this.palabraConfigurada = palabraSecreta;
    }
    public void cambiarPalabra(String palabra) throws PalabraInvalidaException {
        this.palabraConfigurada = UtilTexto.validarPalabra(palabra);
    }
    @Override
    protected String elegirPalabra() {
        return palabraConfigurada;
    }
    @Override
    public Modalidad getModalidad() {
        return Modalidad.PALABRA_FIJA;
    }
}
