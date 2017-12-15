package toiminnot;

import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;

/**
 *
 * @author mikkomo
 */
public abstract class Tietokantaoperaatio extends Operaatio{
    
    
    private VinkkitietokantaRajapinta tk;

    public Tietokantaoperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus);
        this.tk = tk;
    }

    public VinkkitietokantaRajapinta getTk() {
        return tk;
    }

    public void setTk(VinkkitietokantaRajapinta tk) {
        this.tk = tk;
    }
    
}
