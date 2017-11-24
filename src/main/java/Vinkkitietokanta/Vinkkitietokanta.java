/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
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

SELECT vinkki.*, video.*
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

SELECT vinkki.*, video.*, GROUP_CONCAT(tekija.tekija_nimi)
tekija
FROM Vinkki
INNER JOIN Video ON vinkki_id=video.vinkki
INNER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki
INNER JOIN Tekija on tekija_id=tekija;

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
    Statement stmt = null;
     
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
            stmt = conn.createStatement();            
            //System.out.println("statement luotu");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    //Sulje yhteydet tietokantaan
    public void sulje(){
        try{
        if(stmt!=null)
            conn.close();
        }catch(SQLException se){
        }
        try{
           if(conn!=null)
              conn.close();
        }catch(SQLException se){
           se.printStackTrace();
        }
    }
    
    //Tallentuuko suoraan vai pitääkö erikseen kutsua jotain tallenna tietokanta?
    private void tallennaTietokanta(){    
    }
    
    public boolean tietokantaliitetty(){
        if(conn==null) return false;
        return true;
    }

    @Override
    public boolean poistaKirja(String otsikko) {
        //Toivotaan, että syöte on kunnollista
        //DELETE FROM vinkki where otsikko = 'surkea päivä'   
        try{
            String sql = "DELETE FROM vinkki where otsikko = '"+otsikko+"'";
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }
        //Kirjan poistaminen antaa nyt aina true:n tarvitaan joku
        //tapa tarkistaa, että onnistuuko poisto todella
        return true;
    }

    @Override
    public boolean lisaaKirja(String kirjoittaja, String otsikko) {
        //INSERT INTO vinkki VALUES ('hermanni', 'surkea päivä', 'kirja')
        //Toivotaan, että syöte on kunnollista
        try{
            String sql = "INSERT INTO vinkki VALUES ('"+kirjoittaja+"','"+otsikko+"', 'kirja')";
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            System.out.println(se.toString());
        }
        //Kirjan lisääminen antaa nyt aina true:n tarvitaan joku
        //tapa tarkistaa, että onnistuuko poisto todella
        return true;
    }

    @Override
    public List<String> haeKaikkiString() {
        List<String> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Vinkki";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String kirjoittaja  = rs.getString("kirjoittaja");
                String otsikko = rs.getString("otsikko");
                Vinkki vinkki = new Vinkki(kirjoittaja,otsikko);
                lista.add(vinkki.toString());
            }
            rs.close();
        }catch(SQLException se){
            System.out.println(se.toString());
        }
        
        return lista;
        //"SELECT * FROM vinkki"
    }

    @Override
    public List<Vinkki> haeKaikki() {
        //Pasta koodia siivoa joskus 
        List<Vinkki> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM vinkki";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String kirjoittaja  = rs.getString("kirjoittaja");
                String otsikko = rs.getString("otsikko");
                Vinkki vinkki = new Vinkki(kirjoittaja,otsikko);
                lista.add(vinkki);
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return lista;
    }

}
