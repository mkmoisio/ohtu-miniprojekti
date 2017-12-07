
package Vinkkitietokanta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {
    
    Connection conn = null;
    
    public TagDAO(Connection conn){
        this.conn = conn;
    }
    
    //
    public List<Integer> luoTagit (List<String> tag_nimet) {
        tag_nimet.stream().forEach((tag_nimi) -> {
            String tag = "INSERT INTO Tag (tag_nimi) VALUES (?)";
            try {
                PreparedStatement komento = conn.prepareStatement(tag);
                komento.setString(1, tag_nimi);
                komento.executeUpdate();
                komento.close();
            } catch (SQLException e) {
                System.err.println("luoTagit: "+e.getMessage());
            } 
        });
        return haeTagIDt(tag_nimet);
    }
    
    public List<Integer> haeTagIDt( List<String> tagNimia){
        List<Integer> tagIdList = new ArrayList<>();
        int tagID= 0;
        
        for (String tag_nimi : tagNimia){
            String haeTag = "SELECT tag_id FROM Tag WHERE tag_nimi=?";
            try {
                PreparedStatement komento = conn.prepareStatement(haeTag);
                komento.setString(1, tag_nimi);
                ResultSet rs = komento.executeQuery();
                while (rs.next()) {
                tagID = rs.getInt("tag_id");
            }

        } catch (SQLException e) {
            System.err.println("haeTagID: "+e.getMessage());
        }
        tagIdList.add(tagID);
        }
    return tagIdList; 
    }
    
    public void liitaVinkkiTag(String vinkkiID, List<Integer> tagIDt){
        for (int t : tagIDt){
        String lisattavaTag = "INSERT INTO VinkkiTag (vinkki,tag) VALUES (?,?)";
        try {
            PreparedStatement komento = conn.prepareStatement(lisattavaTag);
            komento.setInt(1, Integer.parseInt(vinkkiID));
            komento.setInt(2, t);
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("liitaTag: "+e.getMessage());
        }
        }
    }
    
    //Tarkistaa onko tagID liitetty vinkkiID:hen
    public boolean tagLiitetty(String vinkkiID, String tekijaID) {
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
    
}