# Ejercicios de Código Limpio (Java 21)

Tres ejercicios de refactorización con dificultad creciente. Cada nivel tiene dos paquetes:

- **`sinsol`**: código que funciona pero tiene malos olores (*code smells*), marcados con `// SMELL:`.
  Es el punto de partida que el estudiante debe refactorizar.
- **`consol`**: una solución propuesta, con Javadoc que explica qué principio se aplicó.

Paquete base: `com.udc.reut.cod.app.codigolimpioapp`

Teoría de apoyo: [docs/teoria.md](docs/teoria.md).

| Nivel      | Dominio            | Sin solución                    | Con solución                  | Temas |
|------------|--------------------|---------------------------------|-------------------------------|-------|
| Básico     | Notas de estudiante | `basico.sinsol.Calc`           | `basico.consol.RegistroDeNotas` | Nombres, números mágicos, comentarios, encapsulamiento, DRY |
| Intermedio | Total de un pedido | `intermedio.sinsol.Pedido`      | `intermedio.consol.CalculadoraPedido` | Funciones pequeñas, objeto parámetro, enums, sin banderas, excepciones |
| Avanzado   | Nómina             | `avanzado.sinsol.GestorNomina`  | `avanzado.consol.ServicioNomina` | SOLID (SRP, OCP, LSP, ISP, DIP), inyección de dependencias, testabilidad |

## Pruebas

Cada paquete tiene su clase de prueba en `src/test/java` con los **mismos escenarios y valores esperados**
en `sinsol` y `consol`:

- Las pruebas de `sinsol` son **pruebas de caracterización**: fijan el comportamiento actual antes de refactorizar.
- Las pruebas de `consol` demuestran que la refactorización conserva las reglas de negocio
  y que el diseño nuevo es más fácil de probar (p. ej. dobles de prueba en el nivel avanzado).

```bash
mvn test
```

## Diagramas de clases

Los 6 diagramas están en un solo archivo draw.io con **una pestaña por paquete**:
[docs/diagramas-de-clases.drawio](docs/diagramas-de-clases.drawio).
Se abre en <https://app.diagrams.net> (Archivo → Abrir), en draw.io de escritorio o en VS Code
con la extensión *Draw.io Integration* (`hediet.vscode-drawio`).

Colores: azul = clase, verde = interfaz, morado = record, naranja = enum, rojo = clase con malos olores.

La versión en texto (Mermaid) está en [docs/diagramas-de-clases.md](docs/diagramas-de-clases.md).

GitHub los dibuja directamente. En VS Code se pueden ver con la extensión
*Markdown Preview Mermaid Support*.

## Sugerencia de uso en clase

1. Entregar solo el paquete `sinsol` y su prueba.
2. El estudiante identifica los `SMELL` y refactoriza en un paquete nuevo, manteniendo las pruebas en verde.
3. Comparar con `consol` y discutir alternativas (no hay una única solución correcta).
