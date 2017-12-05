/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import apuviritykset.Muotoilut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class Vinkki {

    private Formaatit formaatti;
    private String otsikko;
    private boolean luettu;
    private List<String> tekijat;
    private HashMap<Attribuutit, String> attribuutit;
    protected String virheTeksti = "Ominaisuutta ei liitetty";
    protected String virheTeksti2 = "hups";
    
    public Vinkki(String otsikko, Formaatit formaatti) {
        this.otsikko = otsikko;
        this.formaatti = formaatti;
        this.luettu = false;
        this.tekijat = new ArrayList<>();
        attribuutit = new HashMap<>();
    }

    //ehkä palauttaa geneerisen Object tyypin? Helpompi käyttöliittymälle
    public String haeOminaisuus(Attribuutit attribuutti) {
        if (null != attribuutti) {
            switch (attribuutti) {
                case OTSIKKO:
                    return otsikko;
                case FORMAATTI:
                    return formaatti.name();
                case LUETTU:
                    return String.valueOf(luettu);
                case TEKIJAT:
                    return printtaaTekijat();
                default:
                    if (attribuutit.containsKey(attribuutti)) {
                        return attribuutit.get(attribuutti);
                    } else {
                        return virheTeksti;
                    }
            }
        }
        return virheTeksti;
    }

    //Myös muokkaa
    public boolean lisaaOminaisuus(Attribuutit attribuutti, Object arvo) {
        if (null != attribuutti) {
            switch (attribuutti) {
                case OTSIKKO:
                    if (!String.class.isInstance(arvo)) {
                        return false;
                    }
                    otsikko = arvo.toString();
                    break;
                case FORMAATTI:
                    if (!Formaatit.class.isInstance(arvo)) {
                        return false;
                    }

                    formaatti = (Formaatit) arvo;
                    break;
                case LUETTU:
                    if (!Boolean.class.isInstance(arvo)) {
                        return false;
                    }
                    luettu = (Boolean) arvo;
                    break;
                case TEKIJAT:
                    if (!String.class.isInstance(arvo)) {
                        return false;
                    }
                    lisaaTekija(arvo.toString().trim());
                    break;
                default:
                    if (!String.class.isInstance(arvo)) {
                        return false;
                    }
                    attribuutit.put(attribuutti, arvo.toString());
                    break;
            }
            return true;
        }
        return false;
    }

    /*
    */
    public boolean lisaaTekijat(String tekijatStr) {
        if (tekijatStr == null || tekijatStr.isEmpty()) return false;
        String erotin = "[----]";
        for (String tekija : tekijatStr.split(erotin)) {
            lisaaTekija(tekija.trim());
        }
        return true;
    }

    public boolean lisaaTekija(String tekija) {
        if (tekija==null || tekijat.contains(tekija) || tekija.isEmpty()) return false;
        tekijat.add(tekija);
        return true;
    }

    public List<String> haeTekijat() {
        return tekijat;
    }

    public String printtaaTekijat() {
        return String.join(",", tekijat);
    }

    public void poistaTekijat() {
        tekijat.clear();
    }

    public Formaatit formaatti() {
        return formaatti;
    }

    public boolean luettu() {
        return luettu;
    }

    public void merkitseLuetuksi() {
        luettu = true;
    }

    public void merkitseLukemattomaksi() {
        luettu = false;
    }

    public String Otsikko() {
        return otsikko;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (null != formaatti) {
            switch (formaatti) {
                case KIRJA:         
                    return Muotoilut.muotoileKirjavinkinTuloste(this);
                    
                case PODCAST:
                    sb.append("Podcastvinkki \n\tOtsikko: ");
                    sb.append(this.Otsikko());
                    if (this.luettu) {
                        sb.append(" (luettu)");
                    } else {
                        sb.append(" (lukematon)");
                    }
                    sb.append("\n\tNimi: ");
                    sb.append(haeOminaisuus(Attribuutit.NIMI));
                    sb.append("\n\tKuvaus: ");
                    sb.append(haeOminaisuus(Attribuutit.KUVAUS));
                    return sb.toString();
                    
                case VIDEO:
                    sb.append("Videovinkki \n\tOtsikko: ");
                    sb.append(this.Otsikko());
                    if (this.luettu) {
                        sb.append(" (luettu)");
                    } else {
                        sb.append(" (lukematon)");
                    }
                    sb.append("\n\tURL: ");
                    sb.append(haeOminaisuus(Attribuutit.URL));                
                    return  sb.toString();
                    
                case BLOGPOST:
                    sb.append("Blogpost-vinkki \n\tOtsikko: ");
                    sb.append(this.Otsikko());
                    if (this.luettu) {
                        sb.append(" (luettu)");
                    } else {
                        sb.append(" (lukematon)");
                    }
                    sb.append("\n\tURL: ");
                    sb.append(haeOminaisuus(Attribuutit.URL));                
                    return  sb.toString();
                case NULL:
                    return Otsikko() + ", luettu, " + luettu;
            }
        }
        return virheTeksti2;
    }

}
