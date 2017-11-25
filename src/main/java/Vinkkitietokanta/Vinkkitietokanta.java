/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */

/*
MI/24.11.2017 Tauluissa Vinkki, Kirja, Podcast, Video ja Kirjoittaja on päällä juokseva id numerointi kun tauluun lisää rivin.

Dumppaan tähän Sprint2:een tarvittavat SQL-komennot liittyen lisäämiseen, poistamiseen ja listaukseen!
(Eivät ole parametrisoituja tarkoituksella, siitä taisi olla Timolla taski)

http://sqlfiddle.com/#!7/4cb80 kautta voi myös katsoa tauluja ja kokeilla komentoja

Listaa valituntyyppiset vinkit (esim. video)
............................................................................

SELECT vinkki.*, video.*, GROUP_CONCAT(tekija.tekija_nimi)
tekija
FROM Vinkki
INNER JOIN Video ON vinkki_id=video.vinkki
INNER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki
INNER JOIN Tekija on tekija_id=tekija;

Ja laita videon tilalle kirja tai podcast niiden vastaaville

Antaa tulosteen: (HUOM yhdistää useamman tekijän samaan sarakkeeseen, tämän voi muuttaakin)
vinkki_id   otsikko luettu  video_id    url vinkki  tekija
4   otsikko5    1   1   https://www.youtube.com/watch?v=Jr9R9NT9lk8 4   Maiju,Maria

Listaa kaikki vinkit ja onko luettu
....................................................................................................................

SELECT vinkki_id, luettu, GROUP_CONCAT(tekija.tekija_nimi)
tekija, otsikko
FROM vinkki
  JOIN vinkkitekija on tekija_id=tekija 
  JOIN tekija on vinkki_id=vinkkitekija.vinkki  
GROUP BY vinkki_id;

Antaa tulosteen:
vinkki_id   luettu  tekija  otsikko
1   0   Matti   otsikko1

HUOM SINÄ JAVA-KOODISSA SQL-KOMENTOJA KÄSITTELEVÄ: en saanut tätä toimimaan muuten kuin siten, että jokaisella vinkillä
pitää olla jokin tekijä ( eli myös videolla). Voi olla että liittyy siihen, että SQLite ei tue FULLJOIN-komentoa. Tai sit vaan
en osannut. Voitko laittaa koodiin:
Joko käyttöliittymään validoinnit jokaiselle vinkille että pakko syöttää tekijä (myös videolle) 
TAI
Jos käyttäjä ei ole syöttänyt tekijää, ohjelma syöttää antaa hänen puolestaan tekijälle dummyarvon (esim. 'tuntematon')


Lisää 
.......

1. Käyttöliittymä kysyy otsikkoa
INSERT INTO Vinkki (otsikko,luettu) VALUES ('otsikko', '0');
HUOM!!! SQLite ei tue TRUE/FALSE-tyylistä joten vaikka luettu on boolean, tietokannassa 
sen arvot ovat joko 0 eli false tai 1 eli true.

2. Käyttöliittymä kysyy tekijää (huom katso kommenttini kohdassa "Listaa kaikki vinkit ja onko luettu")

INSERT INTO Tekija (nimi) VALUES ('James Bond');
INSERT INTO VinkkiTekija (vinkki,tekija) VALUES ('','');

3. Käyttöliittymä kysyy tyyppiä ja sen mukaisesti jatkokysymykset

INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES ();

INSERT INTO Video (url, vinkki) VALUES (); 

INSERT INTO Podcast (nimi,kuvaus,vinkki) VALUES ();

// missä vinkki on viiteavain Vinkki-tauluun



Poista
.................................................................................
Tämä on tosi rumasti tehty mutta sprintin aika loppui kesken tutkimisen ja halusin tämä toimimaan edes jotenkuten. 
Cascade-toiminto ei toimi lainkaan, ks. lisää konstruktorin kommentissa. Jos jollain riittää aika saa laittaa paremmaksi!! 

1. Käyttöliittymä kysyy otsikkoa 

SELECT vinkki_id FROM Vinkki WHERE otsikko='Käyttäjän syöttämä otsikko';
DELETE FROM Kirja WHERE vinkki='' //edellisen haun tulos tähän
DELETE FROM Vinkki WHERE vinkki_id= '' 
SELECT tekija FROM VinkkiTekija WHERE vinkki= ''
DELETE FROM vinkkitekija WHERE vinkki_id= ''
DELETE FROM tekija WHERE tekija_id= '' //Select tekija..haun tulos tuohon

Ja vastaavasti videolle ja podcastille
 
*/



