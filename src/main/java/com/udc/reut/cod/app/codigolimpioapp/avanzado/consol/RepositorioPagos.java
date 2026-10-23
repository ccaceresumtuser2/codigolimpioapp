package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

import java.util.List;

/**
 * Puerto de persistencia de pagos.
 *
 * Principio aplicado: DIP. ServicioNomina depende de esta abstracción, no de una base
 * de datos concreta. Se puede cambiar por JDBC, JPA o un archivo sin tocar el servicio.
 */
public interface RepositorioPagos {

    void guardar(RegistroPago registro);

    List<RegistroPago> listar();
}
