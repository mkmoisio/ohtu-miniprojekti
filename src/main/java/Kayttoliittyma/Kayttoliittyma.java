/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.Lukija;
import io.LukijaRajapinta;
import io.Tulostaja;
import io.TulostusRajapinta;
import java.util.List;
import java.util.Scanner;

/**
 * Itse käyttöliittymä.
 */
public class Kayttoliittyma {

    VinkkitietokantaRajapinta tk;
    LukijaRajapinta lukija;
    TulostusRajapinta tulostus;

    public Kayttoliittyma(VinkkitietokantaRajapinta tk) {
        this.tk = tk;
        this.lukija = new Lukija(System.in);
        this.tulostus = new Tulostaja(System.out);
        
    }

    public void setLukija(LukijaRajapinta lukija) {
        this.lukija = lukija;
    }

    public void setTulostus(TulostusRajapinta tulostus) {
        this.tulostus = tulostus;
    }
    
    
    
//    public Kayttoliittyma(VinkkitietokantaRajapinta tk, LukijaRajapinta lukija) {
//         this.tk = tk;
//         this.lukija = lukija;
//    }
    
  
    public void suorita() {

        while (true) {
            this.tulostus.println("Anna komento");
            String komento = this.lukija.nextLine();

            if (komento.equals("lisää")) {
                this.tulostus.println("Kirjoittaja");
                String kirjoittaja = this.lukija.nextLine();
                this.tulostus.println("Otsikko");
                String otsikko = this.lukija.nextLine();
                if (this.lisaaKirjavinkki(kirjoittaja, otsikko)) {
                    this.tulostus.println("Kirjavinkki lisätty");
                } else {
                    this.tulostus.println("Kirjavinkkiä ei lisätty");
                }
            }
            
            if (komento.equals("lopeta")) {
                return;
            }
        }

    }

    public boolean lisaaKirjavinkki(String kirjoittaja, String otsikko) {
        if (kirjoittaja.isEmpty() || otsikko.isEmpty()) {
            return false;
        } else {
            return this.tk.lisaaKirja(kirjoittaja, otsikko);
        }
    }
    
    public List<Vinkki> haeKaikki(String kirjoittaja) {
       return this.tk.haeKaikki();
    }
    
    public boolean poistaKirja(String otsikko) {
       return this.tk.poistaKirja(otsikko);
    }
    
    private void tulostaKomennot() {
       
    }

}
