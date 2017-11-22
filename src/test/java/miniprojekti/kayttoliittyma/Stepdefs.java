
package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import miniprojekti.kayttoliittyma.Kanta;
import miniprojekti.kayttoliittyma.LukijaStub;
import miniprojekti.kayttoliittyma.TulostusStub;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author mikkomo
 */
public class Stepdefs {

    Kayttoliittyma ui = new Kayttoliittyma(new Kanta());
    LukijaStub l = new LukijaStub();
    TulostusStub t = new TulostusStub();

    @Given("^Command \"([^\"]*)\" is entered$")
    public void command_entered(String command) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote(command);
    }
    
     @When ("^author \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
     public void autor_and_title_entered(String author, String title) throws Throwable {
         l.lisaaSyote(author);
         l.lisaaSyote(title);
     }
    
     @Then("^the application responds with \"([^\"]*)\"$")
     public void  responds_with(String response) {
         assertTrue(t.tulosteSisaltaa(response));
     }
    
}
