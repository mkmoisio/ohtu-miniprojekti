package miniprojekti.kayttoliittyma;

import Kayttoliittyma.Kayttoliittyma;
import Vinkkitietokanta.Vinkkitietokanta;
import apuviritykset.Muotoilut;
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

    private void execute() {
        this.l.lisaaSyote("lopeta");
        this.ui.suorita();
    }
    /*
    LISÄYS ALKAA
    */
    @Given("^Command \"([^\"]*)\" is entered$")
    public void command_entered(String command) throws Throwable {
        tk.nollaa();
        ui.setLukija(l);
        ui.setTulostus(t);

        l.lisaaSyote(command);
    }

    @Given("^Command \"([^\"]*)\" is entered, do not erase data$")
    public void command_entered2(String command) throws Throwable {
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
    /*
    LISÄYS LOPPUU
    */
    /*
    LISTAUS ALKAA
    */
    @Given("^database is reset$")
    public void database_is_reset() {
        tk.nollaa();
    }
    @Given("^command \"([^\"]*)\" is entered and author \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
    public void command_and_author_and_title_entered(String command, String author, String title) {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote(command, author, title);

    }
    
    @Given("^command \"([^\"]*)\" is entered and url \"([^\"]*)\" and title \"([^\"]*)\" are entered$")
    public void command_and_url_and_title_entered(String command, String url, String title) {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote(command, url, title);

    }

    @When("^print command \"([^\"]*)\" is entered$")
    public void print_command_entered(String command) {
        l.lisaaSyote(command);
        l.lisaaSyote("lopeta");
        ui.suorita();
    }

    /*MI/7.12 korjaamatta, käyttää legacy metodia
    @Then("^the application responds with a list containing an unread book tip with author \"([^\"]*)\" and title \"([^\"]*)\"$")
    public void response_contains_unread_book_tip(String author, String title) throws Throwable {
        assertTrue(t.tulosteSisaltaa(Muotoilut.muotoileKirjavinkinTuloste(title, false, author)));
        t.nollaa();
    }*/
    
   
    @Given("^System has an existing tip \"([^\"]*)\" with author \"([^\"]*)\"$")
    public void system_has_an_existing_tip_with_author(String arg1, String arg2) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote("lisää kirja");
        l.lisaaSyote(arg2,arg1);
    }
    
    @Given("^System has an existing podcast \"([^\"]*)\" with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void system_has_an_existing_podcast_with_author(String arg1, String arg2, String arg3) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote("lisää podcast");
        l.lisaaSyote(arg2,arg1,arg3);
    }
    
    
    @Given("^System has an existing video \"([^\"]*)\" with url \"([^\"]*)\"$")
    public void system_has_an_existing_video_with_author(String arg1, String arg2) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote("lisää video");
        l.lisaaSyote(arg2,arg1);
    }
    
    @Given("^System has an existing blogpost \"([^\"]*)\" with url \"([^\"]*)\" and author \"([^\"]*)\"$")
    public void system_has_an_existing_blogpost_with_author(String arg1, String arg2, String arg3) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote("lisää blogpost");
        l.lisaaSyote(arg2,arg3,arg1);
    }
    
    @Given("^System has an existing book \"([^\"]*)\" with author \"([^\"]*)\"$")
    public void system_has_an_existing_book_with_author(String arg1, String arg2) throws Throwable {
        ui.setLukija(l);
        ui.setTulostus(t);
        l.lisaaSyote("lisää kirja");
        l.lisaaSyote(arg2,arg1);
    }

    
    
    @When("^title \"([^\"]*)\" is entered$")
    public void title_is_entered(String arg1) throws Throwable {
        l.lisaaSyote(arg1);
        l.lisaaSyote("lopeta");
        ui.suorita();
    }
    
    @When("^title \"([^\"]*)\" are entered$")
    public void title_are_entered(String arg1) throws Throwable {
        l.lisaaSyote(arg1);
        l.lisaaSyote("lopeta");
        ui.suorita();
    }    
    
    
    
//      @Then("^the application responds with a list containing tip with url \"([^\"]*)\" and title \"([^\"]*)\"$")
//    public void response_contains(String url, String title) {
//        assertTrue(t.tulosteSisaltaa(Muotoilut.));
//    }
//    
    
     /*
    LISTAUS LOPPUU
    */

}
