/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import Vinkkitietokanta.VinkkitietokantaRajapinta;
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
        super.getTulostus().println("Anna otsikko:");
        String otsikko = super.getLukija().nextLine();
        if (super.getTk().poistaVinkki(otsikko)) {
            super.getTulostus().println("Vinkki poistettu");
        } else {
            super.getTulostus().println("Vinkkiä ei poistettu");
        }
    }

}
