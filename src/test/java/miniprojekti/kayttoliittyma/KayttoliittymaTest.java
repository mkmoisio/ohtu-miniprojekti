/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
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

    @Test
    public void kirjavinkinLisaaminenOnnistuuValidillaSyotteellaa() {
        assertTrue(UI.lisaaKirjavinkki("Cormen", "Introduction.to.Algorithms.3rd.Edition.Sep.2010"));       
    }
}
