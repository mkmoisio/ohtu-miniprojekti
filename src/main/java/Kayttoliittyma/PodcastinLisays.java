/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.PodcastValidator;
import io.LukijaRajapinta;
import io.TulostusRajapinta;

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
        super.getTulostus().println("Anna nimi:");
        String nimi = super.getLukija().nextLine();

        super.getTulostus().println("Anna otsikko:");
        String otsikko = super.getLukija().nextLine();

        super.getTulostus().println("Anna kuvaus:");
        String kuvaus = super.getLukija().nextLine();

        PodcastValidator validator = new PodcastValidator(nimi, otsikko, kuvaus);
        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.PODCAST);
            vinkki.lisaaOminaisuus(Attribuutit.NIMI, nimi);
            vinkki.lisaaOminaisuus(Attribuutit.KUVAUS, kuvaus);
             vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println("Podcast lisätty");
            } else {
                super.getTulostus().println("Podcastia ei lisätty");
            }
        } else {
            super.tulostaVirhelista(validator.getVirheet());
        }    
    }
    
}
