/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
import apuviritykset.Muotoilut;
import io.LukijaRajapinta;
import java.io.File;
import java.util.Random;
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

 //   Vinkkitietokanta tietokanta;
    Kayttoliittyma UI;
    LukijaStub lukija;
    TulostusStub tulostus;
    Random random;

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
        
        File dbfile=new File("");
           String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/databases/test/sprint2testikanta.db";
        
        Vinkkitietokanta tietokanta = new Vinkkitietokanta(url);
        this.UI = new Kayttoliittyma(tietokanta);
        this.lukija = new LukijaStub();
        this.tulostus = new TulostusStub();
        this.UI.setLukija(lukija);
        this.UI.setTulostus(tulostus);
        this.random = new Random();
    }

    @After
    public void tearDown() {
    }
    
    private String generoiSatunnainenOtsikko(){
        return "Fifty Shades of Grey" + this.random.nextInt(10000);
    }
    
    @Test
    public void kirjavinkinLisaaminenPalauttaaLisattyTulosteenValidillaSyoteella() {
        this.lukija.nollaa();
        this.lisaaLukijaanKirjavinkinLisays("Cormen", this.generoiSatunnainenOtsikko()); 
        this.suoritaJaLopeta();
        assertTrue(this.tulostus.tulosteSisaltaa("Kirjavinkki lisätty"));
        this.tulostus.nollaa();
    }

    @Test
    public void kirjavinkinLisaaminenPalauttaaEiLisattyTulosteenEiValidillaSyoteella() {
        this.lukija.nollaa();
        this.lisaaLukijaanKirjavinkinLisays("",  this.generoiSatunnainenOtsikko());
        this.lisaaLukijaanKirjavinkinLisays("", "");
        this.lisaaLukijaanKirjavinkinLisays("Cormen", "");
        this.suoritaJaLopeta();
        assertTrue(this.tulostus.tulosteSisaltaa("Kirjavinkkiä ei lisätty"));
        assertTrue(!this.tulostus.tulosteSisaltaa("Kirjavinkki lisätty"));
    }

    /* MI 7.12: korjaamtatta, käyttää legacy metodeja
    @Test
    public void kirjavinkkiEsiintyyKaikkienVinkkienTulosteessaLisaamisenJalkeen() {
        this.lukija.nollaa();
        this.tulostus.nollaa();
        String otsikko = this.generoiSatunnainenOtsikko();
        this.lisaaLukijaanKirjavinkinLisays("E. L. James", otsikko);
        this.lisaaLukijaanKaikkienTulostus();
//        this.lukija.kaikki();
        this.suoritaJaLopeta();
       
        System.out.println(Muotoilut.muotoileKirjavinkinTuloste(otsikko, false, "E. L. James"));
        assertTrue(this.tulostus.tulosteSisaltaa(Muotoilut.muotoileKirjavinkinTuloste(otsikko, false, "E. L. James")));

    }*/

//    @Test
//    public void kirjaVinkkiEiEsiinnyTulosteesaPoistamisenJalkeen() {
//        this.lukija.nollaa();
//        this.tulostus.nollaa();
//        this.lisaaLukijaanKirjavinkinLisays("Kivi", "abc");
//        this.lisaaLukijaanPoisto("abc");
//        this.lisaaLukijaanKaikkienTulostus();
//        
//        this.suoritaJaLopeta();
//        assertTrue(!this.tulostus.tulosteSisaltaa("Vinkki (tyyppi)" + "\n\tkirjoittaja " + "Kivi" + "\n\totsikko " + "abc"));
//    }

    private void suoritaJaLopeta() {
        this.lukija.lisaaSyote("lopeta");
        
        this.UI.suorita();
    }

    private void lisaaLukijaanKaikkienTulostus() {
        this.lukija.lisaaSyote("tulosta kaikki");
    }
    private void lisaaLukijaanPoisto(String otsikko) {
        this.lukija.lisaaSyote("poista", otsikko);
    }
    private void lisaaLukijaanKirjavinkinLisays(String kirjoittaja, String otsikko) {
        this.lukija.lisaaSyote("lisää kirja", kirjoittaja, otsikko,"");
    }

}
