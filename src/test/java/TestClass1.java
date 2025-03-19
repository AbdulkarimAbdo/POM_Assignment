import POM.Home;
import POM.SelectingDocs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestClass1 {
   WebDriver driver;
   Home home;
   SelectingDocs docs;

    @BeforeClass
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        home = new Home(driver);
        docs = new SelectingDocs(driver);
    }

    @AfterClass
    public void tareDown() {
        driver.quit();
    }

    @Test
    public void testHomePage(){
        home.navigateToHome();
        home.clickGetPaid();
        Assert.assertEquals((docs.getNamesOfFreeDocuments())[0],"Exchange a Waiver");
    }

    @Test
    public void testFreeDocsCount(){
        docs.navigateToSelectDoc();
        int actualCount = docs.getCountOfFreeDocuments();
        int expectedCount = 2;
        Assert.assertEquals(actualCount, expectedCount);
    }

    @Test
    public void testFreeDocsNames(){
        docs.navigateToSelectDoc();
        String[] actualNames = docs.getNamesOfFreeDocuments();
        String[] expectedNames = {"Exchange a Waiver","Send a Payment Document"};
        Assert.assertEquals(actualNames, expectedNames);
    }

    @Test
    public void testCountOfDocsInRange(){
        docs.navigateToSelectDoc();
        int actualCount = docs.getCountOfDocumentsInRange(30, 60);
        int expectedCount = 1;
        Assert.assertEquals(actualCount, expectedCount);
    }

    @Test
    public void testNamesOfDocsInRange(){
        docs.navigateToSelectDoc();
        String[] actualNames = docs.getNamesOfDocumentsInRange(30, 60);
        String[] expectedNames = {"Send a Warning"};
        Assert.assertEquals(actualNames, expectedNames);
    }




}
