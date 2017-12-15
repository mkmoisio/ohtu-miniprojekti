package toiminnot.muu;

import Vinkkitietokanta.VinkkitietokantaRajapinta;
import apuviritykset.Muotoilut;
import apuviritykset.vakiot.Ohjetulosteet;
import io.LukijaRajapinta;
import io.TulostusRajapinta;
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mikkomo
 */
public class MerkitseLuetuksi extends Tietokantaoperaatio {

    public MerkitseLuetuksi(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public void suorita() {
        super.getTulostus().println(Ohjetulosteet.ANNA_NIMI_JOKA_MERKITAAN_LUETUKSI);
        String otsikko = super.getLukija().nextLine();

        if (!super.getTk().merkitseLuetuksi(otsikko)) {
            super.getTulostus().println(Muotoilut.muotoileVinkkiaEiLoytynytViesti(otsikko));
        } else {
            super.getTulostus().println(Muotoilut.muotoileMerkittyLuetuksiViesti(otsikko));

        }
    }

}
