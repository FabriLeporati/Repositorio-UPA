# Bitácora — Día 1: Kickoff y diseño

**Fecha:** miércoles 30 de septiembre de 2026 (mañana)  
**Bloque:** Kickoff + diseño (2 h)
**Trabajamos:** Fabrizzio y Edisson, juntos en la misma compu

## Qué nos tocó
En el sorteo nos tocó la **Categoría 3: Trivia / Quiz por turnos**, con la ambientación
**Historia de Paraguay**. Nos gustó porque no hace falta inventar un mapa ni un sistema de
combate: las preguntas son los datos del juego y lo importante es cómo se turnan los jugadores.

## Cómo pensamos el juego (antes de tocar código)
Lo primero que hicimos fue jugarlo "en papel" entre nosotros dos, uno haciendo de computadora
y el otro respondiendo, para ver qué tenía que pasar en cada momento:

1. La compu pregunta los nombres de los dos jugadores.
2. Se elige el nivel (Fácil, Medio o Difícil). Así no le toca a uno una pregunta fácil y al
   otro una difícil: los dos juegan con preguntas del mismo nivel.
3. Se sortea quién empieza, porque si siempre empieza el jugador 1 no es justo.
4. Se hacen **8 preguntas, 4 para cada uno**, alternando. Elegimos 8 porque con 10 o más el
   juego se hacía largo, y con menos casi no hay diferencia de puntos.
5. Cada pregunta tiene 4 opciones y se responde con el número (1 a 4), igual que los menús
   de los TPs.
6. Al final se muestra el marcador y quién ganó.

Mientras lo jugábamos en papel nos pasó que quedamos 2 a 2, y después 3 a 2. Con un solo punto
de diferencia sentimos que el que perdió no "perdió de verdad", así que anotamos la idea de un
**desempate**, pero lo dejamos para más adelante porque primero tiene que funcionar lo básico.
También se nos ocurrió que el que pierde haga una **penitencia** (por ejemplo flexiones), para
que sea más divertido jugarlo con amigos.

## Qué clases necesitamos
Pensamos en las "cosas" que aparecen en el juego y cada una se volvió una clase:

- **Pregunta**: tiene el enunciado, las 4 opciones, cuál es la correcta y la dificultad.
  Pensamos guardar la correcta como un número (1 a 4) y no como texto, porque así comparamos
  directo con lo que escribe el jugador.
- **Jugador**: tiene el nombre y el puntaje. Le agregamos **vidas** pensando en el desempate
  (todavía no sabemos bien cómo va a ser).
- **Juego**: la clase que maneja todo: tiene las preguntas, los dos jugadores y los turnos.
- **Main**: solo el menú (Jugar / Ver reglas / Salir), como en los TPs.
- **OpcionInvalidaException**: nuestra excepción propia para cuando alguien escribe una opción
  que no existe (por ejemplo 7 cuando hay del 1 al 4).

## Qué estructuras de datos y qué algoritmo
- `ArrayList<Pregunta>` para el **banco** con todas las preguntas, porque no sabemos todavía
  cuántas vamos a tener y el ArrayList crece solo.
- `Queue<Jugador>` (con `LinkedList`) para los **turnos**: el que juega sale de adelante de la
  fila y después vuelve al final. Es exactamente lo que pasa cuando dos personas se turnan.
- **Búsqueda lineal**: recorrer todo el banco y quedarnos solo con las preguntas del nivel
  elegido. Es la búsqueda que vimos en clase y nos sirve justo para esto.
- Además queremos **mezclar** las preguntas para que no salgan siempre en el mismo orden.

## Qué hicimos hoy
- Creamos el repositorio en GitHub y agregamos al profe (**ggaleanopy**) como colaborador.
- Fabrizzio subió el `README.md` con la idea del juego y el `.gitignore` (para no subir los
  `.class` que genera Java al compilar).
- Edisson pasó el diagrama de clases a draw.io y lo subimos a `docs/uml/`.

## Para la tarde (Sprint 1)
- Crear las clases `Pregunta` y `Jugador` con atributos privados, constructor, getters y setters.
- Hacer el menú en `Main`.
- Empezar a cargar algunas preguntas para probar.
