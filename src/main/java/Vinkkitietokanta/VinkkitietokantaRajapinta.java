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
    public boolean poistaVinkki(String otsikko);
    
    public boolean merkitseLuetuksi(String otsikko);
    public boolean merkitseLukemattomaksi(String otsikko);

    //public boolean lisaaKirja(String otsikko); //onko käytössä?
    public boolean lisaaKirja(Vinkki vinkki); 
    public boolean lisaaPodcast(Vinkki vinkki); 
    public boolean lisaaVideo(Vinkki vinkki); 
    public boolean lisaaBlogpost(Vinkki vinkki); 

    public boolean lisaaKirja(String kirjoittaja, String otsikko, List<String> tagnimet);
    public boolean lisaaPodcast(String nimi, String otsikko, String kuvaus);
    public boolean lisaaVideo(String url, String otsikko);
    public boolean lisaaBlogpost(String kirjoittaja, String url, String otsikko);

    public boolean lisaaVinkki(Vinkki vinkki);
   
    public List<Vinkki> haeKaikkiKirjat(LukuStatus status);
    public List<String> haeKaikkiKirjatString(LukuStatus status);
    
    public List<Vinkki> haeKaikkiVideot(LukuStatus status);
    public List<String> haeKaikkiVideotString(LukuStatus status);
    
    public List<Vinkki> haeKaikkiBlogpost(LukuStatus status);
    public List<String> haeKaikkiBlogpostString(LukuStatus status);
 
    public List<Vinkki> haeKaikkiPodcast(LukuStatus status);
    public List<String> haeKaikkiPodcastString(LukuStatus status);  

    public List<Vinkki> haeKaikki(LukuStatus status);
    public List<Vinkki> haeKaikki();
    public List<String> haeKaikkiString(LukuStatus status);   
    public List<String> haeKaikkiString();   
    
}
