
package apuviritykset;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Vinkki;

public class Muotoilut {

    private static String muotoileTulosteenAlkuosa(String otsikko, boolean luettu) {
        StringBuilder sb = new StringBuilder();
        sb.append("Kirjavinkki \n\tOtsikko: ");
        sb.append(otsikko);
        if (luettu) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tKirjoittaja: ");
        return sb.toString();

    }

    public static String muotoileKirjavinkinTuloste(Vinkki kirja) {
        StringBuilder sb = new StringBuilder();
        sb.append(Muotoilut.muotoileTulosteenAlkuosa(kirja.otsikko(), kirja.luettu()));
        sb.append(kirja.printtaaTekijat());
        sb.append("\n\tTagit: " +muotoileTagit(kirja));
        return sb.toString();
    }

    public static String muotoilePodcastvinkinTuloste(Vinkki podcast) {
        StringBuilder sb = new StringBuilder();

        sb.append("Podcastvinkki \n\tOtsikko: ");
        sb.append(podcast.otsikko());
        if (podcast.lisaaTekijat(null)) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tNimi: ");
        sb.append(podcast.haeOminaisuus(Attribuutit.NIMI));
        sb.append("\n\tKuvaus: ");
        sb.append(podcast.haeOminaisuus(Attribuutit.KUVAUS));
        sb.append("\n\tTagit: " +muotoileTagit(podcast));
        return sb.toString();
    }

    public static String muotoileVideovinkinTuloste(Vinkki video) {
        StringBuilder sb = new StringBuilder();

        sb.append("Videovinkki \n\tOtsikko: ");
        sb.append(video.otsikko());
        if (video.luettu()) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tURL: ");
        sb.append(video.haeOminaisuus(Attribuutit.URL));
        sb.append("\n\tTagit: " +muotoileTagit(video));
        return sb.toString();
    }

    public static String muotoileBlogpostvinkinTuloste(Vinkki blogautus) {
        StringBuilder sb = new StringBuilder();

        sb.append("Blogpost-vinkki \n\tOtsikko: ");
        sb.append(blogautus.otsikko());
        if (blogautus.luettu()) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tURL: ");
        sb.append(blogautus.haeOminaisuus(Attribuutit.URL));
        sb.append("\n\tTagit:" +muotoileTagit(blogautus));
        return sb.toString();
    }
    
    public static StringBuilder muotoileTagit(Vinkki vinkki){
        StringBuilder tagit = new StringBuilder();
        if (!vinkki.haeTagit().isEmpty()) {
            for (String tag : vinkki.haeTagit()){
                tagit.append(" " +tag);
            }
        }
        return tagit; 
    }

    public static String muotoileNullFormaatinTuloste(Vinkki vinkki) {
        return vinkki.otsikko() + ", luettu, " + vinkki.luettu();
    }
}
