/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.vakiot.Ohjetulosteet;
import apuviritykset.vakiot.Vastaustulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mikkomo
 */
public class Poisto extends Tietokantaoperaatio {

    public Poisto(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public void suorita() {
        super.getTulostus().println(Ohjetulosteet.ANNA_OTSIKKO);
        String otsikko = super.getLukija().nextLine();
        if (super.getTk().poistaVinkki(otsikko)) {
            super.getTulostus().println(Vastaustulosteet.VINKKI_POISTETTU);
        } else {
            super.getTulostus().println(Vastaustulosteet.VINKKIA_EI_POISTETTU);
        }
    }

}
