/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rokka
 */
public class VinkkiTest {
    
    public VinkkiTest() {
    }


    @Test
    public void testaaVaihdaFormaattia() {
        System.out.println("vaihda formaattia");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.FORMAATTI, Formaatit.BLOGPOST));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.FORMAATTI), Formaatit.BLOGPOST.toString());
    }
    
    @Test
    public void testaaVaihdaOtsikkoa() {
        System.out.println("vaihda otsikkoa");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.OTSIKKO, "APACHE2 intiaanit"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.OTSIKKO), "APACHE2 intiaanit");
    }   

    @Test
    public void testaaVaihdaKuvausta() {
        System.out.println("vaihda kuvausta");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, "Surkea Kirja"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.KUVAUS), "Surkea Kirja");
    }  

    @Test
    public void testaaLuettuStatusta() {
        System.out.println("vaihda Luettu statusta");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.LUETTU, true));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.LUETTU), "true");
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.LUETTU, false));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.LUETTU), "false");
    }  
  
    @Test
    public void testaaTekijat() {
        System.out.println("vaihda tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.KIRJA);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.TEKIJAT, "Markku"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.TEKIJAT), "Markku");
    }      
    
    @Test
    public void testaaURL() {
        System.out.println("vaihda url");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.VIDEO);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.URL, "www.url.fi"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.URL),  "www.url.fi");
    }          

    
    @Test
    public void testLisaaTekijat() {
        System.out.println("parse tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertTrue(vinkki.lisaaTekijat("Markku----Pete----Cooper----Lynch"));
        assertTrue(vinkki.haeTekijat().contains("Markku"));
        assertTrue(vinkki.haeTekijat().contains("Pete"));
        assertTrue(vinkki.haeTekijat().contains("Cooper"));
        assertTrue(vinkki.haeTekijat().contains("Lynch"));
        assertEquals(4,vinkki.haeTekijat().size());
    }
    
    @Test
    public void testEiOminaisuutta() {
        System.out.println("parse tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertEquals(vinkki.haeOminaisuus(Attribuutit.TEKIJAT),"");
        assertEquals(vinkki.haeOminaisuus(Attribuutit.URL),"");
        assertEquals(vinkki.haeOminaisuus(Attribuutit.KUVAUS),"");
        assertEquals(vinkki.haeOminaisuus(Attribuutit.ISBN),"");
    }    
    
    @Test
    public void testTyhja() {
        System.out.println("parse tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.ISBN, null));
    }    
        
/*

    @Test
    public void testHaeOminaisuus() {
        System.out.println("haeOminaisuus");
        Attribuutit attribuutti = null;
        Vinkki instance = null;
        String expResult = "";
        String result = instance.haeOminaisuus(attribuutti);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    
    
    @Test
    public void testLisaaTekija() {
        System.out.println("lisaaTekija");
        String tekija = "";
        Vinkki instance = null;
        instance.lisaaTekija(tekija);
        fail("The test case is a prototype.");
    }

    @Test
    public void testHaeTekijat() {
        System.out.println("haeTekijat");
        Vinkki instance = null;
        List<String> expResult = null;
        List<String> result = instance.haeTekijat();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testPrinttaaTekijat() {
        System.out.println("printtaaTekijat");
        Vinkki instance = null;
        String expResult = "";
        String result = instance.printtaaTekijat();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testPoistaTekijat() {
        System.out.println("poistaTekijat");
        Vinkki instance = null;
        instance.poistaTekijat();
        fail("The test case is a prototype.");
    }

    @Test
    public void testFormaatti() {
        System.out.println("formaatti");
        Vinkki instance = null;
        Formaatit expResult = null;
        Formaatit result = instance.formaatti();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLuettu() {
        System.out.println("luettu");
        Vinkki instance = null;
        boolean expResult = false;
        boolean result = instance.luettu();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMerkitseLuetuksi() {
        System.out.println("merkitseLuetuksi");
        Vinkki instance = null;
        instance.merkitseLuetuksi();
        fail("The test case is a prototype.");
    }

    @Test
    public void testMerkitseLukemattomaksi() {
        System.out.println("merkitseLukemattomaksi");
        Vinkki instance = null;
        instance.merkitseLukemattomaksi();
        fail("The test case is a prototype.");
    }

    @Test
    public void testOtsikko() {
        System.out.println("Otsikko");
        Vinkki instance = null;
        String expResult = "";
        String result = instance.Otsikko();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Vinkki instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
*/
    
}
