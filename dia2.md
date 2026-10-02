# Bitácora — Día 2: Sprint 1 (estructura base)

**Fecha:** miércoles 30 de septiembre de 2026 (tarde)  
**Bloque:** Sprint 1 (6 h)
**Fabrizzio:** clases `Pregunta` y `Jugador`
**Edisson:** menú en `Main` y esqueleto de `Juego` con las primeras preguntas

## Lo que pensamos al arrancar
Decidimos empezar por las clases más simples (`Pregunta` y `Jugador`) porque son las que
usa todo lo demás. Si esas andan bien, después el `Juego` solo tiene que "usarlas".

### Pregunta (Fabrizzio)
- Todos los atributos son `private`, así nadie de afuera puede cambiar la respuesta correcta
  por error. Para leerlos están los **getters**.
- Los **setters** los hicimos con una validación simple: `setRespuestaCorrecta` solo acepta
  de 1 a 4 y `setDificultad` solo 1, 2 o 3. Si viene otra cosa, no cambia nada.
- En vez de comparar desde afuera, hicimos el método `esCorrecta(opcion)`, que devuelve
  `true` o `false`. Así el que pregunta no necesita saber cuál es la correcta.
- `getTextoCorrecto()` devuelve el texto de la opción correcta para mostrarlo cuando alguien
  se equivoca. Como el arreglo empieza en 0 y las opciones en 1, va `respuestaCorrecta - 1`.
  Nos equivocamos la primera vez y nos mostraba la opción de al lado 😅.
- `mostrar()` imprime el enunciado y las opciones numeradas con un `for`.

### Jugador (Fabrizzio)
- Empieza con 0 puntos y 2 vidas.
- `sumarPunto()` no recibe nada porque cada acierto vale siempre 1.
- `perderVida()` tiene un `if` para que las vidas nunca queden en negativo.
- Getters y setters para nombre, puntaje y vidas (los setters no aceptan nombres vacíos ni
  números negativos).

### Menú y esqueleto del Juego (Edisson)
- El menú en `Main` es un `do-while` que se repite hasta elegir 3 (Salir), con un `switch`
  para cada opción, igual que en los TPs.
- Pusimos `try/catch` de `InputMismatchException` porque si escribís una letra en vez de un
  número el `Scanner` se trababa y el programa se cortaba. Con el `sc.nextLine()` dentro del
  `catch` se "limpia" lo que escribiste mal.
- `pedirDificultad` repite la pregunta hasta que elijas 1, 2 o 3.
- `Juego` por ahora solo carga un `ArrayList<Pregunta>` con **12 preguntas (4 por nivel)** y
  hace UNA pregunta de prueba, para comprobar que `Pregunta` y `Jugador` funcionan juntas.

## Problemas que tuvimos
- Después de un `nextInt()`, el siguiente `nextLine()` leía vacío y se salteaba el nombre.
  Lo buscamos y es porque `nextInt()` deja el "enter" en el buffer. Lo arreglamos poniendo un
  `sc.nextLine()` justo después de cada `nextInt()`.
- Las preguntas con tildes y "¿" se veían raras en una de las compus; en VS Code se ven bien.

## Cómo quedó
Ya se puede: abrir el menú, ver las reglas, poner los nombres, elegir dificultad y responder
una pregunta de prueba. Todavía no hay turnos ni partida completa.

## Para mañana (Sprint 2)
- Turnos con una `Queue`.
- Buscar las preguntas del nivel elegido (búsqueda lineal) y mezclarlas.
- La partida de 8 preguntas y el marcador.
- La excepción propia.
- Pensar bien el desempate.
