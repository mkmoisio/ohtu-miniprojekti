package Kayttoliittyma;

import toiminnot.lisays.VideonLisays;
import toiminnot.lisays.PodcastinLisays;
import toiminnot.lisays.BlogpostinLisays;
import toiminnot.muu.Poisto;
import toiminnot.muu.KomentojenTulostus;
import toiminnot.lisays.KirjanLisays;
import toiminnot.Operaatio;
import toiminnot.muu.TaginLisays;
import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.BlogpostValidator;
import apuviritykset.KirjaValidator;
import apuviritykset.PodcastValidator;
import apuviritykset.Validator;
import apuviritykset.VideoValidator;
import io.Lukija;
import io.LukijaRajapinta;
import io.Tulostaja;
import io.TulostusRajapinta;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import toiminnot.muu.AvaaNettilinkki;
import toiminnot.muu.MerkitseLuetuksi;
import toiminnot.muu.MuunnaVinkkia;
import toiminnot.tulostus.TulostaBlogpostit;
import toiminnot.tulostus.TulostaKaikki;
import toiminnot.tulostus.TulostaKirjat;
import toiminnot.tulostus.TulostaLuetut;
import toiminnot.tulostus.TulostaLukemattomat;
import toiminnot.tulostus.TulostaPodcastit;
import toiminnot.tulostus.TulostaVideot;
import toiminnot.tulostus.TulostaTaginPerusteella;

/**
 * Itse käyttöliittymä.
 */
public class Kayttoliittyma {

    private final String KOMENNOT = "Komennot:"
            + "\n\t lisää kirja - kirjavinkin lisääminen"
            + "\n\t lisää podcast - podcast-vinkin lisääminen"
            + "\n\t lisää video - videovinkin lisääminen"
            + "\n\t lisää blogpost - blogpost-vinkin lisääminen"
            + "\n\t muunna vinkkiä - muunna vinkin tietoja"
            + "\n\t tulosta kaikki - tulosta kaikki vinkit"
            + "\n\t lukemattomat - tulosta kaikki lukemattomat vinkit"
            + "\n\t luetut - tulosta kaikki luetut vinkit"
            + "\n\t tulosta kirjat - tulosta kaikki kirjavinkit"
            + "\n\t tulosta podcastit - tulosta kaikki podcastit"
            + "\n\t tulosta videot - tulosta kaikki videot"
            + "\n\t tulosta blogpostit - tulosta kaikki blogpostit"
            + "\n\t avaa nettivinkki - avaa nettivinkin oletusselaimessa"
            + "\n\t hae tagilla - tulosta annetun tagin omaavat vinkit"
            + "\n\t merkitse luetuksi - merkitse luetuksi"
            + "\n\t poista - poista vinkki"
            + "\n\t lopeta - lopeta ohjelma "
            + "\n";

    VinkkitietokantaRajapinta tk;
    LukijaRajapinta lukija;
    TulostusRajapinta tulostus;
    Map<String, Operaatio> ops;

    public Kayttoliittyma(VinkkitietokantaRajapinta tk) {
        this.ops = new HashMap();
        this.tk = tk;
        this.lukija = new Lukija(System.in);
        this.tulostus = new Tulostaja(System.out);

    }

