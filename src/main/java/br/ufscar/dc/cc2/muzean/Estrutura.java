package br.ufscar.dc.cc2.muzean;

import org.antlr.v4.runtime.misc.Pair;

/**
 *
 * @author Júnior
 */
interface Estrutura {

    String generateCode();
    Pair<String, String> generateArduinoCode();
}
