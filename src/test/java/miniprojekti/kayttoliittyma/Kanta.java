package miniprojekti.kayttoliittyma;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Vinkkitietokanta.LukuStatus;
import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.Vinkkitietokanta;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author mikkomo
 */
public class Kanta extends Vinkkitietokanta {

    public Kanta(String tkPath) {

        super(tkPath);
    }

    /*
     Tyhjentää koko tietokannan!
     */
    public void nollaa() {
        Queue<String> queries = new ArrayDeque();
        queries.add("DELETE FROM Vinkki;");
        queries.add("DELETE FROM VinkkiTekija");
        queries.add("DELETE FROM Tekija");
        queries.add("DELETE FROM Kirja");
        queries.add("DELETE FROM Podcast");
        queries.add("DELETE FROM Video");
        queries.add("DELETE FROM Vinkki");

        for (String s : queries) {
            try {
                PreparedStatement stmt = super.getConn().prepareStatement(s);

                stmt.executeUpdate();
                stmt.close();
                //komento3.executeUpdate();

            } catch (SQLException e) {
                System.err.println("poistaVinkki: " + e.getMessage());

            }
        }
    }
}
