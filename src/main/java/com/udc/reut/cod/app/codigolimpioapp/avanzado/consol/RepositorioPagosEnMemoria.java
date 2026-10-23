package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria del repositorio. Cada instancia tiene su propio estado,
 * a diferencia de la lista estática del ejercicio sin solución.
 */
public class RepositorioPagosEnMemoria implements RepositorioPagos {

    private final List<RegistroPago> registros = new ArrayList<>();

    @Override
    public void guardar(RegistroPago registro) {
        registros.add(registro);
    }

    @Override
    public List<RegistroPago> listar() {
        return List.copyOf(registros);
    }
}
