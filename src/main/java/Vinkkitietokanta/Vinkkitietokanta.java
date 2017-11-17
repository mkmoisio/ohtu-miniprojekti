/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.util.List;

/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */
public class Vinkkitietokanta implements VinkkitietokantaRajapinta {
    
    public Vinkkitietokanta(){
    }

    @Override
    public boolean poistaKirja(String otsikko) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean lisaaKirja(String kirjoittaja, String otsikko) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> haeKaikkiString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vinkki> haeKaikki() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
