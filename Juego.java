import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de la logica: maneja la partida completa.
 * - banco: ArrayList con las preguntas del juego.
 * - preguntasDelNivel: las del nivel elegido, que se encuentran con la busqueda lineal.
 * - turnos: Queue (cola) con el orden de los jugadores. El que juega sale
 *   de adelante (poll) y despues vuelve al final (offer).
 */
public class Juego {
    private ArrayList<Pregunta> banco;             // por ahora 30 preguntas (10 por nivel)
    private ArrayList<Pregunta> preguntasDelNivel; // solo las del nivel elegido
    private Queue<Jugador> turnos;                 // el orden de los turnos
    private Jugador jugador1;
    private Jugador jugador2;
    private int dificultad;
    private int preguntaActual;                    // cuantas preguntas se usaron
    private Scanner sc;
    private Random random;

    public Juego(Jugador jugador1, Jugador jugador2, int dificultad, Scanner sc) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.dificultad = dificultad;
        this.sc = sc;
        this.random = new Random();
        this.banco = new ArrayList<>();
        this.preguntasDelNivel = new ArrayList<>();
        this.turnos = new LinkedList<>();
        this.preguntaActual = 0;
        cargarPreguntas();
        buscarPreguntasDelNivel();
        mezclarPreguntas();
    }

    // ================= PARTIDA COMPLETA =================

    // Orden de la partida: sorteo -> 8 preguntas -> marcador -> resultado
    public void jugar() {
        sortearQuienEmpieza();
        jugarPartidaNormal();
        mostrarMarcador();

        System.out.println();
        if (jugador1.getPuntaje() > jugador2.getPuntaje()) {
            System.out.println("GANADOR: " + jugador1.getNombre());
        } else if (jugador2.getPuntaje() > jugador1.getPuntaje()) {
            System.out.println("GANADOR: " + jugador2.getNombre());
        } else {
            System.out.println("EMPATE! (despues vamos a hacer el desempate)");
        }
    }

    // Se cargan los dos jugadores en la cola en orden aleatorio
    private void sortearQuienEmpieza() {
        System.out.println();
        System.out.println("Sorteando quien empieza...");
        if (random.nextInt(2) == 0) {
            turnos.offer(jugador1);
            turnos.offer(jugador2);
        } else {
            turnos.offer(jugador2);
            turnos.offer(jugador1);
        }
        System.out.println("Empieza " + turnos.peek().getNombre() + "!");
    }

    // 8 preguntas en total, 4 para cada jugador, alternando
    private void jugarPartidaNormal() {
        System.out.println();
        System.out.println("========== PARTIDA ==========");
        for (int i = 1; i <= 8; i++) {
            Jugador actual = turnos.poll();
            Pregunta pregunta = preguntasDelNivel.get(preguntaActual);
            preguntaActual++;

            System.out.println();
            System.out.println("--- Pregunta " + i + " de 8 para " + actual.getNombre() + " ---");
            if (hacerPregunta(pregunta)) {
                actual.sumarPunto();
            }
            turnos.offer(actual);   // vuelve al final de la fila
        }
    }

    // ================= PREGUNTAS Y RESPUESTAS =================

    // Devuelve true si el jugador acerto
    private boolean hacerPregunta(Pregunta pregunta) {
        pregunta.mostrar();
        int opcion = leerNumero(1, 4);
        if (pregunta.esCorrecta(opcion)) {
            System.out.println("Correcto!");
            return true;
        } else {
            System.out.println("Incorrecto. La respuesta era: " + pregunta.getTextoCorrecto());
            return false;
        }
    }

    // Pide un numero hasta que sea valido, sin que el programa se corte
    private int leerNumero(int minimo, int maximo) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Tu respuesta (" + minimo + "-" + maximo + "): ");
                numero = sc.nextInt();
                sc.nextLine();
                if (numero < minimo || numero > maximo) {
                    System.out.println("Esa opcion no existe.");
                } else {
                    valido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: tenes que escribir un numero.");
                sc.nextLine();
            }
        }
        return numero;
    }

    // ================= FINAL DEL JUEGO =================

    private void mostrarMarcador() {
        System.out.println();
        System.out.println("========== MARCADOR ==========");
        System.out.println("Puntos de " + jugador1.getNombre() + ": " + jugador1.getPuntaje());
        System.out.println("Puntos de " + jugador2.getNombre() + ": " + jugador2.getPuntaje());
    }

    // ================= PREPARACION =================

    // Busqueda lineal: recorre todo el banco y se queda con las del nivel elegido
    private void buscarPreguntasDelNivel() {
        for (Pregunta p : banco) {
            if (p.getDificultad() == dificultad) {
                preguntasDelNivel.add(p);
            }
        }
    }

    // Mezcla al azar: intercambia cada pregunta con otra de posicion aleatoria
    private void mezclarPreguntas() {
        for (int i = preguntasDelNivel.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Pregunta aux = preguntasDelNivel.get(i);
            preguntasDelNivel.set(i, preguntasDelNivel.get(j));
            preguntasDelNivel.set(j, aux);
        }
    }

    // Banco fijo de preguntas: 1 = Facil, 2 = Medio, 3 = Dificil (por ahora 10 por nivel, faltan agregar)
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
        banco.add(new Pregunta("¿Qué países formaron la Triple Alianza contra el Paraguay?",
                new String[]{"Bolivia, Chile y Perú", "Brasil, Argentina y Uruguay",
                        "Brasil, Bolivia y Argentina", "Argentina, Chile y Uruguay"}, 2, 1));
        banco.add(new Pregunta("¿Qué tiene de único la bandera paraguaya?",
                new String[]{"Tiene un sol en el centro", "Es cuadrada",
                        "Tiene un escudo distinto en cada cara", "Tiene cinco franjas"}, 3, 1));
        banco.add(new Pregunta("¿Cómo se llama la moneda del Paraguay, creada en 1943?",
                new String[]{"Peso", "Guaraní", "Real", "Sol"}, 2, 1));
        banco.add(new Pregunta("¿Con qué país comparte el Paraguay la represa de Itaipú?",
                new String[]{"Argentina", "Bolivia", "Uruguay", "Brasil"}, 4, 1));
        banco.add(new Pregunta("¿Cuántas Copas América ganó la selección paraguaya?",
                new String[]{"Ninguna", "Una", "Dos", "Cinco"}, 3, 1));
        banco.add(new Pregunta("¿De qué nacionalidad era el autor de la letra del himno paraguayo?",
                new String[]{"Paraguayo", "Argentino", "Uruguayo", "Español"}, 3, 1));

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
        banco.add(new Pregunta("¿En qué lugar terminó la Guerra de la Triple Alianza, en 1870?",
                new String[]{"Acosta Ñu", "Curupayty", "Cerro Corá", "Humaitá"}, 3, 2));
        banco.add(new Pregunta("¿Qué se conmemora en el Paraguay cada 1 de marzo?",
                new String[]{"La Independencia", "El Día del Niño",
                        "La Paz del Chaco", "El Día de los Héroes"}, 4, 2));
        banco.add(new Pregunta("¿Qué batalla de 1869 se recuerda cada 16 de agosto, Día del Niño?",
                new String[]{"Cerro Corá", "Acosta Ñu", "Boquerón", "Tuyutí"}, 2, 2));
        banco.add(new Pregunta("¿Qué moneda reemplazó el guaraní en 1943?",
                new String[]{"El real", "El dólar", "El peso paraguayo", "El austral"}, 3, 2));
        banco.add(new Pregunta("¿Quién fue el primer presidente constitucional del Paraguay?",
                new String[]{"Francisco Solano López", "Carlos Antonio López",
                        "José Gaspar Rodríguez de Francia", "Fulgencio Yegros"}, 2, 2));
        banco.add(new Pregunta("¿En qué año se inauguró el primer ferrocarril del Paraguay?",
                new String[]{"1861", "1910", "1811", "1935"}, 1, 2));

        // ---------- NIVEL DIFICIL ----------
        banco.add(new Pregunta("¿Cuál fue la primera batalla de la Guerra del Chaco, en 1932?",
                new String[]{"Nanawa", "Boquerón", "Tuyutí", "Cerro Corá"}, 2, 3));
        banco.add(new Pregunta("¿Cuál fue la mayor victoria paraguaya en la Guerra de la Triple Alianza, en 1866?",
                new String[]{"Cerro Corá", "Acosta Ñu", "Boquerón", "Curupayty"}, 4, 3));
        banco.add(new Pregunta("¿Qué se conmemora en el Paraguay cada 12 de junio?",
                new String[]{"La Paz del Chaco", "El Día de los Héroes",
                        "La Independencia", "El Día del Niño"}, 1, 3));
        banco.add(new Pregunta("¿En qué ciudad se firmaron la paz de 1935 y el tratado de 1938 con Bolivia?",
                new String[]{"Asunción", "La Paz", "Montevideo", "Buenos Aires"}, 4, 3));
        banco.add(new Pregunta("¿En qué año se firmó el tratado secreto de la Triple Alianza?",
                new String[]{"1864", "1865", "1870", "1811"}, 2, 3));
        banco.add(new Pregunta("¿Qué periódico se fundó en 1845, durante el gobierno de Carlos Antonio López?",
                new String[]{"ABC Color", "La Nación", "El Paraguayo Independiente", "Última Hora"}, 3, 3));
        banco.add(new Pregunta("¿Qué presidente creó el guaraní como moneda, en 1943?",
                new String[]{"Higinio Morínigo", "Eusebio Ayala",
                        "Alfredo Stroessner", "Carlos Antonio López"}, 1, 3));
        banco.add(new Pregunta("¿En qué año empezó Itaipú a producir energía de forma comercial?",
                new String[]{"1973", "1985", "1999", "2005"}, 2, 3));
        banco.add(new Pregunta("¿Qué tenía de único en América el ferrocarril paraguayo de 1861?",
                new String[]{"Era subterráneo", "Era eléctrico",
                        "Se hizo solo con capital del Estado", "Llegaba hasta el océano"}, 3, 3));
        banco.add(new Pregunta("¿Qué representa el color blanco de la bandera paraguaya?",
                new String[]{"La paz", "La justicia", "La libertad", "La religión"}, 1, 3));
    }
}
