/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.Vinkki;
import apuviritykset.Validator;
import io.LukijaRajapinta;
import io.TulostusRajapinta;

/**
 *
 * @author mikkomo
 */
public class TaginLisays extends MuuOperaatio {
    
  private  Vinkki vinkki;

    public TaginLisays(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        super(lukija, tulostus);
    }

    public void setVinkki(Vinkki vinkki) {
        this.vinkki = vinkki;
    }

    public Vinkki getVinkki() {
        return vinkki;
    }

       
    @Override
    public void suorita() {
        super.getTulostus().println("Anna tageja, tyhjä syöte palauttaa aloitusnäyttöön");
        System.out.println("");

        while (true) {
            String tagSyote = super.getLukija().nextLine();
            if (tagSyote.isEmpty()) {
                break;
            }

            if (vinkki.onkoTagia(tagSyote)) {
                System.out.println("Samanniminen tag on jo olemassa");
                continue;
            }
            if (Validator.taginPituusOk(tagSyote)) {
                vinkki.lisaaTag(tagSyote);
            }
        }

    }

}
