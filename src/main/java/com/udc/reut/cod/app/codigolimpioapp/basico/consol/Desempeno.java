package com.udc.reut.cod.app.codigolimpioapp.basico.consol;

/**
 * Desempeño académico según el promedio.
 *
 * Principio aplicado: un enum reemplaza los "Strings mágicos" ("EXCELENTE", "APROBADO"...).
 * El compilador impide errores de digitación y el IDE autocompleta los valores.
 */
public enum Desempeno {
    EXCELENTE,
    APROBADO,
    REPROBADO
}
