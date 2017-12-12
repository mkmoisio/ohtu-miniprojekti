/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuviritykset;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public class KirjaValidator extends Validator{

   // private List virheet;
    private String kirjoittaja;
    private String otsikko;

    public KirjaValidator(String kirjoittaja, String otsikko) {
        super();
        this.kirjoittaja = kirjoittaja;
        this.otsikko = otsikko;
        
    }

    @Override
    public boolean validoi() {

        if (kirjoittaja.isEmpty()) {
            super.lisaaVirhe("Kirjavinkkiä ei lisätty, koska annettu kirjoittajan nimi oli tyhjä.");

        }
        if (otsikko.isEmpty()) {
            super.lisaaVirhe("Kirjavinkkiä ei lisätty, koska annettu otsikko oli tyhjä.");
        }

        if (otsikko.length() > Validator.OTSIKKO_MAX_PITUUS) {
            super.lisaaVirhe("Kirjavinkkiä ei lisätty, koska annettu otsikko oli pidempi kuin " + Validator.OTSIKKO_MAX_PITUUS + " merkkiä.");
        }

        if (kirjoittaja.length() > Validator.KIRJA_KIRJOTTAJA_MAX_PITUUS) {
            super.lisaaVirhe("Kirjavinkkiä ei lisätty, koska annettu kirjoittaja oli pidempi kuin " + Validator.KIRJA_KIRJOTTAJA_MAX_PITUUS + " merkkiä.");

        }

        return super.getVirheet().isEmpty();
    }

   
}
