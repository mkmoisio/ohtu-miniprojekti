/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import io.LukijaRajapinta;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public class LukijaStub implements LukijaRajapinta{
    
    private ArrayDeque<String> output;
    
    public LukijaStub(){
    }
    
    public void lisaaSyote(String string) {
        output.add(string);
    }

    @Override
    public String nextLine() {
        return output.pop();
    }
    
}
