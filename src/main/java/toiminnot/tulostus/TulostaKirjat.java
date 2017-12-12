/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.tulostus;

import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;
import toiminnot.Listantulostusoperaatio;

/**
 *
 * @author mikkomo
 */
public class TulostaKirjat extends Listantulostusoperaatio {
    
    public TulostaKirjat(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }
    
    @Override
    public void suorita() {
        super.tulostaLista(this.haeKaikkiKirjavinkit());
    }
    
    private List<Vinkki> haeKaikkiKirjavinkit() {
        return super.getTk().haeKaikki(Formaatit.KIRJA, LukuStatus.KAIKKI);
    }
    
}