    public void alusta() {
        Operaatio kirjanLisays = new KirjanLisays(this.lukija, this.tulostus, this.tk, new TaginLisays(this.lukija, this.tulostus));
        Operaatio podcastinLisays = new PodcastinLisays(this.lukija, this.tulostus, this.tk, new TaginLisays(this.lukija, this.tulostus));
        Operaatio blogpostinLisays = new BlogpostinLisays(this.lukija, this.tulostus, this.tk, new TaginLisays(this.lukija, this.tulostus));
        Operaatio videonlisays = new VideonLisays(this.lukija, this.tulostus, this.tk, new TaginLisays(this.lukija, this.tulostus));

        Operaatio poisto = new Poisto(this.lukija, this.tulostus, this.tk);
        Operaatio komentojenTulostus = new KomentojenTulostus(this.lukija, this.tulostus);
        Operaatio tulostaKaikki = new TulostaKaikki(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaLukemattomat = new TulostaLukemattomat(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaLuetut = new TulostaLuetut(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaKirjat = new TulostaKirjat(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaVideot = new TulostaVideot(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaBlogpostit = new TulostaBlogpostit(this.lukija, this.tulostus, this.tk);
        Operaatio tulostaPodcastit = new TulostaPodcastit(this.lukija, this.tulostus, this.tk);
        Operaatio avaaNettilinkki = new AvaaNettilinkki(this.lukija, this.tulostus,this.tk);
        Operaatio haeTagilla = new TulostaTaginPerusteella(this.lukija, this.tulostus, this.tk);
        Operaatio merkitseLuetuksi = new MerkitseLuetuksi(this.lukija, this.tulostus, this.tk);
        Operaatio muunnaVinkkia = new MuunnaVinkkia(this.lukija, this.tulostus, this.tk);

        ops.put("lisää kirja", kirjanLisays);
        ops.put("lisää podcast", podcastinLisays);
        ops.put("lisää video", videonlisays);
        ops.put("lisää blogpost", blogpostinLisays);
        ops.put("muunna vinkkiä", muunnaVinkkia);
        ops.put("poista", poisto);
        ops.put("komennot", komentojenTulostus);
        ops.put("tulosta kaikki", tulostaKaikki);
        ops.put("lukemattomat", tulostaLukemattomat);
        ops.put("luetut", tulostaLuetut);
        ops.put("tulosta kirjat", tulostaKirjat);
        ops.put("tulosta videot", tulostaVideot);
        ops.put("tulosta blogpostit", tulostaBlogpostit);
        ops.put("tulosta podcastit", tulostaPodcastit);
        ops.put("avaa nettivinkki", avaaNettilinkki);
        ops.put("hae tagilla", haeTagilla);
        ops.put("merkitse luetuksi", merkitseLuetuksi);

    }

    public void setLukija(LukijaRajapinta lukija) {
        this.lukija = lukija;
        this.alusta(); // Alustetaan operaatiot uudestaan, että saadaan sellaiset versiot joissa on testeille sopivat stubit
    }

    public void setTulostus(TulostusRajapinta tulostus) {
        this.tulostus = tulostus;
        this.alusta();
    }

    public void suorita() {
        boolean KAYTETAANKOMAPATTUJAOPERAATIOTAVAIEI = true;

        while (true) {
            if (KAYTETAANKOMAPATTUJAOPERAATIOTAVAIEI) {
                ops.get("komennot").suorita();
                String komento = this.lukija.nextLine().toLowerCase();
                Operaatio o = this.ops.get(komento);
                if (o != null) {
                    o.suorita();
                } else {
                    if (komento.equals("lopeta")) {
                        return;
                    }
                }
            } else {

                this.tulostaKomennot();
                String komento = this.lukija.nextLine().toLowerCase();
                switch (komento) {

                    /* LISÄÄMINEN ALKAA */
                    case "lisää kirja":
                        this.lisaaKirja();
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
                    case "muunna vinkkiä":
                        this.muunnaVinkkia();
                        break;
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
                    case "avaa nettivinkki":
                        this.avaaNettivinkki();
                        break;
                    case "hae tagilla":
                        this.haeTagilla();
                        break;
                    /* TULOSTUS LOPPUU */                /* TULOSTUS LOPPUU */

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

        PodcastValidator validator = new PodcastValidator(nimi, otsikko, kuvaus);
        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.PODCAST);
            vinkki.lisaaOminaisuus(Attribuutit.NIMI, nimi);
            vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, kuvaus);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Podcast lisätty");
            } else {
                this.tulostus.println("Podcastia ei lisätty");
            }
        } else {
            this.tulostaVirhelista(validator.getVirheet());
        }
    }

    private void lisaaKirja() {

        this.tulostus.println("Anna kirjoittaja:");
        String kirjoittaja = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();
        KirjaValidator validator = new KirjaValidator(kirjoittaja, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.KIRJA);
            vinkki.lisaaTekija(kirjoittaja);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Kirjavinkki lisätty");
            } else {
                this.tulostus.println("Kirjavinkkiä ei lisätty");
            }
        } else {
            this.tulostaVirhelista(validator.getVirheet());
        }
    }

    private void lisaaVideo() {
        this.tulostus.println("Anna url:");
        String url = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();

        VideoValidator validator = new VideoValidator(url, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.VIDEO);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Video lisätty");
            }
        } else {
            this.tulostaVirhelista(validator.getVirheet());
        }

    }

