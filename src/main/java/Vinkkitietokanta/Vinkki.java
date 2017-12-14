
package Vinkkitietokanta;

import apuviritykset.Muotoilut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Vinkki {

    private Formaatit formaatti;
    private String otsikko;
    private boolean luettu;
    private List<String> tekijat;
    private List<String> tagit;
    private HashMap<Attribuutit, String> attribuutit;
    protected String virheTeksti = "Ominaisuutta ei liitetty";
    protected String virheTeksti2 = "hups";
    

    
    public Vinkki(String otsikko, Formaatit formaatti) {
        this.otsikko = otsikko;
        this.formaatti = formaatti;
        this.luettu = false;
        this.tekijat = new ArrayList<>();
        this.tagit = new ArrayList<>();
        attribuutit = new HashMap<>();
    }

    //ehkä palauttaa geneerisen Object tyypin? Helpompi käyttöliittymälle
    public String haeOminaisuus(Attribuutit attribuutti) {
        if (null != attribuutti) {
            if (attribuutit.containsKey(attribuutti)) {
                return attribuutit.get(attribuutti);
            } else {
                return virheTeksti;
            }

        }
        return virheTeksti;
    }

    //Myös muokkaa
    public boolean lisaaOminaisuus(Attribuutit attribuutti, String arvo) {
        if (null != attribuutti && arvo!=null &&!arvo.isEmpty()) {
            attribuutit.put(attribuutti, arvo);
            return true;
        }
        return false;
    }

    /*Ottaa argumenttina tietokannasta 
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
    
     public boolean lisaaTagit(String tagitStr) {
        if (tagitStr == null || tagitStr.isEmpty()) return false;
        String erotin = "[----]";
        for (String tag : tagitStr.split(erotin)) {
            lisaaTag(tag.trim());
        }
        return true;
    }
    
    public boolean lisaaTag(String tag){ //validoitu käyttöliittymässä
        if (tag==null || tagit.contains(tag) || tag.isEmpty()) return false;
        this.tagit.add(tag);
        return true;
    }

    
    public String getOminaisuudet(){
        String str = "TEKIJA";
        for (Map.Entry<Attribuutit, String> attr : attribuutit.entrySet()){
            str = str + " " + attr.getKey().toString();
        }
        return str;
    }
    
    public void tyhjennaTekijat(){
        tekijat.clear();
    }
    
    public List<String> haeTagit() {
        return tagit;
    }
    
    public boolean onkoTagia(String tagEtsittava){
        for (String tag : tagit){
            if (tag.equals(tagEtsittava)){
                return true;
            }
        }
        return false;
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

    public String otsikko() {
        return otsikko;
    }

    public String virheteksti(){
        return virheTeksti;
    }
    
    @Override
    public String toString() {
        if (null != formaatti) {
            switch (formaatti) {
                case KIRJA:         
                    return Muotoilut.muotoileKirjavinkinTuloste(this);
                case PODCAST:
                    return Muotoilut.muotoilePodcastvinkinTuloste(this);
                case VIDEO:
                    return Muotoilut.muotoileVideovinkinTuloste(this);
                case BLOGPOST:
                    return Muotoilut.muotoileBlogpostvinkinTuloste(this);
                case NULL:
                    return Muotoilut.muotoileNullFormaatinTuloste(this);
            }
        }
        return virheTeksti2;
    }
}
