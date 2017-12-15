package Vinkkitietokanta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rokka
 */

public class TekijatDAO {
    Connection conn = null;
    public TekijatDAO(Connection conn){
        this.conn = conn;
    }
    
    
    //liittää yksittäisen tekijän vinkkiin
    private void liitaTekija(String vinkkiID, String tekijaID) {
        String lisaaVinkkiin = "INSERT INTO VinkkiTekija (vinkki,tekija) VALUES (?,?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setInt(1, Integer.parseInt(vinkkiID));
            komento.setInt(2, Integer.parseInt(tekijaID));
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("liitaTekija: "+e.getMessage());
        }
    }

    
    public List<String> haeJaPaivitaTekijat(String vinkkiID, List<String> tekijat) {
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
    
    
    //Hakee tekijan nimen mukaan
    public String haeTekija(String nimi) {
        String haeTekija = "SELECT tekija_id FROM Tekija WHERE tekija_nimi=?";
        try {
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

    
    //liittää vinkkiID:n tekijöihin listalla
    public void liitaTekijat(String vinkkiID, List<String> tekijaIDt) {
        for (String tekijaID : tekijaIDt) {
            if (!tekijaLiitetty(vinkkiID, tekijaID)) {
                liitaTekija(vinkkiID, tekijaID);
            }
        }
    }
    
    
    
    //Tarkistaa onko tekijaID liitetty vinkkiID:hen
    public boolean tekijaLiitetty(String vinkkiID, String tekijaID) {
        String haeTekija = "SELECT * FROM VinkkiTekija WHERE vinkki=? AND tekija=?";
        try {
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
    
    
    //Luo tekijän nimellä "nimi"
    public void luoTekija(String nimi) {
        String lisaaVinkkiin = "INSERT INTO Tekija (tekija_nimi) VALUES (?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1, nimi);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("luoTekija: "+e.getMessage());
        }
    }

    
}
