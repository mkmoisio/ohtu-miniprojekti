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
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author rokka
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VinkkitietokantaTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    Vinkkitietokanta tk0 = null;

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
        File source = new File(dbfile.getAbsolutePath()+"/databases/test/testikanta.db");
        File dest = new File(dbfile.getAbsolutePath()+"/databases/test/testikanta2.db");
        try{
            Files.delete(dest.toPath());
        }catch(IOException e){
        }
        try{
            Files.copy(source.toPath(), dest.toPath());
        }catch(IOException e){
            System.out.println("Ei voida kopioida testi tietokantaa: "+e.toString());
        }
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/databases/test/testikanta2.db";
        tk0 = new Vinkkitietokanta(url);
        if(!errContent.toString().isEmpty()) System.out.println(errContent.toString());
        assertTrue("Tietokanta pitäisi olla liitettynä",tk0.tietokantaliitetty());
    }

    
    @After
    public void tearDown() {
        if(!errContent.toString().isEmpty()) System.out.println(errContent.toString());
        assertTrue(errContent.toString().isEmpty());
        tk0.sulje();
        File dbfile=new File("");
        File dest = new File(dbfile.getAbsolutePath()+"/databases/test/testikanta2.db");
        try{
            Files.delete(dest.toPath());
        }catch(IOException e){
        }
    }

    /**
     * Test of haeKaikkiString method, of class Vinkkitietokanta.
     */
    @Test
    public void test1HaeKaikkiVinkitTyhjaTaulu() {
        System.out.println("haeKaikki(LukuStatus.KAIKKI) on tyhja");
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
    }
    
    @Test
    public void test2HaeKaikkiVinkitTyhjaTauluLuettu() {
        System.out.println("haeKaikki(LukuStatus.LUETTU) on tyhja");
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(0, result.size());
    }

    @Test
    public void test3HaeKaikkiVinkitTyhjaTauluLukemattomat() {
        System.out.println("haeKaikki(LukuStatus.LUKEMATTOMAT) on tyhja");
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
    }
    
    @Test
    public void test4LisaaYksiJaLoytyyTaulusta() {
        System.out.println("Lisää yksi ja löytyy taulusta 1");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
    }
    
    @Test
    public void test4LisaaYksiJaLoytyyTaulusta2() {
        System.out.println("Lisää yksi ja löytyy taulusta 2");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
    }

    @Test
    public void test5LisaaYksiJaLoytyyOikeastaTaulusta3() {
        System.out.println("Lisää yksi ja löytyy taulusta 3");;
        String otsikko = "Koditon kisse";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiPodcast(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
    }
    
    @Test
    public void test5LisaaYksiJaLoytyyOikeastaTaulusta4() {
        System.out.println("Lisää yksi ja löytyy taulusta 4");;
        String otsikko = "Kosmiset Säteet Syövät aivosi";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.PODCAST);
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiPodcast(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
    }    
    
    
    
    
    @Test
    public void test6LisaaKaksiEriKirjaaJaLoytyyTaulusta() {
        System.out.println("Lisää yksi ja löytyy taulusta 5");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        String otsikko2 = "Tunnettu Sotilas";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.KIRJA);
        tk0.lisaaVinkki(vinkki);
        tk0.lisaaVinkki(vinkki2);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(2, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
        assertEquals(result.get(1).otsikko(),otsikko2);
        assertEquals(result.get(1).formaatti(),Formaatit.KIRJA);
    }
    
    @Test
    public void test7LisaaKaksiSamaaKirjaaJaVainYksiLoytyy() {
        System.out.println("Lisää yksi ja löytyy taulusta 6");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        Vinkki vinkki2 = new Vinkki(otsikko,Formaatit.KIRJA);
        tk0.lisaaVinkki(vinkki);
        tk0.lisaaVinkki(vinkki2);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
    }
    
    @Test
    public void test7LisaaKaksiEriVinkkia() {
        System.out.println("Lisää kaksi ja löytyy oikeista tauluista");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        String otsikko2 = "Tunnettu Sotilas";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.PODCAST);
        tk0.lisaaVinkki(vinkki);
        tk0.lisaaVinkki(vinkki2);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(2, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        result = tk0.haeKaikkiPodcast(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
    }
    
    @Test
    public void test7LisaaKolmeEriVinkkia() {
        System.out.println("Lisää Kolme ja löytyy oikeista tauluista");
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        String otsikko2 = "Tunnettu Sotilas";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.PODCAST);
        String otsikko3 = "Koditon kissa löytää kodin";
        Vinkki vinkki3 = new Vinkki(otsikko3,Formaatit.VIDEO);
        tk0.lisaaVinkki(vinkki);
        tk0.lisaaVinkki(vinkki2);
        tk0.lisaaVinkki(vinkki3);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(3, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
        result = tk0.haeKaikkiPodcast(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko2);
        assertEquals(result.get(0).formaatti(),Formaatit.PODCAST);
        result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko3);
        assertEquals(result.get(0).formaatti(),Formaatit.VIDEO);
    }    
 
    @Test
    public void testLisaaLukematonVinkkiKirja() {
        System.out.println("Hae lukemattomat Kirja");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(0, result.size());
    }     

    @Test
    public void testLisaaLuettuVinkkiKirja() {
        System.out.println("Hae luetut Kirja");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        String otsikko = "Fifty shades of terminal";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.KIRJA);
        vinkki.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
        assertEquals(1, result.size());
    }        

    
    @Test
    public void testLisaaLukematonVinkkiPodcast() {
        System.out.println("Hae lukemattomat Podcast");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        String otsikko = "Ajan rakenne";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.PODCAST);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiPodcast(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikkiPodcast(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(0, result.size());
    }     

    @Test
    public void testLisaaLuettuVinkkiPodcast() {
        System.out.println("Hae luetut Podcast");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        String otsikko = "Ajan Rakenne";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.PODCAST);
        vinkki.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiPodcast(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiPodcast(LukuStatus.LUETTU);
        assertEquals(1, result.size());
    }        

    @Test
    public void testLisaaLukematonVinkkiVideo() {
        System.out.println("Hae lukemattomat Video");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiVideot(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        result = tk0.haeKaikkiVideot(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(0, result.size());
    }     

    @Test
    public void testLisaaLuettuVinkkiVideo() {
        System.out.println("Hae luetut Video");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
        assertEquals(0, result.size());
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiVideot(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(1, result.size());
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        result = tk0.haeKaikkiVideot(LukuStatus.LUETTU);
        assertEquals(1, result.size());
    }           
    
    
    @Test
    public void testLisaaVinkkiSekaisinLuettuStatus() {
        System.out.println("Hae luetut sekaisin");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        String otsikko2 = "Madventures";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.VIDEO);
        vinkki2.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki2);
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko2);
    }     

    @Test
    public void testLisaaVinkkiSekaisinLuettuStatus2() {
        System.out.println("Hae luetut sekaisin 2");
        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
        assertEquals(0, result.size());
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        String otsikko2 = "Madventures";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.VIDEO);
        vinkki2.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki2);
        String otsikko3 = "Sensuelli GNU";
        Vinkki vinkki3 = new Vinkki(otsikko3,Formaatit.KIRJA);
        vinkki3.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki3);
        result = tk0.haeKaikki(LukuStatus.LUKEMATTOMAT);
        assertEquals(1, result.size());
        assertEquals(result.get(0).otsikko(),otsikko);
        result = tk0.haeKaikki(LukuStatus.LUETTU);
        assertEquals(2, result.size());
        List<String> otsikot = new ArrayList<>();
        otsikot.add(otsikko2);
        otsikot.add(otsikko3);
        assertTrue(tarkistaEttaSisaltaaOtsikot(otsikot, result));
    }             
    
    @Test
    public void testPoisto() {
        System.out.println("Testaa Poisto 1");
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        tk0.poistaVinkki(otsikko);
        result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(0,result.size());
    }     

    @Test
    public void testPoistoPoisto() {
        System.out.println("Testaa Poisto Poisto");
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
        tk0.poistaVinkki(otsikko);
        result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(0,result.size());
        tk0.poistaVinkki(otsikko);
        result = tk0.haeKaikki(LukuStatus.KAIKKI);
        assertEquals(0,result.size());
    }  
    
    @Test
    public void testPoistoLisaysPoisto() {
        System.out.println("Testaa Poisto 2");
        String otsikko = "Kissevideo";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.merkitseLukemattomaksi();
        tk0.lisaaVinkki(vinkki);
        tk0.poistaVinkki(otsikko);
        List<Vinkki>  result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(0,result.size());
        tk0.lisaaVinkki(vinkki);
        result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(1,result.size());
        tk0.poistaVinkki(otsikko);
        result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertEquals(0,result.size());
    }        
    
    @Test
    public void testTekijaMukana() {
        System.out.println("Testaa Tekija Mukana");
        String otsikko = "Kissevidoe";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.lisaaTekija("Kissanainen");
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertTrue(result.get(0).haeTekijat().contains("Kissanainen"));
    }            
    
    @Test
    public void testUseitaTekijoitaMukana() {
        System.out.println("Testaa Tekija Mukana 1");
        String otsikko = "Madventures";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.lisaaTekija("Riku");
        vinkki.lisaaTekija("Tunna");
        tk0.lisaaVinkki(vinkki);
        List<Vinkki> result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertTrue(result.get(0).haeTekijat().contains("Riku"));
        assertTrue(result.get(0).haeTekijat().contains("Tunna")); 
    }            
    
    @Test
    public void testUseitaTekijoitaMukana2() {
        System.out.println("Testaa Tekija Mukana 2");
        String otsikko = "Madventures";
        Vinkki vinkki = new Vinkki(otsikko,Formaatit.VIDEO);
        vinkki.lisaaTekija("Riku");
        vinkki.lisaaTekija("Tunna");
        tk0.lisaaVinkki(vinkki);
        String otsikko2 = "Kissevideo";
        Vinkki vinkki2 = new Vinkki(otsikko2,Formaatit.PODCAST);
        vinkki2.lisaaTekija("Kissanainen");
        tk0.lisaaVinkki(vinkki2);
        List<Vinkki> result = tk0.haeKaikkiVideot(LukuStatus.KAIKKI);
        assertTrue("Riku Mukana",result.get(0).haeTekijat().contains("Riku"));
        assertTrue("Tunna mukana",result.get(0).haeTekijat().contains("Tunna")); 
        assertEquals(2,result.get(0).haeTekijat().size());
        assertFalse("Kissanainen ei saa olla mukana",result.get(0).haeTekijat().contains("Kissanainen")); 
        
        result = tk0.haeKaikkiPodcast(LukuStatus.KAIKKI);
        assertFalse("Riku ei saa olla mukana",result.get(0).haeTekijat().contains("Riku"));
        assertFalse("Tunna ei saa olla mukana",result.get(0).haeTekijat().contains("Tunna")); 
        assertTrue("Kissanainen pitää olla mukana",result.get(0).haeTekijat().contains("Kissanainen")); 
        assertEquals(1,result.get(0).haeTekijat().size());
    }   
    
    private boolean tarkistaEttaSisaltaaOtsikot(List<String> otsikot, List<Vinkki> lista){
        int laskin = 0;
        List<String> uniikki = new ArrayList<>();
        for(Vinkki vinkki : lista){
            if(otsikot.contains(vinkki.otsikko()) && !uniikki.contains(vinkki.otsikko())){
                uniikki.add(vinkki.otsikko());
                laskin++;
            }
        }
        if(laskin==otsikot.size()) return true;
        return false;
    }
    
    private boolean tarkistaEttaSisaltaaTekijat(List<String> tekijat, Vinkki vinkki){
        int laskin = 0;
        List<String> uniikki = new ArrayList<>();
        for(String tekija : vinkki.haeTekijat()){
            System.out.println(tekija);
            if(tekijat.contains(tekija) && !uniikki.contains(tekija)){
                uniikki.add(tekija);
                laskin++;
            }
        }
        if(laskin==vinkki.haeTekijat().size()) return true;
        return false;
    }    
    
    
    @Test
    public void testAlustusPitääEpäonnistuaJosTietokantaaEiOleOlemassa() {
        System.out.println("testAlustusPitääEpäonnistuaJosTietokantaaEiOleOlemassa");
        File dbfile=new File("");
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/databases/test/asd.db";
        Vinkkitietokanta tk = new Vinkkitietokanta(url);
        assertFalse(errContent.toString().isEmpty());
        errContent.reset();
    }   

    @Test
    public void testSulje() {
        System.out.println("testSulje");
        tk0.sulje();
        assertTrue(errContent.toString().isEmpty());
    }   

    @Test
    public void testEiliitetynKannanSulkeminenEiOleOngelma() {
        System.out.println("testSulje2");
        tk0.sulje();
        tk0.sulje();
        assertTrue(errContent.toString().isEmpty());
        File dbfile=new File("");
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/databases/test/asd.db";
        Vinkkitietokanta tk = new Vinkkitietokanta(url);
        errContent.reset();
        tk.sulje();
        assertTrue(errContent.toString().isEmpty());
    }      
    @Test
    public void testTietokantaLiitetty() {
        System.out.println("testTietokantaLiitetty");
        assertTrue(tk0.tietokantaliitetty());
        tk0.sulje();
        assertFalse(tk0.tietokantaliitetty());
        File dbfile=new File("");
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/databases/test/asd.db";
        Vinkkitietokanta tk = new Vinkkitietokanta(url);
        errContent.reset();
        assertFalse(tk.tietokantaliitetty());
    }  

    @Test 
    public void testaaLisaaPodcastKaksiKertaa(){
        Vinkki vinkki1 = new Vinkki("Marsut pilalla",Formaatit.PODCAST);
        assertTrue(tk0.lisaaVinkki(vinkki1));
        assertFalse(tk0.lisaaVinkki(vinkki1));
        assertEquals(1,tk0.haeKaikki(LukuStatus.KAIKKI).size());
        assertEquals(1,tk0.haeKaikkiPodcast(LukuStatus.KAIKKI).size());
    }

    @Test 
    public void testaaLisaaBlogpostKaksiKertaa(){
        Vinkki vinkki1 = new Vinkki("Marsut pilalla",Formaatit.BLOGPOST);
        assertTrue(tk0.lisaaVinkki(vinkki1));
        assertFalse(tk0.lisaaVinkki(vinkki1));
        assertEquals(1,tk0.haeKaikki(LukuStatus.KAIKKI).size());
        assertEquals(1,tk0.haeKaikkiBlogpost(LukuStatus.KAIKKI).size());
    }

    @Test 
    public void testaaLisaaVideoKaksiKertaa(){
        Vinkki vinkki1 = new Vinkki("Marsut pilalla",Formaatit.VIDEO);
        assertTrue(tk0.lisaaVinkki(vinkki1));
        assertFalse(tk0.lisaaVinkki(vinkki1));
        assertEquals(1,tk0.haeKaikki(LukuStatus.KAIKKI).size());
        assertEquals(1,tk0.haeKaikkiVideot(LukuStatus.KAIKKI).size());
    }
    
    @Test 
    public void testaaLisaaKirjaKaksiKertaa(){
        Vinkki vinkki1 = new Vinkki("Marsut pilalla",Formaatit.KIRJA);
        assertTrue(tk0.lisaaVinkki(vinkki1));
        assertFalse(tk0.lisaaVinkki(vinkki1));
        assertEquals(1,tk0.haeKaikki(LukuStatus.KAIKKI).size());
        assertEquals(1,tk0.haeKaikkiKirjat(LukuStatus.KAIKKI).size());
    }
        
    @Test 
    public void testMerkitseLuetuksiLukemattomaksiEiOlemassa(){
        Vinkki vinkki1 = new Vinkki("Marsut pilalla",Formaatit.BLOGPOST);
        vinkki1.merkitseLuetuksi();
        tk0.lisaaVinkki(vinkki1);
        assertEquals(1,tk0.haeKaikki(LukuStatus.LUETTU).size());
        assertEquals(0,tk0.haeKaikki(LukuStatus.LUKEMATTOMAT).size());
        assertFalse(tk0.merkitseLukemattomaksi("asd"));
        assertEquals(1,tk0.haeKaikki(LukuStatus.LUETTU).size());
        assertEquals(0,tk0.haeKaikki(LukuStatus.LUKEMATTOMAT).size());
        assertFalse(tk0.merkitseLuetuksi("eee"));
        assertEquals(1,tk0.haeKaikki(LukuStatus.LUETTU).size());
        assertEquals(0,tk0.haeKaikki(LukuStatus.LUKEMATTOMAT).size());
    }
    
    @Test
    public void testHaeYksi() {
        System.out.println("Lisää yksi ja löytyy taulusta 1");
        String otsikko1 = "Fifty shades of terminal, Kirja";
        String otsikko2 = "Fifty shades of terminal, Podcast";
        String otsikko3 = "Fifty shades of terminal, Blogpost";
        String otsikko4 = "Fifty shades of terminal, Video";
        Vinkki kirja = new Vinkki(otsikko1,Formaatit.KIRJA);
        Vinkki podcast = new Vinkki(otsikko2,Formaatit.PODCAST);
        Vinkki blogpost = new Vinkki(otsikko3,Formaatit.BLOGPOST);
        Vinkki video = new Vinkki(otsikko4,Formaatit.VIDEO);
        tk0.lisaaVinkki(kirja);
        tk0.lisaaVinkki(podcast);
        tk0.lisaaVinkki(blogpost);
        tk0.lisaaVinkki(video);
        Vinkki vinkki = tk0.haeVinkki(otsikko1);
        assertEquals(vinkki.otsikko(),otsikko1);
        assertEquals(vinkki.formaatti(),Formaatit.KIRJA);
        vinkki = tk0.haeVinkki(otsikko2);
        assertEquals(vinkki.otsikko(),otsikko2);
        assertEquals(vinkki.formaatti(),Formaatit.PODCAST);
        vinkki = tk0.haeVinkki(otsikko3);
        assertEquals(vinkki.otsikko(),otsikko3);
        assertEquals(vinkki.formaatti(),Formaatit.BLOGPOST);
        vinkki = tk0.haeVinkki(otsikko4);
        assertEquals(vinkki.otsikko(),otsikko4);
        assertEquals(vinkki.formaatti(),Formaatit.VIDEO);
    }

    @Test
    public void testTagit() {
        System.out.println("testaa tageja");
        String otsikko1 = "Fifty shades of terminal, Kirja";
        String otsikko2 = "Fifty shades of terminal, Podcast";
        String otsikko3 = "Fifty shades of terminal, Blogpost";
        String otsikko4 = "Fifty shades of terminal, Video";
        Vinkki kirja = new Vinkki(otsikko1,Formaatit.KIRJA);
        Vinkki podcast = new Vinkki(otsikko2,Formaatit.PODCAST);
        Vinkki blogpost = new Vinkki(otsikko3,Formaatit.BLOGPOST);
        Vinkki video = new Vinkki(otsikko4,Formaatit.VIDEO);
        kirja.lisaaTag("puupuristin");
        video.lisaaTag("massa");
        blogpost.lisaaTag("puupuristin");
        blogpost.lisaaTag("rändöm");
        tk0.lisaaVinkki(kirja);
        tk0.lisaaVinkki(podcast);
        tk0.lisaaVinkki(blogpost);
        tk0.lisaaVinkki(video);
        List<Vinkki> vinkit = tk0.haeTagilla("Torrentit");
        assertEquals(0,vinkit.size());
        vinkit = tk0.haeTagilla("massa");
        assertEquals(1,vinkit.size());
        assertEquals(video.formaatti(),vinkit.get(0).formaatti());
        assertEquals(otsikko4,vinkit.get(0).otsikko());
        vinkit = tk0.haeTagilla("puupuristin");
        assertEquals(2,vinkit.size());

    }

}
