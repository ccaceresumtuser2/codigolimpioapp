package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Puerto de notificación (correo, SMS, consola...).
 *
 * Principio aplicado: DIP. En pruebas se reemplaza por un doble que registra los mensajes.
 */
public interface Notificador {

    void notificar(String destinatario, String mensaje);
}
