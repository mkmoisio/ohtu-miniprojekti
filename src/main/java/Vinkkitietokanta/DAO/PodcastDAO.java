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
public class PodcastDAO extends ProtoDAO implements DAORajapinta{
    HashMap<Attribuutit, String> attr = new HashMap<>();

    public PodcastDAO(Connection conn){
        super(conn);
        attr.put(Attribuutit.KUVAUS, "kuvaus");
        attr.put(Attribuutit.NIMI, "nimi");
    }
    
    @Override
    public boolean lisaaVinkki(String vinkkiID, Vinkki vinkki) {
        String query = "INSERT INTO Podcast (nimi, kuvaus, vinkki) VALUES (?,?,?)";
        List<Attribuutit> attr = new ArrayList<>();
        attr.add(Attribuutit.NIMI);
        attr.add(Attribuutit.KUVAUS);
        return suoritaKomento(vinkkiID, vinkki, attr, query, "lisaaPodcast: ");
    }
    
    @Override
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, podcast.nimi, podcast.kuvaus, "
                + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Podcast ON vinkki_id=podcast.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "GROUP BY vinkki_id";
        HashMap<Attribuutit, String> attr = new HashMap<>();
        attr.put(Attribuutit.KUVAUS, "kuvaus");
        attr.put(Attribuutit.NIMI, "nimi");
        return suoritaKomento2(Formaatit.PODCAST, status, list, attr, query, "haeKaikkiPodcastBase: ");
    }

    @Override
    public Vinkki haeVinkki(String vinkkiId) {
        String query = "SELECT vinkki.otsikko, vinkki.luettu, podcast.nimi, podcast.kuvaus, "
                + "group_concat(tekija_nimi, '----') as tekijat \n, group_concat(tag_nimi, '----') as tagit \n"
                + "FROM Vinkki \n"
                + "INNER JOIN Podcast ON vinkki_id=podcast.vinkki \n"
                + "LEFT OUTER JOIN VinkkiTekija on vinkki_id=vinkkitekija.vinkki \n"
                + "LEFT OUTER JOIN Tekija on tekija_id=tekija \n"
                + "LEFT OUTER JOIN VinkkiTag on vinkki_id=vinkkitag.vinkki \n"
                + "LEFT OUTER JOIN Tag on tag_id=tag \n"
                + "WHERE vinkki_id = ? \n"
                + "GROUP BY vinkki_id";
        List<Vinkki> list = suoritaKomento3(Formaatit.PODCAST, LukuStatus.KAIKKI, new ArrayList<>(), attr, query, "haePodcastID:llä: ",vinkkiId);
        if(!list.isEmpty()) return list.get(0);
        return null;
    }
}
