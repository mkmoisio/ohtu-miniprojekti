/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
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
    Vinkkitietokanta tk0 = null;
    Vinkki vinkki = new Vinkki("Testikirjoittaja","Kirjannimi"); 

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
        //Puhdista paremmaksi
        File dbfile=new File("");
        File source = new File(dbfile.getAbsolutePath()+"/ohtuminiTEST.db");
        File dest = new File(dbfile.getAbsolutePath()+"/ohtuminiTEST2.db");
        try{
            Files.delete(dest.toPath());
        }catch(IOException e){
        }
        try{
            Files.copy(source.toPath(), dest.toPath());
        }catch(IOException e){
            System.out.println("Ei voida kopioida testi tietokantaa: "+e.toString());
        }
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/ohtuminiTEST2.db";
        tk0 = new Vinkkitietokanta(url);
        System.out.println(errContent.toString());
        assertTrue("Tietokanta pitäisi olla liitettynä",tk0.tietokantaliitetty());
    }

    
    @After
    public void tearDown() {
        tk0.sulje();
        File dbfile=new File("");
        File dest = new File(dbfile.getAbsolutePath()+"/ohtuminiTEST2.db");
        try{
            Files.delete(dest.toPath());
        }catch(IOException e){
        }
    }

    /**
     * Test of haeKaikkiString method, of class Vinkkitietokanta.
     */
    @Test
    public void testHaeKaikkiString() {
        System.out.println("haeKaikkiString");
        List<String> result = tk0.haeKaikkiString();
        assertEquals(2, result.size());
        assertEquals(vinkki.toString(),result.get(1));
    }

    /**
     * Test of haeKaikki method, of class Vinkkitietokanta.
     */
    @Test
    public void testHaeKaikki() {
        System.out.println("haeKaikki");
        System.out.println("haeKaikkiString");
        List<Vinkki> result = tk0.haeKaikki();
        assertEquals(2, result.size());
        assertEquals(vinkki.kirjoittaja,result.get(1).kirjoittaja);
        assertEquals(vinkki.otsikko,result.get(1).otsikko);
    }        

    /**
    * Test of lisaaKirja method, of class Vinkkitietokanta.
    */
    @Test
    public void testLisaaKirja() {
        System.out.println("lisaaKirja");
        String kirjoittaja = "Marutei Senpai";
        String otsikko = "Sushia ja loisia";
        Vinkki vinkki = new Vinkki(kirjoittaja, otsikko);
        tk0.lisaaKirja(kirjoittaja, otsikko);
        List<Vinkki> result = tk0.haeKaikki();
        assertEquals(3, result.size());
        Vinkki vinkki2 = result.get(2);
        assertEquals(vinkki.kirjoittaja, vinkki2.kirjoittaja);
        assertEquals(vinkki.otsikko, vinkki2.otsikko);
    }
    
    /**
     * Test of poistaKirja method, of class Vinkkitietokanta.
     */
    @Test
    public void testPoistaKirja() {
        System.out.println("poistaKirja");
        String kirjoittaja = "Marutei Senpai";
        String otsikko = "Sushia ja loisia";
        Vinkki vinkki = new Vinkki(kirjoittaja, otsikko);
        tk0.lisaaKirja(kirjoittaja, otsikko);
        kirjoittaja = "Richard Stallman";
        otsikko = "Sensuelli GNU";
        Vinkki vinkki2 = new Vinkki(kirjoittaja, otsikko);
        tk0.lisaaKirja(kirjoittaja, otsikko);
        List<Vinkki> result = tk0.haeKaikki();
        assertEquals(4, result.size());
        tk0.poistaKirja("Sushia ja loisia");
        result = tk0.haeKaikki();
        assertEquals(3, result.size());
        tk0.poistaKirja("Sensuelli GNU");
        result = tk0.haeKaikki();        
        assertEquals(2,result.size());
        for(String str : tk0.haeKaikkiString()){
            assertFalse(str.contains("Törvalds"));
            assertFalse(str.contains("Stallman"));
        }
    }




    
}
