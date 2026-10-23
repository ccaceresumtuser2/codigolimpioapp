package com.udc.reut.cod.app.codigolimpioapp.avanzado.sinsol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de caracterización de la nómina.
 * Dolores de prueba que delatan el mal diseño:
 *   - Hay que limpiar la lista estática BD antes de cada prueba (estado global).
 *   - No se puede verificar que se envió el correo: va directo a System.out.
 *   - No se puede probar el cálculo de un tipo de empleado de forma aislada.
 */
class GestorNominaTest {

    private static final double DELTA = 0.01;

    private GestorNomina gestor;

    @BeforeEach
    void setUp() {
        GestorNomina.BD.clear(); // sin esto, las pruebas se contaminan entre sí
        gestor = new GestorNomina();
    }

    @Test
    void procesaLosTresTiposDeEmpleado() {
        gestor.agregarEmpleado("Ana", "PLANTA", 2_400_000, 10, 0, "ana@udc.edu.co");
        gestor.agregarEmpleado("Luis", "CONTRATISTA", 0, 100, 30_000, "luis@udc.edu.co");
        gestor.agregarEmpleado("Eva", "PRACTICANTE", 1_300_000, 0, 0, "eva@udc.edu.co");

        // 2.333.000 + 2.700.000 + 1.300.000
        assertEquals(6_333_000, gestor.procesarNomina(), DELTA);
        assertEquals(3, GestorNomina.BD.size());
        assertEquals("Ana;2333000.0", GestorNomina.BD.get(0));
    }

    @Test
    void empleadoInvalidoSeOmite() {
        gestor.agregarEmpleado("Error", "PLANTA", -1, 0, 0, "x@udc.edu.co");

        assertEquals(0, gestor.procesarNomina(), DELTA);
        assertEquals(0, GestorNomina.BD.size());
    }

    @Test
    void tipoDesconocidoSeOmite() {
        gestor.agregarEmpleado("Raro", "PENSIONADO", 1_000_000, 0, 0, "r@udc.edu.co");

        assertEquals(0, gestor.procesarNomina(), DELTA);
    }
}
