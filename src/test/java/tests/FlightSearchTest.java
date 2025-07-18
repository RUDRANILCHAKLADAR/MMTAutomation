package tests;

import base.BaseTest;
import org.testng.annotations.*;
import pages.FlightSearchPage;
import utils.ConfigReader;
import org.testng.Reporter;

public class FlightSearchTest extends BaseTest {
    FlightSearchPage flightSearchPage;

    @BeforeClass
    public void setUpTest() {
        ConfigReader.loadProperties();
        setup();
        flightSearchPage = new FlightSearchPage(driver);
    }

    @Test
    public void testMostExpensiveFlight() throws InterruptedException {
        String fromCity = ConfigReader.get("fromCity");
        String toCity = ConfigReader.get("toCity");

        flightSearchPage.searchFlight(fromCity, toCity);
        String details = flightSearchPage.getMostExpensiveFlightDetails();

        Reporter.log("Test Case: Most Expensive Flight Details", true);
        Reporter.log(details, true);  // <-- This will show in HTML report
    }

    @AfterClass
    public void cleanUp() {
        tearDown();
    }
}
