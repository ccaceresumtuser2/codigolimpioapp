package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

import java.util.List;

/**
 * EJERCICIO AVANZADO (CON SOLUCIÓN)
 *
 * Procesa la nómina (mismas reglas que {@code avanzado.sinsol.GestorNomina}).
 *
 * Principios SOLID aplicados:
 *   - SRP: el servicio solo orquesta. El cálculo vive en cada Empleado, la persistencia
 *          en RepositorioPagos y el envío de mensajes en Notificador.
 *   - OCP: no hay switch por tipo; se usa polimorfismo (empleado.calcularPagoNeto()).
 *   - DIP: las dependencias llegan por constructor como interfaces (inyección de dependencias).
 *   - Validación en la construcción: un Empleado inválido no se puede crear.
 */
public class ServicioNomina {

    private final RepositorioPagos repositorio;
    private final Notificador notificador;

    public ServicioNomina(RepositorioPagos repositorio, Notificador notificador) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }

    public double procesar(List<Empleado> empleados) {
        return empleados.stream()
                .mapToDouble(this::pagar)
                .sum();
    }

    private double pagar(Empleado empleado) {
        double pagoNeto = empleado.calcularPagoNeto();
        repositorio.guardar(new RegistroPago(empleado.nombre(), pagoNeto));
        notificador.notificar(empleado.correo(), "Su pago de nómina es " + pagoNeto);
        return pagoNeto;
    }
}
