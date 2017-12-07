/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;


import Vinkkitietokanta.DAO.BlogpostDAO;
import Vinkkitietokanta.DAO.KirjatDAO;
import Vinkkitietokanta.DAO.PodcastDAO;
import Vinkkitietokanta.DAO.VinkkiDAO;
import Vinkkitietokanta.DAO.TekijatDAO;
import Vinkkitietokanta.DAO.VideotDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */

/*
 MI/24.11.2017 Tauluissa Vinkki, Kirja, Podcast, Video ja Tekija on päällä juokseva id numerointi kun tauluun lisää rivin.

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
 Jos käyttäjä ei ole syöttänyt tekijää, ohjelma lisää hänen puolestaan tekijälle dummyarvon (esim. 'tuntematon')


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
    TekijatDAO tekijatDAO = null;
    KirjatDAO kirjatDAO = null;
    PodcastDAO podcastDAO = null;
    BlogpostDAO blogpostDAO = null;
    VideotDAO videotDAO = null;
    VinkkiDAO vinkkiDAO = null;
    Connection conn = null;

    public Vinkkitietokanta(String tkPath) {
        //Liitä tietokanta        
        try {

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
            SQLiteConfig config = new SQLiteConfig();  
            config.enforceForeignKeys(true);
            config.resetOpenMode(SQLiteOpenMode.CREATE);
            conn = DriverManager.getConnection(tkPath,config.toProperties());
            tekijatDAO = new TekijatDAO(conn);
            kirjatDAO = new KirjatDAO(conn);
            blogpostDAO = new BlogpostDAO(conn);
            videotDAO = new VideotDAO(conn);
            vinkkiDAO = new VinkkiDAO(conn);
            podcastDAO = new PodcastDAO(conn);
            
            
        } catch (SQLException e) {
            System.err.println("Vinkkitietokanta: " +e.getMessage());
        }
    }

    public void sulje() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            System.err.println("sulje: " +se.getMessage());
        }
    }

    public boolean tietokantaliitetty() {
        if (conn == null) return false;
        try {
            return !conn.isClosed();
        } catch (SQLException se) {
            System.err.println("tietokantaliitetty: "+se.getMessage());
        }
        return false;
    }
    


    ///////////////////////////////////////
    ///////////////////////////////////////
    ///Lisaa ja poista vinkki
    @Override
    public boolean lisaaVinkki(Vinkki vinkki) {
        if (null != vinkki.formaatti()) {
            if(!vinkkiDAO.haeOtsikolla(vinkki.Otsikko()).isEmpty()) return false;
            vinkkiDAO.luoVinkki(vinkki.Otsikko(), vinkki.luettu());
            String vinkkiID = vinkkiDAO.haeOtsikolla(vinkki.Otsikko());
            if(vinkkiID.isEmpty()) return false;
            List<String> tekijaIDt = tekijatDAO.haeJaPaivitaTekijat(vinkkiID, vinkki.haeTekijat());
            tekijatDAO.liitaTekijat(vinkkiID, tekijaIDt);
            switch (vinkki.formaatti()) {
                case KIRJA:
                    return kirjatDAO.lisaaVinkki(vinkkiID, vinkki);
                case PODCAST:
                    return podcastDAO.lisaaVinkki(vinkkiID, vinkki);
                case VIDEO:
                    return videotDAO.lisaaVinkki(vinkkiID, vinkki);
                case BLOGPOST:
                    return blogpostDAO.lisaaVinkki(vinkkiID, vinkki);
            }
        }
        return false;
    }

    @Override
    public boolean poistaVinkki(String otsikko){
        return vinkkiDAO.poistaVinkki(otsikko);
    }

    
    ///////////////////////////////////////
    ///////////////////////////////////////
    ///Merkitse Luetuksi
    @Override
    public boolean merkitseLuetuksi(String otsikko) {
        String vinkkiID = vinkkiDAO.haeOtsikolla(otsikko);
        if (vinkkiID.isEmpty()) {
            return false;
        }
        return vinkkiDAO.merkitseLukuStatus(vinkkiID, true);
    }

    @Override
    public boolean merkitseLukemattomaksi(String otsikko) {
        String vinkkiID = vinkkiDAO.haeOtsikolla(otsikko);
        if (vinkkiID.isEmpty()) {
            return false;
        }
        return vinkkiDAO.merkitseLukuStatus(vinkkiID, false);
    }


    ///////////////////////////////////////
    ///////////////////////////////////////
    ///Apufunktioita
    @Override
    public List<String> muunnaVinkkiLista(List<Vinkki> vinkkiLista) {
        List<String> lista = new ArrayList<>();
        for (Vinkki vinkki : vinkkiLista) {
            lista.add(vinkki.toString());
        }
        return lista;
    }
    
    
    ///////////////////////////////////////
    ///////////////////////////////////////
    ///HAE KAIKKI METODIT
    @Override
    public List<Vinkki> haeKaikki(LukuStatus status) {
        List<Vinkki> lista = kirjatDAO.haeListana(status, new ArrayList<>());
        lista = videotDAO.haeListana(status, lista);
        lista = podcastDAO.haeListana(status, lista);
        lista = blogpostDAO.haeListana(status, lista);
        return lista;
    }
 
    @Override
    public List<Vinkki> haeKaikkiKirjat(LukuStatus status) {
        return kirjatDAO.haeListana(status, new ArrayList<>());
    }
    
    @Override
    public List<Vinkki> haeKaikkiVideot(LukuStatus status) {
        return videotDAO.haeListana(status, new ArrayList<>());
    }

    @Override
    public List<Vinkki> haeKaikkiBlogpost(LukuStatus status) {
        return blogpostDAO.haeListana(status, new ArrayList<>());
    }

    @Override
    public List<Vinkki> haeKaikkiPodcast(LukuStatus status) {
        return podcastDAO.haeListana(status, new ArrayList<>());
    }

    public Connection getConn() {
        return conn;
    }


}
