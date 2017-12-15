import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //Luo kayttoliittyma ja tietokantasovellus
        File dbfile=new File("");
        String url="jdbc:sqlite:"+dbfile.getAbsolutePath()+"/tietokanta_demo.db";
      
        Vinkkitietokanta tietokanta = new Vinkkitietokanta(url);
        Kayttoliittyma UI = new Kayttoliittyma(tietokanta);
        UI.alusta();
        UI.suorita();
    }
}
