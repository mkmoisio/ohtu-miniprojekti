/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rokka
 */
public class VinkkiTest {
    
    public VinkkiTest() {
    }


    
    /*
    Ei tuettu enää
    @Test
    public void testaaVaihdaFormaattia() {
        System.out.println("vaihda formaattia");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.FORMAATTI, Formaatit.BLOGPOST));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.FORMAATTI), Formaatit.BLOGPOST.toString());
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.FORMAATTI, "asdasd"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.FORMAATTI), Formaatit.BLOGPOST.toString());
    }
    */
    
    @Test
    public void testaaHaeOminaisuusEiOlemassa() {
        System.out.println("ominaisuus ei olemassa");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertEquals(vinkki.haeOminaisuus(Attribuutit.URL), vinkki.virheTeksti);
        assertEquals(vinkki.haeOminaisuus(null), vinkki.virheTeksti);
    }
    
    /*
    Otsikon vaihtamista ei enää tueta
    @Test
    public void testaaVaihdaOtsikkoa() {
        System.out.println("vaihda otsikkoa");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.OTSIKKO, "APACHE2 intiaanit"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.OTSIKKO), "APACHE2 intiaanit");
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.OTSIKKO, 1));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.OTSIKKO), "APACHE2 intiaanit");
    }   
    */
    @Test
    public void testaaVaihdaKuvausta() {
        System.out.println("vaihda kuvausta");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, "Surkea Kirja"));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.KUVAUS), "Surkea Kirja");
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, ""));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.KUVAUS), "Surkea Kirja");
    }  

    /*
    Ei tuettu enää
    @Test
    public void testaaLuettuStatusta() {
        System.out.println("vaihda Luettu statusta");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.PODCAST);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.LUETTU, true));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.LUETTU), "true");
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.LUETTU, false));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.LUETTU), "false");
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.LUETTU, 1));
        assertEquals(vinkki.haeOminaisuus(Attribuutit.LUETTU), "false");
    }  
    */
    
    /*
    Ei tuettu enää
    
    @Test
    public void testaaTekijat() {
        System.out.println("vaihda tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.KIRJA);
        assertTrue(vinkki.lisaaOminaisuus(Attribuutit.TEKIJAT, "Markku"));
        assertEquals("Markku",vinkki.haeOminaisuus(Attribuutit.TEKIJAT));
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.TEKIJAT, 1));
        assertEquals("Markku",vinkki.haeTekijat().get(10));
    }      
    */
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
        assertTrue(vinkki.haeTekijat().isEmpty());
        assertEquals(vinkki.virheTeksti,vinkki.haeOminaisuus(Attribuutit.URL));
        assertEquals(vinkki.virheTeksti,vinkki.haeOminaisuus(Attribuutit.KUVAUS));
        assertEquals(vinkki.virheTeksti,vinkki.haeOminaisuus(Attribuutit.ISBN));
    }    
    
    @Test
    public void testTyhja() {
        System.out.println("parse tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.ISBN, null));
    }    
    
    @Test
    public void testLisaaTekijaHuonoSyote() {
        System.out.println("lisaaTekijaHuonoSyote tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertFalse(vinkki.lisaaOminaisuus(Attribuutit.ISBN, null));
        assertFalse(vinkki.lisaaOminaisuus(null, null));
    }   

    @Test
    public void testLisaaTekijatValidiEkaHuonoToka() {
        System.out.println("lisaaTekijat eka hyva sitten duplikaatti");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertTrue(vinkki.lisaaTekijat("Markku"));
        assertEquals(1,vinkki.haeTekijat().size());
        assertTrue(vinkki.lisaaTekijat("Markku"));
        assertEquals(1,vinkki.haeTekijat().size());
    }   
    
        
    @Test
    public void testLisaaTekijaHuonoSyoteTekijat() {
        System.out.println("lisaaTekijatHuonoSyote tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertFalse(vinkki.lisaaTekija(null));
        assertFalse(vinkki.lisaaTekija(""));
        assertFalse(vinkki.lisaaTekijat(null));
        assertFalse(vinkki.lisaaTekijat(""));
        assertEquals(0,vinkki.haeTekijat().size());
        assertTrue(vinkki.lisaaTekija("Markku"));
        assertFalse(vinkki.lisaaTekija("Markku"));
    }   
    
    @Test
    public void testPoistaTekijat() {
        System.out.println("lisaaTekijatHuonoSyote tekijat");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertTrue(vinkki.lisaaTekija("Marrkku"));
        assertEquals(1,vinkki.haeTekijat().size());
        vinkki.poistaTekijat();
        assertEquals(0,vinkki.haeTekijat().size());
    }   
 
    @Test
    public void testGettersSetters() {
        System.out.println("testaa gettereitä");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertEquals(Formaatit.BLOGPOST,vinkki.formaatti());
        assertEquals("CIA vs FBI",vinkki.otsikko());
        assertFalse(vinkki.luettu());
        vinkki.merkitseLuetuksi();
        assertTrue(vinkki.luettu());
        vinkki.merkitseLukemattomaksi();
        assertFalse(vinkki.luettu());
    }   
  
   @Test
    public void testaaToStringSimppeli() {
        System.out.println("testaaToStringSimppeli (testaa vaan ettei kaadu)");
        apuToString(new Vinkki("CIA vs FBI",Formaatit.BLOGPOST));
        apuToString(new Vinkki("CIA vs FBI",Formaatit.PODCAST));
        apuToString(new Vinkki("CIA vs FBI",Formaatit.KIRJA));
        apuToString(new Vinkki("CIA vs FBI",Formaatit.NULL));
        apuToString(new Vinkki("CIA vs FBI",Formaatit.VIDEO));     
    }     
    
    private void apuToString(Vinkki vinkki){
        assertTrue(!vinkki.toString().isEmpty());
        vinkki.merkitseLuetuksi();
        assertTrue(!vinkki.toString().isEmpty());
    }
    
    @Test
    public void testLiitettyTagi() {
        System.out.println("testaa tageja");
        Vinkki vinkki = new Vinkki("CIA vs FBI",Formaatit.BLOGPOST);
        assertFalse(vinkki.onkoTagia("mersu"));
        vinkki.lisaaTag("mersu");
        assertTrue(vinkki.onkoTagia("mersu"));
        vinkki.lisaaTagit("kasino----bitcoin");
        assertTrue(vinkki.onkoTagia("kasino"));
        assertTrue(vinkki.onkoTagia("bitcoin"));
         
    }     
}
