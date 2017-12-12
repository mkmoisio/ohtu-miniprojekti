/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot;

import io.LukijaRajapinta;
import io.TulostusRajapinta;

/**
 *
 * @author mikkomo
 */
public abstract class MuuOperaatio extends Operaatio {

    public MuuOperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        super(lukija, tulostus);
    }



 
}
