/**
 * Excepcion propia del juego.
 * Se lanza cuando el jugador escribe un numero de opcion que no existe
 * (por ejemplo 7 cuando solo hay opciones del 1 al 4).
 * Extiende de Exception, asi que es "checked": quien la lanza tiene que
 * declararla con throws y quien la usa tiene que atraparla con catch.
 */
public class OpcionInvalidaException extends Exception {
    public OpcionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
