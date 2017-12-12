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
import toiminnot.Tietokantaoperaatio;

/**
 *
 * @author mikkomo
 */
public class MuunnaVinkkia extends Tietokantaoperaatio {

    public MuunnaVinkkia(LukijaRajapinta lukija, TulostusRajapinta tulostus, VinkkitietokantaRajapinta tk) {
        super(lukija, tulostus, tk);
    }

    @Override
    public void suorita() {
        super.getTulostus().println("Anna vinkin otsikko, jota muutetaan");
        String otsikko = super.getLukija().nextLine();
        Vinkki vinkki = super.getTk().haeVinkki(otsikko);
        if(vinkki==null) {
            super.getTulostus().println("Vinkkiä otsikolla: "+otsikko+" ei löytynyt");
        }else{
            while (true) {
                super.getTulostus().println("Mitä muutetaan (tyhjä lopettaa): "+vinkki.getOminaisuudet());
                String syote = super.getLukija().nextLine();
                if (syote.isEmpty()) {
                    break;
                }
                String arvo;
                //Muuta tekijoita
                if(syote.toLowerCase().equals("tekija")){
                    super.getTulostus().println("Tekija: "+vinkki.printtaaTekijat());
                    super.getTulostus().println("Anna uusi tekija (tyhjä peruu)");
                    String tekija = super.getLukija().nextLine();
                    if(!tekija.isEmpty()){
                        vinkki.tyhjennaTekijat();
                        while(!tekija.isEmpty()){
                            vinkki.lisaaTekija(tekija);
                            super.getTulostus().println("Tekijä "+tekija+" lisätty (tyhjä lopettaa)");
                            tekija = super.getLukija().nextLine();
                        }
                    }    
                }else{
                    try{
                        Attribuutit attr = Attribuutit.valueOf(syote);
                        arvo = vinkki.haeOminaisuus(attr);
                        if(!arvo.equals(vinkki.virheteksti())){
                            super.getTulostus().println(syote.toUpperCase()+": "+arvo);
                            super.getTulostus().println("Anna uusi "+syote.toLowerCase()+" (tyhja peruu)");
                            String syote2 = super.getLukija().nextLine();
                            if(!syote2.isEmpty()){
                                vinkki.lisaaOminaisuus(attr, syote2);
                            }
                        }
                    }catch(IllegalArgumentException e){
                         System.out.println("Ei tuettu");
                    }  
                }
                
                
                
            }
            super.getTk().poistaVinkki(otsikko);
            super.getTk().lisaaVinkki(vinkki);
        }
    }

}
