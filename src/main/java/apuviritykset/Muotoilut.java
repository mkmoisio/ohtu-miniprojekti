/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuviritykset;

import Vinkkitietokanta.Attribuutit;
import Vinkkitietokanta.Vinkki;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mikkomo
 */
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
        sb.append("\n\tKirjoitaja: ");
        return sb.toString();

    }

    public static String muotoileKirjavinkinTuloste(Vinkki kirja) {
        StringBuilder sb = new StringBuilder();
        sb.append(Muotoilut.muotoileTulosteenAlkuosa(kirja.Otsikko(), kirja.luettu()));
        sb.append(kirja.haeOminaisuus(Attribuutit.TEKIJAT));

        return sb.toString();
    }

    public static String muotoileKirjavinkinTuloste(String otsikko, boolean luettu, List<String> tekijat) {
        StringBuilder sb = new StringBuilder();
        sb.append(Muotoilut.muotoileTulosteenAlkuosa(otsikko, luettu));

        sb.append(String.join(",", tekijat));

        return sb.toString();
    }

    public static String muotoileKirjavinkinTuloste(String otsikko, boolean luettu, String tekija) {
        List<String> list = new ArrayList();
        list.add(tekija);
        return muotoileKirjavinkinTuloste(otsikko, luettu, list);
    }

    public static String muotoilePodcastvinkinTuloste(Vinkki podcast) {
        StringBuilder sb = new StringBuilder();

        sb.append("Podcastvinkki \n\tOtsikko: ");
        sb.append(podcast.Otsikko());
        if (podcast.lisaaTekijat(null)) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tNimi: ");
        sb.append(podcast.haeOminaisuus(Attribuutit.NIMI));
        sb.append("\n\tKuvaus: ");
        sb.append(podcast.haeOminaisuus(Attribuutit.KUVAUS));
        return sb.toString();
    }

    public static String muotoileVideovinkinTuloste(Vinkki video) {
        StringBuilder sb = new StringBuilder();

        sb.append("Videovinkki \n\tOtsikko: ");
        sb.append(video.Otsikko());
        if (video.luettu()) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tURL: ");
        sb.append(video.haeOminaisuus(Attribuutit.URL));
        sb.append("\n\tKuvaus: ");
        sb.append(video.haeOminaisuus(Attribuutit.KUVAUS));
        return sb.toString();
    }

    public static String muotoileBlogpostvinkinTuloste(Vinkki blogautus) {
        StringBuilder sb = new StringBuilder();

        sb.append("Blogpost-vinkki \n\tOtsikko: ");
        sb.append(blogautus.Otsikko());
        if (blogautus.luettu()) {
            sb.append(" (luettu)");
        } else {
            sb.append(" (lukematon)");
        }
        sb.append("\n\tURL: ");
        sb.append(blogautus.haeOminaisuus(Attribuutit.URL));
        sb.append("\n\tKuvaus: ");
        sb.append(blogautus.haeOminaisuus(Attribuutit.KUVAUS));
        return sb.toString();
    }

    public static String muotoileNullFormaatinTuloste(Vinkki vinkki) {
        return vinkki.Otsikko() + ", luettu, " + vinkki.luettu();
    }
}
