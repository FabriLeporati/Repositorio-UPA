# Documento de diseño — Trivia: Historia del Paraguay

**Proyecto Integrador Final · Informática I · UPA**  
**Integrantes:** Fabrizzio Leporati y Edisson Segovia  
**Categoría:** 3 — Trivia / Quiz por turnos · **Ambientación:** Historia de Paraguay

## 1. Idea del juego
Trivia de opción múltiple para dos jugadores que se turnan en la misma computadora. Se elige
un nivel (Fácil, Medio o Difícil), se sortea quién empieza y se hacen 8 preguntas (4 por
jugador). Cada acierto vale 1 punto. Si quedan empatados o a 1 punto, se juega una muerte
súbita con 2 vidas por jugador. El que pierde hace una penitencia elegida al azar.

## 2. Clases
- **`Pregunta`** — enunciado, 4 opciones (`String[]`), respuesta correcta (número de 1 a 4)
  y dificultad (1 a 3). Atributos privados, constructor, getters y setters con validación.
  Tiene `esCorrecta(opcion)` para que nadie de afuera tenga que conocer la respuesta, y
  `mostrar()` para imprimirse sola.
- **`Jugador`** — nombre, puntaje y vidas. Atributos privados, getters y setters (no aceptan
  nombres vacíos ni números negativos). `sumarPunto()` y `perderVida()` cambian el estado de
  forma controlada (las vidas nunca bajan de 0).
- **`Juego`** — maneja la partida completa: carga las preguntas, busca las del nivel, las
  mezcla, maneja los turnos, la muerte súbita, el resultado y las penitencias.
- **`Main`** — punto de entrada: menú principal (Jugar / Ver reglas / Salir), pedido de
  nombres y dificultad. Crea los dos `Jugador` y el `Juego`.
- **`OpcionInvalidaException`** — excepción propia que hereda de `Exception`.

**Relaciones:** `Main` crea un `Juego` y dos `Jugador`. `Juego` tiene 2 jugadores y 48
preguntas (agregación). `Juego` lanza `OpcionInvalidaException`, que hereda de `Exception`.
El diagrama completo está en `docs/uml/diagrama_clases.png`.

## 3. Decisiones tomadas

**Estructuras de datos.**

- `ArrayList<Pregunta>` para el banco, porque es una lista que crece sola y se recorre fácil
  con un `for`.
- `Queue<Jugador>` (implementada con `LinkedList`) para los turnos: el jugador que juega sale
  de adelante con `poll()` y vuelve al final con `offer()`. Representa exactamente cómo dos
  personas se turnan, y la misma cola sigue funcionando en la muerte súbita.
- `ArrayList<String>` para las penitencias, de donde se elige una al azar.

**Algoritmo de búsqueda.** `buscarPreguntasDelNivel()` hace una **búsqueda lineal**: recorre
las 48 preguntas una por una y se queda con las 16 cuya dificultad coincide con la elegida.
Después `mezclarPreguntas()` las desordena intercambiando cada una con otra de posición al
azar (algoritmo de Fisher-Yates), para que cada partida sea distinta.

**Excepción propia.** El caso inválido propio del juego es responder con una opción que no
existe (por ejemplo 7 cuando hay del 1 al 4). `validarOpcion()` lanza
`OpcionInvalidaException` con un mensaje claro, y `leerNumero()` la atrapa y vuelve a pedir
el número. Además se atrapa `InputMismatchException` cuando se escriben letras. Así el
programa nunca se corta por una respuesta mal escrita.

**Respuesta correcta como número.** Se guarda como `int` (1 a 4) y no como texto, porque se
compara directo con lo que escribe el jugador.

**8 preguntas por partida.** Lo decidimos jugando en papel: con más se hacía largo y con
menos casi no había diferencia de puntos.

**Desempate con muerte súbita.** Con 1 punto de diferencia el resultado puede ser suerte,
por eso se considera "parejo" y se desempata. Cada nivel tiene 16 preguntas: 8 para la
partida y hasta 8 para la muerte súbita. Si incluso así se acaban las preguntas sin que nadie
pierda las 2 vidas, los dos hacen la penitencia y gana el que la termina primero.

**Sorteo de quién empieza.** Con `Random`, para que no empiece siempre el jugador 1.

## 4. Cómo se probó
Probamos partidas con victoria clara, empate con muerte súbita, muerte súbita sin errores
hasta agotar las preguntas, respuestas con letras, números fuera de rango y nombres vacíos.
Con estas pruebas encontramos y arreglamos dos errores (nombre vacío y ganador incorrecto al
agotarse las preguntas), contados en la bitácora del día 4.
