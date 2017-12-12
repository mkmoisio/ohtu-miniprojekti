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
public class MerkitseLuetuksi extends Tietokantaoperaatio {

    public MerkitseLuetuksi(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public void suorita() {
        super.getTulostus().println("Anna sen vinkin otsikko, joka merkitään luetuksi");
        String otsikko = super.getLukija().nextLine();
        
        if (!super.getTk().merkitseLuetuksi(otsikko)) {
            super.getTulostus().println("Virhe: Vinkkiä " + otsikko + " ei löytynyt");
        } else {
            super.getTulostus().println("Vinkki otsikolla " + otsikko + " merkitty luetuksi");

        }
    }

}
