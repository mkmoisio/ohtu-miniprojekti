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
    Kayttoliittyma ui = new Kayttoliittyma(new Vinkkitietokanta("jdbc:sqlite:" + dbfile.getAbsolutePath() + "/sprint2testikanta.db"));
    LukijaStub l = new LukijaStub();
    TulostusStub t = new TulostusStub();

    @Given("^Command \"([^\"]*)\" is entered$")
    public void command_entered(String command) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote(command);
    }

    @When("^author \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
    public void autor_and_title_entered(String author, String title) throws Throwable {
        l.lisaaSyote(author);
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
