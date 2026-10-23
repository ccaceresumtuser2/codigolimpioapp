package com.udc.reut.cod.app.codigolimpioapp.basico.consol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EJERCICIO BÁSICO (CON SOLUCIÓN)
 *
 * Registro de notas de un estudiante (escala 0.0 a 5.0).
 *
 * Principios de código limpio aplicados frente a {@code basico.sinsol.Calc}:
 *   - Nombres que revelan la intención: RegistroDeNotas, agregarNota, calcularPromedio.
 *   - Constantes con nombre en lugar de números mágicos.
 *   - Encapsulamiento: la lista es privada y se expone solo como lectura.
 *   - DRY: obtenerDesempeno() reutiliza calcularPromedio().
 *   - Fallar rápido: una nota inválida lanza excepción en vez de ignorarse.
 *   - Sin comentarios redundantes: el código se explica por sí mismo.
 */
public class RegistroDeNotas {

    public static final double NOTA_MINIMA = 0.0;
    public static final double NOTA_MAXIMA = 5.0;
    public static final double NOTA_APROBATORIA = 3.0;
    public static final double NOTA_EXCELENCIA = 4.5;

    private final List<Double> notas = new ArrayList<>();

    public void agregarNota(double nota) {
        if (!esNotaValida(nota)) {
            throw new IllegalArgumentException(
                    "La nota debe estar entre " + NOTA_MINIMA + " y " + NOTA_MAXIMA + ": " + nota);
        }
        notas.add(nota);
    }

    public double calcularPromedio() {
        return notas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(NOTA_MINIMA);
    }

    public Desempeno obtenerDesempeno() {
        double promedio = calcularPromedio();
        if (promedio >= NOTA_EXCELENCIA) {
            return Desempeno.EXCELENTE;
        }
        if (promedio >= NOTA_APROBATORIA) {
            return Desempeno.APROBADO;
        }
        return Desempeno.REPROBADO;
    }

    public List<Double> getNotas() {
        return Collections.unmodifiableList(notas);
    }

    private boolean esNotaValida(double nota) {
        return nota >= NOTA_MINIMA && nota <= NOTA_MAXIMA;
    }
}
