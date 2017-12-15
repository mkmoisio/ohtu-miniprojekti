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

    public Operaatio(TulostusRajapinta tulostus) {
        this.tulostus = tulostus;
    }

    public Operaatio(LukijaRajapinta lukija) {
        this.lukija = lukija;
    }

    public Operaatio() {

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
