package com.udc.reut.cod.app.codigolimpioapp.basico.sinsol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Pruebas de caracterización: describen lo que el código hace HOY.
 * Deben seguir pasando (adaptadas a los nuevos nombres) después de refactorizar.
 */
class CalcTest {

    private static final double DELTA = 0.0001;

    @Test
    void promedioDeTresNotas() {
        Calc c = new Calc();
        c.a(4.0);
        c.a(3.0);
        c.a(5.0);

        assertEquals(4.0, c.prom(), DELTA);
        assertEquals("APROBADO", c.res());
    }

    @Test
    void promedioAltoEsExcelente() {
        Calc c = new Calc();
        c.a(4.5);
        c.a(5.0);

        assertEquals("EXCELENTE", c.res());
    }

    @Test
    void promedioBajoEsReprobado() {
        Calc c = new Calc();
        c.a(2.0);
        c.a(3.0);

        assertEquals("REPROBADO", c.res());
    }

    @Test
    void sinNotasElPromedioEsCero() {
        Calc c = new Calc();

        assertEquals(0.0, c.prom(), DELTA);
        assertEquals("REPROBADO", c.res());
    }

    @Test
    void notaFueraDeRangoSeIgnoraEnSilencio() {
        Calc c = new Calc();
        c.a(6.0);

        // Comportamiento problemático: no hay forma de saber que la nota fue rechazada
        assertEquals(0, c.l.size());
    }
}
