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
public class Compasso extends Estrutura{
    private List<Som> sons =  new ArrayList<>();
    
    public Compasso(){
    }
    
    public void add(Som s){
        sons.add(s);
    }
    
    public void addAll(List<Som> sons){
        this.sons = sons;
    }
}
