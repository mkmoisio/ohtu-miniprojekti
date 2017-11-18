/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Vinkkitietokanta.Vinkki;
import Vinkkitietokanta.VinkkitietokantaRajapinta;
import java.util.Scanner;

/**
 * Itse käyttöliittymä.
 */
public class Kayttoliittyma {

    VinkkitietokantaRajapinta tk;

    public Kayttoliittyma(VinkkitietokantaRajapinta tk) {
        this.tk = tk;
    }

    public void suorita() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String komento = scanner.nextLine();

            if (komento.equals("lisää")) {
                System.out.println("Kirjoittaja");
                String kirjoittaja = scanner.nextLine();
                System.out.println("Otsikko");
                String otsikko = scanner.nextLine();
                if (this.lisaaKirjavinkki(kirjoittaja, otsikko)) {
                    System.out.println("Kirjavinkki lisätty");
                } else {
                    System.out.println("Kirjavinkkiä ei lisätty");
                }
            }
        }

    }

    public boolean lisaaKirjavinkki(String kirjoittaja, String otsikko) {
        if (kirjoittaja.isEmpty() || otsikko.isEmpty()) {
            return false;
        } else {
            return this.tk.lisaaKirja(kirjoittaja, otsikko);
        }
    }

}
