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
public class VideotDAO extends ProtoDAO implements DAORajapinta{
    HashMap<Attribuutit, String> attr = new HashMap<>();
    
    public VideotDAO(Connection conn){
        super(conn);
        attr.put(Attribuutit.URL, "url");
    }
    
    @Override
    public boolean lisaaVinkki(String vinkkiID, Vinkki vinkki) {
        String query = "INSERT INTO Video (url, vinkki) VALUES (?, ?)";
        List<Attribuutit> attr = new ArrayList<>();
        attr.add(Attribuutit.URL);
        return suoritaKomento(vinkkiID, vinkki, attr, query, "lisaaVideo: ");
    }
    
    
    @Override
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, video.url, "
                + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Video ON vinkki_id=video.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";
        return suoritaKomento2(Formaatit.VIDEO, status, list, attr, query, "haeKaikkiVideotBase: ");
    }

    @Override
    public Vinkki haeVinkki(String vinkkiId) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, video.url, "
                + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Video ON vinkki_id=video.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "WHERE vinkki_id = ? \n"
                + "GROUP BY vinkki_id";
        List<Vinkki> list = suoritaKomento3(Formaatit.VIDEO, LukuStatus.KAIKKI, new ArrayList<>(), attr, query, "haeVideoID:llä: ",vinkkiId);
        if(!list.isEmpty()) return list.get(0);
        return null;
    }
}
