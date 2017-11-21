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
        this.UI = new Kayttoliittyma(tietokanta);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kirjavinkinLisaaminenOnnistuuValidillaSyotteellaa() {
        assertTrue(alustaJaSuoritaSyotteella("lisää", "Cormen", "Introduction.to.Algorithms.3rd.Edition.Sep.2010", "lopeta", "Kirjavinkki lisätty"));
    }

    @Test
    public void kirjavinkkiaEiListataTyhjallaKirjoittajalla() {
        assertTrue(alustaJaSuoritaSyotteella("lisää", "", "Introduction.to.Algorithms.3rd.Edition.Sep.2010", "lopeta", "Kirjavinkkiä ei lisätty"));
    }
    
    @Test
    public void kirjavinkkiaEiListataTyhjallaOtsikolla() {
        assertTrue(alustaJaSuoritaSyotteella("lisää", "Cormen", "", "lopeta", "Kirjavinkkiä ei lisätty"));
    }

    private boolean alustaJaSuoritaSyotteella(String komento, String kirjoittaja, String otsikko, String komento2, String halutaan) {
        LukijaStub lukija = new LukijaStub();
        TulostusStub tulostus = new TulostusStub();
        lukija.lisaaSyote(komento, kirjoittaja, otsikko, komento2, halutaan);
        this.UI.setLukija(lukija);
        this.UI.setTulostus(tulostus);
        this.UI.suorita();
        return tulostus.tulosteessaEsiintyy(halutaan);
    }

}
