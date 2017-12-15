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
