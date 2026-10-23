package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Estudiante en práctica. Recibe un apoyo de sostenimiento fijo sin deducciones.
 */
public record Practicante(String nombre, String correo, double apoyoSostenimiento) implements Empleado {

    public Practicante {
        if (apoyoSostenimiento < 0) {
            throw new IllegalArgumentException("El apoyo no puede ser negativo: " + nombre);
        }
    }

    @Override
    public double calcularPagoNeto() {
        return apoyoSostenimiento;
    }
}
