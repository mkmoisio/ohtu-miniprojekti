/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuviritykset;

/**
 *
 * @author mikkomo
 */
public class BlogpostValidator extends Validator{
    
    private String url;
    private  String kirjoittaja;
    private String otsikko;

    public BlogpostValidator(String url, String kirjoittaja, String otsikko) {
        this.url = url;
        this.kirjoittaja = kirjoittaja;
        this.otsikko = otsikko;
    }
    
    

    @Override
    public boolean validoi() {
        
         if (kirjoittaja.isEmpty()) {
            super.lisaaVirhe("Blogpostvinkkiä ei lisätty, koska annettu kirjoittajan nimi oli tyhjä.");

        }
        if (otsikko.isEmpty()) {
            super.lisaaVirhe("Blogpostvinkkiä ei lisätty, koska annettu otsikko oli tyhjä.");
        }

        if (otsikko.length() > Validator.getBLOGPOST_OTSIKKO_MAX_PITUUS()) {
            super.lisaaVirhe("Blogpostvinkkiä ei lisätty, koska annettu otsikko oli pidempi kuin " + Validator.getBLOGPOST_OTSIKKO_MAX_PITUUS() + " merkkiä.");
        }

        if (url.length() > Validator.getBLOGPOST_URL_MAX_PITUUS()) {
            super.lisaaVirhe("Blogpostvinkkiä ei lisätty, koska annettu kirjoittaja oli pidempi kuin " + Validator.getBLOGPOST_URL_MAX_PITUUS() + " merkkiä.");

        }
        return super.getVirheet().isEmpty();
    }
    
}