package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;

public class FlightSearchPage {
    WebDriver driver;
    WebDriverWait wait;

    public FlightSearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void searchFlight(String fromCity, String toCity) throws InterruptedException {
        // Close popup if present
        try {
            WebElement body = driver.findElement(By.tagName("body"));
            body.click(); // dismiss any modal
        } catch (Exception ignored) {}

        // Click Flights
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@data-cy='menu_Flights']"))).click();

        // From City
        WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
        from.click();
        WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='From']")));
        fromInput.sendKeys(fromCity);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + fromCity + "')]"))).click();

        // To City
        WebElement toInput = driver.findElement(By.xpath("//input[@placeholder='To']"));
        toInput.sendKeys(toCity);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + toCity + "')]"))).click();

        // Departure date (just pick tomorrow)
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label='Thu Jul 19 2025']"))); // Modify dynamically if needed
        date.click();

        // Search
        driver.findElement(By.xpath("//a[text()='Search']")).click();
    }

    public String getMostExpensiveFlightDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='listingCard']")));

        List<WebElement> prices = driver.findElements(By.xpath("//div[@class='blackText fontSize18 blackFont white-space-no-wrap']"));
        List<WebElement> names = driver.findElements(By.xpath("//span[contains(@class,'airways-name')]"));

        double maxPrice = 0;
        int index = 0;
        for (int i = 0; i < prices.size(); i++) {
            String priceStr = prices.get(i).getText().replace("₹", "").replace(",", "").trim();
            double price = Double.parseDouble(priceStr);
            if (price > maxPrice) {
                maxPrice = price;
                index = i;
            }
        }

        System.out.println("Most Expensive Flight Details:");
        System.out.println("Airline: " + names.get(index).getText());
        System.out.println("Price: ₹" + maxPrice);
        return "Airline: " + names.get(index).getText();
    }
}
