/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

/**
 *
 */
public class Vinkki {
    String kirjoittaja;
    String otsikko;
    public Vinkki(String kirjoittaja, String otsikko){
        this.kirjoittaja = kirjoittaja;
        this.otsikko = otsikko;
    }

    @Override
    public String toString() {
        return "Vinkki{" + "kirjoittaja=" + kirjoittaja + ", otsikko=" + otsikko + '}';
    }
    
    
}