    private void lisaaBlogpost() {
        this.tulostus.println("Anna url:");
        String url = this.lukija.nextLine();
        this.tulostus.println("Anna kirjoittaja:");
        String kirjoittajat = this.lukija.nextLine();
        this.tulostus.println("Anna otsikko:");
        String otsikko = this.lukija.nextLine();

        BlogpostValidator validator = new BlogpostValidator(url, kirjoittajat, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.BLOGPOST);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            vinkki.lisaaTekija(kirjoittajat);
            this.lisaaTagit(vinkki);
            if (this.tk.lisaaVinkki(vinkki)) {
                this.tulostus.println("Blogpost lisätty");
            }
        } else {
            this.tulostaVirhelista(validator.getVirheet());
        }
    }

    private void merkitseLuetuksi() {
        this.tulostus.println("Anna sen vinkin otsikko, joka merkitään luetuksi");
        String otsikko = this.lukija.nextLine();
        if (!this.tk.merkitseLuetuksi(otsikko)) {
            this.tulostus.println("Virhe: Vinkkiä " + otsikko + " ei löytynyt");
            return;
        }
        this.tulostus.println("Vinkki otsikolla " + otsikko + " merkitty luetuksi");
    }

    //Kysyy käyttäjältä tageja
    private void lisaaTagit(Vinkki vinkki) {
        this.tulostus.println("Anna tageja, tyhjä syöte palauttaa aloitusnäyttöön");
        System.out.println("");

        while (true) {
            String tagSyote = lukija.nextLine();
            if (tagSyote.isEmpty()) {
                break;
            }

            if (vinkki.onkoTagia(tagSyote)) {
                System.out.println("Samanniminen tag on jo olemassa");
                continue;
            }
            if (Validator.taginPituusOk(tagSyote)) {
                vinkki.lisaaTag(tagSyote);
            }
        }
    }

    private void haeTagilla() {
        this.tulostus.println("Anna tagi");
        this.tulostus.println("");
        String tagSyote = lukija.nextLine();
        this.tulostaLista(this.haeTagillla(tagSyote));
    }
    
    private void avaaNettivinkki() {
        try {
            tulostus.println("Anna otsikko: ");
            String osoite = lukija.nextLine();
            Vinkki vinkki = tk.haeVinkki(osoite);
            String url = vinkki.haeOminaisuus(Attribuutit.URL);
            if(!url.equals(vinkki.virheteksti())){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException ex) {
                    ex.getMessage();
                }    
            }else{
                tulostus.println("Vinkki ei sisällä url:ia");
            }
            
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }
    }


    /* KANNAN METODEITA KUTSUVAT METODIT */
    private List<Vinkki> haeKaikkiVinkit() {
        return this.tk.haeKaikki(LukuStatus.KAIKKI);
    }

    private List<Vinkki> haeTagillla(String tag) {
        return this.tk.haeTagilla(tag);
    }

    private List<Vinkki> haeKaikkuLukemattomat() {
        return this.tk.haeKaikki(LukuStatus.LUKEMATTOMAT);
    }

    private List<Vinkki> haeKaikkiLuetut() {
        return this.tk.haeKaikki(LukuStatus.LUETTU);
    }

    public List<Vinkki> haeKaikkiKirjavinkit() {
        return this.tk.haeKaikki(Formaatit.KIRJA, LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiPodcastvinkit() {
        return this.tk.haeKaikki(Formaatit.PODCAST, LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiVideovinkit() {
        return this.tk.haeKaikki(Formaatit.VIDEO, LukuStatus.KAIKKI);
    }

    public List<Vinkki> haeKaikkiBlogpostvinkit() {
        return this.tk.haeKaikki(Formaatit.BLOGPOST, LukuStatus.KAIKKI);
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

    private void tulostaVirhelista(List<String> lista) {
        for (String s : lista) {
            this.tulostus.println(s);

        }
    }
    /* TULOSTUS PÄÄTTYY*/

    private void muunnaVinkkia() {
        this.tulostus.println("Anna vinkin otsikko, jota muutetaan");
        System.out.println("");
        String otsikko = this.lukija.nextLine();
        Vinkki vinkki = tk.haeVinkki(otsikko);
        if(vinkki==null) {
            this.tulostus.println("Vinkkiä otsikolla: "+otsikko+" ei löytynyt");
        }else{
            while (true) {
                this.tulostus.println("Mitä muutetaan (tyhjä lopettaa): "+vinkki.getOminaisuudet());
                String syote = lukija.nextLine();
                if (syote.isEmpty()) {
                    break;
                }
                String arvo;
                //Muuta tekijoita
                if(syote.toLowerCase().equals("tekija")){
                    this.tulostus.println("Tekija: "+vinkki.printtaaTekijat());
                    this.tulostus.println("Anna uusi tekija (tyhjä peruu)");
                    String tekija = lukija.nextLine();
                    if(!tekija.isEmpty()){
                        vinkki.tyhjennaTekijat();
                        while(!tekija.isEmpty()){
                            vinkki.lisaaTekija(tekija);
                            this.tulostus.println("Tekijä "+tekija+" lisätty (tyhjä lopettaa)");
                            tekija = lukija.nextLine();
                        }
                    }    
                }else{
                    try{
                        Attribuutit attr = Attribuutit.valueOf(syote);
                        arvo = vinkki.haeOminaisuus(attr);
                        if(!arvo.equals(vinkki.virheteksti())){
                            this.tulostus.println(syote.toUpperCase()+": "+arvo);
                            this.tulostus.println("Anna uusi "+syote.toLowerCase()+" (tyhja peruu)");
                            String syote2 = lukija.nextLine();
                            if(!syote2.isEmpty()){
                                vinkki.lisaaOminaisuus(attr, syote2);
                            }
                        }
                    }catch(IllegalArgumentException e){
                         System.out.println("Ei tuettu");
                    }  
                }
                
                
                
            }
            tk.poistaVinkki(otsikko);
            tk.lisaaVinkki(vinkki);
        }
     }

    
    
}
