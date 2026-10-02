import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de la logica: maneja la partida completa.
 * - banco: ArrayList con las 48 preguntas (16 por nivel).
 * - preguntasDelNivel: las 16 que se encuentran con la busqueda lineal.
 * - turnos: Queue (cola) con el orden de los jugadores. El que juega sale
 *   de adelante (poll) y despues vuelve al final (offer).
 */
public class Juego {
    private ArrayList<Pregunta> banco;             // las 48 preguntas del juego
    private ArrayList<Pregunta> preguntasDelNivel; // solo las 16 del nivel elegido
    private Queue<Jugador> turnos;                 // el orden de los turnos
    private ArrayList<String> penitencias;
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
        this.penitencias = new ArrayList<>();
        this.preguntaActual = 0;
        cargarPreguntas();
        cargarPenitencias();
        buscarPreguntasDelNivel();
        mezclarPreguntas();
    }

    // ================= PARTIDA COMPLETA =================

    // Orden de la partida: sorteo -> 8 preguntas -> marcador -> (muerte subita) -> resultado
    public void jugar() {
        sortearQuienEmpieza();
        jugarPartidaNormal();
        mostrarMarcador();

        Jugador ganador;
        Jugador perdedor;

        if (estanParejos()) {
            jugarMuerteSubita();
            if (jugador1.getVidas() == 0) {
                ganador = jugador2;
                perdedor = jugador1;
            } else {
                ganador = jugador1;
                perdedor = jugador2;
            }
        } else if (jugador1.getPuntaje() > jugador2.getPuntaje()) {
            ganador = jugador1;
            perdedor = jugador2;
        } else {
            ganador = jugador2;
            perdedor = jugador1;
        }

        mostrarGanador(ganador, perdedor);
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

    // Empatados o con 1 acierto de diferencia
    private boolean estanParejos() {
        int diferencia = jugador1.getPuntaje() - jugador2.getPuntaje();
        return diferencia >= -1 && diferencia <= 1;
    }

    private void jugarMuerteSubita() {
        System.out.println();
        System.out.println("========== MUERTE SUBITA ==========");
        System.out.println("Estan muy parejos! Cada uno tiene 2 vidas.");
        System.out.println("Un error quita una vida. Pierde el primero que se quede sin vidas.");
        jugador1.setVidas(2);
        jugador2.setVidas(2);


        // Sigue mientras los dos tengan vidas y queden preguntas sin usar
        while (jugador1.getVidas() > 0 && jugador2.getVidas() > 0
                && preguntaActual < preguntasDelNivel.size()) {
            Jugador actual = turnos.poll();
            Pregunta pregunta = preguntasDelNivel.get(preguntaActual);
            preguntaActual++;

            System.out.println();
            System.out.println("--- Turno de " + actual.getNombre()
                    + " (vidas: " + actual.getVidas() + ") ---");
            if (!hacerPregunta(pregunta)) {
                actual.perderVida();
                System.out.println(actual.getNombre() + " pierde una vida. Le quedan: " + actual.getVidas());
            }
            turnos.offer(actual);
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
                validarOpcion(numero, minimo, maximo);
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: tenes que escribir un numero.");
                sc.nextLine();
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        }
        return numero;
    }

    // Si la opcion esta fuera del rango, lanza nuestra excepcion propia
    private void validarOpcion(int opcion, int minimo, int maximo) throws OpcionInvalidaException {
        if (opcion < minimo || opcion > maximo) {
            throw new OpcionInvalidaException("La opcion " + opcion + " no existe. Elegi entre "
                    + minimo + " y " + maximo + ".");
        }
    }

    // ================= FINAL DEL JUEGO =================

    private void mostrarMarcador() {
        System.out.println();
        System.out.println("========== MARCADOR ==========");
        System.out.println("Puntos de " + jugador1.getNombre() + ": " + jugador1.getPuntaje());
        System.out.println("Puntos de " + jugador2.getNombre() + ": " + jugador2.getPuntaje());
    }

    private void mostrarGanador(Jugador ganador, Jugador perdedor) {
        System.out.println();
        System.out.println("========== RESULTADO ==========");
        System.out.println("GANADOR: " + ganador.getNombre());
        System.out.println("PERDEDOR: " + perdedor.getNombre());
        System.out.println();
        System.out.println("Penitencia para " + perdedor.getNombre() + ":");
        System.out.println(elegirPenitencia());
    }

    // Elige una penitencia al azar de la lista
    private String elegirPenitencia() {
        int indice = random.nextInt(penitencias.size());
        return penitencias.get(indice);
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

    // Lista de penitencias para el perdedor
    private void cargarPenitencias() {
        penitencias.add("Hacer 10 flexiones de brazos.");
        penitencias.add("Cantar el coro del himno nacional.");
        penitencias.add("Imitar durante 10 segundos a un animal que elija el ganador.");
        penitencias.add("Decir sin equivocarse este trabalenguas en guarani:\n"
                + "\"Apyka puku kupépe apyta apuka puku.\"");
    }

    // Banco fijo de preguntas: 1 = Facil, 2 = Medio, 3 = Dificil
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
        banco.add(new Pregunta("¿Tiene el Paraguay salida al mar?",
                new String[]{"Sí, al océano Atlántico", "Sí, al océano Pacífico",
                        "No, pero tiene puertos sobre ríos", "Sí, por el río Amazonas"}, 3, 1));
        banco.add(new Pregunta("¿Qué río divide al Paraguay en la región Oriental y el Chaco?",
                new String[]{"Río Paraná", "Río Paraguay", "Río Pilcomayo", "Río de la Plata"}, 2, 1));
        banco.add(new Pregunta("¿Con qué países limita el Paraguay?",
                new String[]{"Argentina, Brasil y Bolivia", "Argentina, Chile y Bolivia",
                        "Brasil, Uruguay y Bolivia", "Argentina, Brasil y Uruguay"}, 1, 1));
        banco.add(new Pregunta("¿Quién gobernaba el Paraguay durante la Guerra de la Triple Alianza?",
                new String[]{"Francisco Solano López", "Eusebio Ayala",
                        "José Gaspar Rodríguez de Francia", "Carlos Antonio López"}, 1, 1));
        banco.add(new Pregunta("¿Con qué apodo se conocía al Dr. José Gaspar Rodríguez de Francia?",
                new String[]{"El Libertador", "El Mariscal", "El Supremo", "El Gran Capitán"}, 3, 1));
        banco.add(new Pregunta("¿Qué lema tiene el escudo del reverso de la bandera?",
                new String[]{"Orden y Progreso", "Unión y Fuerza", "Libertad o Muerte", "Paz y Justicia"}, 4, 1));

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
        banco.add(new Pregunta("¿En qué año ganó el Paraguay su primera Copa América?",
                new String[]{"1979", "1953", "2011", "1930"}, 2, 2));
        banco.add(new Pregunta("¿A qué selección le ganó el Paraguay la final de la Copa América de 1979?",
                new String[]{"Brasil", "Argentina", "Chile", "Uruguay"}, 3, 2));
        banco.add(new Pregunta("¿Sobre qué río está la represa de Itaipú?",
                new String[]{"Río Paraguay", "Río Pilcomayo", "Río Uruguay", "Río Paraná"}, 4, 2));
        banco.add(new Pregunta("¿En qué año se adoptó la bandera paraguaya con sus dos escudos?",
                new String[]{"1811", "1842", "1870", "1992"}, 2, 2));
        banco.add(new Pregunta("¿Qué título tuvo el Dr. Francia desde 1816 hasta su muerte?",
                new String[]{"Presidente constitucional", "Mariscal", "Cónsul", "Dictador Perpetuo"}, 4, 2));
        banco.add(new Pregunta("¿Quién encabezó la toma del cuartel la noche del 14 de mayo de 1811?",
                new String[]{"Pedro Juan Caballero", "Fulgencio Yegros",
                        "José Gaspar Rodríguez de Francia", "Carlos Antonio López"}, 1, 2));

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
        banco.add(new Pregunta("¿En qué año se firmó el tratado secreto de la Triple Alianza?",
                new String[]{"1864", "1865", "1870", "1811"}, 2, 3));
        banco.add(new Pregunta("¿Qué periódico se fundó en 1845, durante el gobierno de Carlos Antonio López?",
                new String[]{"ABC Color", "La Nación", "El Paraguayo Independiente", "Última Hora"}, 3, 3));
        banco.add(new Pregunta("¿Qué presidente creó el guaraní como moneda, en 1943?",
                new String[]{"Higinio Morínigo", "Eusebio Ayala",
                        "Alfredo Stroessner", "Carlos Antonio López"}, 1, 3));
        banco.add(new Pregunta("¿En qué año empezó Itaipú a generar energía?",
                new String[]{"1973", "1984", "1999", "2005"}, 2, 3));
        banco.add(new Pregunta("¿Qué tenía de único en América el ferrocarril paraguayo de 1861?",
                new String[]{"Era subterráneo", "Era eléctrico",
                        "Se hizo solo con capital del Estado", "Llegaba hasta el océano"}, 3, 3));
        banco.add(new Pregunta("¿Qué representa el color blanco de la bandera paraguaya?",
                new String[]{"La paz", "La justicia", "La libertad", "La religión"}, 1, 3));
        banco.add(new Pregunta("¿Cuál era el apodo en guaraní del Dr. José Gaspar Rodríguez de Francia?",
                new String[]{"Mburuvicha", "Karai Guasu", "Tupã", "Ñandejára"}, 2, 3));
        banco.add(new Pregunta("¿En qué país se jugó la Copa América de 1953 que ganó el Paraguay?",
                new String[]{"Paraguay", "Brasil", "Perú", "Argentina"}, 3, 3));
        banco.add(new Pregunta("¿Con qué cargo gobernó Carlos Antonio López desde 1841, antes de ser presidente?",
                new String[]{"Dictador", "Cónsul", "Rey", "Virrey"}, 2, 3));
        banco.add(new Pregunta("¿En qué año asumió Francisco Solano López la presidencia?",
                new String[]{"1844", "1870", "1862", "1811"}, 3, 3));
        banco.add(new Pregunta("¿Desde qué año se celebra en el Paraguay el Día de la Madre el 15 de mayo?",
                new String[]{"1811", "1924", "1992", "1870"}, 2, 3));
        banco.add(new Pregunta("¿En qué año se escribió la letra del himno nacional paraguayo?",
                new String[]{"1846", "1811", "1870", "1935"}, 1, 3));
    }
}
