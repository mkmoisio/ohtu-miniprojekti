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
import apuviritykset.BlogpostValidator;
import apuviritykset.Validator;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.muu.TaginLisays;

/**
 *
 * @author mikkomo
 */
public class BlogpostinLisays extends Lisaysoperaatio {

    public BlogpostinLisays(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk, taginLisays);
    }

    @Override
    public void suorita() {
        super.getTulostus().println("Anna url:");
        String url = super.getLukija().nextLine();
        super.getTulostus().println("Anna kirjoittaja:");
        String kirjoittajat = super.getLukija().nextLine();
        super.getTulostus().println("Anna otsikko:");
        String otsikko = super.getLukija().nextLine();

        Validator validator = new BlogpostValidator(url, kirjoittajat, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.BLOGPOST);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            vinkki.lisaaTekija(kirjoittajat);
            vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println("Blogpost lisätty");
            }
        } else {
            super.tulostaVirhelista(validator.getVirheet());
        }
    }

}
