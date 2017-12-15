package apuviritykset;

import apuviritykset.vakiot.Vakiot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public abstract class Validator {

 
    public static int getKIRJA_KIRJOTTAJA_MAX_PITUUS() {
        return Vakiot.KIRJA_KIRJOTTAJA_MAX_PITUUS;
    }

    public static int getKIRJA_OTSIKKO_MAX_PITUUS() {
        return Vakiot.KIRJA_OTSIKKO_MAX_PITUUS;
    }

    public static int getPODCAST_NIMI_MAX_PITUUS() {
        return Vakiot.PODCAST_NIMI_MAX_PITUUS;
    }

    public static int getPODCAST_KUVAUS_MAX_PITUUS() {
        return Vakiot.PODCAST_KUVAUS_MAX_PITUUS;
    }

    public static int getPODCAST_OTSIKKO_MAX_PITUUS() {
        return Vakiot.PODCAST_OTSIKKO_MAX_PITUUS;
    }

    public static int getVIDEO_OTSIKKO_MAX_PITUUS() {
        return Vakiot.VIDEO_OTSIKKO_MAX_PITUUS;
    }

    public static int getVIDEO_URL_MAX_PITUUS() {
        return Vakiot.VIDEO_URL_MAX_PITUUS;
    }

    public static int getBLOGPOST_OTSIKKO_MAX_PITUUS() {
        return Vakiot.BLOGPOST_OTSIKKO_MAX_PITUUS;
    }

    public static int getBLOGPOST_URL_MAX_PITUUS() {
        return Vakiot.BLOGPOST_URL_MAX_PITUUS;
    }

    public static int getTAG_MAX_PITUUS() {
        return Vakiot.TAG_MAX_PITUUS;
    }

    public static int getOTSIKKO_MAX_PITUUS() {
        return Vakiot.OTSIKKO_MAX_PITUUS;
    }
    
    private List<String> virheet;
    
    public Validator() {
        this.virheet = new ArrayList();
    }
   
    public static boolean taginPituusOk(String tag){
        return ((tag.length() <= Vakiot.TAG_MAX_PITUUS));
    }

    public abstract boolean validoi() ;
    
    public  List<String> getVirheet(){
        return this.virheet;
    }

    protected void lisaaVirhe(String virhe) {
        this.virheet.add(virhe);
    }
    
    
}
