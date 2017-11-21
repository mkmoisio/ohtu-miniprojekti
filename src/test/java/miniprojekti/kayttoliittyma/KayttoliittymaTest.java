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
        this.tietokanta = new Vinkkitietokanta();
        this.UI = new Kayttoliittyma(tietokanta);
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void kirjavinkinLisaaminenOnnistuuValidillaSyotteellaa() {
//        assertTrue(UI.lisaaKirjavinkki("Cormen", "Introduction.to.Algorithms.3rd.Edition.Sep.2010"));       
//    }
    @Test
    public void kirjavinkinLisaaminenOnnistuuValidillaSyotteellaa() {
        LukijaStub lukija = new LukijaStub();
        TulostusStub tulostus = new TulostusStub();
        lukija.lisaaSyote("lisää");
        lukija.lisaaSyote("Cormen");
        lukija.lisaaSyote("Introduction.to.Algorithms.3rd.Edition.Sep.2010");
        lukija.lisaaSyote("lopeta");
        this.UI.setLukija(lukija);
        this.UI.setTulostus(tulostus);
        this.UI.suorita();
        
        assertEquals("Anna komento", tulostus.pop());
        assertEquals("Kirjoittaja", tulostus.pop());
        assertEquals("Otsikko", tulostus.pop());
        assertEquals("Kirjavinkki lisätty", tulostus.pop());
        // assertTrue(UI.lisaaKirjavinkki(, ));       
    }
}
