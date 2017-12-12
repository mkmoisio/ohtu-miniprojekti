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
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mikkomo
 */
public abstract class Listantulostusoperaatio extends Tietokantaoperaatio {

    public Listantulostusoperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public abstract void suorita();

    protected void tulostaLista(List<Vinkki> lista) {
        lista.stream().forEach((v) -> {
            super.getTulostus().println(v.toString());
        });
    }
}
