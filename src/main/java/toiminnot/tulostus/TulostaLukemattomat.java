/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.tulostus;

import toiminnot.Listantulostusoperaatio;
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public class TulostaLukemattomat extends Listantulostusoperaatio {

    public TulostaLukemattomat(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public void suorita() {
        super.tulostaLista(this.haeKaikkuLukemattomat());
    }

    private List<Vinkki> haeKaikkuLukemattomat() {
        return super.getTk().haeKaikki(LukuStatus.LUKEMATTOMAT);
    }
}
