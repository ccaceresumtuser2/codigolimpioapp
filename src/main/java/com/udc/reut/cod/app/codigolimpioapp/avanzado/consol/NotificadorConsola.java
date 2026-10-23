package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

/**
 * Notificador que escribe en la consola. Útil para demostraciones.
 */
public class NotificadorConsola implements Notificador {

    @Override
    public void notificar(String destinatario, String mensaje) {
        System.out.println("Enviando correo a " + destinatario + ": " + mensaje);
    }
}
