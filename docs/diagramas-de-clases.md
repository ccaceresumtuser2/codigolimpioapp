# Diagramas de clases — Ejercicios de Código Limpio

Paquete base: `com.udc.reut.cod.app.codigolimpioapp`

1. [Básico — sinsol](#diagrama-de-clases--basicosinsol)
2. [Básico — consol](#diagrama-de-clases--basicoconsol)
3. [Intermedio — sinsol](#diagrama-de-clases--intermediosinsol)
4. [Intermedio — consol](#diagrama-de-clases--intermedioconsol)
5. [Avanzado — sinsol](#diagrama-de-clases--avanzadosinsol)
6. [Avanzado — consol](#diagrama-de-clases--avanzadoconsol)

---

## Diagrama de clases — `basico.sinsol`

```mermaid
classDiagram
    direction LR
    class Calc {
        +List~Double~ l
        +a(double n) void
        +prom() double
        +res() String
    }
    note for Calc "Problemas:\n- nombres de una letra (l, a, n)\n- atributo público\n- números mágicos 0, 5, 3, 4.5\n- promedio duplicado en prom() y res()\n- Strings mágicos como resultado\n- nota inválida se ignora en silencio"
```

**Lectura del diagrama:** una sola clase con nombres crípticos. El atributo `l` es público (`+`),
por lo que cualquier cliente puede modificar las notas sin pasar por la validación de `a()`.

---

## Diagrama de clases — `basico.consol`

```mermaid
classDiagram
    direction LR
    class RegistroDeNotas {
        +double NOTA_MINIMA$
        +double NOTA_MAXIMA$
        +double NOTA_APROBATORIA$
        +double NOTA_EXCELENCIA$
        -List~Double~ notas
        +agregarNota(double nota) void
        +calcularPromedio() double
        +obtenerDesempeno() Desempeno
        +getNotas() List~Double~
        -esNotaValida(double nota) boolean
    }
    class Desempeno {
        <<enumeration>>
        EXCELENTE
        APROBADO
        REPROBADO
    }
    RegistroDeNotas ..> Desempeno : retorna
```

**Cambios frente a `sinsol`:**

| Antes (`Calc`)          | Después (`RegistroDeNotas`)               | Principio                      |
|-------------------------|-------------------------------------------|--------------------------------|
| `l` público             | `notas` privado + vista no modificable    | Encapsulamiento                |
| `a`, `prom`, `res`      | `agregarNota`, `calcularPromedio`, ...    | Nombres que revelan intención  |
| `0`, `5`, `3`, `4.5`    | `NOTA_MINIMA`, `NOTA_MAXIMA`, ...         | Sin números mágicos            |
| `"APROBADO"` (String)   | `Desempeno.APROBADO` (enum)               | Tipos en vez de Strings        |
| Promedio calculado 2 veces | `obtenerDesempeno()` usa `calcularPromedio()` | DRY                      |
| Nota inválida ignorada  | `IllegalArgumentException`                | Fallar rápido                  |

---

## Diagrama de clases — `intermedio.sinsol`

```mermaid
classDiagram
    direction LR
    class Pedido {
        +calc(List~String[]~ items, int tc, boolean exp, String cup) double
    }
    note for Pedido "Problemas:\n- un método de ~50 líneas con 4 niveles de if\n- String[] para ítems (obsesión por primitivos)\n- int mágico para tipo de cliente\n- boolean bandera (exp)\n- retorna -1 como código de error\n- imprime en consola (efecto secundario)"
```

**Lectura del diagrama:** toda la lógica del negocio está escondida en un único método.
El diagrama no muestra ningún concepto del dominio (ítem, cliente, envío, resumen):
todos están "aplanados" en parámetros primitivos.

---

## Diagrama de clases — `intermedio.consol`

```mermaid
classDiagram
    direction LR
    class CalculadoraPedido {
        ~String CUPON_DESCUENTO$
        ~double PORCENTAJE_CUPON$
        ~double MINIMO_PARA_CUPON$
        ~double TASA_IVA$
        ~double COSTO_ENVIO_ESTANDAR$
        ~double MINIMO_ENVIO_GRATIS$
        +calcular(Pedido pedido) ResumenPedido
        -calcularSubtotal(Pedido) double
        -calcularDescuentoCliente(Pedido, double) double
        -calcularDescuentoCupon(Pedido, double) double
        -calcularIva(double) double
        -calcularEnvio(Pedido, double) double
    }
    class Pedido {
        <<record>>
        +List~ItemPedido~ items
        +TipoCliente tipoCliente
        +TipoEnvio tipoEnvio
        +String codigoCupon
        +cupon() Optional~String~
    }
    class ItemPedido {
        <<record>>
        +String nombre
        +double precioUnitario
        +int cantidad
        +subtotal() double
    }
    class ResumenPedido {
        <<record>>
        +double subtotal
        +double descuento
        +double iva
        +double envio
        +base() double
        +total() double
    }
    class TipoCliente {
        <<enumeration>>
        NORMAL
        FRECUENTE
        VIP
        -double porcentajeDescuento
        +porcentajeDescuento() double
    }
    class TipoEnvio {
        <<enumeration>>
        ESTANDAR
        EXPRESS
        -double recargo
        +recargo() double
    }

    CalculadoraPedido ..> Pedido : recibe
    CalculadoraPedido ..> ResumenPedido : crea
    Pedido "1" *-- "1..*" ItemPedido : items
    Pedido --> TipoCliente
    Pedido --> TipoEnvio
```

**Cambios frente a `sinsol`:**

| Antes                                  | Después                                   | Principio                          |
|----------------------------------------|-------------------------------------------|------------------------------------|
| Método `calc` de ~50 líneas            | 6 métodos privados pequeños               | Funciones pequeñas, una sola cosa  |
| `String[]{nombre, precio, cantidad}`   | `record ItemPedido`                       | Evitar obsesión por primitivos     |
| `int tc` (1, 2, 3)                     | `enum TipoCliente` con su descuento       | Reemplazar condicional por datos   |
| `boolean exp`                          | `enum TipoEnvio`                          | Sin argumentos bandera             |
| 4 parámetros sueltos                   | `record Pedido`                           | Objeto parámetro                   |
| `return -1`                            | Excepción en el constructor               | Excepciones, no códigos de error   |
| `System.out.println` dentro del cálculo | `ResumenPedido` devuelto al cliente      | Sin efectos secundarios            |
| `if` anidados                          | Cláusulas de guarda                       | Reducir anidamiento                |

---

## Diagrama de clases — `avanzado.sinsol`

```mermaid
classDiagram
    direction LR
    class GestorNomina {
        +List~String~ BD$
        -List~Empleado~ empleados
        +agregarEmpleado(String nombre, String tipo, double salario, int horas, double valorHora, String correo) void
        +procesarNomina() double
    }
    class Empleado {
        +String nombre
        +String tipo
        +double salario
        +int horas
        +double valorHora
        +String correo
    }
    class System_out {
        <<detalle concreto>>
    }

    GestorNomina "1" o-- "*" Empleado
    GestorNomina ..> System_out : imprime correos y errores

    note for GestorNomina "Viola SRP: valida, calcula, guarda,\nnotifica y reporta.\nViola OCP: switch(tipo).\nViola DIP: usa lista estática y System.out."
    note for Empleado "Clase comodín: valorHora solo sirve\na CONTRATISTA, tipo es un String."
```

**Lectura del diagrama:** `GestorNomina` concentra todas las responsabilidades y depende de
detalles concretos. `BD` está subrayado/estático (`$`): es estado global compartido.
Agregar un tipo de empleado nuevo obliga a modificar `procesarNomina()`.

---

## Diagrama de clases — `avanzado.consol`

```mermaid
classDiagram
    direction TB
    class ServicioNomina {
        -RepositorioPagos repositorio
        -Notificador notificador
        +ServicioNomina(RepositorioPagos, Notificador)
        +procesar(List~Empleado~ empleados) double
        -pagar(Empleado) double
    }

    class Empleado {
        <<interface>>
        +nombre() String
        +correo() String
        +calcularPagoNeto() double
    }
    class EmpleadoPlanta {
        <<record>>
        +double salarioBase
        +int horasExtra
        +calcularPagoNeto() double
        -calcularPagoBruto() double
        -calcularDeducciones() double
    }
    class Contratista {
        <<record>>
        +int horasTrabajadas
        +double valorHora
        +calcularPagoNeto() double
    }
    class Practicante {
        <<record>>
        +double apoyoSostenimiento
        +calcularPagoNeto() double
    }

    class RepositorioPagos {
        <<interface>>
        +guardar(RegistroPago) void
        +listar() List~RegistroPago~
    }
    class RepositorioPagosEnMemoria {
        -List~RegistroPago~ registros
    }
    class Notificador {
        <<interface>>
        +notificar(String destinatario, String mensaje) void
    }
    class NotificadorConsola
    class RegistroPago {
        <<record>>
        +String nombreEmpleado
        +double pagoNeto
    }

    Empleado <|.. EmpleadoPlanta
    Empleado <|.. Contratista
    Empleado <|.. Practicante
    RepositorioPagos <|.. RepositorioPagosEnMemoria
    Notificador <|.. NotificadorConsola

    ServicioNomina ..> Empleado : usa
    ServicioNomina --> RepositorioPagos : depende de la abstracción
    ServicioNomina --> Notificador : depende de la abstracción
    ServicioNomina ..> RegistroPago : crea
    RepositorioPagos ..> RegistroPago
```

**Cambios frente a `sinsol`:**

| Antes (`GestorNomina`)                 | Después                                              | Principio |
|----------------------------------------|------------------------------------------------------|-----------|
| Una clase hace 5 cosas                 | `ServicioNomina` orquesta; cálculo, persistencia y notificación separados | **S**RP |
| `switch (e.tipo)`                      | `Empleado.calcularPagoNeto()` polimórfico            | **O**CP   |
| `Empleado` comodín con campos inútiles | Un record por tipo, cada uno con solo sus datos      | **L**SP / **I**SP |
| `static List BD`, `System.out`         | Interfaces `RepositorioPagos` y `Notificador` inyectadas | **D**IP |
| Validación con `continue` silencioso   | Validación en el constructor del record              | Fallar rápido |

**Extensibilidad:** para agregar un `Pensionado` basta con crear una clase que implemente
`Empleado`. `ServicioNomina` no cambia (ver la prueba `agregarUnTipoNuevoNoRequiereModificarElServicio`).
