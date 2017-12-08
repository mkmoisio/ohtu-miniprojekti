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
public class BlogpostDAO extends ProtoDAO implements DAORajapinta{
    HashMap<Attribuutit, String> attr = new HashMap<>();
    
    public BlogpostDAO(Connection conn){
        super(conn);
        attr.put(Attribuutit.KUVAUS, "kuvaus");
        attr.put(Attribuutit.URL, "url");
    }
    
    @Override
    public boolean lisaaVinkki(String vinkkiID, Vinkki vinkki) {
        
        String query = " INSERT INTO Blogpost (url, kuvaus, vinkki) VALUES (?, ?, ?)";
        List<Attribuutit> attr = new ArrayList<>();
        attr.add(Attribuutit.URL);
        attr.add(Attribuutit.KUVAUS);
        return suoritaKomento(vinkkiID, vinkki, attr, query, "lisaaBlogpost: ");        
    }

    
    
    @Override
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, blogpost.url, blogpost.kuvaus,"
                + " group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
            + "FROM Vinkki \n"
            + "INNER JOIN Blogpost ON vinkki_id=blogpost.vinkki \n"
            + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
            + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
            + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
            + "LEFT OUTER JOIN Tag on tag_id=tag \n"
            + "GROUP BY vinkki_id";     
        return suoritaKomento2(Formaatit.BLOGPOST, status, list, attr, query, "haeKaikkiBlogpostBase: ");
    }

    @Override
    public Vinkki haeVinkki(String vinkkiId) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, blogpost.url, blogpost.kuvaus,"
                + " group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
            + "FROM Vinkki \n"
            + "INNER JOIN Blogpost ON vinkki_id=blogpost.vinkki \n"
            + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
            + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
            + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
            + "LEFT OUTER JOIN Tag on tag_id=tag \n"
            + "WHERE vinkki_id = ? \n"
            + "GROUP BY vinkki_id";
        List<Vinkki> list = suoritaKomento3(Formaatit.BLOGPOST, LukuStatus.KAIKKI, new ArrayList<>(), attr, query, "haeBlogpostD:llä: ",vinkkiId);
        if(!list.isEmpty()) return list.get(0);
        return null;
    }
}
