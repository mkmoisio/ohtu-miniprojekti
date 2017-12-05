/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    Connection conn = null;
    String tkPath;

    public Vinkkitietokanta(String tkPath) {      
        this.tkPath= tkPath;
    }
    
    public Connection avaaYhteys(){
        try {
            SQLiteConfig config = new SQLiteConfig();  
            config.enforceForeignKeys(true);
            config.resetOpenMode(SQLiteOpenMode.CREATE);
            conn = DriverManager.getConnection(tkPath,config.toProperties());
        } catch (SQLException e) {
            System.err.println("Vinkkitietokanta: " +e.getMessage());
        } 
        return conn;
    }

    //Sulje yhteydet tietokantaan
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

    @Override
    public boolean lisaaPodcast(Vinkki podcast) {
        return lisaaVinkki(podcast);
    }

    @Override
    public boolean lisaaVideo(Vinkki video) {
        return lisaaVinkki(video);
    }
    
    @Override
    public boolean lisaaBlogpost(Vinkki blogpost) {
        return lisaaVinkki(blogpost);
    }

    @Override
    public boolean lisaaKirja(Vinkki kirja) {
        return lisaaVinkki(kirja);
    }

    /*@Override
    public boolean lisaaKirja(String otsikko) {
        return lisaaKirja("", otsikko);
    } onko käytössä? */

    /* KÄYTTÖLIITTYMÄSTÄ PÄÄDYTÄÄN TÄHÄN! TÄSSÄ LUODAAN VINKKI OIKEALLA FORMAATILLA JA LIITETÄÄN TEKIJÄT&TAGIT*/
    @Override
    public boolean lisaaKirja(String kirjoittajat, String otsikko, List<String> tagnimet) {
        Vinkki kirja = new Vinkki(otsikko, Formaatit.KIRJA);
        if(kirjoittajat != null){
            if (!kirjoittajat.isEmpty()) {
                kirja.lisaaTekijat(kirjoittajat);
            }
        }
        return lisaaVinkki(kirja);
    }

    @Override
    public boolean lisaaPodcast(String nimi, String otsikko, String kuvaus) {
        Vinkki podcast = new Vinkki(otsikko, Formaatit.PODCAST);
        podcast.lisaaOminaisuus(Attribuutit.KUVAUS, kuvaus);
        podcast.lisaaOminaisuus(Attribuutit.NIMI, nimi);
        return lisaaVinkki(podcast);
    }

    @Override
    public boolean lisaaVideo(String url, String otsikko) {
        Vinkki video = new Vinkki(otsikko, Formaatit.VIDEO);
        video.lisaaOminaisuus(Attribuutit.URL, url);
        return lisaaVinkki(video);
    }

    @Override
    public boolean lisaaBlogpost(String url, String kirjoittajat, String otsikko) {
        Vinkki blogpost = new Vinkki(otsikko, Formaatit.BLOGPOST);
        blogpost.lisaaOminaisuus(Attribuutit.URL, url);
        if (!kirjoittajat.equals("") || kirjoittajat != null) {
            blogpost.lisaaTekijat(kirjoittajat);
        }
        return lisaaVinkki(blogpost);
    }
    /*
    Ylläolevat kutsuvat kaikki tätä
    */ 
    @Override
    public boolean lisaaVinkki(Vinkki vinkki) {
        if (null != vinkki.formaatti()) {
            if(!haeOtsikolla(vinkki.Otsikko()).isEmpty()) return false;
            luoVinkki(vinkki.Otsikko(), vinkki.luettu());
            String vinkkiID = haeOtsikolla(vinkki.Otsikko());
            if(vinkkiID.isEmpty()) return false;
            List<String> tekijaIDt = haeJaPaivitaTekijat(vinkkiID, vinkki.haeTekijat());
            liitaTekijat(vinkkiID, tekijaIDt);
            List<Integer> tagit = haeJaPaivitaTagit(Integer.parseInt(vinkkiID), vinkki.haeTagit());
            liitaTagit(Integer.parseInt(vinkkiID), tagit);
            switch (vinkki.formaatti()) {
                case KIRJA:
                    return lisaaKirja(vinkkiID, vinkki);
                case PODCAST:
                    return lisaaPodcast(vinkkiID, vinkki);
                case VIDEO:
                    return lisaaVideo(vinkkiID, vinkki);
                case BLOGPOST:
                    return lisaaBlogpost(vinkkiID, vinkki);
            }
        }
        return false;
    }
    /* */
    
    /* POISTAMISET*/
    
    @Override
    public boolean poistaKirja(String otsikko) {
        return poistaVinkki(otsikko);
    }

    //EI me haluta kaikkia tekijöitä poistaa
    @Override
    public boolean poistaVinkki(String otsikko) {
        String vinkkiID = haeOtsikolla(otsikko);
        //System.err.println("ID"+vinkkiID);
        if (vinkkiID.isEmpty()) {
            return false;
        }
        //String poistaKirja = "DELETE FROM Kirja WHERE vinkki_id=?";
        String poistaVinkki = "DELETE FROM Vinkki WHERE vinkki_id=?";
        //String poistaVinkkiTekijä = "DELETE FROM VinkkiTekijä WHERE vinkki_id=?";
        try {
            this.avaaYhteys();
            //PreparedStatement komento1 = conn.prepareStatement(poistaKirja);
            PreparedStatement komento2 = conn.prepareStatement(poistaVinkki);
            //PreparedStatement komento3 = conn.prepareStatement(poistaVinkkiTekijä);
            //komento1.setInt(1,Integer.parseInt(vinkkiID));
            komento2.setInt(1, Integer.parseInt(vinkkiID));
            //komento3.setInt(1,Integer.parseInt(vinkkiID));
            //komento1.executeUpdate();
            komento2.executeUpdate();
            komento2.close();
            //komento3.executeUpdate();

        } catch (SQLException e) {
            System.err.println("poistaVinkki: "+e.getMessage());
            return false;
        }

        return true;
    }
    
    /* */
    
    /* LIITÄ TEKIJÄT*/
    private boolean tekijaLiitetty(String vinkkiID, String tekijaID) {
        String haeTekija = "SELECT * FROM VinkkiTekija WHERE vinkki=? AND tekija=?";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeTekija);
            komento.setInt(1, Integer.parseInt(vinkkiID));
            komento.setInt(2, Integer.parseInt(tekijaID));
            ResultSet rs = komento.executeQuery();
            boolean loyty = false;
            while (rs.next()) {
                loyty = true;
            }
            return loyty;
        } catch (SQLException e) {
            System.err.println("tekijaLiitetty: "+e.getMessage());
            return false;
        }
    }

    private void liitaTekija(String vinkkiID, String tekijaID) {
        String lisaaVinkkiin = "INSERT INTO VinkkiTekija (vinkki,tekija) VALUES (?,?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setInt(1, Integer.parseInt(vinkkiID));
            komento.setInt(2, Integer.parseInt(tekijaID));
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("liitaTekija: "+e.getMessage());
        }
    }

    private void liitaTekijat(String vinkkiID, List<String> tekijaIDt) {
        for (String tekijaID : tekijaIDt) {
            if (!tekijaLiitetty(vinkkiID, tekijaID)) {
                liitaTekija(vinkkiID, tekijaID);
            }
        }
    }
    /* */
    
    private String haeTekija(String nimi) {
        String haeTekija = "SELECT tekija_id FROM Tekija WHERE tekija_nimi=?";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeTekija);
            komento.setString(1, nimi);
            ResultSet rs = komento.executeQuery();
            String kirjaID = "";
            while (rs.next()) {
                kirjaID = Integer.toString(rs.getInt("tekija_id"));
            }
            return kirjaID;
        } catch (SQLException e) {
            System.err.println("haeTekija: "+e.getMessage());
        }
        return "";
    }

    private void luoTekija(String nimi) {
        String lisaaVinkkiin = "INSERT INTO Tekija (tekija_nimi) VALUES (?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1, nimi);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("luoTekija: "+e.getMessage());
        }
    }

    public String haeOtsikolla(String otsikko) {
        String haeID = "SELECT vinkki_id FROM Vinkki WHERE otsikko=?";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeID);
            komento.setString(1, otsikko);
            ResultSet rs = komento.executeQuery();
            String kirjaID = "";
            while (rs.next()) {
                kirjaID = rs.getString("vinkki_id");
            }
            return kirjaID;
        } catch (SQLException e) {
            System.err.println("haeOtsikolla: "+e.getMessage());
        }
        return "";
    }

    public void luoVinkki(String otsikko, boolean luettu) {
        String lisaaVinkkiin = "INSERT INTO Vinkki (otsikko,luettu) VALUES (?, ?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1, otsikko);
            komento.setBoolean(2, luettu);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("luoVinkki: "+e.getMessage());
        }
    }

    private List<String> haeJaPaivitaTekijat(String vinkkiID, List<String> tekijat) {
        List<String> tekijaIDt = new ArrayList<>();
        for (String tekija : tekijat) {
            String tekijaID = haeTekija(tekija);
            if (tekijaID.isEmpty()) {
                luoTekija(tekija);
                tekijaID = haeTekija(tekija);
            }
            tekijaIDt.add(tekijaID);
        }
        return tekijaIDt;
    }
    
    private List<Integer> haeJaPaivitaTagit(int vinkkiID, List<String> tagit) {
        List<Integer> tagit = new ArrayList<>();
        for (int tag_id : tagit) {
            int tag_id = haeTag(tag);
            if (tag_id.isEmpty()) {
                luoTag(tekija);
                tekijaID = haeTekija(tekija);
            }
            tagit.add(tag_id);
        }
        return tagit;
    }
     
    /* KANTAAN LISÄYKSET*/ 

    public boolean lisaaKirja(String vinkkiID, Vinkki kirja) {
        String lisaaVinkkiin = "INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES (?,?,?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1, kirja.haeOminaisuus(Attribuutit.ISBN));
            komento.setString(2, kirja.haeOminaisuus(Attribuutit.KUVAUS)); 
            komento.setInt(3, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("lisaaKirja: "+ e.getMessage());
            return false;
        }

    }
    private boolean lisaaPodcast(String vinkkiID, Vinkki vinkki) {

        String query = "INSERT INTO Podcast (nimi, kuvaus, vinkki) VALUES (?,?,?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(query);
            komento.setString(1, vinkki.haeOminaisuus(Attribuutit.NIMI));
            komento.setString(2, vinkki.haeOminaisuus(Attribuutit.KUVAUS));
            komento.setInt(3, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("lisaaPodcast: "+ e.getMessage());
        }
        return false;
    }

    private boolean lisaaVideo(String vinkkiID, Vinkki vinkki) {
        String query = " INSERT INTO Video (url, vinkki) VALUES (?, ?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(query);
            komento.setString(1, vinkki.haeOminaisuus(Attribuutit.URL));
            komento.setInt(2, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("lisaaVideo: "+ e.getMessage());

        }
        return false;
    }
    
    private boolean lisaaBlogpost(String vinkkiID, Vinkki vinkki) {
        String query = " INSERT INTO Blogpost (url, kuvaus, vinkki) VALUES (?, ?, ?)";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(query);
            komento.setString(1, vinkki.haeOminaisuus(Attribuutit.URL));
            komento.setString(2, vinkki.haeOminaisuus(Attribuutit.KUVAUS));
            komento.setInt(3, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("lisaaBlogpost: "+ e.getMessage());

        }
        return false;
    }
    
    /* KANTAAN LISÄYKSET LOPPUU*/


    /* LUKUSTATUKSEN MERKITSEMINEN*/
    
    @Override
    public boolean merkitseLuetuksi(String otsikko) {
        String vinkkiID = haeOtsikolla(otsikko);
        if (vinkkiID.isEmpty()) {
            return false;
        }
        return merkitseLukuStatus(vinkkiID, true);
    }

    @Override
    public boolean merkitseLukemattomaksi(String otsikko) {
        String vinkkiID = haeOtsikolla(otsikko);
        if (vinkkiID.isEmpty()) {
            return false;
        }
        return merkitseLukuStatus(vinkkiID, false);
    }
    
    private boolean merkitseLukuStatus(String vinkkiID, boolean luettu) {
        String lisaaVinkkiin = "UPDATE vinkki SET luettu=? WHERE vinkki_id=?";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setBoolean(1, luettu);
            komento.setInt(2, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("merkitseLukuStatus"+e.getMessage());
        }
        return false;
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

    private List<String> muunnaVinkkiLista(List<Vinkki> vinkkiLista) {
        List<String> lista = new ArrayList<>();
        for (Vinkki vinkki : vinkkiLista) {
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
        List<Vinkki> lista = haeKaikkiKirjatBase(status, null);
        lista = haeKaikkiVideotBase(status, lista);
        lista = haeKaikkiPodcastBase(status, lista);
        lista = haeKaikkiBlogpostBase(status, lista);
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
        String haeKirjatString = "SELECT vinkki.otsikko, vinkki.luettu, kirja.isbn, kirja.kuvaus, group_concat(tekija_nimi, '----') as tekijat \n"
                + "FROM Vinkki \n"
                + "INNER JOIN kirja ON vinkki_id=kirja.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";
        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeKirjatString);
            List<Vinkki> lista = null;
            if (list == null) {
                lista = new ArrayList<>();
            } else {
                lista = list;
            }

            ResultSet rs = komento.executeQuery();
            while (rs.next()) {
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if (luettu == status.getValue() || status == LukuStatus.KAIKKI) {
                    Vinkki kirja = new Vinkki(rs.getString("otsikko"), Formaatit.KIRJA);
                    if (luettu == 0) {
                        kirja.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.FALSE);
                    } else {
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
            System.err.println("haeKaikkiKirjatBase:"+e.getMessage());
        }
        return null;
    }

    //////////////IMPLEMENTOI NÄMÄ
    private List<Vinkki> haeKaikkiPodcastBase(LukuStatus status, List<Vinkki> list) {

        String haePodcastitString = "SELECT vinkki.otsikko, vinkki.luettu, podcast.nimi, podcast.kuvaus, group_concat(tekija_nimi, '----') as tekijat \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Podcast ON vinkki_id=podcast.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";

        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haePodcastitString);
            List<Vinkki> lista = null;
            if (list == null) {
                lista = new ArrayList<>();
            } else {
                lista = list;
            }

            ResultSet rs = komento.executeQuery();
            while (rs.next()) {
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if (luettu == status.getValue() || status == LukuStatus.KAIKKI) {
                    Vinkki podcast = new Vinkki(rs.getString("otsikko"), Formaatit.PODCAST);
                    if (luettu == 0) {
                        podcast.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.FALSE);
                    } else {
                        podcast.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.TRUE);
                    }

                    podcast.lisaaTekijat(rs.getString("tekijat"));
                    podcast.lisaaOminaisuus(Attribuutit.KUVAUS, rs.getString("kuvaus"));
                    podcast.lisaaOminaisuus(Attribuutit.NIMI, rs.getString("nimi"));
                    lista.add(podcast);
                }
            }

            komento.close();
            return lista;
        } catch (SQLException e) {
            System.err.println("haeKaikkiPodcastBase:"+e.getMessage());
        }
        return null;
    }

    private List<Vinkki> haeKaikkiVideotBase(LukuStatus status, List<Vinkki> list) {

        String haeVideoString = "SELECT vinkki.otsikko, vinkki.luettu, video.url, group_concat(tekija_nimi, '----') as tekijat \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Video ON vinkki_id=video.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";

        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeVideoString);
            List<Vinkki> lista = null;
            if (list == null) {
                lista = new ArrayList<>();
            } else {
                lista = list;
            }

            ResultSet rs = komento.executeQuery();
            while (rs.next()) {
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if (luettu == status.getValue() || status == LukuStatus.KAIKKI) {
                    Vinkki video = new Vinkki(rs.getString("otsikko"), Formaatit.VIDEO);
                    if (luettu == 0) {
                        video.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.FALSE);
                    } else {
                        video.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.TRUE);
                    }

                    video.lisaaTekijat(rs.getString("tekijat"));
                    video.lisaaOminaisuus(Attribuutit.URL, rs.getString("url"));
                    // video.lisaaOminaisuus(Attribuutit.NIMI, rs.getString("nimi"));
                    lista.add(video);
                }
            }

            komento.close();
            return lista;
        } catch (SQLException e) {
            System.err.println("haeKaikkiVideotBase:"+e.getMessage());
        }
        return null;

    }
    
    private List<Vinkki> haeKaikkiBlogpostBase(LukuStatus status, List<Vinkki> list) {

        String haeBlogpostString = "SELECT vinkki.otsikko, vinkki.luettu, blogpost.url, blogpost.kuvaus, group_concat(tekija_nimi, '----') as tekijat \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Blogpost ON vinkki_id=blogpost.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";

        try {
            this.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(haeBlogpostString);
            List<Vinkki> lista = null;
            if (list == null) {
                lista = new ArrayList<>();
            } else {
                lista = list;
            }

            ResultSet rs = komento.executeQuery();
            while (rs.next()) {
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if (luettu == status.getValue() || status == LukuStatus.KAIKKI) {
                    Vinkki blogpost = new Vinkki(rs.getString("otsikko"), Formaatit.BLOGPOST);
                    if (luettu == 0) {
                        blogpost.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.FALSE);
                    } else {
                        blogpost.lisaaOminaisuus(Attribuutit.LUETTU, Boolean.TRUE);
                    }

                    blogpost.lisaaTekijat(rs.getString("tekijat"));
                    blogpost.lisaaOminaisuus(Attribuutit.KUVAUS, rs.getString("kuvaus"));
                    blogpost.lisaaOminaisuus(Attribuutit.URL, rs.getString("url"));
                    lista.add(blogpost);
                }
            }

            komento.close();
            return lista;
        } catch (SQLException e) {
            System.err.println("haeKaikkiBlogpostBase:"+e.getMessage());
        }
        return null;

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
    public List<Vinkki> haeKaikkiBlogpost(LukuStatus status) {
        return haeKaikkiBlogpostBase(status, null);
    }

    @Override
    public List<String> haeKaikkiBlogpostString(LukuStatus status) {
        List<Vinkki> vinkkiLista = haeKaikkiBlogpostBase(status, null);
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

    public Connection getConn() {
        return conn;
    }
    
}
