package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

/**
 * Un producto dentro del pedido.
 *
 * Principio aplicado: objeto de valor en lugar de String[] (obsesión por primitivos).
 * Los campos tienen nombre y tipo, y la validación vive junto a los datos.
 */
public record ItemPedido(String nombre, double precioUnitario, int cantidad) {

    public ItemPedido {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }

    public double subtotal() {
        return precioUnitario * cantidad;
    }
}
