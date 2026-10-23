package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

/**
 * Resultado detallado del cálculo de un pedido.
 *
 * Principio aplicado: separar cálculo de presentación. La calculadora devuelve datos
 * y quien la usa decide si los imprime, los guarda o los muestra en una vista.
 */
public record ResumenPedido(double subtotal, double descuento, double iva, double envio) {

    public double base() {
        return subtotal - descuento;
    }

    public double total() {
        return base() + iva + envio;
    }
}
