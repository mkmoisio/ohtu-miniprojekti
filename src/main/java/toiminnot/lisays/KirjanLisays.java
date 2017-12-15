/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.lisays;

import toiminnot.Lisaysoperaatio;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.KirjaValidator;
import apuviritykset.Validator;
import apuviritykset.vakiot.Ohjetulosteet;
import apuviritykset.vakiot.Vastaustulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;
import toiminnot.muu.TaginLisays;

/**
 *
 * @author mikkomo
 */
public class KirjanLisays extends Lisaysoperaatio {


    public KirjanLisays(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk, taginLisays);
    }

    @Override
    public void suorita() {
        super.getTulostus().println(Ohjetulosteet.ANNA_KIRJOITTAJA);
        String kirjoittaja = super.getLukija().nextLine();
        super.getTulostus().println(Ohjetulosteet.ANNA_OTSIKKO);
        String otsikko = super.getLukija().nextLine();
        Validator validator = new KirjaValidator(kirjoittaja, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.KIRJA);
            vinkki.lisaaTekija(kirjoittaja);
            vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println(Vastaustulosteet.KIRJAVINKKI_LISATTY);
            } else {
                super.getTulostus().println(Vastaustulosteet.KIRJAVINKKIA_EI_LISATTY);
            }
        } else {
            super.tulostaVirhelista(validator.getVirheet());
        }
    }

 
}
