/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta.DAO;

import Vinkkitietokanta.Vinkki;
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
public class VinkkiDAO {

    Connection conn = null;

    public VinkkiDAO(Connection conn) {
        this.conn = conn;
    }

    public void luoVinkki(String otsikko, boolean luettu) {
        String lisaaVinkkiin = "INSERT INTO Vinkki (otsikko,luettu) VALUES (?, ?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setString(1, otsikko);
            komento.setBoolean(2, luettu);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("luoVinkki: " + e.getMessage());
        }
    }

    public String haeOtsikolla(String otsikko) {
        String haeID = "SELECT vinkki_id FROM Vinkki WHERE otsikko=?";
        try {
            PreparedStatement komento = conn.prepareStatement(haeID);
            komento.setString(1, otsikko);
            ResultSet rs = komento.executeQuery();
            String kirjaID = "";
            while (rs.next()) {
                kirjaID = rs.getString("vinkki_id");
            }
            return kirjaID;
        } catch (SQLException e) {
            System.err.println("haeOtsikolla: " + e.getMessage());
        }
        return "";
    }

    public boolean merkitseLukuStatus(String vinkkiID, boolean luettu) {
        String lisaaVinkkiin = "UPDATE vinkki SET luettu=? WHERE vinkki_id=?";
        try {
            PreparedStatement komento = conn.prepareStatement(lisaaVinkkiin);
            komento.setBoolean(1, luettu);
            komento.setInt(2, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println("merkitseLukuStatus" + e.getMessage());
        }
        return false;
    }

    //EI me haluta kaikkia tekijöitä poistaa
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
            System.err.println("poistaVinkki: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<String> haeVinkkienIDtTagilla(String tag) {
        String query = "SELECT * FROM Vinkki INNER JOIN (SELECT vinkki FROM VinkkiTag WHERE VinkkiTag.tag = (SELECT tag_id FROM Tag WHERE Tag_nimi=?)) AS tableB ON Vinkki.vinkki_id = tableB.vinkki";
        List<String> list = new ArrayList();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tag);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vinkki_id = rs.getString("vinkki_id");
                list.add(vinkki_id);
            }
        } catch (Exception e) {
            System.err.println("haeVinkkienIDtTagilla: " + e.getMessage());
        }
        return list;
    }

}
