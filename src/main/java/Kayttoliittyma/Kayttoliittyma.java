/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.Validator;
import io.Lukija;
import io.LukijaRajapinta;
import io.Tulostaja;
import io.TulostusRajapinta;
import java.util.List;

/**
 * Itse käyttöliittymä.
 */
public class Kayttoliittyma {

    private final String KOMENNOT = "Komennot:"
            + "\n\t lisää kirja - kirjavinkin lisääminen"
            + "\n\t lisää podcast - podcast-vinkin lisääminen"
            + "\n\t tulosta kaikki - tulosta kaikki vinkit"
            + "\n\t tulosta kirjat - tulosta kaikki kirjavinkit"
            + "\n\t tulosta podcastit - tulosta kaikki podcastit"
            + "\n\t poista - poista vinkki"
            + "\n\t lopeta - lopeta ohjelma "
            + "\n";

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

    public void suorita() {

        String kirjoittaja;
        String otsikko;
        String nimi;
        while (true) {
            this.tulostaKomennot();
            String komento = this.lukija.nextLine();

            switch (komento) {
                case "lisää kirja":
                    this.lisaaKirjavinkki();
                    break;

                case "lisää podcast":
                    this.lisaaPodcast();
                    break;
                //break; MUISTA TÄLLÄ KERTAA LAITTAA BREAK KUN LISÄÄT TÄTÄ
                case "tulosta kirjat":
                    this.tulostaKirjavinkit();
                    break;
                case "tulosta kaikki":
                    this.tulostaKaikkiVinkit();
                    break;
                case "tulosta lukemattomat":
                    this.tulostaKaikkiLukemattomat();
                    break;

                case "tulosta podcastit":
                    this.tulostaPodcastit();
                    break;
                case "poista":
                    this.tulostus.println("Anna otsikko:");
                    otsikko = this.lukija.nextLine();
                    if (this.poistaKirja(otsikko)) {
                        this.tulostus.println("Kirjavinkki poistettu");
                    } else {
                        this.tulostus.println("Kirjavinkkiä ei poistettu");
                    }

                    break;
                case "lopeta":
                    return;
                default:

            }

        }

    }

    private void lisaaPodcast() {
        this.tulostus.println("Anna nimi:");
        String nimi = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();
        this.tulostus.println("Anna kuvaus:");
        String kuvaus = this.lukija.nextLine();

        if (Validator.podcastvinkinSyoteOk(nimi, otsikko, kuvaus)) {
            if (this.tk.lisaaPodcast(nimi, otsikko, kuvaus)) {
                this.tulostus.println("Podcast lisätty");
                return;
            }
        }
        this.tulostus.println("Podcastia ei lisätty");
    }

    private void lisaaKirjavinkki() {
        this.tulostus.println("Anna kirjoittaja:");
        String kirjoittaja = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();

        if (Validator.kirjavinkinSyoteOk(kirjoittaja, otsikko)) {
            this.tk.lisaaKirja(kirjoittaja, otsikko);
            this.tulostus.println("Kirjavinkki lisätty");
            return;
        }
        this.tulostus.println("Kirjavinkkiä ei lisätty");
    }

    private void lisaaVideo() {

    }

    /* KANNAN METODEITA KUTSUVAT METODIT */

    public List<Vinkki> haeKaikkiKirjavinkit() {
        return this.tk.haeKaikkiKirjat(LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiPodcastvinkit() {
        return this.tk.haeKaikkiPodcast(LukuStatus.KAIKKI);
    }

    public boolean poistaKirja(String otsikko) {
        return this.tk.poistaKirja(otsikko);
    }
    
    // KAIKKI
     private List<Vinkki> haeKaikkiVinkit() {
        return this.tk.haeKaikki();
    }
     
    private List<Vinkki> haeKaikkuLukemattomat() {
        return this.tk.haeKaikki(LukuStatus.LUKEMATTOMAT);
    }

    /* KANTAA KUTSUVAT METODIT LOPPUVAT TÄHÄN*/
    
    
    private void tulostaKirjavinkit() {
        tulostaLista(this.haeKaikkiKirjavinkit());
    }

    private void tulostaPodcastit() {
        tulostaLista(this.haeKaikkiPodcastvinkit());
    }

    private void tulostaKomennot() {
        this.tulostus.println(this.KOMENNOT);
    }

    private void tulostaKaikkiVinkit() {
        this.tulostaLista(this.haeKaikkiVinkit());
    }


    private void tulostaKaikkiLukemattomat() {
        this.tulostaLista(this.haeKaikkuLukemattomat());
    }

    private void tulostaLista(List<Vinkki> lista) {
        for (Vinkki v : lista) {
            this.tulostus.println(v.toString());
        }
    }


    
    

}
