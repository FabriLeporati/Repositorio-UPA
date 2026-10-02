# Trivia: Historia del Paraguay

Proyecto Integrador Final — Informática I (UPA)
Categoría 3: **Trivia / Quiz por turnos** · Ambientación: **Historia de Paraguay**

## Integrantes
- Fabrizzio Leporati
- Edisson Segovia

## De qué se trata
Juego de preguntas de opción múltiple sobre la historia del Paraguay, para **2 jugadores**
que se turnan en la misma computadora. Se juega todo por consola.

## Reglas
1. Se eligen los nombres y un nivel: **Fácil, Medio o Difícil**.
2. Se sortea quién empieza. Se hacen **8 preguntas, 4 para cada uno**, alternando.
3. Cada acierto vale **1 punto**. Se responde escribiendo el número de la opción (1 a 4).
4. Si terminan **empatados o con 1 punto de diferencia** → **MUERTE SÚBITA**: cada uno tiene
   2 vidas, cada error quita una y pierde el primero que se queda sin vidas.
5. Si en la muerte súbita se acaban las preguntas, los dos hacen la penitencia y gana el
   primero en terminarla.
6. El perdedor hace una **penitencia** elegida al azar.

## Cómo ejecutarlo
**VS Code:** abrir la carpeta, tener instalada la extensión *Extension Pack for Java*,
abrir `Main.java` y tocar **Run**.

**Terminal:**
```
javac -encoding UTF-8 *.java
java Main
```

## Estructura
| Archivo | Qué hace |
|---|---|
| `Main.java` | Menú principal (Jugar / Ver reglas / Salir), pide nombres y dificultad |
| `Juego.java` | Banco de preguntas, turnos, partida, muerte súbita, penitencias |
| `Jugador.java` | Nombre, puntaje y vidas de cada jugador |
| `Pregunta.java` | Enunciado, opciones, respuesta correcta y dificultad |
| `OpcionInvalidaException.java` | Excepción propia: opción que no existe |
| `docs/uml/` | Diagrama de clases |
| `docs/bitacora/` | Lo que hicimos cada día |
| `docs/diseno.md` | Documento de diseño y decisiones |

## Requisitos de la consigna y dónde están
| Requisito | Dónde |
|---|---|
| Clases encapsuladas | `Pregunta` y `Jugador` (atributos privados, constructor, getters y setters) |
| Estructura de datos | `ArrayList<Pregunta>` (banco), `Queue<Jugador>` (turnos), `ArrayList<String>` (penitencias) |
| Excepción propia | `OpcionInvalidaException`, lanzada en `Juego.validarOpcion()` y atrapada en `Juego.leerNumero()` |
| Algoritmo de búsqueda | Búsqueda lineal en `Juego.buscarPreguntasDelNivel()` (+ mezcla en `mezclarPreguntas()`) |
| Menú funcional | `Main`: se juega de principio a fin con resultado claro (ganador, perdedor, puntaje) |
