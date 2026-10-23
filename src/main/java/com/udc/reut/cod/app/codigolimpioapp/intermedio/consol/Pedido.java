package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

import java.util.List;
import java.util.Optional;

/**
 * Datos de entrada de un pedido.
 *
 * Principio aplicado: "objeto parámetro". Agrupa los 4 parámetros del método original
 * en un solo objeto con nombre, y valida que el pedido tenga al menos un ítem.
 */
public record Pedido(List<ItemPedido> items, TipoCliente tipoCliente, TipoEnvio tipoEnvio, String codigoCupon) {

    public Pedido {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un ítem");
        }
        if (tipoCliente == null || tipoEnvio == null) {
            throw new IllegalArgumentException("Tipo de cliente y tipo de envío son obligatorios");
        }
        items = List.copyOf(items);
    }

    public Pedido(List<ItemPedido> items, TipoCliente tipoCliente, TipoEnvio tipoEnvio) {
        this(items, tipoCliente, tipoEnvio, null);
    }

    public Optional<String> cupon() {
        return Optional.ofNullable(codigoCupon);
    }
}
