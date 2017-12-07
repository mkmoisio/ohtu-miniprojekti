/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
