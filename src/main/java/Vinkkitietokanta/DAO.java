
package Vinkkitietokanta;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mariailvonen
 */
public interface DAO <T,A> {
    
    void lisaa(T tyyppi) throws SQLException;
    List<T> haeKaikki() throws SQLException;
    T hae(A avain) throws SQLException;
    void poista (A avain) throws SQLException;
   
}
