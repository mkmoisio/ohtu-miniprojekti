
import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;

public class Main {
    public static void main(String[] args) {
        //Luo kayttoliittyma ja tietokantasovellus
        Vinkkitietokanta tietokanta = new Vinkkitietokanta();
        Kayttoliittyma UI = new Kayttoliittyma(tietokanta);
        
        
    }
}
