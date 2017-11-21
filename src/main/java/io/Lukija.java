/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
