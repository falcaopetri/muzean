package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Júnior
 */
public class Loop implements Estrutura {
    private int numeroRep;
    private List<Estrutura> estrutura =  new ArrayList<>();
    
    public Loop(int numeroRep){
        this.numeroRep = numeroRep;
    }
    
    public void add(Estrutura s){
        estrutura.add(s);
    }
    
    public void addAll(List<Estrutura> estrutura){
        this.estrutura = estrutura;
    }

    @Override
    public String generateCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
