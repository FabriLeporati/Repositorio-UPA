# Bitácora — Día 3: Sprint 2 (lógica completa)

**Fecha:** jueves 1 de octubre de 2026 (mañana y tarde)  
**Bloque:** Sprint 2 (6 h)
**Fabrizzio:** turnos, búsqueda por nivel, mezcla y partida de 8 preguntas; después la muerte súbita
**Edisson:** excepción propia, lectura segura de respuestas y penitencias

## Parte 1 — Turnos, búsqueda y partida normal (Fabrizzio)

### Los turnos con una Queue
Pensamos los turnos como una fila: el que está adelante juega, y cuando termina vuelve al final.
En Java eso es una `Queue<Jugador>` (la creamos con `new LinkedList<>()`):
- `poll()` saca al que está adelante (el que juega ahora).
- `offer()` lo vuelve a poner al final.

Para el sorteo usamos `Random`: si sale 0 metemos primero al jugador 1, si sale 1 al jugador 2.
`peek()` nos dice quién quedó adelante sin sacarlo, para mostrar "Empieza ...".

### La búsqueda lineal por nivel
`buscarPreguntasDelNivel()` recorre **todo** el banco con un `for` y, si la dificultad de la
pregunta es igual a la que eligieron, la agrega a `preguntasDelNivel`. Es una búsqueda lineal:
mira una por una, de la primera a la última.

### Mezclar las preguntas
Si no las mezclábamos, siempre salía primero la misma pregunta. Para mezclar recorremos la
lista desde el final y cada pregunta la cambiamos de lugar con otra de una posición al azar
(usando una variable `aux`, como cuando intercambiamos dos números). Después nos enteramos de
que esto se llama algoritmo de Fisher-Yates.

### La partida normal
Un `for` de 1 a 8: sacamos al jugador de la cola, le hacemos la siguiente pregunta de la lista
(`preguntaActual` dice por cuál vamos), si acierta suma un punto, y vuelve a la cola.
Al final se muestra el marcador y quién ganó (por ahora si empataban solo decía "EMPATE").

## Parte 2 — Excepción propia (Edisson)
Antes, si alguien ponía 7 en una pregunta de 4 opciones, lo controlábamos con un `if`.
Lo cambiamos para usar nuestra excepción **`OpcionInvalidaException`**:
- La clase hereda de `Exception` y solo tiene un constructor que recibe el mensaje.
- `validarOpcion(opcion, minimo, maximo)` lanza la excepción con `throw` si la opción está
  fuera del rango. Como es una excepción "checked", el método tiene que decir `throws`.
- `leerNumero` la atrapa con su propio `catch` y muestra el mensaje, por ejemplo:
  *"La opcion 7 no existe. Elegi entre 1 y 4."* Después vuelve a preguntar.
- Así quedan dos `catch`: uno para cuando escribís letras (`InputMismatchException`, de Java)
  y otro para cuando escribís un número que no existe (la nuestra).

## Parte 3 — Muerte súbita y penitencias (Fabrizzio + Edisson)
Al final decidimos el desempate así: si terminan **empatados o con 1 punto de diferencia**
(`estanParejos()`), se juega **muerte súbita**:
- Cada uno tiene 2 vidas (por eso le habíamos puesto vidas al `Jugador` el primer día).
  Al empezar la muerte súbita les ponemos las 2 vidas con `setVidas(2)`.
- Se siguen turnando con la misma cola. Si fallás, `perderVida()`.
- Pierde el primero que se queda sin vidas.

Para las **penitencias** hicimos un `ArrayList<String>` con 4 penitencias y se elige una al
azar con `random.nextInt(penitencias.size())`. `mostrarGanador` muestra ganador, perdedor y
la penitencia.

En las reglas del menú agregamos la explicación de la muerte súbita y la penitencia.

## Problemas que tuvimos
- Al principio en la muerte súbita el `while` no tenía límite y, si nadie fallaba, pedía la
  pregunta número 11 y el programa se caía con `IndexOutOfBoundsException`. Le agregamos la
  condición `preguntaActual < preguntasDelNivel.size()`.
- **Nos quedó pendiente:** si se acaban las preguntas y los dos todavía tienen vidas, el
  programa declara ganador al jugador 1 aunque no le corresponda. Lo vemos en los ajustes finales.

## Para la noche (ajustes finales)
- Arreglar el caso de que se acaben las preguntas en la muerte súbita.
- Cargar más preguntas (por ahora hay 10 por nivel).
- Probar partidas completas: que alguien gane, que haya muerte súbita, y escribir mal a propósito.
