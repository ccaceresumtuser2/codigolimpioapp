package com.udc.reut.cod.app.codigolimpioapp.avanzado.sinsol;

import java.util.ArrayList;
import java.util.List;

/**
 * EJERCICIO AVANZADO (SIN SOLUCIÓN)
 *
 * Procesa la nómina mensual de una empresa.
 *
 * Reglas de negocio:
 *   - PLANTA:      bruto = salario + horasExtra * (salario / 240) * 1.25
 *                  neto  = bruto - 8% del salario (4% salud + 4% pensión)
 *   - CONTRATISTA: honorarios = horas * valorHora;  neto = honorarios - 10% de retención
 *   - PRACTICANTE: neto = apoyo de sostenimiento (salario), sin deducciones
 *   Por cada empleado se guarda un registro "nombre;neto" y se le envía un correo.
 *
 * Tarea del estudiante: refactorizar aplicando SOLID
 *   1. SRP: esta clase calcula, valida, guarda, notifica y reporta (5 responsabilidades).
 *   2. OCP: el switch por tipo obliga a modificar esta clase para cada tipo nuevo.
 *          -> usar polimorfismo (un tipo de empleado por clase).
 *   3. DIP: depende de detalles concretos (lista estática, System.out).
 *          -> depender de interfaces inyectadas por constructor.
 *   4. LSP/ISP: Empleado tiene campos que no aplican a todos los tipos.
 *   5. Testabilidad: el estado global estático contamina las pruebas entre sí.
 */
public class GestorNomina {

    // SMELL: "base de datos" global y estática -> estado compartido entre pruebas
    public static List<String> BD = new ArrayList<>();

    private List<Empleado> empleados = new ArrayList<>();

    // SMELL: 6 parámetros primitivos; fácil confundir el orden
    public void agregarEmpleado(String nombre, String tipo, double salario, int horas, double valorHora, String correo) {
        Empleado e = new Empleado();
        e.nombre = nombre;
        e.tipo = tipo;
        e.salario = salario;
        e.horas = horas;
        e.valorHora = valorHora;
        e.correo = correo;
        empleados.add(e);
    }

    // SMELL: método "Dios" que hace todo
    public double procesarNomina() {
        double total = 0;
        for (Empleado e : empleados) {
            // 1. validación
            if (e.salario < 0 || e.horas < 0) {
                System.out.println("Empleado invalido: " + e.nombre);
                continue;
            }

            // 2. cálculo -> SMELL: switch por tipo (viola OCP)
            double neto;
            switch (e.tipo) {
                case "PLANTA":
                    double bruto = e.salario + e.horas * (e.salario / 240) * 1.25;
                    neto = bruto - e.salario * 0.08;
                    break;
                case "CONTRATISTA":
                    double hon = e.horas * e.valorHora;
                    neto = hon - hon * 0.10;
                    break;
                case "PRACTICANTE":
                    neto = e.salario;
                    break;
                default:
                    System.out.println("Tipo desconocido: " + e.tipo);
                    continue;
            }

            // 3. persistencia -> SMELL: acoplado a un detalle concreto
            BD.add(e.nombre + ";" + neto);

            // 4. notificación -> SMELL: acoplado a la consola, imposible de verificar en pruebas
            System.out.println("Enviando correo a " + e.correo + ": su pago es " + neto);

            total += neto;
        }
        // 5. reporte
        System.out.println("Total nomina: " + total);
        return total;
    }
}
