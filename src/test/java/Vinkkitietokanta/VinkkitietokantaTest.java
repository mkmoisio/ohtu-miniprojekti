///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Vinkkitietokanta;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.nio.file.Files;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author rokka
// */
//public class VinkkitietokantaTest {
//    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
//    Vinkkitietokanta tk0 = null;
//    Vinkki vinkki = new Vinkki("Kirjannimi",Formaatit.KIRJA); 
//
//    public VinkkitietokantaTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        System.setErr(new PrintStream(errContent));
//        //Puhdista paremmaksi
//        File dbfile=new File("");
//        File source = new File(dbfile.getAbsolutePath()+"/sprint2testikanta.db");
//        File dest = new File(dbfile.getAbsolutePath()+"/sprint2testikanta2.db");
//        try{
//            Files.delete(dest.toPath());
//        }catch(IOException e){
//        }
//        try{
//            Files.copy(source.toPath(), dest.toPath());
//        }catch(IOException e){
//            System.out.println("Ei voida kopioida testi tietokantaa: "+e.toString());
//        }
//        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/sprint2testikanta2.db";
//        tk0 = new Vinkkitietokanta(url);
//        System.out.println(errContent.toString());
//        assertTrue("Tietokanta pitäisi olla liitettynä",tk0.tietokantaliitetty());
//    }
//
//    
//    @After
//    public void tearDown() {
//        tk0.sulje();
//        File dbfile=new File("");
//        File dest = new File(dbfile.getAbsolutePath()+"/sprint2testikanta2.db");
//        try{
//            Files.delete(dest.toPath());
//        }catch(IOException e){
//        }
//    }
//
//    /**
//     * Test of haeKaikkiString method, of class Vinkkitietokanta.
//     */
//    @Test
//    public void testHaeKaikkiKirjatString() {
//        System.out.println("haeKaikkiKirjatString");
//        List<String> result = tk0.haeKaikkiKirjatString(LukuStatus.KAIKKI);
//        assertEquals(3, result.size());
//    }
//
//    
//    /**
//     * Test of haeKaikki method, of class Vinkkitietokanta.
//     */
//    
//    @Test
//    public void testHaeKaikkiKirjat() {
//        System.out.println("haeKaikkiKirjat");
//        List<Vinkki> result = tk0.haeKaikki(LukuStatus.KAIKKI);
//        assertEquals(3, result.size());
//        assertEquals(result.get(0).haeTekijat().get(0),"Matti");
//        assertEquals(result.get(0).Otsikko(),"otsikko1");
//        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
//    }  
//    
//    @Test
//    public void testHaeKaikkiKirjatLukemattomat() {
//        System.out.println("haeKaikkiKirjat Lukemattomat");
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
//        assertEquals(3, result.size());
//        assertEquals(result.get(0).haeTekijat().get(0),"Matti");
//        assertEquals(result.get(0).Otsikko(),"otsikko1");
//        assertEquals(result.get(0).formaatti(),Formaatit.KIRJA);
//    }        
//
//        @Test
//    public void testHaeKaikkiKirjatLuetut() {
//        System.out.println("haeKaikkiKirjat Lukemattomat");
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
//        assertEquals(0, result.size());
//    }   
//
//    /**
//    * Test of lisaaKirja method, of class Vinkkitietokanta.
//    */
//    @Test
//    public void testLisaaKirjaVinkkiSuoraan() {
//        System.out.println("lisaaKirja suoraan");
//        String otsikko = "Sushia ja loisia";
//        Vinkki kirja = new Vinkki(otsikko,Formaatit.KIRJA);
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
//        assertEquals(3, result.size());
//        tk0.lisaaKirja(kirja);
//        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
//        assertEquals(4, result.size());
//        Vinkki vinkki2 = result.get(3);
//        assertEquals(kirja.printtaaTekijat(), vinkki2.printtaaTekijat());
//        assertEquals(kirja.Otsikko(), vinkki2.Otsikko());
//    }
//    
//    
//    /**
//    * Test of lisaaKirja method, of class Vinkkitietokanta.
//    */
//    @Test
//    public void testLisaaKirjaVinkkiSuoraan2() {
//        System.out.println("lisaaKirja suoraan2");
//        String otsikko = "Sushia ja loisia";
//        Vinkki kirja = new Vinkki(otsikko,Formaatit.KIRJA);
//        kirja.lisaaTekija("MARUTEI SENPAI");
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
//        assertEquals(3, result.size());
//        tk0.lisaaKirja(kirja);
//        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);
//        assertEquals(4, result.size());
//        Vinkki vinkki2 = result.get(3);
//        assertEquals(kirja.printtaaTekijat(), vinkki2.printtaaTekijat());
//        assertEquals(kirja.Otsikko(), vinkki2.Otsikko());
//    }
//    
//    
//    
//    /**
//     * Test of poistaKirja method, of class Vinkkitietokanta.
//     */
//    @Test
//    public void testPoistaKirja() {
//        System.out.println("poistaKirja");
//        String otsikko = "Sushia ja loisia";
//        Vinkki vinkki = new Vinkki(otsikko, Formaatit.KIRJA);
//        vinkki.lisaaTekija("Törvalds");
//        tk0.lisaaVinkki(vinkki);
//        otsikko = "Sensuelli GNU";
//        Vinkki vinkki2 = new Vinkki(otsikko, Formaatit.KIRJA);
//        vinkki2.lisaaTekija("Stallman");
//        tk0.lisaaVinkki(vinkki2);
//        List<Vinkki> result = tk0.haeKaikki();
//        assertEquals(5, result.size());
//        tk0.poistaKirja("Sushia ja loisia");
//        result = tk0.haeKaikki();
//        assertEquals(4, result.size());
//        tk0.poistaVinkki("Sensuelli GNU");
//        result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);        
//        assertEquals(3,result.size());
//        for(String str : tk0.haeKaikkiString()){
//            assertFalse(str.contains("Törvalds"));
//            assertFalse(str.contains("Stallman"));
//        }
//    }
//
//    @Test
//    public void testPoistaEiOlemassaOleva() {
//        System.out.println("poistaKirja ei olemassa");
//        tk0.poistaKirja("Sushia ja loisia");
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.KAIKKI);     
//        assertEquals(3,result.size());
//    }
//
//    @Test
//    public void TestLukematonAluksi() {
//        System.out.println("Kirja lukematon aluksi");
//        String otsikko = "Sushia ja loisia";
//        Vinkki kirja = new Vinkki(otsikko,Formaatit.KIRJA);
//        kirja.lisaaTekija("MARUTEI SENPAI");
//        tk0.lisaaVinkki(kirja);
//        List<String> result = tk0.haeKaikkiKirjatString(LukuStatus.LUKEMATTOMAT);
//        boolean sisaltaaMarutei=false;
//        for(String str : tk0.haeKaikkiString()){
//            if(str.contains("MARUTEI SENPAI")) sisaltaaMarutei=true;
//        }
//        assertTrue(sisaltaaMarutei);
//    }
//    
//    
//    @Test
//    public void TestMerkitseLuetuksi() {
//        System.out.println("Merkitty luetuksi");
//        String otsikko = "Sushia ja loisia";
//        Vinkki kirja = new Vinkki(otsikko,Formaatit.KIRJA);
//        kirja.lisaaTekija("MARUTEI TSURUNEN");
//        tk0.lisaaVinkki(kirja);
//        tk0.merkitseLuetuksi(otsikko);
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
//        assertEquals(1,result.size());
//        boolean sisaltaaMarutei=false;
//        for(Vinkki vinkki : result){
//            if(vinkki.printtaaTekijat().contains("MARUTEI TSURUNEN")) sisaltaaMarutei=true;
//        }
//        assertTrue(sisaltaaMarutei);
//        result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
//        assertEquals(3,result.size());
//        sisaltaaMarutei=false;
//        for(Vinkki vinkki : result){
//            if(vinkki.printtaaTekijat().contains("MARUTEI TSURUNEN")) sisaltaaMarutei=true;
//        }
//        assertFalse(sisaltaaMarutei);
//    }
//    
//
//    @Test
//    public void TestMerkitseLukemattomaksi() {
//        System.out.println("Merkitse lukemattomaksi");
//        String otsikko = "Sushia ja loisia";
//        Vinkki kirja = new Vinkki(otsikko,Formaatit.KIRJA);
//        kirja.lisaaTekija("MARUTEI TSURUNEN");
//        tk0.lisaaVinkki(kirja);
//        tk0.merkitseLukemattomaksi(otsikko);
//        List<Vinkki> result = tk0.haeKaikkiKirjat(LukuStatus.LUETTU);
//        assertEquals(0,result.size());
//        boolean sisaltaaMarutei=false;
//        for(Vinkki vinkki : result){
//            if(vinkki.printtaaTekijat().contains("MARUTEI TSURUNEN")) sisaltaaMarutei=true;
//        }
//        assertFalse(sisaltaaMarutei);
//        result = tk0.haeKaikkiKirjat(LukuStatus.LUKEMATTOMAT);
//        assertEquals(4,result.size());
//        sisaltaaMarutei=false;
//        for(Vinkki vinkki : result){
//            if(vinkki.printtaaTekijat().contains("MARUTEI TSURUNEN")) sisaltaaMarutei=true;
//        }
//        assertTrue(sisaltaaMarutei);
//    }
//    
//    
//}
