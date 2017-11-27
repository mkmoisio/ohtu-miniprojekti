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

/**
 * Itse käyttöliittymä.
 */
public class Kayttoliittyma {

    private final String KOMENNOT = "Komennot:"
            + "\n\t lisää - vinkin lisääminen"
            + "\n\t tulosta - tulosta kaikki vinkit"
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

        while (true) {
            this.tulostaKomennot();
            String komento = this.lukija.nextLine();

            switch (komento) {
                case "lisää":
                    this.tulostus.println("Anna kirjoittaja:");
                    kirjoittaja = this.lukija.nextLine();
                    this.tulostus.println("Anna otsikko:");
                    otsikko = this.lukija.nextLine();
                    if (this.lisaaKirjavinkki(kirjoittaja, otsikko)) {
                        this.tulostus.println("Kirjavinkki lisätty");
                    } else {
                        this.tulostus.println("Kirjavinkkiä ei lisätty");
                    }
                    break;

                case "tulosta":
                    this.tulostaKirjavinkit();
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

    public boolean lisaaKirjavinkki(String kirjoittaja, String otsikko) {
        int kirjoittajaPituus = kirjoittaja.length();
        int otsikkoPituus = otsikko.length();
        if (kirjoittaja.isEmpty() || otsikko.isEmpty()) {
            System.out.println("Ei saa olla tyhjä.");
            return false;
        } else if (kirjoittajaPituus > 50 || otsikkoPituus > 50) {
            System.out.println("Liian pitkä");
            return false;
        } else {
            return this.tk.lisaaKirja(kirjoittaja, otsikko);
        }
    }

    public List<Vinkki> haeKaikki() {
        return this.tk.haeKaikki();
    }

    public boolean poistaKirja(String otsikko) {
        if (otsikko.isEmpty()) {
            System.out.println("Otsikko ei voi olla tyhjä.");
            return false;
        } else {
        return this.tk.poistaKirja(otsikko);
    }
    }

    private void tulostaKirjavinkit() {
        for (Vinkki v : this.haeKaikki()) {
            this.tulostus.println(v.toString());
        }
    }

    private void tulostaKomennot() {
        this.tulostus.println(this.KOMENNOT);
    }

    private String muotoileTulosteVinkkilistasta(List<Vinkki> list) {
        StringBuilder sb = new StringBuilder();

        for (Vinkki v : list) {
            sb.append(v.toString());
        }
        return sb.toString();
    }
}
