package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

/**
 * Tipo de cliente con su porcentaje de descuento.
 *
 * Principio aplicado: el enum reemplaza el int mágico (1, 2, 3) y la cadena de if/else.
 * Cada constante conoce su propio descuento; un tipo inválido ya no puede existir.
 */
public enum TipoCliente {
    NORMAL(0.00),
    FRECUENTE(0.05),
    VIP(0.10);

    private final double porcentajeDescuento;

    TipoCliente(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double porcentajeDescuento() {
        return porcentajeDescuento;
    }
}