public class Vinkkitietokanta implements VinkkitietokantaRajapinta {

    Connection conn = null;

    public Vinkkitietokanta(String tkPath){
        //Liitä tietokanta        
        try{

	/*MI/23.11 Tämä osa kommentoitu pois ainakin sprint 2 kohdalla.
	SQLitessa silleen että vaikka on ON DELETE CASCADE viiteavainten kohdalla niin joku viiteavainsupport ei ole oletuksena päällä
	eikä CASCADE siis oikeasti toimi suoraan: foreign keys pitää ensin laittaa päälle.
	Kun laitoin tämän viiteavainsupportin päälle (suoraan tietokannassa, allaolevassa koodissa oli ajatuksena että
	sama tehdään koodillisesti aina kun tietokantayhteys avataan) niin vinkin poistaminen ei toiminut lainkaan. Siksi vastaava 
    koodi alla ei käytössä enkä ehdi sprint2 kohdalla tutkia enempää.
	Tästä johtuen poistaminen on tällä hetkellä niin manuaalista eli pitää käydä joka taulussa poistamassa tietoja.
	
    Nämä koodirivit ovat täältä: http://code-know-how.blogspot.fi/2011/10/how-to-enable-foreign-keys-in-sqlite3.html				
	SQLiteConfig config = new SQLiteConfig();  
    config.enforceForeignKeys(true);*/

            conn=DriverManager.getConnection(tkPath);
            //System.out.println("tietokanta liitetty");      
            //System.out.println("statement luotu");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    //Sulje yhteydet tietokantaan
    public void sulje(){
        try{
           if(conn!=null)
              conn.close();
        }catch(SQLException se){
           se.printStackTrace();
        }
    }
    

    public boolean tietokantaliitetty(){
        if(conn==null) return false;
        return true;
    }

    @Override
    public boolean lisaaPodcast(Vinkki podcast) {
        return lisaaVinkki(podcast);
    }

    @Override
    public boolean lisaaVideo(Vinkki video) {
        return lisaaVinkki(video);
    }
    
    @Override
    public boolean lisaaKirja(Vinkki kirja) {
        return lisaaVinkki(kirja);
    }
    
    @Override
    public boolean lisaaKirja(String otsikko) {
        return lisaaKirja("",otsikko);
    }

    @Override
    public boolean lisaaKirja(String kirjoittajat, String otsikko) {
        Vinkki kirja = new Vinkki(otsikko, Formaatit.KIRJA);
        if(kirjoittajat!=null || !kirjoittajat.equals("")){
            kirja.lisaaTekijat(kirjoittajat);
        }
        lisaaVinkki(kirja);
        return true;
    }

    @Override
    public boolean poistaKirja(String otsikko) {
        return poistaVinkki(otsikko);
    }
    
    //EI me haluta kaikkia tekijöitä poistaa
    @Override
    public boolean poistaVinkki(String otsikko) {
        String vinkkiID=haeOtsikolla(otsikko);
        //System.out.println("ID"+vinkkiID);
        if(vinkkiID.isEmpty()) return false;
        //String poistaKirja = "DELETE FROM Kirja WHERE vinkki_id=?";
        String poistaVinkki = "DELETE FROM Vinkki WHERE vinkki_id=?";
        //String poistaVinkkiTekijä = "DELETE FROM VinkkiTekijä WHERE vinkki_id=?";
        try {
            //PreparedStatement komento1 = conn.prepareStatement(poistaKirja);
            PreparedStatement komento2 = conn.prepareStatement(poistaVinkki);
            //PreparedStatement komento3 = conn.prepareStatement(poistaVinkkiTekijä);
            //komento1.setInt(1,Integer.parseInt(vinkkiID));
            komento2.setInt(1,Integer.parseInt(vinkkiID));
            //komento3.setInt(1,Integer.parseInt(vinkkiID));
            //komento1.executeUpdate();
            komento2.executeUpdate();
            //komento3.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    
        
        return false;
    }

    private boolean tekijaLiitetty(String vinkkiID, String tekijaID){
        String haeTekija = "SELECT * FROM VinkkiTekija WHERE vinkki=? AND tekija=?";
        try {
            PreparedStatement komento = conn.prepareStatement(haeTekija);
            komento.setInt(1,Integer.parseInt(vinkkiID));
            komento.setInt(2,Integer.parseInt(tekijaID));
            ResultSet rs = komento.executeQuery();
            boolean loyty = false;
            while(rs.next()){
                loyty = true;
            }
            return loyty;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    private void liitaTekija(String vinkkiID, String tekijaID){
        String lisaaVinkkiin =  "INSERT INTO VinkkiTekija (vinkki,tekija) VALUES (?,?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setInt(1,Integer.parseInt(vinkkiID));
            komento.setInt(2,Integer.parseInt(tekijaID));
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void liitaTekijat(String vinkkiID, List<String> tekijaIDt){
        for(String tekijaID : tekijaIDt){
            if(!tekijaLiitetty(vinkkiID,tekijaID)) liitaTekija(vinkkiID, tekijaID);
        }
    };
    
    private String haeTekija(String nimi){
        String haeTekija = "SELECT tekija_id FROM Tekija WHERE tekija_nimi=?";
        try {
            PreparedStatement komento = conn.prepareStatement(haeTekija);
            komento.setString(1,nimi);
            ResultSet rs = komento.executeQuery();
            String kirjaID = "";
            while(rs.next()){
                kirjaID = Integer.toString(rs.getInt("tekija_id"));
            }
            return kirjaID;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    private void luoTekija(String nimi){
        String lisaaVinkkiin = "INSERT INTO Tekija (tekija_nimi) VALUES (?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1,nimi);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    


    public String haeOtsikolla(String otsikko){
        String haeID = "SELECT vinkki_id FROM Vinkki WHERE otsikko=?";
        try {
            PreparedStatement komento = conn.prepareStatement(haeID);
            komento.setString(1,otsikko);
            ResultSet rs = komento.executeQuery();
            String kirjaID = "";
            while(rs.next()){
                kirjaID = rs.getString("vinkki_id");
            }
            return kirjaID;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    
    public void luoVinkki(String otsikko, boolean luettu){
        String lisaaVinkkiin = "INSERT INTO Vinkki (otsikko,luettu) VALUES (?, ?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1,otsikko);
            komento.setBoolean(2,luettu);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String haeJaLuoVinkkiID(Vinkki vinkki) {
        String kirjaID = haeOtsikolla(vinkki.Otsikko());
        if(kirjaID.isEmpty()){
            luoVinkki(vinkki.Otsikko(),vinkki.luettu());
        }
        return haeOtsikolla(vinkki.Otsikko());
    }

    
    private List<String> haeJaPaivitaTekijat(String vinkkiID, List<String> tekijat) {
        List<String> tekijaIDt = new ArrayList<>();
        for(String tekija: tekijat){
            String tekijaID = haeTekija(tekija);
            if(tekijaID.isEmpty()){
                luoTekija(tekija);
                tekijaID=haeTekija(tekija);
            }
            tekijaIDt.add(tekijaID);
        }
        return tekijaIDt;
    }

    public boolean lisaaKirja(String vinkkiID,Vinkki kirja) {
        //INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES ();
        String lisaaVinkkiin = "INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES (?,?,?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1,kirja.haeOminaisuus(Attribuutit.ISBN));
            komento.setString(1,kirja.haeOminaisuus(Attribuutit.KUVAUS));
            komento.setInt(3,Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    
    //////////////IMPLEMENTOI NÄMÄ
    private boolean lisaaPodcast(String vinkkiID, Vinkki vinkki) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean lisaaVideo(String vinkkiID, Vinkki vinkki) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public boolean lisaaVinkki(Vinkki vinkki) {
        if(null!=vinkki.formaatti()){
            String vinkkiID = haeJaLuoVinkkiID(vinkki);
            List<String> tekijaIDt = haeJaPaivitaTekijat(vinkkiID,vinkki.haeTekijat());
            liitaTekijat(vinkkiID, tekijaIDt);
            switch (vinkki.formaatti()) {
            case KIRJA:
                return lisaaKirja(vinkkiID,vinkki);
            case PODCAST:
                return lisaaPodcast(vinkkiID,vinkki);
            case VIDEO:
                return lisaaVideo(vinkkiID,vinkki);
            }
        }
        return false;
    }
    

        
    private boolean merkitseLukuStatus(String vinkkiID, boolean luettu){
        String lisaaVinkkiin = "UPDATE vinkki SET luettu=? WHERE vinkki_id=?";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setBoolean(1,luettu);
            komento.setInt(2,Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean merkitseLuetuksi(String otsikko) {
        String vinkkiID = haeOtsikolla(otsikko); 
        if(vinkkiID.isEmpty()) return false;
        return merkitseLukuStatus(vinkkiID,true);
    }

    @Override
    public boolean merkitseLukemattomaksi(String otsikko) {
        String vinkkiID = haeOtsikolla(otsikko); 
        if(vinkkiID.isEmpty()) return false;
        return merkitseLukuStatus(vinkkiID,false);
    }

    

    
    
    ///////////////////////////////////////
    ///////////////////////////////////////
    ///HAE KAIKKI METODIT
    
    @Override
    public List<Vinkki> haeKaikkiKirjat(LukuStatus status) {
        return haeKaikkiKirjatBase(status, null);
    }

    @Override
    public List<String> haeKaikkiKirjatString(LukuStatus status) {
        List<Vinkki> vinkkiLista = haeKaikkiKirjatBase(status, null);
        return muunnaVinkkiLista(vinkkiLista);
    }

    private List<String> muunnaVinkkiLista(List<Vinkki> vinkkiLista){
        List<String> lista = new ArrayList<>();
        for(Vinkki vinkki:vinkkiLista){
            lista.add(vinkki.toString());
        }
        return lista;
    }
    
    @Override
    public List<String> haeKaikkiString(LukuStatus status) {
        List<Vinkki> vinkkiLista = haeKaikki(status);
        return muunnaVinkkiLista(vinkkiLista);
    }

    //////////////IMPLEMENTOI NÄMÄ
    @Override
    public List<Vinkki> haeKaikki(LukuStatus status) {
        List<Vinkki> lista =haeKaikkiKirjatBase(status, null);
        //haeKaikkiVideotBase(status, lista);
        //haeKaikkiPodcastBase(status, lista);
        return lista;
    }

    private List<Vinkki> haeKaikkiKirjatBase(LukuStatus status, List<Vinkki> list) {
        /*
        SELECT vinkki.otsikko, kirja.isbn, kirja.kuvaus, group_concat(tekija_nimi, ' ') as tekijat
        FROM Vinkki
        INNER JOIN kirja ON vinkki_id=kirja.vinkki
        LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki
        LEFT OUTER JOIN Tekija on tekija_id=tekija
        GROUP BY vinkki_id
        */
        String haeKirjatString = "SELECT vinkki.otsikko, vinkki.luettu, kirja.isbn, kirja.kuvaus, group_concat(tekija_nimi, ' ') as tekijat \n"
                                  +  "FROM Vinkki \n"
                                  +  "INNER JOIN kirja ON vinkki_id=kirja.vinkki \n"
                                  +  "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                                  +  "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                                  +  "GROUP BY vinkki_id";
        try {
            PreparedStatement komento = conn.prepareStatement(haeKirjatString);
            List<Vinkki> lista = null;
            if(list==null){
                lista = new ArrayList<>();
            }else{
                lista = list;
            }
            
            ResultSet rs = komento.executeQuery();
            while(rs.next()){
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if(luettu==status.getValue() || status==LukuStatus.KAIKKI){
                    Vinkki kirja = new Vinkki(rs.getString("otsikko"), Formaatit.KIRJA);
                    if(luettu==0){
                        kirja.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.FALSE);
                    }else{
                        kirja.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.TRUE);
                    }
                    kirja.lisaaOminaisuus(Attribuutit.ISBN, rs.getString("isbn"));
                    kirja.lisaaTekijat(rs.getString("tekijat"));
                    kirja.lisaaOminaisuus(Attribuutit.KUVAUS, rs.getString("kuvaus"));
                    lista.add(kirja);                   
                }
            }
            komento.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return null;
    }
    
    
    
    //////////////IMPLEMENTOI NÄMÄ
    private List<Vinkki> haeKaikkiPodcastBase(LukuStatus status, List<Vinkki> list){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    private List<Vinkki> haeKaikkiVideotBase(LukuStatus status, List<Vinkki> list){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 

    @Override
    public List<Vinkki> haeKaikkiVideot(LukuStatus status) {
        return haeKaikkiVideotBase(status, null);
    }

    @Override
    public List<String> haeKaikkiVideotString(LukuStatus status) {
        List<Vinkki> vinkkiLista = haeKaikkiVideotBase(status, null);
        return muunnaVinkkiLista(vinkkiLista);
    }

    @Override
    public List<Vinkki> haeKaikkiPodcast(LukuStatus status) {
        return haeKaikkiPodcastBase(status, null);
    }

    @Override
    public List<String> haeKaikkiPodcastString(LukuStatus status) {
        List<Vinkki> vinkkiLista = haeKaikkiPodcastBase(status, null);
        return muunnaVinkkiLista(vinkkiLista);
    }

    @Override
    public List<Vinkki> haeKaikki() {
        return haeKaikki(LukuStatus.KAIKKI);
    }

    @Override
    public List<String> haeKaikkiString() {
        return haeKaikkiString(LukuStatus.KAIKKI);
    }


}
