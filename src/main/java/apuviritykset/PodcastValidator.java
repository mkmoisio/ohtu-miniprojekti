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
public class PodcastValidator extends Validator {

    private String nimi;
    private String otsikko;
    private String kuvaus;

    public PodcastValidator(String nimi, String otsikko, String kuvaus) {
        super();
        this.nimi = nimi;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
    }

    @Override
    public boolean validoi() {

        if (nimi.isEmpty()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu nimi oli tyhjä.");
        }
        if (Validator.getPODCAST_NIMI_MAX_PITUUS() < nimi.length()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu nimi oli pidempi kuin " + Validator.getPODCAST_NIMI_MAX_PITUUS() + " merkkiä.");
        }

        if (otsikko.isEmpty()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu otsikko oli tyhjä.");
        }

        if (Validator.getOTSIKKO_MAX_PITUUS() < otsikko.length()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu otsikko oli pidempi kuin " + Validator.getOTSIKKO_MAX_PITUUS() + " merkkiä.");

        }
        
        if (kuvaus.isEmpty()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu kuvaus oli tyhjä.");
        }
        
        if (Validator.getPODCAST_KUVAUS_MAX_PITUUS() < kuvaus.length()) {
            super.lisaaVirhe("Podcastia ei lisätty, koska annettu kuvaus oli pidempi kuin " + Validator.getPODCAST_KUVAUS_MAX_PITUUS() + " merkkiä.");
        }
        
        return super.getVirheet().isEmpty();

    }

}

