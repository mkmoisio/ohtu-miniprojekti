package Vinkkitietokanta.DAO;

import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import java.util.List;

/**
 *
 * @author rokka
 */
public interface DAORajapinta {
    public boolean lisaaVinkki(String vinkkiID, Vinkki vinkki);
    public List<Vinkki> haeListana(LukuStatus status, List<Vinkki> list);
    public Vinkki haeVinkki(String vinkkiId);
}
