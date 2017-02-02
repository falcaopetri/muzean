/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.muzean;

/**
 *
 * @author Júnior
 */
public class Som {

    private String nota;
    private int duration;

    void incrementDuration() {
        duration++;
    }

    String getNota() {
        return nota;
    }

    Som(Som s) {
        this.nota = s.getNota();
        this.duration = s.getDuration();
    }

    Integer getDuration() {
        return duration;
    }

    public Som(String nota) {
        this.nota = nota;
        this.duration = 1;
    }

    @Override
    public String toString() {
        return getNoteNumber();
    }

    public String toArduinoCode() {
        if (nota.equals("-")) {
            return "0";
        } else {
            return "NOTE_" + nota.replace("#", "S");
        }
    }

    private String getNoteNumber() {
        // TODO comportamento confuso do *
        int transposicao = TabelaDeSimbolos.getTransposicao();
        String out = "";
        if (nota.length() == 2) {
            // LETTER OCTAVE
            int number = getNoteBaseNumber(nota.charAt(0)) + 12 * Integer.parseInt(Character.toString(nota.charAt(1))) + 12;
            return Integer.toString(number + transposicao);
        } else if (nota.length() == 3) {
            int number = getNoteBaseNumber(nota.charAt(0)) + 12 * Integer.parseInt(Character.toString(nota.charAt(2))) + 12;
            number += 1;
            return Integer.toString(number + transposicao);
        } else if (nota.equals("*")) {
            return "*";
        } else if (nota.equals("-")) {
            return "'-'";
        } else {
            throw new AssertionError("nota inválida: " + nota);
        }
    }

    private int getNoteBaseNumber(char c) {
        // TODO fazer um map melhor
        switch (c) {
            case 'C':
                return 0;
            case 'D':
                return 2;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 7;
            case 'A':
                return 9;
            case 'B':
                return 11;
            default:
                throw new AssertionError("nota inválida");
        }
    }
}
