package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Persona vinculada por prestación de servicios. Cobra por hora y se le aplica retención.
 */
public record Contratista(String nombre, String correo, int horasTrabajadas, double valorHora) implements Empleado {

    static final double PORCENTAJE_RETENCION = 0.10;

    public Contratista {
        if (horasTrabajadas < 0 || valorHora < 0) {
            throw new IllegalArgumentException("Horas y valor hora no pueden ser negativos: " + nombre);
        }
    }

    @Override
    public double calcularPagoNeto() {
        double honorarios = horasTrabajadas * valorHora;
        return honorarios - honorarios * PORCENTAJE_RETENCION;
    }
}
