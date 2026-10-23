package com.udc.reut.cod.app.codigolimpioapp.basico.consol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegistroDeNotasTest {

    private static final double DELTA = 0.0001;

    private RegistroDeNotas registro;

    @BeforeEach
    void setUp() {
        registro = new RegistroDeNotas();
    }

    @Test
    void calculaElPromedioDeLasNotas() {
        registro.agregarNota(4.0);
        registro.agregarNota(3.0);
        registro.agregarNota(5.0);

        assertEquals(4.0, registro.calcularPromedio(), DELTA);
        assertEquals(Desempeno.APROBADO, registro.obtenerDesempeno());
    }

    @Test
    void promedioIgualOMayorA45EsExcelente() {
        registro.agregarNota(4.5);
        registro.agregarNota(5.0);

        assertEquals(Desempeno.EXCELENTE, registro.obtenerDesempeno());
    }

    @Test
    void promedioMenorATresEsReprobado() {
        registro.agregarNota(2.0);
        registro.agregarNota(3.0);

        assertEquals(Desempeno.REPROBADO, registro.obtenerDesempeno());
    }

    @Test
    void sinNotasElPromedioEsCeroYReprueba() {
        assertEquals(0.0, registro.calcularPromedio(), DELTA);
        assertEquals(Desempeno.REPROBADO, registro.obtenerDesempeno());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 5.1, 6.0})
    void notaFueraDeRangoLanzaExcepcion(double notaInvalida) {
        assertThrows(IllegalArgumentException.class, () -> registro.agregarNota(notaInvalida));
    }

    @Test
    void laListaDeNotasNoSePuedeModificarDesdeAfuera() {
        registro.agregarNota(4.0);

        assertThrows(UnsupportedOperationException.class, () -> registro.getNotas().add(1.0));
    }
}
