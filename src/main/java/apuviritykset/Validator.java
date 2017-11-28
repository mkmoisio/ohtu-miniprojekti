package apuviritykset;

/**
 *
 * @author mikkomo
 */
public class Validator {

    public static final boolean DEBUG = true;

    private static final int KIRJA_KIRJOTTAJA_MAX_PITUUS = 50;
    private static final int KIRJA_OTSIKKO_MAX_PITUUS = 50;
    private static final int PODCAST_NIMI_MAX_PITUUS = 50;
    private static final int PODCAST_KUVAUS_MAX_PITUUS = 50;
    private static final int PODCAST_OTSIKKO_MAX_PITUUS = 50;

    public static boolean podcastvinkinSyoteOk(String nimi, String otsikko, String kuvaus) {
        return (!nimi.isEmpty()
                && !otsikko.isEmpty()
                && !kuvaus.isEmpty() 
                && (nimi.length() <= PODCAST_NIMI_MAX_PITUUS)
                && (otsikko.length() <= PODCAST_OTSIKKO_MAX_PITUUS)
                && (kuvaus.length() <= PODCAST_KUVAUS_MAX_PITUUS));
    }

    public static boolean kirjavinkinSyoteOk(String kirjoittaja, String otsikko) {
        return (!kirjoittaja.isEmpty()
                && !otsikko.isEmpty()
                && (kirjoittaja.length() <= KIRJA_KIRJOTTAJA_MAX_PITUUS)
                && (otsikko.length() <= KIRJA_OTSIKKO_MAX_PITUUS));
    }
    
}
