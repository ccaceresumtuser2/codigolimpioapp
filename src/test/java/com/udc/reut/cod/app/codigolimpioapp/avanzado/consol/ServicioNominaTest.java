package com.udc.reut.cod.app.codigolimpioapp.avanzado.consol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Gracias a DIP, el servicio se prueba con dobles de prueba simples (sin Mockito):
 * un repositorio en memoria nuevo por prueba y un notificador que registra los mensajes.
 */
class ServicioNominaTest {

    private static final double DELTA = 0.01;

    /** Doble de prueba (spy): guarda los mensajes en vez de enviarlos. */
    private static class NotificadorEspia implements Notificador {
        final List<String> destinatarios = new ArrayList<>();

        @Override
        public void notificar(String destinatario, String mensaje) {
            destinatarios.add(destinatario);
        }
    }

    private RepositorioPagos repositorio;
    private NotificadorEspia notificador;
    private ServicioNomina servicio;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioPagosEnMemoria();
        notificador = new NotificadorEspia();
        servicio = new ServicioNomina(repositorio, notificador);
    }

    @Test
    void procesaLosTresTiposDeEmpleado() {
        List<Empleado> empleados = List.of(
                new EmpleadoPlanta("Ana", "ana@udc.edu.co", 2_400_000, 10),
                new Contratista("Luis", "luis@udc.edu.co", 100, 30_000),
                new Practicante("Eva", "eva@udc.edu.co", 1_300_000));

        double total = servicio.procesar(empleados);

        assertEquals(6_333_000, total, DELTA);
        assertEquals(new RegistroPago("Ana", 2_333_000), repositorio.listar().get(0));
        assertEquals(List.of("ana@udc.edu.co", "luis@udc.edu.co", "eva@udc.edu.co"), notificador.destinatarios);
    }

    @Test
    void sinEmpleadosElTotalEsCero() {
        assertEquals(0, servicio.procesar(List.of()), DELTA);
        assertEquals(0, repositorio.listar().size());
    }

    // --- Cada regla de pago se prueba de forma aislada (imposible en la versión sin solución) ---

    @Test
    void empleadoPlantaRecibeHorasExtraMenosSaludYPension() {
        // bruto = 2.400.000 + 10 * 12.500 = 2.525.000 ; deducciones = 192.000
        assertEquals(2_333_000, new EmpleadoPlanta("Ana", "a", 2_400_000, 10).calcularPagoNeto(), DELTA);
    }

    @Test
    void contratistaRecibeHonorariosMenosRetencion() {
        assertEquals(2_700_000, new Contratista("Luis", "l", 100, 30_000).calcularPagoNeto(), DELTA);
    }

    @Test
    void practicanteRecibeElApoyoCompleto() {
        assertEquals(1_300_000, new Practicante("Eva", "e", 1_300_000).calcularPagoNeto(), DELTA);
    }

    @Test
    void empleadoConSalarioNegativoNoSePuedeCrear() {
        assertThrows(IllegalArgumentException.class, () -> new EmpleadoPlanta("Error", "x", -1, 0));
    }

    @Test
    void agregarUnTipoNuevoNoRequiereModificarElServicio() {
        // OCP: un tipo nuevo se define sin tocar ServicioNomina
        Empleado pensionado = new Empleado() {
            public String nombre() { return "Raúl"; }
            public String correo() { return "raul@udc.edu.co"; }
            public double calcularPagoNeto() { return 1_000_000; }
        };

        assertEquals(1_000_000, servicio.procesar(List.of(pensionado)), DELTA);
    }
}
