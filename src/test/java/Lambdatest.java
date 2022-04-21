import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

class TestClass1 implements Runnable {
    public void run() {
        Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
        capsHashtable.put("browserName", "Chrome");
        capsHashtable.put("version", "92.0");
        capsHashtable.put("platform", "Windows 10");
        capsHashtable.put("resolution", "1024x768");
        capsHashtable.put("build", "Checking-lambdatest-build-1");
        capsHashtable.put("name", "Test 1");
        Lambdatest r1 = new Lambdatest();
        r1.executeTest(capsHashtable);
    }
}

class TestClass2 implements Runnable {
    public void run() {
        Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
        capsHashtable.put("browserName", "Firefox");
        capsHashtable.put("version", "98.0");
        capsHashtable.put("platform", "Windows 10");
        capsHashtable.put("resolution", "1024x768");
        capsHashtable.put("build", "Checking-lambdatest-build-1");
        capsHashtable.put("name", "Test 2");
        Lambdatest r2 = new Lambdatest();
        r2.executeTest(capsHashtable);
    }
}

public class Lambdatest {
    WebDriver driver;
    Actions actions;
    BasePage basePage = new BasePage();
    Scenario1PageActions scenario1PageActions = new Scenario1PageActions();
    Scenario2PageActions scenario2PageActions = new Scenario2PageActions();
    Scenario3PageActions scenario3PageActions = new Scenario3PageActions();

    public static final String USERNAME = "muratkangal0101";
    public static final String AUTOMATE_KEY = "ow1nSpOuDoHy9cj3mGcWQzZqUbEEzJq6pGgpotBWYkW9gdh1aL";
    public static final String URL1 = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.lambdatest.com/wd/hub";

    public static void main(String[] args) {
        Thread object1 = new Thread(new TestClass1());
        object1.start();
        Thread object2 = new Thread(new TestClass2());
        object2.start();

    }

    /**
     * This class contains common methods used in whole project
     */
    class BasePage {
        public final String SCENARIO_URL = "https://www.lambdatest.com/selenium-playground";

        BasePage navigateToURL() {
            driver.get(SCENARIO_URL);
            return this;
        }

        BasePage selectMenu(String menu) {
            driver.findElement(By.xpath("//a[text()='" + menu + "']")).click();
            return this;
        }

        BasePage verifyURLContains(String url) {
            Assert.assertTrue(driver.getCurrentUrl().contains(url));
            return this;
        }
    }

    /**
     * Locators and Methods os Scenario-1
     */
    //This class contains only locators of Scenario-1
    class Scenario1Page {

        public final By ENTER_MESSAGE = By.cssSelector("div > input#user-message");
        public final By GET_CHECKED_VALUE = By.cssSelector("div > #showInput");
        public final By CONSOLE_MESSAGE = By.id("message");
    }

    //This class contains only methods of Scenario-1
    class Scenario1PageActions extends Scenario1Page {

        Scenario1PageActions enterMessageToSingleInput(String message) {
            driver.findElement(ENTER_MESSAGE).sendKeys(message);
            return this;
        }

        Scenario1PageActions clickGetCheckedButton() {
            driver.findElement(GET_CHECKED_VALUE).click();
            return this;
        }

        Scenario1Page verifyMessageOnTheConsole(String message) {
            Assert.assertEquals(driver.findElement(CONSOLE_MESSAGE).getText(), message);
            return this;
        }
    }

    /**
     * Locators and Methods os Scenario-2
     */
    //This class contains only locators of Scenario-2
    class Scenario2Page {

        WebElement SLIDER(String value) {
            return driver.findElement(By.xpath("//h4[contains(text(),'" + value + "')]/following-sibling::div/input"));
        }

        WebElement SLIDER_RANGE(String value) {
            return driver.findElement(By.xpath("//h4[contains(text(),'" + value + "')]/following-sibling::div/output"));
        }

    }

