package com.udc.reut.cod.app.codigolimpioapp.basico.sinsol;

import java.util.ArrayList;
import java.util.List;

/**
 * EJERCICIO BÁSICO (SIN SOLUCIÓN)
 *
 * Registro de notas de un estudiante. El código FUNCIONA, pero es difícil de leer.
 *
 * Tarea del estudiante: refactorizar aplicando
 *   1. Nombres significativos (clase, atributos, métodos, variables).
 *   2. Eliminar números mágicos (usar constantes con nombre).
 *   3. Eliminar comentarios que solo traducen un mal nombre.
 *   4. Encapsulamiento (no exponer la lista interna).
 *   5. No duplicar lógica (el promedio se calcula dos veces).
 *   6. No fallar en silencio (una nota inválida se ignora sin avisar).
 *   7. Reemplazar "Strings mágicos" por un tipo (enum).
 */
public class Calc { // SMELL: "Calc" no dice qué calcula ni de qué dominio es

    // lista de notas
    public List<Double> l = new ArrayList<>(); // SMELL: atributo público y nombre de una letra

    // agrega nota
    public void a(double n) { // SMELL: nombres "a" y "n" obligan a leer el comentario
        if (n >= 0 && n <= 5) { // SMELL: números mágicos 0 y 5
            l.add(n);
        }
        // SMELL: si la nota es inválida se ignora sin avisar (falla silenciosa)
    }

    // promedio
    public double prom() {
        double s = 0; // SMELL: "s" = suma
        for (int i = 0; i < l.size(); i++) {
            s = s + l.get(i);
        }
        if (l.size() == 0) {
            return 0;
        }
        return s / l.size();
    }

    // resultado
    public String res() {
        // SMELL: lógica duplicada, vuelve a calcular el promedio en lugar de reutilizar prom()
        double s = 0;
        for (int i = 0; i < l.size(); i++) {
            s = s + l.get(i);
        }
        double r = l.size() == 0 ? 0 : s / l.size();

        if (r >= 4.5) {           // SMELL: número mágico
            return "EXCELENTE";   // SMELL: String mágico, el cliente puede escribirlo mal
        } else if (r >= 3) {      // SMELL: número mágico
            return "APROBADO";
        } else {
            return "REPROBADO";
        }
    }
}
