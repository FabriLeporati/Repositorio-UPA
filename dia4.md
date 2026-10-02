# Bitácora — Día 4: Ajustes finales y pruebas

**Fecha:** jueves 1 de octubre de 2026 (noche)  
**Bloque:** Ajustes finales (4 h)
**Edisson:** banco completo de preguntas y revisión de los datos
**Fabrizzio:** arreglo del empate final, validación de nombres, comentarios y documentación

## Banco completo de preguntas (Edisson)
Con 10 preguntas por nivel la muerte súbita se quedaba sin preguntas muy rápido. Subimos a
**16 por nivel (48 en total)**: 8 para la partida normal y hasta 8 más para la muerte súbita.

Revisamos las respuestas buscando en internet y encontramos dos cosas para corregir:
- Itaipú empezó a generar energía en **1984** (la primera turbina, el 5 de mayo), no en 1985
  como habíamos puesto.
- En la pregunta de la Guerra del Chaco cambiamos "primera batalla" por **"primera gran
  batalla"**, porque antes de Boquerón ya hubo enfrentamientos más chicos.

## Pruebas de partida completa (los dos)
Jugamos muchas partidas, a propósito de distintas formas:
1. **Uno gana claro** (por 2 o más puntos): sale el resultado y la penitencia. ✔️
2. **Empate en la partida normal** → muerte súbita → uno se queda sin vidas. ✔️
3. **Escribir letras** en vez de números: dice "tenes que escribir un numero" y vuelve a
   preguntar, no se corta. ✔️
4. **Escribir 9** en una pregunta: sale el mensaje de nuestra excepción. ✔️
5. **Dejar el nombre vacío** (apretar Enter): ❌ el juego aceptaba un jugador sin nombre y
   después decía "Empieza !". Lo arreglamos: `pedirNombre` repite la pregunta hasta que el
   nombre tenga algo, y usamos `trim()` para que tampoco valgan solo espacios.
6. **Muerte súbita en la que nadie falla** (respondimos todas bien con la hoja de respuestas):
   ❌ el error que quedó pendiente a la tarde, ganaba siempre el jugador 1.

## Arreglo del empate final (Fabrizzio)
Pensamos qué sería justo si los dos aciertan todo hasta que se acaban las preguntas, y
decidimos: **los dos hacen la misma penitencia y gana el primero en terminarla**.
- En `jugar()` ahora hay tres casos después de la muerte súbita: el jugador 1 se quedó sin
  vidas, el jugador 2 se quedó sin vidas, o ninguno (se acabaron las preguntas).
- Para el último caso hicimos `resolverEmpateFinal()`, que muestra la penitencia y pregunta
  quién terminó primero. Para leer esa respuesta reusamos `leerNumero(1, 2)`, así también
  está protegida por nuestra excepción.
- Agregamos esta regla en "Ver reglas".

Volvimos a probar el caso 6 y ahora funciona. ✔️

## Comentarios y documentación
- Agregamos comentarios arriba de cada clase explicando para qué sirve, y en los métodos que
  pueden ser difíciles de entender (la cola, la búsqueda lineal, la mezcla, la excepción).
- Actualizamos el `README.md` con cómo se juega y cómo se ejecuta, y el `LEEME.txt`.
- Agregamos el documento de diseño en `docs/diseno.md`.

## Qué vamos a mostrar en la Demo
1. El menú y "Ver reglas".
2. Una partida en nivel Fácil: el sorteo, un par de preguntas, una respuesta mal escrita a
   propósito (letra y número fuera de rango) para mostrar la excepción.
3. El marcador final y, si se da, la muerte súbita.
4. El resultado y la penitencia del que pierde.

## Cómo nos repartimos para la defensa
Como el profe pregunta a cada uno sobre cualquier parte, nos explicamos el código mutuamente:
Fabrizzio le explicó a Edisson la cola de turnos y la muerte súbita, y Edisson le
explicó a Fabrizzio la excepción y el banco de preguntas. Los dos podemos explicar y modificar
cualquier clase.

## Estado final
✅ El juego se puede jugar de principio a fin, con victoria, derrota, muerte súbita y empate final.
