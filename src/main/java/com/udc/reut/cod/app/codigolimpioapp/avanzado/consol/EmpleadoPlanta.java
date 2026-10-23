package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Empleado con contrato a término indefinido.
 * Recibe salario más horas extra y se le descuenta salud y pensión.
 */
public record EmpleadoPlanta(String nombre, String correo, double salarioBase, int horasExtra) implements Empleado {

    static final double HORAS_LABORALES_MES = 240;
    static final double RECARGO_HORA_EXTRA = 1.25;
    static final double PORCENTAJE_SALUD = 0.04;
    static final double PORCENTAJE_PENSION = 0.04;

    public EmpleadoPlanta {
        if (salarioBase < 0 || horasExtra < 0) {
            throw new IllegalArgumentException("Salario y horas extra no pueden ser negativos: " + nombre);
        }
    }

    @Override
    public double calcularPagoNeto() {
        return calcularPagoBruto() - calcularDeducciones();
    }

    private double calcularPagoBruto() {
        double valorHoraExtra = salarioBase / HORAS_LABORALES_MES * RECARGO_HORA_EXTRA;
        return salarioBase + horasExtra * valorHoraExtra;
    }

    private double calcularDeducciones() {
        return salarioBase * (PORCENTAJE_SALUD + PORCENTAJE_PENSION);
    }
}
