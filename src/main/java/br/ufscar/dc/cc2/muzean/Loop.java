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
public class Loop extends Estrutura{
    private int numeroRep;
    private List<Estrutura> estrutura =  new ArrayList<>();
    
    public void addLista(Estrutura s){
        estrutura.add(s);
    }
    
}
