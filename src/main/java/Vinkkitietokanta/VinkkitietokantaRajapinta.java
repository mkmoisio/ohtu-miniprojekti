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
    public boolean poistaVinkki(String otsikko);
    
    public boolean merkitseLuetuksi(String otsikko);
    public boolean merkitseLukemattomaksi(String otsikko);

    public boolean lisaaVinkki(Vinkki vinkki);
   
    public List<Vinkki> haeKaikkiKirjat(LukuStatus status);
    public List<Vinkki> haeKaikkiVideot(LukuStatus status);
    public List<Vinkki> haeKaikkiBlogpost(LukuStatus status);
    public List<Vinkki> haeKaikkiPodcast(LukuStatus status);
    public List<Vinkki> haeKaikki(LukuStatus status);
    
    public List<String> muunnaVinkkiLista(List<Vinkki> list);
}
