/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import io.LukijaRajapinta;
import io.TulostusRajapinta;

/**
 *
 * @author mikkomo
 */
public class KomentojenTulostus extends MuuOperaatio {
    
      private final String KOMENNOT = "Komennot:"
            + "\n\t lisää kirja - kirjavinkin lisääminen"
            + "\n\t lisää podcast - podcast-vinkin lisääminen"
            + "\n\t lisää video - videovinkin lisääminen"
            + "\n\t lisää blogpost - blogpost-vinkin lisääminen"
            + "\n\t tulosta kaikki - tulosta kaikki vinkit"
            + "\n\t lukemattomat - tulosta kaikki lukemattomat vinkit"
            + "\n\t luetut - tulosta kaikki luetut vinkit"
            + "\n\t tulosta kirjat - tulosta kaikki kirjavinkit"
            + "\n\t tulosta podcastit - tulosta kaikki podcastit"
            + "\n\t tulosta videot - tulosta kaikki videot"
            + "\n\t tulosta blogpostit - tulosta kaikki blogpostit"
            + "\n\t hae tagilla - tulosta annetun tagin omaavat vinkit"
            + "\n\t merkitse luetuksi - merkitse luetuksi"
            + "\n\t poista - poista vinkki"
            + "\n\t lopeta - lopeta ohjelma "
            + "\n";

    public KomentojenTulostus(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        super(lukija, tulostus);
    }

    @Override
    public void suorita() {
        super.getTulostus().println(KOMENNOT);
    }
    
}
