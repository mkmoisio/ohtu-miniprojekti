package toiminnot;

import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;
import toiminnot.muu.TaginLisays;

/**
 *
 * @author mikkomo
 */
public abstract class Lisaysoperaatio extends Tietokantaoperaatio {

    private TaginLisays taginLisays;

    public Lisaysoperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk, TaginLisays taginLisays) {
        super(lukija, tulostus, tk);
        this.taginLisays = taginLisays;
    }

    protected Vinkki lisaaTagit(Vinkki vinkki) {
        this.taginLisays.setVinkki(vinkki);
        this.taginLisays.suorita();
        return this.taginLisays.getVinkki();
    }

    protected void tulostaVirhelista(List<String> lista) {
        lista.stream().forEach((s) -> {
            super.getTulostus().println(s);
        });
    }
}
