/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import toiminnot.MuuOperaatio;

/**
 *
 * @author mariailvonen
 */
public class AvaaNettilinkki extends MuuOperaatio{

    public AvaaNettilinkki(LukijaRajapinta lukija, TulostusRajapinta tulostus) {
        super(lukija, tulostus);
    }

    @Override
    public void suorita() {
        try {
            super.getTulostus().println("Anna url: ");
            String osoite= super.getLukija().nextLine();
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(osoite));
            } catch (IOException ex) {
                ex.getMessage();
            }
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }
    }
    
}
