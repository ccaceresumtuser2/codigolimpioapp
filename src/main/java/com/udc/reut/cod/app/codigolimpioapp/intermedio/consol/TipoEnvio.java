package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

/**
 * Modalidad de envío y su recargo.
 *
 * Principio aplicado: reemplaza el parámetro booleano "bandera" (express = true/false).
 * {@code TipoEnvio.EXPRESS} se lee mejor que {@code true} en la llamada.
 */
public enum TipoEnvio {
    ESTANDAR(0),
    EXPRESS(15_000);

    private final double recargo;

    TipoEnvio(double recargo) {
        this.recargo = recargo;
    }

    public double recargo() {
        return recargo;
    }
}
