/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Júnior
 */
public class Escala {

    static private List<Integer> lista = new ArrayList<>();

    static public void setEscala(String nota, String escala) {
        ArrayList<Integer> notas = new ArrayList<>();
        if (escala.equals("M")) {
            Integer notas_arr[] = {0, 2, 4, 5, 7, 9, 11};
            notas.addAll(Arrays.asList(notas_arr));
        } else {
            Integer notas_arr[] = {0, 2, 3, 5, 7, 8, 10};
            notas.addAll(Arrays.asList(notas_arr));
        }

        for (Integer i : notas) {
            lista.add((getNoteBaseNumber(nota) + i) % 12);
        }
    }

    static public boolean verifyScale(String nota) {
        for (Integer i : lista) {
            if (getNoteBaseNumber(nota) == i) {
                return true;
            }
        }
        return false;
    }

    static void clear() {
        lista.clear();
    }

    static public int getNoteBaseNumber(String c) {
        // TODO fazer um map melhor
        switch (c) {
            case "C":
                return 0;
            case "C#":
                return 1;
            case "D":
                return 2;
            case "D#":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "F#":
                return 6;
            case "G":
                return 7;
            case "G#":
                return 8;
            case "A":
                return 9;
            case "A#":
                return 10;
            case "B":
                return 11;
            default:
                throw new AssertionError("nota inválida");
        }
    }
}
