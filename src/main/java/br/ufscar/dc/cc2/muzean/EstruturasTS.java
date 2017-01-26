package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Júnior
 */
public class EstruturasTS extends EntradaTS {

    private List<Estrutura> estruturas = new ArrayList<>();

    public EstruturasTS(String nome, List<Estrutura> estruturas) {
        super(nome, Tipo.ESTRUTURAS);
        this.estruturas = estruturas;
    }

    public List<Estrutura> getEstruturas() {
        return estruturas;
    }

}
