/**
 * Representa a uno de los dos jugadores de la trivia.
 * Guarda su nombre, cuantos puntos lleva y cuantas vidas le quedan
 * (las vidas solo se usan en la muerte subita).
 */
public class Jugador {
    private String nombre;
    private int puntaje;
    private int vidas;

    // Todo jugador empieza con 0 puntos y 2 vidas
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.vidas = 2;
    }

    // ---------- GETTERS ----------

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getVidas() {
        return vidas;
    }

    // ---------- SETTERS ----------
    // Validan los datos para que el objeto nunca quede en un estado raro

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public void setPuntaje(int puntaje) {
        if (puntaje >= 0) {
            this.puntaje = puntaje;
        }
    }

    public void setVidas(int vidas) {
        if (vidas >= 0) {
            this.vidas = vidas;
        }
    }

    // ---------- ACCIONES DEL JUEGO ----------

    // Cada acierto vale siempre 1 punto, por eso no recibe parametro
    public void sumarPunto() {
        puntaje++;
    }

    // Solo se usa en la muerte subita. Nunca baja de 0
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }
}
