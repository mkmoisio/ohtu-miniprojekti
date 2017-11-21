/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rokka
 */
public class VinkkitietokantaTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    
    public VinkkitietokantaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.setErr(new PrintStream(errContent));
        File dbfile=new File("");
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/ohtuminiTEST.db";
        Vinkkitietokanta tk0 = new Vinkkitietokanta(url);
        System.out.println(errContent.toString());
        assertTrue("Tietokanta pitäisi olla liitettynä",tk0.tietokantaliitetty());
    }
    
    @After
    public void tearDown() {
    }

        
     
    
    
    /**
     * Test of tietokantaliitetty method, of class Vinkkitietokanta.
     */
    @Test
    public void testTietokantaliitetty() {
        System.out.println("tietokantaliitetty");
        Vinkkitietokanta instance = null;
        boolean expResult = false;
        boolean result = instance.tietokantaliitetty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of poistaKirja method, of class Vinkkitietokanta.
     */
    @Test
    public void testPoistaKirja() {
        System.out.println("poistaKirja");
        String otsikko = "";
        Vinkkitietokanta instance = null;
        boolean expResult = false;
        boolean result = instance.poistaKirja(otsikko);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lisaaKirja method, of class Vinkkitietokanta.
     */
    @Test
    public void testLisaaKirja() {
        System.out.println("lisaaKirja");
        String kirjoittaja = "";
        String otsikko = "";
        Vinkkitietokanta instance = null;
        boolean expResult = false;
        boolean result = instance.lisaaKirja(kirjoittaja, otsikko);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of haeKaikkiString method, of class Vinkkitietokanta.
     */
    @Test
    public void testHaeKaikkiString() {
        System.out.println("haeKaikkiString");
        Vinkkitietokanta instance = null;
        List<String> expResult = null;
        List<String> result = instance.haeKaikkiString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of haeKaikki method, of class Vinkkitietokanta.
     */
    @Test
    public void testHaeKaikki() {
        System.out.println("haeKaikki");
        Vinkkitietokanta instance = null;
        List<Vinkki> expResult = null;
        List<Vinkki> result = instance.haeKaikki();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
