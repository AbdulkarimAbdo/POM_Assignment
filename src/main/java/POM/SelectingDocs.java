package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class SelectingDocs {
    WebDriver driver;
    String  docsUrl = "https://app.levelset.com/wizard/SelectDocument/",
            freeDocsXpath = "//span[@class='price-amount' and text()='Free']/ancestor::div[contains(@class,'left-right-pair')]/div[@class='left']",
            docForValidationXpath = "//div[@class='left' and contains(text(),'%s')]",
            priceRangeLocator = "//span[contains(@class , 'price-amount') and number(translate(text(),'$',''))>%d and number(translate(text(),'$',''))<%d ]/ancestor::div[contains(@class,'left-right-pair')]/div[@class='left']";
    public SelectingDocs()
    {

    }
    public SelectingDocs(WebDriver driver) {
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

    public void navigateToSelectDoc (){
        driver.get(docsUrl);
        By expectedElem = new By.ByXPath(String.format(docForValidationXpath,"Exchange a Waiver"));
        assertTrue(validateElement(expectedElem,"clickable",3));
    }
    public int getCountOfFreeDocuments ()
    {
        int count = 0;
        By expectedElem = new By.ByXPath(String.format(docForValidationXpath,"Send a Payment Document"));
        if(validateElement(expectedElem,"present",5))
        {
            count = driver.findElements(By.xpath(freeDocsXpath)).size();
        }
        return count;
    }

    public String[] getNamesOfFreeDocuments()
    {
        String[] docNames = new String[getCountOfFreeDocuments()];
        By expectedElem = new By.ByXPath(String.format(docForValidationXpath,"Send a Payment Document"));
        if(validateElement(expectedElem,"present",5))
        {
            List<WebElement> elements = driver.findElements(By.xpath(freeDocsXpath));
            for(int i=0; i<elements.size(); i++)
            {
                docNames[i] = elements.get(i).getText();
            }
        }
        return docNames;
    }

    public int getCountOfDocumentsInRange(int start, int end)
    {
        int count = 0;
        By expectedElem = new By.ByXPath(String.format(docForValidationXpath,"Need a Notice"));
        if(validateElement(expectedElem,"present",5))
        {
            count = driver.findElements(By.xpath(String.format(priceRangeLocator,start,end))).size();
        }
        return count;
    }

    public String[] getNamesOfDocumentsInRange(int start, int end)
    {
        String[] docNames = new String[getCountOfDocumentsInRange(start,end)];
        By expectedElem = new By.ByXPath(String.format(docForValidationXpath,"Need a Notice"));
        if(validateElement(expectedElem,"present",5))
        {
            List<WebElement> elements = driver.findElements(By.xpath(String.format(priceRangeLocator,start,end)));
            for(int i=0; i<elements.size(); i++)
            {
                docNames[i] = elements.get(i).getText();
            }
        }
        return docNames;
    }








}
