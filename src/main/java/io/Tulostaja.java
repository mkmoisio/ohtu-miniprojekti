package io;

import java.io.PrintStream;

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
