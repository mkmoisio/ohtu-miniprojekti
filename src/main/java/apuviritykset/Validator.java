package apuviritykset;

/**
 *
 * @author mikkomo
 */
public class Validator {

    public static final boolean DEBUG = false;

    // JOS MUOKKAAT NÄITÄ NIIN MUOKKAA MYÖS CUCUMBERIN FEATUREJA VASTAVASTI
    protected static final int KIRJA_KIRJOTTAJA_MAX_PITUUS = 50;
    private static final int KIRJA_OTSIKKO_MAX_PITUUS = 100;
    public static final int PODCAST_NIMI_MAX_PITUUS = 50;
    public static final int PODCAST_KUVAUS_MAX_PITUUS = 100; 
    private static final int PODCAST_OTSIKKO_MAX_PITUUS = 100;
    private static final int VIDEO_OTSIKKO_MAX_PITUUS = 100;
    private static final int VIDEO_URL_MAX_PITUUS = 50;
    private static final int BLOGPOST_OTSIKKO_MAX_PITUUS = 100;
    private static final int BLOGPOST_URL_MAX_PITUUS = 50;
    private static final int TAG_MAX_PITUUS = 50;
    public static final int OTSIKKO_MAX_PITUUS = 100;
    
    public static boolean vinkinOtsikkoTyhja(String otsikko) {
        if (otsikko.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean vinkinOtsikkoLiianPitka(String otsikko) {
        if (otsikko.length() > OTSIKKO_MAX_PITUUS) {
            return true;
        }
        return false;
    }
    
    public static boolean vinkinNimiTyhja(String nimi) {
        if (nimi.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean podcastvinkinNimiLiianPitka(String nimi) {
        if (nimi.length() > PODCAST_NIMI_MAX_PITUUS) {
            return true;
        }
        return false;
    }
    
    public static boolean vinkinKuvausTyhja(String kuvaus) {
        if (kuvaus.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean vinkinKuvausLiianPitka(String kuvaus) {
        if (kuvaus.length() > PODCAST_KUVAUS_MAX_PITUUS) {
            return true;
        }
        return false;
    }
        
    
    public static boolean podcastvinkinSyoteOk(String nimi, String otsikko, String kuvaus) {
        if (!nimi.isEmpty()
                && !otsikko.isEmpty()
                && !kuvaus.isEmpty()
                && (nimi.length() <= PODCAST_NIMI_MAX_PITUUS)
                && (otsikko.length() <= PODCAST_OTSIKKO_MAX_PITUUS)
                && (kuvaus.length() <= PODCAST_KUVAUS_MAX_PITUUS)) {
            if (Validator.DEBUG) {
                System.out.println("Validator: podcastvinkinSyoteOk: Pass");
            }
            return true;
        }
        return false;
    }

    protected boolean kirjavinkinSyoteOk(String kirjoittaja, String otsikko) {
        return (!kirjoittaja.isEmpty()
                && !otsikko.isEmpty()
                && (kirjoittaja.length() <= KIRJA_KIRJOTTAJA_MAX_PITUUS)
                && (otsikko.length() <= KIRJA_OTSIKKO_MAX_PITUUS));
    }

    public static boolean videovinkinSyoteOk(String url, String otsikko) {
        return (!url.isEmpty()
                && !otsikko.isEmpty()
                && (url.length() <= VIDEO_URL_MAX_PITUUS)
                && (otsikko.length() <= VIDEO_OTSIKKO_MAX_PITUUS));

    }
    
    public static boolean blogivinkinSyoteOk(String url, String otsikko) {
        return (!url.isEmpty()
                && !otsikko.isEmpty()
                && (url.length() <= BLOGPOST_URL_MAX_PITUUS)
                && (otsikko.length() <= BLOGPOST_OTSIKKO_MAX_PITUUS));

    }
    
    public static boolean taginPituusOk(String tag){
        return ((tag.length() <= TAG_MAX_PITUUS));
    }


}
