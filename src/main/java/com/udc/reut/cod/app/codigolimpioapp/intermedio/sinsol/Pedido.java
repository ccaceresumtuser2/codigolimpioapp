package com.udc.reut.cod.app.codigolimpioapp.intermedio.sinsol;

import java.util.List;

/**
 * EJERCICIO INTERMEDIO (SIN SOLUCIÓN)
 *
 * Calcula el total a pagar de un pedido de una tienda en línea.
 *
 * Reglas de negocio:
 *   - Subtotal = suma de (precio * cantidad) de cada ítem.
 *   - Descuento por cliente sobre el subtotal: 1=NORMAL 0%, 2=FRECUENTE 5%, 3=VIP 10%.
 *   - Cupón "DESC10": 10% adicional sobre el subtotal si el subtotal es >= 100.000.
 *   - Base = subtotal - descuentos.  IVA = 19% de la base.
 *   - Envío: gratis si la base es >= 200.000, si no cuesta 10.000.
 *     Envío express suma 15.000 adicionales (siempre).
 *   - Total = base + IVA + envío.
 *
 * Tarea del estudiante: refactorizar aplicando
 *   1. Funciones pequeñas que hacen UNA sola cosa (extraer métodos).
 *   2. Evitar la obsesión por primitivos (String[] para ítems, int para tipo de cliente).
 *   3. Eliminar parámetros booleanos "bandera" (express).
 *   4. Reducir el número de parámetros (agruparlos en un objeto).
 *   5. Cláusulas de guarda en lugar de if anidados.
 *   6. Excepciones en lugar de códigos de error (-1).
 *   7. Separar el cálculo de la impresión por consola.
 */
public class Pedido {

    // SMELL: método largo, 4 parámetros, uno de ellos booleano (bandera) y un int "mágico"
    public double calc(List<String[]> items, int tc, boolean exp, String cup) {
        double t = 0;
        if (items != null) {
            if (items.size() > 0) {
                // SMELL: String[] {nombre, precio, cantidad} -> hay que recordar el orden de los índices
                for (String[] it : items) {
                    double p = Double.parseDouble(it[1]);
                    int c = Integer.parseInt(it[2]);
                    t = t + p * c;
                }

                // SMELL: if/else por tipo con números mágicos
                double d = 0;
                if (tc == 1) {
                    d = 0;
                } else if (tc == 2) {
                    d = t * 0.05;
                } else if (tc == 3) {
                    d = t * 0.10;
                } else {
                    System.out.println("tipo cliente invalido");
                    return -1; // SMELL: código de error en vez de excepción
                }

                if (cup != null) {
                    if (cup.equals("DESC10")) {
                        if (t >= 100000) {
                            d = d + t * 0.10;
                        }
                    }
                }

                double b = t - d;
                double iva = b * 0.19;

                double env = 0;
                if (b >= 200000) {
                    env = 0;
                } else {
                    env = 10000;
                }
                if (exp == true) { // SMELL: comparar un boolean con true
                    env = env + 15000;
                }

                double total = b + iva + env;
                // SMELL: mezcla cálculo con presentación (efecto secundario)
                System.out.println("Total pedido: " + total);
                return total;
            }
        }
        return 0;
    }
}
