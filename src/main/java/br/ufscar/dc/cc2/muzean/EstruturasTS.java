/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Júnior
 */
public class EstruturasTS extends EntradaTS{
    
    private List<Estrutura> estruturas =  new ArrayList<>();

    public EstruturasTS(String nome, List<Estrutura> estruturas){
        super(nome, Tipo.ESTRUTURAS);
        this.estruturas = estruturas;
    }
    
    
}
