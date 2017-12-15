package toiminnot;

import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public abstract class Listantulostusoperaatio extends Tietokantaoperaatio {

    public Listantulostusoperaatio(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public abstract void suorita();

    protected void tulostaLista(List<Vinkki> lista) {
        lista.stream().forEach((v) -> {
            super.getTulostus().println(v.toString());
        });
    }
}
