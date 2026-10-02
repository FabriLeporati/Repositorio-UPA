import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que va a manejar la partida.
 * Por ahora (dia 2) solo carga el banco de preguntas y hace UNA pregunta
 * de prueba, para ver que la clase Pregunta funciona bien.
 * Los turnos, la busqueda por nivel y el puntaje van en el sprint 2.
 */
public class Juego {
    private ArrayList<Pregunta> banco;   // todas las preguntas del juego
    private Jugador jugador1;
    private Jugador jugador2;
    private int dificultad;
    private Scanner sc;

    public Juego(Jugador jugador1, Jugador jugador2, int dificultad, Scanner sc) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.dificultad = dificultad;
        this.sc = sc;
        this.banco = new ArrayList<>();
        cargarPreguntas();
    }

    // PRUEBA: muestra la primera pregunta del banco y dice si acertaste
    public void jugar() {
        System.out.println();
        System.out.println("Preguntas cargadas: " + banco.size());
        System.out.println("Jugadores: " + jugador1.getNombre() + " y " + jugador2.getNombre()
                + " / Nivel elegido: " + dificultad);
        System.out.println();

        Pregunta prueba = banco.get(0);
        prueba.mostrar();
        System.out.print("Tu respuesta (1-4): ");
        int opcion = sc.nextInt();
        sc.nextLine();
        if (prueba.esCorrecta(opcion)) {
            System.out.println("Correcto!");
            jugador1.sumarPunto();
        } else {
            System.out.println("Incorrecto. La respuesta era: " + prueba.getTextoCorrecto());
        }
        System.out.println("Puntos de " + jugador1.getNombre() + ": " + jugador1.getPuntaje());
        System.out.println();
        System.out.println("(La partida completa todavia esta en construccion)");
    }

    // Banco fijo de preguntas: 1 = Facil, 2 = Medio, 3 = Dificil
    // Por ahora 4 por nivel, despues agregamos mas
    private void cargarPreguntas() {
        // ---------- NIVEL FACIL ----------
        banco.add(new Pregunta("¿En qué año se independizó el Paraguay?",
                new String[]{"1810", "1811", "1816", "1870"}, 2, 1));
        banco.add(new Pregunta("¿En qué días de mayo ocurrió la independencia?",
                new String[]{"1 y 2", "25 y 26", "14 y 15", "20 y 21"}, 3, 1));
        banco.add(new Pregunta("¿Qué otro día especial se celebra el 15 de mayo, junto con la independencia?",
                new String[]{"Día de la Madre", "Día del Niño", "Día del Maestro", "Día de la Amistad"}, 1, 1));
        banco.add(new Pregunta("¿Contra qué país fue la Guerra del Chaco?",
                new String[]{"Brasil", "Argentina", "Uruguay", "Bolivia"}, 4, 1));

        // ---------- NIVEL MEDIO ----------
        banco.add(new Pregunta("¿Quién fue el comandante del ejército paraguayo en la Guerra del Chaco?",
                new String[]{"José Félix Estigarribia", "Bernardino Caballero",
                        "Fulgencio Yegros", "Eusebio Ayala"}, 1, 2));
        banco.add(new Pregunta("¿Quién era el presidente del Paraguay durante la Guerra del Chaco?",
                new String[]{"Higinio Morínigo", "Carlos Antonio López",
                        "Eusebio Ayala", "Francisco Solano López"}, 3, 2));
        banco.add(new Pregunta("¿En qué año se fundó Asunción?",
                new String[]{"1492", "1811", "1600", "1537"}, 4, 2));
        banco.add(new Pregunta("¿En qué años fue la Guerra de la Triple Alianza?",
                new String[]{"1864-1870", "1811-1816", "1932-1935", "1879-1884"}, 1, 2));

        // ---------- NIVEL DIFICIL ----------
        banco.add(new Pregunta("¿Cuál fue la primera gran batalla de la Guerra del Chaco, en 1932?",
                new String[]{"Nanawa", "Boquerón", "Tuyutí", "Cerro Corá"}, 2, 3));
        banco.add(new Pregunta("¿Cuál fue la mayor victoria paraguaya en la Guerra de la Triple Alianza, en 1866?",
                new String[]{"Cerro Corá", "Acosta Ñu", "Boquerón", "Curupayty"}, 4, 3));
        banco.add(new Pregunta("¿Qué se conmemora en el Paraguay cada 12 de junio?",
                new String[]{"La Paz del Chaco", "El Día de los Héroes",
                        "La Independencia", "El Día del Niño"}, 1, 3));
        banco.add(new Pregunta("¿En qué ciudad se firmaron la paz de 1935 y el tratado de 1938 con Bolivia?",
                new String[]{"Asunción", "La Paz", "Montevideo", "Buenos Aires"}, 4, 3));
    }
}
