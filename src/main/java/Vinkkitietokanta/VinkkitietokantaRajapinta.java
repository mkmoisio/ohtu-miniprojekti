/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.util.List;

/**
 *
 * 
 */
public interface VinkkitietokantaRajapinta {
    public boolean poistaKirja(String otsikko);
    public boolean lisaaKirja(String kirjoittaja, String otsikko);
    
    public List<String> haeKaikkiString();
    public List<Vinkki> haeKaikki();
}
