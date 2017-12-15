package Vinkkitietokanta;

import Vinkkitietokanta.DAO.BlogpostDAO;
import Vinkkitietokanta.DAO.DAORajapinta;
import Vinkkitietokanta.DAO.KirjatDAO;
import Vinkkitietokanta.DAO.PodcastDAO;
import Vinkkitietokanta.DAO.TagDAO;
import Vinkkitietokanta.DAO.VinkkiDAO;
import Vinkkitietokanta.DAO.TekijatDAO;
import Vinkkitietokanta.DAO.VideotDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */
public class Vinkkitietokanta implements VinkkitietokantaRajapinta {

    TekijatDAO tekijatDAO = null;
    VinkkiDAO vinkkiDAO = null;
    TagDAO tagDAO = null;
    Connection conn = null;
    Map<Formaatit, DAORajapinta> DAOt = new HashMap<>();

    public Vinkkitietokanta(String tkPath) {
        //Liitä tietokanta        
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.resetOpenMode(SQLiteOpenMode.CREATE);
            conn = DriverManager.getConnection(tkPath, config.toProperties());

            DAOt.put(Formaatit.BLOGPOST, new BlogpostDAO(conn));
            DAOt.put(Formaatit.VIDEO, new VideotDAO(conn));
            DAOt.put(Formaatit.PODCAST, new PodcastDAO(conn));
            DAOt.put(Formaatit.KIRJA, new KirjatDAO(conn));

            tekijatDAO = new TekijatDAO(conn);
            vinkkiDAO = new VinkkiDAO(conn);
            tagDAO = new TagDAO(conn);

            
        } catch (SQLException e) {
            System.err.println("Vinkkitietokanta: " + e.getMessage());
        }
    }

    public void sulje() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            System.err.println("sulje: " + se.getMessage());
        }
    }

    public boolean tietokantaliitetty() {
        if (conn == null) {
            return false;
        }
        try {
            return !conn.isClosed();
        } catch (SQLException se) {
            System.err.println("tietokantaliitetty: " + se.getMessage());
        }
        return false;
    }

    ///Logiikkaa, tulee käyttöliittymästä: Lisaa ja poista vinkki
    @Override
    public boolean lisaaVinkki(Vinkki vinkki) {
        if (null != vinkki.formaatti()) {
            if (!vinkkiDAO.haeOtsikolla(vinkki.otsikko()).isEmpty()) {
                return false;
            }
            vinkkiDAO.luoVinkki(vinkki.otsikko(), vinkki.luettu());
            String vinkkiID = vinkkiDAO.haeOtsikolla(vinkki.otsikko());
            if (vinkkiID.isEmpty()) {
                return false;
            }

            List<String> tekijaIDt = tekijatDAO.haeJaPaivitaTekijat(vinkkiID, vinkki.haeTekijat());
            tekijatDAO.liitaTekijat(vinkkiID, tekijaIDt);

            List<Integer> luodutTagID = tagDAO.luoTagit(vinkki.haeTagit());
            tagDAO.liitaVinkkiTag(vinkkiID, luodutTagID);
            return DAOt.get(vinkki.formaatti()).lisaaVinkki(vinkkiID, vinkki);
        }
        return false;
    }

    
    @Override
    public Vinkki haeVinkki(String otsikko) {
        String vinkkiID = vinkkiDAO.haeOtsikolla(otsikko);
        if (vinkkiID.isEmpty()) {
            return null;
        }
        for (DAORajapinta DAO : DAOt.values()) {
            Vinkki vinkki = DAO.haeVinkki(vinkkiID);
            if (vinkki != null) {
                return vinkki;
            }
        }
        return null;
    }

    
    @Override
    public boolean poistaVinkki(String otsikko) {
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
    ///HAE KAIKKI METODIT
    @Override
    public List<Vinkki> haeKaikki(LukuStatus status) {
        List<Vinkki> lista = new ArrayList<>();
        for (DAORajapinta DAO : DAOt.values()) {
            lista = DAO.haeListana(status, lista);
        }
        return lista;
    }

    @Override
    public List<Vinkki> haeTagilla(String tag) {
        List<String> vinkkiIDt = vinkkiDAO.haeVinkkienIDtTagilla(tag);
        
        List<Vinkki> lista = new ArrayList<>();
        for (String s : vinkkiIDt) {
          //  System.out.println(s);
            for (DAORajapinta dao : this.DAOt.values()) {
                Vinkki v = dao.haeVinkki(s);
                if (v != null) {
                    lista.add(v);
                }
            }
        }
        return lista;
    }

    @Override
    public List<Vinkki> haeKaikki(Formaatit formaatti, LukuStatus status) {
        return DAOt.get(formaatti).haeListana(status, new ArrayList<>());
    }

    public Connection getConn() {
        return conn;
    }

    List<Vinkki> haeKaikkiVideot(LukuStatus lukuStatus) {
        return haeKaikki(Formaatit.VIDEO, lukuStatus);
    }

    List<Vinkki> haeKaikkiKirjat(LukuStatus lukuStatus) {
        return haeKaikki(Formaatit.KIRJA, lukuStatus);
    }

    List<Vinkki> haeKaikkiPodcast(LukuStatus lukuStatus) {
        return haeKaikki(Formaatit.PODCAST, lukuStatus);
    }

    List<Vinkki> haeKaikkiBlogpost(LukuStatus lukuStatus) {
        return haeKaikki(Formaatit.BLOGPOST, lukuStatus);
    }

}
