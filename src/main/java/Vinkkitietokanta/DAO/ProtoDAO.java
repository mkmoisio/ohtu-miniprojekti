/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta.DAO;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.Vinkki;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rokka
 */
abstract public class ProtoDAO {
    protected Connection conn = null;
    public ProtoDAO(Connection conn){
        this.conn = conn;
    }
    
    protected boolean suoritaKomento(String vinkkiID, Vinkki vinkki, List<Attribuutit> attribuutit, String sql, String func){
        String query = sql;
        try {
            PreparedStatement komento = conn.prepareStatement(query);
            int indx = 0;
            for(Attribuutit attr : attribuutit){
                komento.setString(++indx, vinkki.haeOminaisuus(attr));
            }
            komento.setInt(++indx, Integer.parseInt(vinkkiID));
            komento.executeUpdate();
            komento.close();
            return true;
        } catch (SQLException e) {
            System.err.println(func+e.getMessage());
        }
        return false;
    }
    
    protected List<Vinkki> suoritaKomento2(Formaatit formaatti,LukuStatus status, List<Vinkki> list, HashMap<Attribuutit,String> attribuutit, String sql, String func){
        return suoritaKomento3(formaatti,status, 
            list, attribuutit, sql, func, "");
    }
    
    protected List<Vinkki> suoritaKomento3(Formaatit formaatti,LukuStatus status, 
            List<Vinkki> list, HashMap<Attribuutit,String> attribuutit, 
            String sql, String func, String vinkkiID){
        String query = sql;
        try {
            PreparedStatement komento = conn.prepareStatement(query);
            if(!vinkkiID.isEmpty()) komento.setInt(1, Integer.parseInt(vinkkiID));
            ResultSet rs = komento.executeQuery();
            while (rs.next()) {
                int luettu = Integer.parseInt(rs.getString("luettu"));
                if (luettu == status.getValue() || status == LukuStatus.KAIKKI) {
                    Vinkki vinkki = new Vinkki(rs.getString("otsikko"), formaatti);
                    if (luettu == 0) {
                        vinkki.merkitseLukemattomaksi();
                    } else {
                        vinkki.merkitseLuetuksi();
                    }

                    vinkki.lisaaTekijat(rs.getString("tekijat"));
                    vinkki.lisaaTagit(rs.getString("tagit"));
                    for (Map.Entry<Attribuutit, String> attr : attribuutit.entrySet()){
                        vinkki.lisaaOminaisuus((Attribuutit)attr.getKey(), rs.getString(attr.getValue()));
                    }
                    list.add(vinkki);
                }
            }

            komento.close();
            return list;
        } catch (SQLException e) {
            System.err.println(func+e.getMessage());
        }
        return null;
    }    
    
}
