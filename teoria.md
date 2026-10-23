# Teoría de Código Limpio

> "Cualquier tonto puede escribir código que una computadora entienda.
> Los buenos programadores escriben código que los humanos pueden entender." — Martin Fowler

Este documento resume los principios que se practican en los ejercicios del proyecto.
Cada sección indica en qué ejercicio aparece (**B** = básico, **I** = intermedio, **A** = avanzado).

---

## Índice

1. [¿Qué es el código limpio?](#1-qué-es-el-código-limpio)
2. [Nombres significativos](#2-nombres-significativos) — B
3. [Funciones](#3-funciones) — I
4. [Comentarios](#4-comentarios) — B
5. [Formato](#5-formato)
6. [Objetos y estructuras de datos](#6-objetos-y-estructuras-de-datos) — B, I, A
7. [Manejo de errores](#7-manejo-de-errores) — B, I, A
8. [Principios DRY, KISS y YAGNI](#8-principios-dry-kiss-y-yagni) — B
9. [Principios SOLID](#9-principios-solid) — A
10. [Pruebas unitarias](#10-pruebas-unitarias) — B, I, A
11. [Code smells (malos olores)](#11-code-smells-malos-olores)
12. [Refactorización](#12-refactorización)
13. [Lista de verificación](#13-lista-de-verificación)
14. [Bibliografía](#14-bibliografía)

---

## 1. ¿Qué es el código limpio?

El código limpio es código **fácil de leer, de entender y de cambiar** por alguien que no lo escribió.
No se trata de que funcione (eso es lo mínimo), sino de que el costo de mantenerlo sea bajo.

Características:

- **Legible**: se lee como prosa bien escrita; revela la intención del autor.
- **Simple**: hace una cosa y la hace bien, sin complejidad innecesaria.
- **Probado**: tiene pruebas automáticas que permiten cambiarlo con confianza.
- **Sin duplicación**: cada regla de negocio está en un solo lugar.
- **Expresivo**: usa los conceptos del dominio (Pedido, Nómina, Nota), no detalles técnicos.

**¿Por qué importa?** Un programador pasa mucho más tiempo *leyendo* código que escribiéndolo.
El código sucio genera **deuda técnica**: cada cambio cuesta más, aparecen más errores y
el equipo se vuelve más lento con el tiempo.

**Regla del Boy Scout:** *deja el código un poco más limpio de lo que lo encontraste.*

---

## 2. Nombres significativos

> Ejercicio: **básico** (`Calc` → `RegistroDeNotas`)

Un buen nombre responde *por qué existe*, *qué hace* y *cómo se usa*, sin necesidad de comentarios.

| Regla | Mal | Bien |
|-------|-----|------|
| Revelar la intención | `int d; // días` | `int diasDesdeCreacion;` |
| Evitar abreviaturas crípticas | `prom()`, `res()`, `l` | `calcularPromedio()`, `obtenerDesempeno()`, `notas` |
| Nombres pronunciables y buscables | `genymdhms` | `fechaGeneracion` |
| Sin números mágicos | `if (r >= 4.5)` | `if (promedio >= NOTA_EXCELENCIA)` |
| Clases con **sustantivos** | `Calc`, `Procesar` | `RegistroDeNotas`, `CalculadoraPedido` |
| Métodos con **verbos** | `total()` (ambiguo) | `calcularTotal()` |
| Un término por concepto | `obtener`, `traer`, `buscar` mezclados | Elegir uno y usarlo siempre |
| Evitar desinformación | `listaClientes` que es un `Set` | `clientes` |

**Convenciones en Java:** `PascalCase` para clases, `camelCase` para métodos y variables,
`MAYUSCULAS_CON_GUION` para constantes.

---

## 3. Funciones

> Ejercicio: **intermedio** (`Pedido.calc` → `CalculadoraPedido`)

1. **Pequeñas.** Idealmente de pocas líneas. Si no cabe en la pantalla, es demasiado larga.
2. **Hacen una sola cosa.** Si puedes extraer otra función con un nombre que no sea una
   simple repetición, la función hacía más de una cosa.
3. **Un nivel de abstracción por función.** No mezclar reglas de negocio con detalles como
   parsear un `String` o imprimir en consola.

   ```java
   public ResumenPedido calcular(Pedido pedido) {
       double subtotal = calcularSubtotal(pedido);
       double descuento = calcularDescuentoCliente(pedido, subtotal) + calcularDescuentoCupon(pedido, subtotal);
       double base = subtotal - descuento;
       return new ResumenPedido(subtotal, descuento, calcularIva(base), calcularEnvio(pedido, base));
   }
   ```

4. **Pocos argumentos.** 0 a 2 es lo ideal; 3 debe justificarse; más de 3 → agrupar en un
   **objeto parámetro** (`Pedido`).
5. **Sin argumentos bandera.** Un `boolean` indica que la función hace dos cosas.
   `calc(items, 3, true, "X")` no dice nada; `TipoEnvio.EXPRESS` sí.
6. **Sin efectos secundarios ocultos.** Una función llamada `calcular` no debe imprimir,
   guardar ni modificar estado global.
7. **Separar comandos de consultas (CQS).** Una función o *hace algo* o *responde algo*, no ambas.
8. **Cláusulas de guarda** en lugar de `if` anidados:

   ```java
   // Mal                                   // Bien
   if (items != null) {                      if (items == null || items.isEmpty()) {
       if (items.size() > 0) {                   throw new IllegalArgumentException(...);
           ...                               }
       }                                     ...
   }
   ```

---

## 4. Comentarios

> Ejercicio: **básico** (comentarios como `// promedio` sobre `prom()`)

> "No comentes el código malo: reescríbelo." — Kernighan y Plauger

Un comentario suele ser la señal de que no logramos expresarnos con el código.

**Comentarios malos:**

- **Redundantes**: `i++; // incrementa i`.
- **Que traducen un mal nombre**: `public void a(double n) // agrega nota` → renombrar a `agregarNota(double nota)`.
- **Desactualizados o engañosos**: el código cambió y el comentario no.
- **Código comentado**: bórralo; para eso existe Git.
- **Diarios o firmas**: `// modificado por Juan el 3/5`. Git ya guarda eso.

**Comentarios buenos:**

- **Explican el *por qué***, no el *qué*: `// El banco exige redondear hacia abajo (circular 12/2024)`.
- **Javadoc en APIs públicas.**
- **Advertencias**: `// No es thread-safe`.
- **TODO** con contexto (y con fecha de vencimiento en la práctica).

En los ejercicios, los `// SMELL:` son comentarios **didácticos**; no forman parte del estilo recomendado.

---

## 5. Formato

- **Vertical**: los conceptos relacionados van juntos; separar bloques con una línea en blanco.
  Las funciones que llaman a otras van arriba de las llamadas (se lee de arriba a abajo, como un periódico).
- **Horizontal**: líneas cortas (≈ 100–120 caracteres), sangría consistente.
- **Reglas del equipo**: todo el equipo usa el mismo estilo. Automatizar con el formateador del IDE
  o herramientas como Checkstyle o Spotless.

---

## 6. Objetos y estructuras de datos

> Ejercicios: **básico** (encapsulamiento), **intermedio** (value objects), **avanzado** (polimorfismo)

- **Encapsulamiento.** Los atributos son `private`; se exponen comportamientos, no datos.
  `public List<Double> l` permite saltarse la validación. Devolver `Collections.unmodifiableList(notas)`.
- **Ley de Demeter.** Un objeto solo habla con sus "amigos cercanos":
  evitar cadenas como `pedido.getCliente().getDireccion().getCiudad().getNombre()`.
- **Obsesión por primitivos.** Usar tipos del dominio en lugar de `String`, `int` o arreglos:

  | Primitivo | Tipo del dominio |
  |-----------|------------------|
  | `String[]{"Mouse","30000","1"}` | `record ItemPedido(String nombre, double precioUnitario, int cantidad)` |
  | `int tipoCliente = 3` | `enum TipoCliente { NORMAL, FRECUENTE, VIP }` |
  | `String "APROBADO"` | `enum Desempeno` |

- **Inmutabilidad.** Los `record` de Java son inmutables: más fáciles de razonar y seguros entre hilos.
- **Validar en el constructor.** Un objeto nunca debe existir en un estado inválido.

---

## 7. Manejo de errores

> Ejercicios: **básico** (falla silenciosa), **intermedio** (`return -1`), **avanzado** (`continue`)

1. **Usar excepciones, no códigos de error.** `return -1` obliga a cada llamador a recordar revisarlo.
2. **Fallar rápido (*fail fast*).** Detectar el error lo más cerca posible de su origen.
3. **No fallar en silencio.** Ignorar una nota inválida esconde errores que luego son difíciles de rastrear.
4. **Mensajes con contexto**: `"La nota debe estar entre 0.0 y 5.0: 6.0"`.
5. **No devolver `null`.** Usar `Optional`, colecciones vacías o excepciones.
6. **No pasar `null` como argumento.** (Ver `Pedido.cupon()` que devuelve `Optional<String>`.)

---

## 8. Principios DRY, KISS y YAGNI

| Principio | Significado | Ejemplo en el proyecto |
|-----------|-------------|------------------------|
| **DRY** — *Don't Repeat Yourself* | Cada conocimiento debe tener una única representación. | `Calc.res()` recalculaba el promedio; `obtenerDesempeno()` reutiliza `calcularPromedio()`. |
| **KISS** — *Keep It Simple, Stupid* | La solución más simple que funcione. | No se usó un framework de inyección: basta un constructor. |
| **YAGNI** — *You Aren't Gonna Need It* | No construir lo que aún no se necesita. | No se creó una jerarquía de cupones porque hoy solo existe `DESC10`. |

---

## 9. Principios SOLID

> Ejercicio: **avanzado** (`GestorNomina` → `ServicioNomina`)

### S — Responsabilidad única (SRP)
*Una clase debe tener una sola razón para cambiar.*
`GestorNomina` validaba, calculaba, guardaba, notificaba y reportaba: cinco razones para cambiar.
En la solución, cada responsabilidad tiene su propia clase.

### O — Abierto/Cerrado (OCP)
*Abierto a la extensión, cerrado a la modificación.*
El `switch (tipo)` obligaba a editar el gestor para cada tipo de empleado nuevo.
Con polimorfismo, un `Pensionado` es simplemente una clase nueva que implementa `Empleado`.

```java
// Antes                                      // Después
switch (e.tipo) {                             double neto = empleado.calcularPagoNeto();
    case "PLANTA": ...
    case "CONTRATISTA": ...
}
```

### L — Sustitución de Liskov (LSP)
*Un subtipo debe poder usarse donde se espera su tipo base sin romper el programa.*
Cualquier `Empleado` (planta, contratista, practicante) funciona en `ServicioNomina`.

### I — Segregación de interfaces (ISP)
*Ningún cliente debe depender de métodos que no usa.*
La clase comodín `Empleado` tenía `valorHora` y `horasExtra` para todos. Ahora cada tipo tiene solo sus datos,
y las interfaces `Notificador` y `RepositorioPagos` son pequeñas.

### D — Inversión de dependencias (DIP)
*Los módulos de alto nivel dependen de abstracciones, no de detalles.*
`ServicioNomina` recibe por constructor un `RepositorioPagos` y un `Notificador` (interfaces).
Se puede cambiar la lista en memoria por una base de datos real, o la consola por correo, sin tocar el servicio.
Esto se llama **inyección de dependencias**.

---

## 10. Pruebas unitarias

> Todos los ejercicios incluyen pruebas en `src/test/java`.

**Las tres leyes de TDD:**
1. No escribir código de producción sin una prueba que falle.
2. No escribir más prueba de la necesaria para fallar.
3. No escribir más código del necesario para pasar la prueba.

**Principios F.I.R.S.T.:**

| | Significado |
|-|-------------|
| **F**ast | Rápidas, para ejecutarlas a menudo. |
| **I**ndependent | Independientes: no comparten estado (la lista estática `GestorNomina.BD` rompe esto). |
| **R**epeatable | Mismo resultado en cualquier entorno. |
| **S**elf-validating | Pasan o fallan, sin revisar la consola a mano. |
| **T**imely | Escritas a tiempo, idealmente antes del código. |

**Estructura AAA (Arrange – Act – Assert):**

```java
@Test
void clienteNormalConEnvioGratis() {
    Pedido pedido = new Pedido(List.of(new ItemPedido("Monitor", 250_000, 1)),   // Arrange
                               TipoCliente.NORMAL, TipoEnvio.ESTANDAR);
    ResumenPedido resumen = calculadora.calcular(pedido);                         // Act
    assertEquals(297_500, resumen.total(), DELTA);                                // Assert
}
```

**Pruebas de caracterización:** las pruebas de los paquetes `sinsol` fijan el comportamiento
*actual* del código antes de refactorizarlo. Son la red de seguridad del refactor.

**Dobles de prueba:** el código que depende de interfaces se prueba con implementaciones falsas
(*fakes*, *spies*). Ver `NotificadorEspia` en `ServicioNominaTest`.

---

## 11. Code smells (malos olores)

Un *code smell* es un síntoma superficial que suele indicar un problema más profundo.

| Mal olor | Síntoma | Refactorización típica | Ejercicio |
|----------|---------|------------------------|-----------|
| Nombres crípticos | `a`, `l`, `prom` | Renombrar | B |
| Números mágicos | `0.19`, `4.5`, `240` | Extraer constante | B, I, A |
| Código duplicado | Misma lógica en dos métodos | Extraer método | B |
| Método largo | Más de 20–30 líneas | Extraer método | I |
| Lista larga de parámetros | 4 o más parámetros | Introducir objeto parámetro | I, A |
| Argumento bandera | `boolean exp` | Enum o dos métodos | I |
| Obsesión por primitivos | `String[]`, `int tipo` | Value object / enum | I |
| Condicionales anidados | `if` dentro de `if` | Cláusulas de guarda | I |
| Sentencias switch por tipo | `switch (tipo)` | Reemplazar condicional por polimorfismo | A |
| Clase Dios | Una clase hace todo | Extraer clase | A |
| Estado global | `static List BD` | Inyección de dependencias | A |
| Atributos públicos | `public List l` | Encapsular | B, A |
| Comentarios redundantes | `// agrega nota` | Renombrar y borrar el comentario | B |

---

## 12. Refactorización

> "Refactorizar es cambiar la estructura interna del software para hacerlo más fácil de entender
> y más barato de modificar, **sin cambiar su comportamiento observable**." — Martin Fowler

**Proceso seguro:**

1. Asegurar que existen pruebas que pasan (pruebas de caracterización).
2. Hacer **un cambio pequeño** (renombrar, extraer un método...).
3. Ejecutar las pruebas.
4. Si pasan, hacer commit. Si fallan, deshacer.
5. Repetir.

**Refactorizaciones más usadas:** renombrar, extraer método, extraer variable, extraer clase,
extraer constante, introducir objeto parámetro, reemplazar condicional por polimorfismo,
reemplazar código de tipo por enum o por subclases, encapsular colección.

Los IDE (NetBeans, IntelliJ, VS Code) automatizan muchas de ellas de forma segura (menú *Refactor*).

---

## 13. Lista de verificación

Antes de entregar código, pregúntate:

- [ ] ¿Los nombres explican la intención sin necesidad de comentarios?
- [ ] ¿Hay números o Strings mágicos?
- [ ] ¿Cada método hace una sola cosa y cabe en la pantalla?
- [ ] ¿Algún método tiene más de 3 parámetros o un parámetro `boolean`?
- [ ] ¿Hay código duplicado?
- [ ] ¿Los atributos son privados?
- [ ] ¿Los errores lanzan excepciones con un mensaje claro, en vez de ignorarse o devolver `-1`/`null`?
- [ ] ¿Hay un `switch`/`if` por tipo que podría ser polimorfismo?
- [ ] ¿Las clases dependen de interfaces y reciben sus dependencias por constructor?
- [ ] ¿Hay pruebas automáticas y pasan todas?

---

## 14. Bibliografía

- Robert C. Martin. *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall, 2008.
- Martin Fowler. *Refactoring: Improving the Design of Existing Code*. 2.ª ed. Addison-Wesley, 2018.
- Robert C. Martin. *Clean Architecture*. Prentice Hall, 2017.
- Joshua Bloch. *Effective Java*. 3.ª ed. Addison-Wesley, 2018.
- Kent Beck. *Test-Driven Development: By Example*. Addison-Wesley, 2002.
- Catálogo de refactorizaciones: <https://refactoring.com/catalog/>
- Catálogo de code smells: <https://refactoring.guru/es/refactoring/smells>
