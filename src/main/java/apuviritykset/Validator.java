package apuviritykset;

/**
 *
 * @author mikkomo
 */
public class Validator {

    public static final boolean DEBUG = false;

    private static final int KIRJA_KIRJOTTAJA_MAX_PITUUS = 50;
    private static final int KIRJA_OTSIKKO_MAX_PITUUS = 50;
    private static final int PODCAST_NIMI_MAX_PITUUS = 50;
    private static final int PODCAST_KUVAUS_MAX_PITUUS = 50;
    private static final int PODCAST_OTSIKKO_MAX_PITUUS = 50;
    private static final int VIDEO_OTSIKKO_MAX_PITUUS = 50;
    private static final int VIDEO_URL_MAX_PITUUS = 500;

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

    public static boolean kirjavinkinSyoteOk(String kirjoittaja, String otsikko) {
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

}
