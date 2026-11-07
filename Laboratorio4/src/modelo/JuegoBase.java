/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import excepciones.LetraInvalidaException;
import excepciones.LetraRepetidaException;
import excepciones.PalabraInvalidaException;
import excepciones.PartidaTerminadaException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class JuegoBase implements JuegoAhorcado, Serializable{
    private static final long serialVersionUID = 1L;
    public static final int MAX_INTENTOS = 6;
    protected String palabraSecreta;
    protected char[] palabraMostrada;
    protected int intentosRestantes;
    protected List<Character> letrasAcertadas;
    protected List<Character> letrasErradas;
    protected EtapaAhorcado etapa;
    protected EstadoPartida estado;
    protected JuegoBase() {
        palabraSecreta = "";
        palabraMostrada = new char[0];
        intentosRestantes = MAX_INTENTOS;
        letrasAcertadas = new ArrayList<>();
        letrasErradas = new ArrayList<>();
        etapa = EtapaAhorcado.VACIO;
        estado = EstadoPartida.EN_CURSO;
    }
    protected abstract String elegirPalabra() throws PalabraInvalidaException;
    @Override
    public void establecerPalabraSecreta(String palabra) throws PalabraInvalidaException{
        String normalizada = UtilTexto.validarPalabra(palabra);
        palabraSecreta = normalizada;
        palabraMostrada = new char[normalizada.length()];
        Arrays.fill(palabraMostrada, '_');
        intentosRestantes = MAX_INTENTOS;
        letrasAcertadas = new ArrayList<>();
        letrasErradas = new ArrayList<>();
        etapa = EtapaAhorcado.VACIO;
        estado = EstadoPartida.EN_CURSO;
    }
    @Override
    public void reiniciar() throws PalabraInvalidaException{
        establecerPalabraSecreta(elegirPalabra());
    }
    @Override
    public ResultadoLetra ingresarLetra(String entrada)
    throws LetraInvalidaException, LetraRepetidaException, PartidaTerminadaException {
        if (estado != EstadoPartida.EN_CURSO) {
            throw new PartidaTerminadaException("La partida ya termino, Presione «Jugar de nuevo»");
        }
        if (entrada==null||entrada.length()!= 1||!Character.isLetter(entrada.charAt(0))) {
            throw new LetraInvalidaException("Ingrese una unica letra (A–Z o Ñ)");
        }
        String normalizada = UtilTexto.normalizar(entrada);
        if (normalizada.length() != 1 || !UtilTexto.esLetra(normalizada.charAt(0))) {
            throw new LetraInvalidaException("Ingrese una única letra (A–Z o Ñ)");
        }
        char letra = normalizada.charAt(0);
        if (letrasAcertadas.contains(letra) || letrasErradas.contains(letra)) {
            throw new LetraRepetidaException(
            "La letra " + letra + " ya fue ingresada, Pruebe con otra", letra);
        }  
        ResultadoLetra resultado;
        if (palabraSecreta.indexOf(letra)>=0) {
            letrasAcertadas.add(letra);
            revelarLetra(letra, 0);
            resultado = ResultadoLetra.ACIERTO;
        }
        else{
            letrasErradas.add(letra);
            intentosRestantes--;
            etapa = EtapaAhorcado.dePorErrores(letrasErradas.size());
            resultado = ResultadoLetra.ERROR;
        }
        if (haGanado(0)){
            estado = EstadoPartida.GANADA;
        } 
        else if (haPerdido()){
            estado = EstadoPartida.PERDIDA;
        }
            return resultado;
    }
    protected void revelarLetra(char letra, int posicion){
        if (posicion >=palabraSecreta.length()){
            return;
        }
        if (palabraSecreta.charAt(posicion)==letra){
            palabraMostrada[posicion] = letra;
        }
        revelarLetra(letra, posicion + 1);
    }
    protected boolean haGanado(int posicion) {
        if (posicion >= palabraSecreta.length()){
            return true;
        }
        if (palabraMostrada[posicion]!=palabraSecreta.charAt(posicion)){
            return false;
        }
        return haGanado(posicion+ 1);
    }
    protected boolean haPerdido() {
        return intentosRestantes<= 0;
    }
    @Override
    public String getPalabraMostrada(){
        return String.valueOf(palabraMostrada);
    }
    @Override
    public int getIntentosRestantes(){
        return intentosRestantes;
    }
    @Override
    public List<Character> getLetrasAcertadas(){
        return Collections.unmodifiableList(new ArrayList<>(letrasAcertadas));
    }
    @Override
    public List<Character> getLetrasErradas(){
        return Collections.unmodifiableList(new ArrayList<>(letrasErradas));
    }
    @Override
    public EtapaAhorcado getEtapa(){
        return etapa;
    }
    @Override
    public EstadoPartida getEstado(){
        return estado;
    }
    @Override
    public String getPalabraSecreta(){
        return palabraSecreta;
    }
}
