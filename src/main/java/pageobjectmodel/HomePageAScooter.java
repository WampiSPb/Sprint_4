package pageobjectmodel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageAScooter {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    // Локаторы
    private static final By BUTTON_ORDER_IN_HEADER = By.xpath("//button[contains(@class, 'Button_Button__ra12g')]");
    private static final By BUTTON_ORDER_IN_MIDDLE = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private static final By COOKIE_BUTTON = By.id("rcc-confirm-button");

    public HomePageAScooter(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    // методы для куки

    public HomePageAScooter clickCookie() {
        wait.until(ExpectedConditions.elementToBeClickable(COOKIE_BUTTON)).click();
        return this;
    }

    // методы для вопросов

    public WebElement findQuestion(String questionText) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text() = '" + questionText + "']")
        ));
    }

    public HomePageAScooter scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return this;
    }

    public HomePageAScooter clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        return this;
    }

    public String getAnswerText(String answer) {
        By answerLocator = By.xpath("//*[@id='accordion__panel-" + answer + "']/p");
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return driver.findElement(answerLocator).getText();
    }

    // методы для кнопок "заказать"

    public HomePageAScooter clickButtonOrderInHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_ORDER_IN_HEADER)).click();
        return this;
    }

    public HomePageAScooter clickButtonOrderInMiddle() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(BUTTON_ORDER_IN_MIDDLE));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_ORDER_IN_MIDDLE)).click();
        return this;
    }

    public HomePageAScooter clickOrderButton(String buttonLocation) {
        if (buttonLocation.equals("header")) {
            clickButtonOrderInHeader();
        } else if (buttonLocation.equals("middle")) {
            clickButtonOrderInMiddle();
        }
        return this;
    }

    // переход на страницу заказа

    public OrderPageAScooter goToOrderPage(String buttonLocation) {
        clickCookie();
        clickOrderButton(buttonLocation);
        return new OrderPageAScooter(driver);
    }
}

