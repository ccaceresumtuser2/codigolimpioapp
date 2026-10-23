package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class CalculadoraPedidoTest {

    private static final double DELTA = 0.01;

    private final CalculadoraPedido calculadora = new CalculadoraPedido();

    @Test
    void clienteVipConCuponYEnvioExpress() {
        Pedido pedido = new Pedido(
                List.of(new ItemPedido("Teclado", 50_000, 2), new ItemPedido("Mouse", 30_000, 1)),
                TipoCliente.VIP,
                TipoEnvio.EXPRESS,
                "DESC10");

        ResumenPedido resumen = calculadora.calcular(pedido);

        assertAll(
                () -> assertEquals(130_000, resumen.subtotal(), DELTA),
                () -> assertEquals(26_000, resumen.descuento(), DELTA),
                () -> assertEquals(19_760, resumen.iva(), DELTA),
                () -> assertEquals(25_000, resumen.envio(), DELTA),
                () -> assertEquals(148_760, resumen.total(), DELTA));
    }

    @Test
    void clienteNormalConEnvioGratis() {
        Pedido pedido = new Pedido(List.of(new ItemPedido("Monitor", 250_000, 1)), TipoCliente.NORMAL, TipoEnvio.ESTANDAR);

        ResumenPedido resumen = calculadora.calcular(pedido);

        assertEquals(0, resumen.envio(), DELTA);
        assertEquals(297_500, resumen.total(), DELTA);
    }

    @Test
    void cuponNoAplicaSiElSubtotalEsMenorA100Mil() {
        Pedido pedido = new Pedido(
                List.of(new ItemPedido("Audífonos", 50_000, 1)), TipoCliente.FRECUENTE, TipoEnvio.ESTANDAR, "DESC10");

        ResumenPedido resumen = calculadora.calcular(pedido);

        assertEquals(2_500, resumen.descuento(), DELTA);
        assertEquals(66_525, resumen.total(), DELTA);
    }

    @Test
    void cuponDesconocidoNoDaDescuento() {
        Pedido pedido = new Pedido(
                List.of(new ItemPedido("Silla", 150_000, 1)), TipoCliente.NORMAL, TipoEnvio.ESTANDAR, "FALSO");

        assertEquals(0, calculadora.calcular(pedido).descuento(), DELTA);
    }

    @Test
    void pedidoSinItemsNoSePuedeCrear() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(List.of(), TipoCliente.NORMAL, TipoEnvio.ESTANDAR));
    }

    @Test
    void itemConCantidadCeroNoSePuedeCrear() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Cable", 10_000, 0));
    }
}
