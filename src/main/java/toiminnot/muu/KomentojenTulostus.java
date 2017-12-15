/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import apuviritykset.vakiot.Vastaustulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.MuuOperaatio;

/**
 *
 * @author mikkomo
 */
public class KomentojenTulostus extends MuuOperaatio {


    public KomentojenTulostus(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        super(lukija, tulostus);
    }

    @Override
    public void suorita() {
        super.getTulostus().println(Vastaustulosteet.KOMENNOT_TULOSTE);
    }

}
