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
import apuviritykset.VideoValidator;
import apuviritykset.vakiot.Ohjetulosteet;
import apuviritykset.vakiot.Vastaustulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.muu.TaginLisays;

/**
 *
 * @author mikkomo
 */
public class VideonLisays extends Lisaysoperaatio{

    public VideonLisays(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk, taginLisays);
    }

    @Override
    public void suorita() {
        
        
        super.getTulostus().println(Ohjetulosteet.ANNA_URL);
        String url = super.getLukija().nextLine();
        super.getTulostus().println(Ohjetulosteet.ANNA_OTSIKKO);
        String otsikko = super.getLukija().nextLine();

        VideoValidator validator = new VideoValidator(url, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.VIDEO);
            vinkki.lisaaOminaisuus(Attribuutit.URL, url);
            vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println(Vastaustulosteet.VIDEOVINKKI_LISATTY);
            }
        } else {
           super.tulostaVirhelista(validator.getVirheet());
        }
    }
    
}
