package Kayttoliittyma;

import toiminnot.lisays.VideonLisays;
import toiminnot.lisays.PodcastinLisays;
import toiminnot.lisays.BlogpostinLisays;
import toiminnot.muu.Poisto;
import toiminnot.muu.KomentojenTulostus;
import toiminnot.lisays.KirjanLisays;
import toiminnot.Operaatio;
import toiminnot.muu.TaginLisays;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.vakiot.Komennot;
import io.Lukija;
import io.LukijaRajapinta;
import io.Tulostaja;
import io.TulostusRajapinta;
import java.util.HashMap;
import java.util.Map;
import toiminnot.muu.AvaaNettilinkki;
import toiminnot.muu.Lopetus;
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
        Operaatio avaaNettilinkki = new AvaaNettilinkki(this.lukija, this.tulostus, this.tk);
        Operaatio haeTagilla = new TulostaTaginPerusteella(this.lukija, this.tulostus, this.tk);
        Operaatio merkitseLuetuksi = new MerkitseLuetuksi(this.lukija, this.tulostus, this.tk);
        Operaatio muunnaVinkkia = new MuunnaVinkkia(this.lukija, this.tulostus, this.tk);
        Operaatio lopetus = new Lopetus();

        ops.put(Komennot.LISAA_KIRJA, kirjanLisays);
        ops.put(Komennot.LISAA_PODCAST, podcastinLisays);
        ops.put(Komennot.LISAA_VIDEO, videonlisays);
        ops.put(Komennot.LISAA_BLOGPOST, blogpostinLisays);
        ops.put(Komennot.MUUNNA_VINKKIA, muunnaVinkkia);
        ops.put(Komennot.POISTA_VINKKI, poisto);
        ops.put(Komennot.TULOSTA_KOMENNOT, komentojenTulostus);
        ops.put(Komennot.TULOSTA_KAIKKI, tulostaKaikki);
        ops.put(Komennot.TULOSTA_LUKEMATTOMAT, tulostaLukemattomat);
        ops.put(Komennot.TULOSTA_LUETUT, tulostaLuetut);
        ops.put(Komennot.TULOSTA_KIRJAT, tulostaKirjat);
        ops.put(Komennot.TULOSTA_VIDEOT, tulostaVideot);
        ops.put(Komennot.TULOSTA_BLOGPOSTIT, tulostaBlogpostit);
        ops.put(Komennot.TULOSTA_PODCASTIT, tulostaPodcastit);
        ops.put(Komennot.AVAA_NETTIVINKKI, avaaNettilinkki);
        ops.put(Komennot.HAE_TAGILLA, haeTagilla);
        ops.put(Komennot.MERKITSE_LUETUKSI, merkitseLuetuksi);
        ops.put(Komennot.LOPETA, lopetus);
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

        while (true) {
            ops.get(Komennot.TULOSTA_KOMENNOT).suorita();
            String komento = this.lukija.nextLine().toLowerCase();
            Operaatio o = this.ops.get(komento);
            if (o != null) {
                o.suorita();
            }
        }
    }
}
