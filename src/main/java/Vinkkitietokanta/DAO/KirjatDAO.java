package Vinkkitietokanta.DAO;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rokka
 */
public class KirjatDAO extends ProtoDAO implements DAORajapinta{
    HashMap<Attribuutit, String> attr = new HashMap<>();
    
    public KirjatDAO(Connection conn){
        super(conn);
        attr.put(Attribuutit.KUVAUS, "kuvaus");
        attr.put(Attribuutit.ISBN, "isbn");   
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
    public Vinkki haeVinkki(String vinkkiID) {
         String query = "SELECT vinkki.otsikko, vinkki.luettu, kirja.isbn, kirja.kuvaus,"
                 + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN kirja ON vinkki_id=kirja.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "WHERE vinkki_id = ? \n"
                + "GROUP BY vinkki_id";
        List<Vinkki> list = suoritaKomento3(Formaatit.KIRJA, LukuStatus.KAIKKI, new ArrayList<>(), attr, query, "haeKirjaID:llä: ",vinkkiID);
        if(!list.isEmpty()) return list.get(0);
        return null;
    }

    
    @Override
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list) {
         String query = "SELECT vinkki.otsikko, vinkki.luettu, kirja.isbn, kirja.kuvaus,"
                 + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN kirja ON vinkki_id=kirja.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";
        return suoritaKomento2(Formaatit.KIRJA, status, list, attr, query, "haeKaikkiKirjatBase: ");
    }

}
