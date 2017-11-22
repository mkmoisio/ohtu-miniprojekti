/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
import io.LukijaRajapinta;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikkomo
 */
public class KayttoliittymaTest {

    Vinkkitietokanta tietokanta;
    Kayttoliittyma UI;
    LukijaStub lukija;
    TulostusStub tulostus;

    public KayttoliittymaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //  this.tietokanta = new Vinkkitietokanta();
        this.UI = new Kayttoliittyma(new Kanta());
        this.lukija = new LukijaStub();
        this.tulostus = new TulostusStub();
        this.UI.setLukija(lukija);
        this.UI.setTulostus(tulostus);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kirjavinkinLisaaminenPalauttaaLisattyTulosteenValidillaSyoteella() {
        this.lukija.nollaa();
        this.lisaaLukijaanLisays("Cormen", "Introduction.to.Algorithms.3rd.Edition.Sep.2010");
        this.suoritaJaLopeta();
        assertTrue(this.tulostus.tulosteSisaltaa("Kirjavinkki lisätty"));
        this.tulostus.nollaa();
    }

    @Test
    public void kirjavinkinLisaaminenPalauttaaEiLisattyTulosteenEiValidillaSyoteella() {
        this.lukija.nollaa();
        this.lisaaLukijaanLisays("", "Introduction.to.Algorithms.3rd.Edition.Sep.2010");
        this.lisaaLukijaanLisays("", "");
        this.lisaaLukijaanLisays("Cormen", "");
        this.suoritaJaLopeta();
        assertTrue(this.tulostus.tulosteSisaltaa("Kirjavinkkiä ei lisätty"));
        assertTrue(!this.tulostus.tulosteSisaltaa("Kirjavinkki lisätty"));
    }

    @Test
    public void kirjavinkkiEsiintyyKaikkienVinkkienTulosteessaLisaamisenJalkeen() {
        this.lukija.nollaa();
        this.tulostus.nollaa();
        this.lisaaLukijaanLisays("E. L. James", "Fifty Shades of Grey");
        this.lisaaLukijaanTulostus();
//        this.lukija.kaikki();
        this.suoritaJaLopeta();
        assertTrue(this.tulostus.tulosteSisaltaa("Vinkki (tyyppi)" + "\n\tkirjoittaja " + "E. L. James" + "\n\totsikko " + "Fifty Shades of Grey"));

    }

    @Test
    public void kirjaVinkkiEiEsiinnyTulosteesaPoistamisenJalkeen() {
        this.lukija.nollaa();
        this.tulostus.nollaa();
        this.lisaaLukijaanLisays("Kivi", "abc");
        this.lisaaLukijaanPoisto("abc");
        this.lisaaLukijaanTulostus();
        
        this.suoritaJaLopeta();
        assertTrue(!this.tulostus.tulosteSisaltaa("Vinkki (tyyppi)" + "\n\tkirjoittaja " + "Kivi" + "\n\totsikko " + "abc"));
    }

    private void suoritaJaLopeta() {
        this.lukija.lisaaSyote("lopeta");
        
        this.UI.suorita();
    }

    private void lisaaLukijaanTulostus() {
        this.lukija.lisaaSyote("tulosta");
    }
    private void lisaaLukijaanPoisto(String otsikko) {
        this.lukija.lisaaSyote("poista", otsikko);
    }
    private void lisaaLukijaanLisays(String kirjoittaja, String otsikko) {
        this.lukija.lisaaSyote("lisää", kirjoittaja, otsikko);
    }

}
