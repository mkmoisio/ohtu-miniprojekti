/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.lisays;

import toiminnot.Lisaysoperaatio;
import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.PodcastValidator;
import apuviritykset.Validator;
import apuviritykset.vakiot.Ohjetulosteet;
import apuviritykset.vakiot.Vastaustulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.muu.TaginLisays;

/**
 *
 * @author mikkomo
 */
public class PodcastinLisays extends Lisaysoperaatio{

    public PodcastinLisays(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk, taginLisays);
    }


    @Override
    public void suorita() {
        super.getTulostus().println(Ohjetulosteet.ANNA_NIMI);
        String nimi = super.getLukija().nextLine();

        super.getTulostus().println(Ohjetulosteet.ANNA_OTSIKKO);
        String otsikko = super.getLukija().nextLine();

        super.getTulostus().println(Ohjetulosteet.ANNA_KUVAUS);
        String kuvaus = super.getLukija().nextLine();

        Validator validator = new PodcastValidator(nimi, otsikko, kuvaus);
        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.PODCAST);
            vinkki.lisaaOminaisuus(Attribuutit.NIMI, nimi);
            vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, kuvaus);
             vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println(Vastaustulosteet.PODCASTVINKKI_LISATTY);
            } else {
                super.getTulostus().println(Vastaustulosteet.PODCASTVINKKIA_EI_LISATTY);
            }
        } else {
            super.tulostaVirhelista(validator.getVirheet());
        }    
    }
    
}
