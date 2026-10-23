package com.udc.reut.cod.app.codigolimpioapp.intermedio.sinsol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de caracterización del cálculo de pedidos.
 * Observe lo difícil que es leer las llamadas: ¿qué significa 3? ¿y true?
 */
class PedidoTest {

    private static final double DELTA = 0.01;

    private final Pedido pedido = new Pedido();

    @Test
    void clienteVipConCuponYEnvioExpress() {
        List<String[]> items = List.of(
                new String[]{"Teclado", "50000", "2"},
                new String[]{"Mouse", "30000", "1"});

        // subtotal 130.000 - descuentos 26.000 = base 104.000 + IVA 19.760 + envío 25.000
        assertEquals(148_760, pedido.calc(items, 3, true, "DESC10"), DELTA);
    }

    @Test
    void clienteNormalConEnvioGratis() {
        List<String[]> items = List.<String[]>of(new String[]{"Monitor", "250000", "1"});

        assertEquals(297_500, pedido.calc(items, 1, false, null), DELTA);
    }

    @Test
    void cuponNoAplicaSiElSubtotalEsMenorA100Mil() {
        List<String[]> items = List.<String[]>of(new String[]{"Audífonos", "50000", "1"});

        assertEquals(66_525, pedido.calc(items, 2, false, "DESC10"), DELTA);
    }

    @Test
    void tipoDeClienteInvalidoDevuelveMenosUno() {
        List<String[]> items = List.<String[]>of(new String[]{"Cable", "10000", "1"});

        assertEquals(-1, pedido.calc(items, 9, false, null), DELTA);
    }

    @Test
    void pedidoVacioDevuelveCero() {
        assertEquals(0, pedido.calc(List.of(), 1, false, null), DELTA);
    }
}
