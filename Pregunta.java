/**
 * Una pregunta de opcion multiple de la trivia.
 * Tiene el enunciado, 4 opciones, cual es la correcta (de 1 a 4)
 * y su dificultad (1 = Facil, 2 = Medio, 3 = Dificil).
 */
public class Pregunta {
    private String enunciado;
    private String[] opciones;
    private int respuestaCorrecta;   // numero de opcion, de 1 a 4
    private int dificultad;          // 1, 2 o 3

    public Pregunta(String enunciado, String[] opciones, int respuestaCorrecta, int dificultad) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.dificultad = dificultad;
    }

    // ---------- GETTERS ----------

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public int getDificultad() {
        return dificultad;
    }

    // ---------- SETTERS ----------

    public void setEnunciado(String enunciado) {
        if (enunciado != null && !enunciado.isEmpty()) {
            this.enunciado = enunciado;
        }
    }

    public void setOpciones(String[] opciones) {
        if (opciones != null && opciones.length == 4) {
            this.opciones = opciones;
        }
    }

    // Solo acepta un numero de opcion que exista (1 a 4)
    public void setRespuestaCorrecta(int respuestaCorrecta) {
        if (respuestaCorrecta >= 1 && respuestaCorrecta <= 4) {
            this.respuestaCorrecta = respuestaCorrecta;
        }
    }

    // Solo acepta 1 (Facil), 2 (Medio) o 3 (Dificil)
    public void setDificultad(int dificultad) {
        if (dificultad >= 1 && dificultad <= 3) {
            this.dificultad = dificultad;
        }
    }

    // ---------- METODOS DE LA PREGUNTA ----------

    // Compara lo que eligio el jugador con la respuesta correcta
    public boolean esCorrecta(int opcion) {
        return opcion == respuestaCorrecta;
    }

    // Devuelve el texto de la opcion correcta (para mostrarlo si el jugador falla)
    public String getTextoCorrecto() {
        return opciones[respuestaCorrecta - 1];
    }

    // Imprime el enunciado y las 4 opciones numeradas
    public void mostrar() {
        System.out.println(enunciado);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ") " + opciones[i]);
        }
    }
}
