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
public abstract class Operaatio {
    
    private LukijaRajapinta lukija;
    private TulostusRajapinta tulostus;

    public Operaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        this.lukija = lukija;
        this.tulostus = tulostus;
    }
   

    public void setLukija(LukijaRajapinta lukija) {
        this.lukija = lukija;
    }

    public void setTulostus(TulostusRajapinta tulostus) {
        this.tulostus = tulostus;
    }

    public LukijaRajapinta getLukija() {
        return lukija;
    }

    public TulostusRajapinta getTulostus() {
        return tulostus;
    }
    
   
    public abstract void suorita();
    
}
