/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import toiminnot.MuuOperaatio;
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mariailvonen
 */
public class AvaaNettilinkki extends Tietokantaoperaatio{

    public AvaaNettilinkki(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus,tk);
    }

    @Override
    public void suorita() {
        try {
            super.getTulostus().println("Anna otsikko: ");
            String osoite = super.getLukija().nextLine();
            Vinkki vinkki = super.getTk().haeVinkki(osoite);
            String url = vinkki.haeOminaisuus(Attribuutit.URL);
            if(!url.equals(vinkki.virheteksti())){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException ex) {
                    ex.getMessage();
                }    
            }else{
                super.getTulostus().println("Vinkki ei sisällä url:ia");
            }
            
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }
    }
    
}
