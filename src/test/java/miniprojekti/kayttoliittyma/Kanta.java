package miniprojekti.kayttoliittyma;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mikkomo
 */
public class Kanta implements VinkkitietokantaRajapinta {
    
    Map<String, String> vinkit ;
    
    public Kanta() {
        this.vinkit = new HashMap();
    }

    @Override
    public boolean poistaKirja(String otsikko) {
       String poistettu = this.vinkit.remove(otsikko);
       return (poistettu != null);
    }

    @Override
    public boolean lisaaKirja(String kirjoittaja, String otsikko) {
        this.vinkit.put(otsikko, kirjoittaja);
        return true;
    }

    @Override
    public List<String> haeKaikkiString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vinkki> haeKaikki() {
        List<Vinkki> list = new ArrayList();
        for (Map.Entry<String, String> entry : vinkit.entrySet()) {
            list.add(new Vinkki(entry.getValue(), entry.getKey()));
        }
        return list;
    }

    
    
}
