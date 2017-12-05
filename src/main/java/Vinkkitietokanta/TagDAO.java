
package Vinkkitietokanta;

import domain.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mariailvonen
 */
public class TagDAO implements DAO <Tag, String> {
    
    Vinkkitietokanta tk;

    public TagDAO(Vinkkitietokanta tk) {
        this.tk = tk;
    }

    @Override
    public void lisaa(Tag tag) throws SQLException {
        String lisaaTag = "INSERT INTO Tag (tag_nimi) VALUES (?)";
        try {
            Connection conn= tk.avaaYhteys();
            PreparedStatement komento = conn.prepareStatement(lisaaTag);
            komento.setString(1, tag.getTag_nimi());
            komento.executeUpdate();
            komento.close();
        } catch (SQLException e) {
            System.err.println("lisaaKirja: "+ e.getMessage());
        }
    }

    @Override
    public List haeKaikki() throws SQLException {
        return null;
    }

    @Override
    public Tag hae(String avain) throws SQLException {
        return null;
    }
    
    @Override
    public void poista(String avain) throws SQLException {
        
    }    
    
    
    
}
