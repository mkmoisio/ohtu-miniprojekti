/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import io.TulostusRajapinta;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author mikkomo
 */
public class TulostusStub implements TulostusRajapinta{
    
    private Deque<String> tulosteet;
    
    public TulostusStub(){
        this.tulosteet = new ArrayDeque();
    }

    @Override
    public void println(String x) {
        this.tulosteet.add(x);
    }
    
    public String pop() {
        return this.tulosteet.pop();
    }
}
