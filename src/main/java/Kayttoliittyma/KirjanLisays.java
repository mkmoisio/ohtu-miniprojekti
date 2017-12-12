/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.Formaatit;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.KirjaValidator;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;

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
        super.getTulostus().println("Anna kirjoittaja:");
        String kirjoittaja = super.getLukija().nextLine();
        super.getTulostus().println("Anna otsikko:");
        String otsikko = super.getLukija().nextLine();
        KirjaValidator validator = new KirjaValidator(kirjoittaja, otsikko);

        if (validator.validoi()) {
            Vinkki vinkki = new Vinkki(otsikko, Formaatit.KIRJA);
            vinkki.lisaaTekija(kirjoittaja);
            vinkki = super.lisaaTagit(vinkki);
            if (super.getTk().lisaaVinkki(vinkki)) {
                super.getTulostus().println("Kirjavinkki lisätty");
            } else {
                super.getTulostus().println("Kirjavinkkiä ei lisätty");
            }
        } else {
            super.tulostaVirhelista(validator.getVirheet());
        }
    }

 
}
