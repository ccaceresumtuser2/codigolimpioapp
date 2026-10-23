package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Abstracción de un empleado al que se le paga en la nómina.
 *
 * Principios aplicados:
 *   - OCP: agregar un tipo nuevo (p. ej. "Pensionado") es crear una clase nueva,
 *          sin modificar ServicioNomina ni las clases existentes.
 *   - LSP: cualquier implementación puede usarse donde se espera un Empleado.
 *   - ISP: la interfaz solo pide lo que el servicio de nómina necesita.
 */
public interface Empleado {

    String nombre();

    String correo();

    double calcularPagoNeto();
}
