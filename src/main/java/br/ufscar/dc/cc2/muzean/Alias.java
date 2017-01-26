package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Júnior
 */
public class Alias implements Estrutura{
    private List<Estrutura> estruturas =  new ArrayList<>();
    
    public void addLista(Estrutura s){
        estruturas.add(s);
    }

    @Override
    public String generateCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
