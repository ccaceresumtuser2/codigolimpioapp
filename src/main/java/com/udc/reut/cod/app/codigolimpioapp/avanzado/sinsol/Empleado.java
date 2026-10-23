package com.udc.reut.cod.app.codigolimpioapp.avanzado.sinsol;

/**
 * SMELL: clase "comodín" para todos los tipos de empleado.
 * Algunos campos solo tienen sentido para un tipo (valorHora solo para CONTRATISTA,
 * horasExtra solo para PLANTA) y el tipo se guarda como String.
 */
public class Empleado {
    public String nombre;
    public String tipo;      // "PLANTA", "CONTRATISTA", "PRACTICANTE"
    public double salario;   // salario base / apoyo de sostenimiento
    public int horas;        // horas extra (PLANTA) u horas trabajadas (CONTRATISTA)
    public double valorHora; // solo CONTRATISTA
    public String correo;
}
