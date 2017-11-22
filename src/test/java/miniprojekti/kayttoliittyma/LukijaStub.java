/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import io.LukijaRajapinta;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 *
 * @author mikkomo
 */
public class LukijaStub implements LukijaRajapinta {

    private ArrayDeque<String> output;

    public LukijaStub() {
        this.output = new ArrayDeque();
    }

    public void lisaaSyote(String syote) {
        this.output.add(syote);
    }
    
    public void lisaaSyote(String... syote) {
        this.output.addAll(Arrays.asList(syote));
    }

    @Override
    public String nextLine() {
        return output.pop();
    }
    
    public void nollaa() {
        this.output.clear();
    }
    
//    public void kaikki() {
//       
//        for (String s : this.output) {
//            System.out.println(s);
//        }
//    }

}
