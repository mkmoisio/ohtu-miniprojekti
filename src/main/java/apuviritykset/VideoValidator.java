/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuviritykset;

import apuviritykset.vakiot.Vastaustulosteet;

/**
 *
 * @author mikkomo
 */
public class VideoValidator extends Validator {

    private String otsikko;
    private String url;

    public VideoValidator(String url, String otsikko) {
        super();
        this.url = url;
        this.otsikko = otsikko;

    }

    @Override
    public boolean validoi() {

        if (otsikko.length() > Validator.getVIDEO_OTSIKKO_MAX_PITUUS()) {
            super.lisaaVirhe(Vastaustulosteet.VIDEOVINKKIA_EI_LISATTY + ", koska annettu otsikko oli pidempi kuin " + Validator.getVIDEO_OTSIKKO_MAX_PITUUS() + " merkkiä.");
        }

        if (otsikko.isEmpty()) {
            super.lisaaVirhe(Vastaustulosteet.VIDEOVINKKIA_EI_LISATTY + ", koska annettu otsikko oli tyhjä.");
        }

        if (url.isEmpty()) {
            super.lisaaVirhe(Vastaustulosteet.VIDEOVINKKIA_EI_LISATTY + ", koska annettu url oli tyhjä.");
        }

        if (url.length() > Validator.getVIDEO_URL_MAX_PITUUS()) {
            super.lisaaVirhe(Vastaustulosteet.VIDEOVINKKIA_EI_LISATTY + ", koska annettu url oli pidempi kuin " + Validator.getVIDEO_URL_MAX_PITUUS() + " merkkiä.");
        }

        return super.getVirheet().isEmpty();
    }

}
