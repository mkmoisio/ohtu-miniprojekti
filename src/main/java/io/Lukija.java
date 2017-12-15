package io;

import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author mikkomo
 */
public class Lukija implements LukijaRajapinta {
 
    private Scanner scanner;
   
    public Lukija(InputStream is) {
        this.scanner = new Scanner(is);
    }

    @Override
    public String nextLine() {
        return this.scanner.nextLine();
    }
    
}
