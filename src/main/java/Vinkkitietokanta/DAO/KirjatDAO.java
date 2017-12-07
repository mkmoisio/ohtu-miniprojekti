/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta.DAO;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rokka
 */
public class KirjatDAO extends ProtoDAO implements DAORajapinta{
    public KirjatDAO(Connection conn){
        super(conn);
    }
    

    
    @Override
    public boolean lisaaVinkki(String vinkkiID, Vinkki vinkki) {
        //INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES ();
        String query = "INSERT INTO Kirja (isbn,kuvaus,vinkki) VALUES (?,?,?)";
        List<Attribuutit> attr = new ArrayList<>();
        attr.add(Attribuutit.ISBN);
        attr.add(Attribuutit.KUVAUS);
        return suoritaKomento(vinkkiID, vinkki, attr, query, "lisaaKirja: ");
    }
    
    @Override
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list) {
         String query = "SELECT vinkki.otsikko, vinkki.luettu, kirja.isbn, kirja.kuvaus, group_concat(tekija_nimi, '----') as tekijat \n"
                + "FROM Vinkki \n"
                + "INNER JOIN kirja ON vinkki_id=kirja.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "GROUP BY vinkki_id";
        HashMap<Attribuutit, String> attr = new HashMap<>();
        attr.put(Attribuutit.KUVAUS, "kuvaus");
        attr.put(Attribuutit.ISBN, "isbn");
        
        return suoritaKomento2(Formaatit.KIRJA, status, list, attr, query, "haeKaikkiKirjatBase: ");
    }

}
