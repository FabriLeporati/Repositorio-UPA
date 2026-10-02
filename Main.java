import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Punto de entrada del programa.
 * Muestra el menu principal (Jugar / Ver reglas / Salir), pide los
 * nombres y la dificultad, y le pasa todo a la clase Juego.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        System.out.println("==============================================");
        System.out.println("   TRIVIA: HISTORIA DEL PARAGUAY");
        System.out.println("==============================================");

        // El menu se repite hasta que el usuario elija 3 (Salir)
        do {
            System.out.println();
            System.out.println("--- MENU ---");
            System.out.println("1) Jugar");
            System.out.println("2) Ver reglas");
            System.out.println("3) Salir");
            System.out.print("Elegi una opcion: ");
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        jugarPartida(sc);
                        break;
                    case 2:
                        mostrarReglas();
                        break;
                    case 3:
                        System.out.println("Gracias por jugar. Hasta la proxima!");
                        break;
                    default:
                        System.out.println("Opcion invalida. Elegi 1, 2 o 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: tenes que escribir un numero.");
                sc.nextLine();
            }
        } while (opcion != 3);

        sc.close();
    }

    // Arma los dos jugadores y el juego, y arranca la partida
    private static void jugarPartida(Scanner sc) {
        System.out.println();
        System.out.println("Hola jugadores! Vamos a ver quien sabe mas de la historia del Paraguay.");
        String nombre1 = pedirNombre(sc, 1);
        String nombre2 = pedirNombre(sc, 2);
        int dificultad = pedirDificultad(sc);

        Jugador jugador1 = new Jugador(nombre1);
        Jugador jugador2 = new Jugador(nombre2);
        Juego juego = new Juego(jugador1, jugador2, dificultad, sc);
        juego.jugar();
    }

    private static String pedirNombre(Scanner sc, int numero) {
        System.out.print("Nombre del jugador " + numero + ": ");
        String nombre = sc.nextLine();
        return nombre;
    }

    // Repite la pregunta hasta que elijan 1, 2 o 3
    private static int pedirDificultad(Scanner sc) {
        int dificultad = 0;
        while (dificultad < 1 || dificultad > 3) {
            System.out.println();
            System.out.println("Elegi la dificultad:");
            System.out.println("1) Facil");
            System.out.println("2) Medio");
            System.out.println("3) Dificil");
            System.out.print("Opcion: ");
            try {
                dificultad = sc.nextInt();
                sc.nextLine();
                if (dificultad < 1 || dificultad > 3) {
                    System.out.println("Opcion invalida. Elegi 1, 2 o 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: tenes que escribir un numero.");
                sc.nextLine();
            }
        }
        return dificultad;
    }

    private static void mostrarReglas() {
        System.out.println();
        System.out.println("========== REGLAS ==========");
        System.out.println("- Juegan 2 jugadores y eligen un nivel: Facil, Medio o Dificil.");
        System.out.println("- Se sortea quien empieza y se turnan: 8 preguntas en total, 4 cada uno.");
        System.out.println("- Cada acierto vale 1 punto. Si fallas, no sumas y pasa el turno.");
        System.out.println("- Si terminan empatados o con 1 punto de diferencia: MUERTE SUBITA.");
        System.out.println("  Cada uno tiene 2 vidas. Un error quita una. Pierde el que se queda sin vidas.");
        System.out.println("- El perdedor hace una penitencia elegida al azar.");
        System.out.println("- Se responde escribiendo el numero de la opcion (1 a 4).");
    }
}
