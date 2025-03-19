package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.testng.Assert.assertTrue;


public class Home {
    WebDriver driver;
    String homeUrl = "https://www.levelset.com/", getPaidLocatorLinkText = "//a[contains(text(), 'Get paid ')]";

    public Home(WebDriver driver) {
        this.driver = driver;
    }

    public boolean validateElement (By element, String s, int timeout)
    {
        ExpectedCondition<WebElement> x = null;
        switch (s){
            case "visible":
                x= ExpectedConditions.visibilityOfElementLocated(element);
                break;
            case "clickable":
                x = ExpectedConditions.elementToBeClickable(element);
                break;
            case "present":
                x = ExpectedConditions.presenceOfElementLocated(element);
                break;
        }
            try {
                if(x!= null)
                {
                    new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(x);
                    return true;
                }
            }
            catch (Exception e){
                return false;
            }
        return false;
    }
    public boolean clickOnElement (By element, By expectedElement, int timeout) {
        for (int i = 0; i < 3; i++) {
            if (validateElement(element, "clickable", timeout)) {
                driver.findElement(element).click();
                if (validateElement(expectedElement, "present", timeout)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void navigateToHome (){
        driver.get(homeUrl);
        By expectedElement = new By.ByXPath(getPaidLocatorLinkText);
        assertTrue(validateElement(expectedElement,"visible",3));
    }

    public void clickGetPaid (){
        By element = new By.ByXPath(getPaidLocatorLinkText);
        By expectedElement = new By.ByXPath(String.format(new SelectingDocs().docForValidationXpath,"Exchange a Waiver"));
        assertTrue(clickOnElement(element,expectedElement,3),"Element Isn't Found");
    }
}
