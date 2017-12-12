package apuviritykset;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public abstract class Validator {

    public static final boolean DEBUG = false;

    // JOS MUOKKAAT NÄITÄ NIIN MUOKKAA MYÖS CUCUMBERIN FEATUREJA VASTAVASTI
    private static final int KIRJA_KIRJOTTAJA_MAX_PITUUS = 50;
    private static final int KIRJA_OTSIKKO_MAX_PITUUS = 100;
    private static final int PODCAST_NIMI_MAX_PITUUS = 50;
    private static final int PODCAST_KUVAUS_MAX_PITUUS = 100; 
    private static final int PODCAST_OTSIKKO_MAX_PITUUS = 100;
    private static final int VIDEO_OTSIKKO_MAX_PITUUS = 100;
    private static final int VIDEO_URL_MAX_PITUUS = 50;
    private static final int BLOGPOST_OTSIKKO_MAX_PITUUS = 100;
    private static final int BLOGPOST_URL_MAX_PITUUS = 50;
    private static final int TAG_MAX_PITUUS = 50;
    private static final int OTSIKKO_MAX_PITUUS = 100;

    public static int getKIRJA_KIRJOTTAJA_MAX_PITUUS() {
        return KIRJA_KIRJOTTAJA_MAX_PITUUS;
    }

    public static int getKIRJA_OTSIKKO_MAX_PITUUS() {
        return KIRJA_OTSIKKO_MAX_PITUUS;
    }

    public static int getPODCAST_NIMI_MAX_PITUUS() {
        return PODCAST_NIMI_MAX_PITUUS;
    }

    public static int getPODCAST_KUVAUS_MAX_PITUUS() {
        return PODCAST_KUVAUS_MAX_PITUUS;
    }

    public static int getPODCAST_OTSIKKO_MAX_PITUUS() {
        return PODCAST_OTSIKKO_MAX_PITUUS;
    }

    public static int getVIDEO_OTSIKKO_MAX_PITUUS() {
        return VIDEO_OTSIKKO_MAX_PITUUS;
    }

    public static int getVIDEO_URL_MAX_PITUUS() {
        return VIDEO_URL_MAX_PITUUS;
    }

    public static int getBLOGPOST_OTSIKKO_MAX_PITUUS() {
        return BLOGPOST_OTSIKKO_MAX_PITUUS;
    }

    public static int getBLOGPOST_URL_MAX_PITUUS() {
        return BLOGPOST_URL_MAX_PITUUS;
    }

    public static int getTAG_MAX_PITUUS() {
        return TAG_MAX_PITUUS;
    }

    public static int getOTSIKKO_MAX_PITUUS() {
        return OTSIKKO_MAX_PITUUS;
    }
    
    private List<String> virheet;
    
    public Validator() {
        this.virheet = new ArrayList();
    }
   
    public static boolean taginPituusOk(String tag){
        return ((tag.length() <= TAG_MAX_PITUUS));
    }

    public abstract boolean validoi() ;
    
    public  List<String> getVirheet(){
        return this.virheet;
    }

    protected void lisaaVirhe(String virhe) {
        this.virheet.add(virhe);
    }
    
    
}
