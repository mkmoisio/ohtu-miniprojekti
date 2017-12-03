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
}
