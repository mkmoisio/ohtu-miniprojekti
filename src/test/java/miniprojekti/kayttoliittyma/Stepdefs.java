package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author mikkomo
 */
public class Stepdefs {

    File dbfile = new File("");

    Kanta tk = new Kanta("jdbc:sqlite:" + dbfile.getAbsolutePath() + "/databases/test/cucumber-kanta.db"); 
    Kayttoliittyma ui = new Kayttoliittyma(this.tk);
    LukijaStub l = new LukijaStub();
    TulostusStub t = new TulostusStub();

    
    
    @Given("^Command \"([^\"]*)\" is entered$")
    public void command_entered(String command) throws Throwable { 
        tk.nollaa();   
        ui.setLukija(l);
        ui.setTulostus(t);
        
        l.lisaaSyote(command);
    }

    @When("^author \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
    public void book_autor_and_title_entered(String author, String title) throws Throwable {
        l.lisaaSyote(author);
        l.lisaaSyote(title);
        l.lisaaSyote("lopeta");
        ui.suorita();
    }

    @When("^title \"([^\"]*)\" and name \"([^\"]*)\" and description \"([^\"]*)\" are entered$")
    public void podcast_name_title_and_description_entered(String title, String name, String description) {
        l.lisaaSyote(name, title, description, "Lopeta");
        ui.suorita();
    }

    @When("^url \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
    public void video_url_and_title_entered(String url, String title) {
        l.lisaaSyote(url);
        l.lisaaSyote(title);
        l.lisaaSyote("lopeta");

        ui.suorita();
    }

    @Then("^the application responds with \"([^\"]*)\"$")
    public void responds_with(String response) {
        assertTrue(t.tulosteSisaltaa(response));
        
        t.nollaa();
    }

}
