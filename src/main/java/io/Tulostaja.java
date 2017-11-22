/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import Vinkkitietokanta.Vinkki;
import java.io.PrintStream;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public class Tulostaja implements TulostusRajapinta {
    private final PrintStream ps;

    public Tulostaja(PrintStream ps) {  
        this.ps = ps;
    }

    @Override
    public void println(String x) {
        ps.println(x);
        
    }
    
    @Override
    public void print(String x) {
        ps.print(x);
    }

}
