package com.udc.reut.cod.app.codigolimpioapp.intermedio.consol;

/**
 * EJERCICIO INTERMEDIO (CON SOLUCIÓN)
 *
 * Calcula el total a pagar de un pedido (mismas reglas que {@code intermedio.sinsol.Pedido}).
 *
 * Principios de código limpio aplicados:
 *   - Funciones pequeñas: cada regla de negocio está en su propio método con nombre.
 *   - Un solo nivel de abstracción: calcular() se lee como un resumen de los pasos.
 *   - Sin banderas ni ints mágicos: TipoCliente y TipoEnvio.
 *   - Objeto parámetro (Pedido) y objeto de resultado (ResumenPedido).
 *   - Constantes con nombre para tasas y montos.
 *   - Sin efectos secundarios: no imprime nada.
 */
public class CalculadoraPedido {

    static final String CUPON_DESCUENTO = "DESC10";
    static final double PORCENTAJE_CUPON = 0.10;
    static final double MINIMO_PARA_CUPON = 100_000;
    static final double TASA_IVA = 0.19;
    static final double COSTO_ENVIO_ESTANDAR = 10_000;
    static final double MINIMO_ENVIO_GRATIS = 200_000;

    public ResumenPedido calcular(Pedido pedido) {
        double subtotal = calcularSubtotal(pedido);
        double descuento = calcularDescuentoCliente(pedido, subtotal) + calcularDescuentoCupon(pedido, subtotal);
        double base = subtotal - descuento;
        return new ResumenPedido(subtotal, descuento, calcularIva(base), calcularEnvio(pedido, base));
    }

    private double calcularSubtotal(Pedido pedido) {
        return pedido.items().stream()
                .mapToDouble(ItemPedido::subtotal)
                .sum();
    }

    private double calcularDescuentoCliente(Pedido pedido, double subtotal) {
        return subtotal * pedido.tipoCliente().porcentajeDescuento();
    }

    private double calcularDescuentoCupon(Pedido pedido, double subtotal) {
        boolean cuponValido = pedido.cupon().filter(CUPON_DESCUENTO::equals).isPresent();
        if (!cuponValido || subtotal < MINIMO_PARA_CUPON) {
            return 0;
        }
        return subtotal * PORCENTAJE_CUPON;
    }

    private double calcularIva(double base) {
        return base * TASA_IVA;
    }

    private double calcularEnvio(Pedido pedido, double base) {
        double costoBase = base >= MINIMO_ENVIO_GRATIS ? 0 : COSTO_ENVIO_ESTANDAR;
        return costoBase + pedido.tipoEnvio().recargo();
    }
}
