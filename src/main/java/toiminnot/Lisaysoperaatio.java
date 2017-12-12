/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot;

import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;
import toiminnot.muu.TaginLisays;
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mikkomo
 */
public abstract class Lisaysoperaatio extends Tietokantaoperaatio {

    private TaginLisays taginLisays;

    public Lisaysoperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk);
        this.taginLisays = taginLisays;
    }

    protected Vinkki lisaaTagit(Vinkki vinkki) {
        this.taginLisays.setVinkki(vinkki);
        this.taginLisays.suorita();
        return this.taginLisays.getVinkki();
    }

    protected void tulostaVirhelista(List<String> lista) {
        lista.stream().forEach((s) -> {
            super.getTulostus().println(s);
        });
    }
}