    //This class contains only methods of Scenario-2
    class Scenario2PageActions extends Scenario2Page {
        Scenario2PageActions dragSlider(String value, int num) {
            actions = new Actions(driver);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,350)");
            int volume = 0;
            for (int i = 0; i < 900; i++) {
                actions.dragAndDropBy(SLIDER(value), i, 0).perform();
                String range = SLIDER_RANGE(value).getText();
                volume = Integer.parseInt(range);
                if (volume == num) break;
            }
            Assert.assertEquals(volume, num);
            return this;
        }
    }

    /**
     * Locators and Methods os Scenario-3
     */
    //This class contains only locators of Scenario-3
    class Scenario3Page {

        public final By SUBMIT_BUTTON = By.xpath("//button[text()='Submit']");
        public final By NAME_INPUT = By.id("name");
        public final By STATE_INPUT = By.id("inputState");
        public final By COUNTRY_INPUT = By.name("country");
        public final By SUCCESS_MESSAGE = By.cssSelector(".success-msg.hidden");
    }

    //This class contains only methods of Scenario-3
    class Scenario3PageActions extends Scenario3Page {

        Scenario3PageActions clickSubmitButton() {
            driver.findElement(SUBMIT_BUTTON).click();
            return this;
        }

        Scenario3PageActions verifyErrorMessage(String message) {
            Assert.assertEquals(driver.findElement(NAME_INPUT).getAttribute("validationMessage"), message);
            return this;
        }

        Scenario3PageActions enterDataInput(String input, String data) {
            driver.findElement(By.cssSelector("input[name='" + input + "']")).sendKeys(data);
            return this;
        }

        Scenario3PageActions enterState(String state) {
            driver.findElement(STATE_INPUT).sendKeys(state);
            return this;
        }

        Scenario3PageActions selectCountry(String country) {
            Select dropDown = new Select(driver.findElement(COUNTRY_INPUT));
            dropDown.selectByVisibleText(country);
            return this;
        }

        Scenario3PageActions verifySuccessMessage(String message) {
            Assert.assertEquals(driver.findElement(SUCCESS_MESSAGE).getText(), message);
            return this;
        }

    }

    public void executeTest(Hashtable<String, String> capsHashtable) {
        String key;
        DesiredCapabilities caps = new DesiredCapabilities();
        // Iterate over the hashtable and set the capabilities
        Set<String> keys = capsHashtable.keySet();
        Iterator<String> itr = keys.iterator();
        while (itr.hasNext()) {
            key = itr.next();
            caps.setCapability(key, capsHashtable.get(key));
        }

        /**
         * Scenario - 1
         */
        try {
            driver = new RemoteWebDriver(new URL(URL1), caps);
            basePage.navigateToURL()
                    .selectMenu("Simple Form Demo")
                    .verifyURLContains("simple-form-demo");

            String message = "Welcome to LambdaTest";
            scenario1PageActions.enterMessageToSingleInput(message)
                    .clickGetCheckedButton()
                    .verifyMessageOnTheConsole(message);
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /**
         * Scenario - 2
         */
        try {
            driver = new RemoteWebDriver(new URL(URL1), caps);
            basePage.navigateToURL()
                    .selectMenu("Drag & Drop Sliders");

            scenario2PageActions.dragSlider("Default value 15", 95);
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /**
         * Scenario - 3
         */
        try {
            driver = new RemoteWebDriver(new URL(URL1), caps);
            basePage.navigateToURL()
                    .selectMenu("Input Form Submit");

            scenario3PageActions.clickSubmitButton()
                    .verifyErrorMessage("Please fill out this field.")
                    .enterDataInput("name", "John Doe")
                    .enterDataInput("email", "johndoe@gmail.com")
                    .enterDataInput("password", "123456789")
                    .enterDataInput("company", "LambdaTest")
                    .enterDataInput("website", "helloworld.com")
                    .enterDataInput("city", "Virginia")
                    .enterDataInput("address_line1", "WDM 56")
                    .enterDataInput("address_line2", "Blackberry")
                    .enterDataInput("zip", "556655")
                    .enterState("New York")
                    .selectCountry("United States")
                    .clickSubmitButton()
                    .verifySuccessMessage("Thanks for contacting us, we will get back to you shortly.");
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}