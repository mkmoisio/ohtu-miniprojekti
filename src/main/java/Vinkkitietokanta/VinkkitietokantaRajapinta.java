package Vinkkitietokanta;

import java.util.List;

/**
 *
 * 
 */
public interface VinkkitietokantaRajapinta {
    public boolean poistaVinkki(String otsikko);
    
    public boolean merkitseLuetuksi(String otsikko);
    public boolean merkitseLukemattomaksi(String otsikko);

    public boolean lisaaVinkki(Vinkki vinkki);
    public Vinkki haeVinkki(String otsikko);
    
    public List<Vinkki> haeKaikki(LukuStatus status);
    public List<Vinkki> haeKaikki(Formaatit formaatti, LukuStatus status);

    public List<Vinkki> haeTagilla(String tag);
    
}
