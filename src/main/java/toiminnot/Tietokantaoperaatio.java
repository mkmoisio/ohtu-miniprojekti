/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
