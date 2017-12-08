package Kayttoliittyma;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
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
            + "\n\t lisää video - videovinkin lisääminen"
            + "\n\t lisää blogpost - blogpost-vinkin lisääminen"
            + "\n\t tulosta kaikki - tulosta kaikki vinkit"
            + "\n\t lukemattomat - tulosta kaikki lukemattomat vinkit"
            + "\n\t luetut - tulosta kaikki luetut vinkit"
            + "\n\t tulosta kirjat - tulosta kaikki kirjavinkit"
            + "\n\t tulosta podcastit - tulosta kaikki podcastit"
            + "\n\t tulosta videot - tulosta kaikki videot"
            + "\n\t tulosta blogpostit - tulosta kaikki blogpostit"
            + "\n\t merkitse luetuksi - merkitse luetuksi"
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

        while (true) {
            this.tulostaKomennot();
            String komento = this.lukija.nextLine().toLowerCase();

            switch (komento) {

                /* LISÄÄMINEN ALKAA */
                case "lisää kirja":
                    this.lisaaKirjavinkki();
                    break;
                case "lisää podcast":
                    this.lisaaPodcast();
                    break;
                case "lisää video":
                    this.lisaaVideo();
                    break;
                case "lisää blogpost":
                    this.lisaaBlogpost();
                    break;
                /* LISÄÄMINEN LOPPUU */
                //case "muunna vinkkiä":
                //    this.muunnaVinkkia();
                //    break;
 /* TULOSTUS ALKAA */
                case "tulosta kaikki":
                    this.tulostaKaikkiVinkit();
                    break;
                case "lukemattomat":
                    this.tulostaKaikkiLukemattomat();
                    break;
                case "luetut":
                    this.tulostaKaikkiLuetut();
                    break;
                case "tulosta kirjat":
                    this.tulostaKirjavinkit();
                    break;
                case "tulosta podcastit":
                    this.tulostaPodcastit();
                    break;
                case "tulosta videot":
                    this.tulostaVideot();
                    break;
                case "tulosta blogpostit":
                    this.tulostaBlogpostit();
                    break;
                /* TULOSTUS LOPPUU */

                case "merkitse luetuksi":
                    this.merkitseLuetuksi();
                    break;
                case "poista":
                    this.poista();
                    break;

                case "lopeta":
                    return;
                default:

            }

        }

    }

    private void poista() {
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();
        if (this.poistaVinkki(otsikko)) {
            this.tulostus.println("Vinkki poistettu");
        } else {
            this.tulostus.println("Vinkkiä ei poistettu");
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
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.PODCAST);
            vinkki.lisaaOminaisuus(Attribuutit.NIMI, nimi);
            vinkki.lisaaOminaisuus(Attribuutit.KUVAUS,kuvaus);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
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
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.KIRJA);
            vinkki.lisaaTekija(kirjoittaja);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Kirjavinkki lisätty");
                return;
            }
        }
        this.tulostus.println("Kirjavinkkiä ei lisätty");
    }

    private void lisaaVideo() {
        this.tulostus.println("Anna url:");
        String url = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();

        if (Validator.videovinkinSyoteOk(url, otsikko)){
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.VIDEO);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Video lisätty");
                return;
            }
        }
        this.tulostus.println("Videota ei lisätty");
    }

    private void lisaaBlogpost() {
        this.tulostus.println("Anna url:");
        String url = this.lukija.nextLine();
        this.tulostus.println("Anna kirjoittaja:");
        String kirjoittajat = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();
        
        if (Validator.videovinkinSyoteOk(url, otsikko)) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.BLOGPOST);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            vinkki.lisaaTekija(kirjoittajat);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Blogpost lisätty");
                return;
            }
        }
        this.tulostus.println("Blogpost ei lisätty");
    }

    private void merkitseLuetuksi() {
        this.tulostus.println("Anna sen vinkin otsikko, joka merkitään luetuksi");
        String otsikko = this.lukija.nextLine();
        if(!this.tk.merkitseLuetuksi(otsikko)){
            this.tulostus.println("Virhe: Vinkkiä "+otsikko+" ei löytynyt");
            return;
        }
        this.tulostus.println("Vinkki otsikolla "+otsikko+" merkitty luetuksi");
    }
    
    //Kysyy käyttäjältä tageja
    private void lisaaTagit(Vinkki vinkki){
        this.tulostus.println("Anna tageja, tyhjä syöte palauttaa aloitusnäyttöön");
        System.out.println("");

        while(true){
            String tagSyote= lukija.nextLine();
            if (tagSyote.isEmpty()){
                break;
            }
            /*
            if (vinkki.onkoTagia(tagSyote)){
                System.out.println("Samanniminen tag on jo olemassa");
                this.lisaaTagit(vinkki);
            }*/
            if (Validator.taginPituusOk(tagSyote)){
            vinkki.lisaaTag(tagSyote);
            }
        }
    }
 

    /* KANNAN METODEITA KUTSUVAT METODIT */
    private List<Vinkki> haeKaikkiVinkit() {
        return this.tk.haeKaikki(LukuStatus.KAIKKI);
    }

    private List<Vinkki> haeKaikkuLukemattomat() {
        return this.tk.haeKaikki(LukuStatus.LUKEMATTOMAT);
    }

    private List<Vinkki> haeKaikkiLuetut() {
        return this.tk.haeKaikki(LukuStatus.LUETTU);
    }

    public List<Vinkki> haeKaikkiKirjavinkit() {
        return this.tk.haeKaikki(Formaatit.KIRJA,LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiPodcastvinkit() {
        return this.tk.haeKaikki(Formaatit.PODCAST,LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiVideovinkit() {
        return this.tk.haeKaikki(Formaatit.VIDEO,LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiBlogpostvinkit() {
        return this.tk.haeKaikki(Formaatit.BLOGPOST,LukuStatus.KAIKKI);
    }

    private boolean poistaVinkki(String otsikko) {
        return this.tk.poistaVinkki(otsikko);
    }
    /* KANTAA KUTSUVAT METODIT LOPPUVAT TÄHÄN*/

 /* TULOSTUS */
    private void tulostaKomennot() {
        this.tulostus.println(this.KOMENNOT);
    }
    
    private void tulostaKirjavinkit() {
        tulostaLista(this.haeKaikkiKirjavinkit());
    }

    private void tulostaPodcastit() {
        tulostaLista(this.haeKaikkiPodcastvinkit());
    }

    private void tulostaVideot() {
        tulostaLista(this.haeKaikkiVideovinkit());
    }

    private void tulostaBlogpostit() {
        tulostaLista(this.haeKaikkiBlogpostvinkit());
    } 

    private void tulostaKaikkiVinkit() {
        this.tulostaLista(this.haeKaikkiVinkit());
    }

    private void tulostaKaikkiLukemattomat() {
        this.tulostaLista(this.haeKaikkuLukemattomat());
    }

    private void tulostaKaikkiLuetut() {
        this.tulostaLista(this.haeKaikkiLuetut());
    }

    private void tulostaLista(List<Vinkki> lista) {
        for (Vinkki v : lista) {
            this.tulostus.println(v.toString());
        }
    }
    /* TULOSTUS PÄÄTTYY*/
    /*
    private void muunnaVinkkia() {
        this.tulostus.println("Anna vinkin otsikko, jota muutetaan");
        System.out.println("");
        String otsikko = this.lukija.nextLine();
        Vinkki vinkki = tk.haeVinkki(otsikko);
        if(vinkki==null) {
            this.tulostus.println("Vinkkiä otsikolla: "+otsikko+" ei löytynyt");
        }else{
            this.tulostus.println("Vinkkiä ei poistettu");
        }
    }
*/
}
