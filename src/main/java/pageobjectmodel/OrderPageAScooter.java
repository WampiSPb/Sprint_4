package pageobjectmodel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderPageAScooter {

    private WebDriver driver;

    // Локаторы формы "Для кого самокат"
    private static final By INPUT_FIELD_NAME = By.xpath("//input[@placeholder='* Имя']");
    private static final By INPUT_FIELD_SURNAME = By.xpath("//input[@placeholder='* Фамилия']");
    private static final By INPUT_FIELD_ADDRESS = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By INPUT_FIELD_METRO_STATION = By.xpath("//input[contains(@placeholder,'Станция метро')]");
    private static final By INPUT_FIELD_PHONE = By.xpath("//input[contains(@placeholder,'Телефон')]");
    private static final By BUTTON_FURTHER = By.xpath("//button[text()='Далее']");

    // Локаторы формы "Про аренду"
    private static final By INPUT_FIELD_DATE = By.xpath("//input[contains(@placeholder,'Когда привезти самокат')]");
    private static final By RENTAL_PERIOD = By.xpath("//div[text()='* Срок аренды']");
    private static final By CHECKBOX_BLACK = By.id("black");
    private static final By CHECKBOX_GREY = By.id("grey");
    private static final By NEXT_MONTH_BUTTON = By.xpath("//button[contains(@aria-label, 'Next Month')]");
    private static final By INPUT_FIELD_COMMENT = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private static final By BUTTON_ORDER = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and text()='Заказать']");
    private static final By BUTTON_YES = By.xpath("//button[text()='Да']");
    private static final By ORDER_CONFIRMATION = By.xpath("//*[contains(text(), 'Заказ оформлен')]");

    public OrderPageAScooter(WebDriver driver) {
        this.driver = driver;
    }

    // форма "для кого самокат"

    public OrderPageAScooter fillName(String name) {
        driver.findElement(INPUT_FIELD_NAME).sendKeys(name);
        return this;
    }

    public OrderPageAScooter fillSurname(String surname) {
        driver.findElement(INPUT_FIELD_SURNAME).sendKeys(surname);
        return this;
    }

    public OrderPageAScooter fillAddress(String address) {
        driver.findElement(INPUT_FIELD_ADDRESS).sendKeys(address);
        return this;
    }

    public OrderPageAScooter selectMetroStation(int metroIndex) {
        driver.findElement(INPUT_FIELD_METRO_STATION).click();
        String locator = String.format("//div[@class='select-search__select']/ul/li[%d]", metroIndex);
        WebElement station = driver.findElement(By.xpath(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", station);
        station.click();
        return this;
    }

    public OrderPageAScooter fillPhone(String phone) {
        driver.findElement(INPUT_FIELD_PHONE).sendKeys(phone);
        return this;
    }

    public OrderPageAScooter clickFurtherButton() {
        driver.findElement(BUTTON_FURTHER).click();
        return this;
    }

    public OrderPageAScooter fillCustomerForm(String buttonLocation, String name, String surname,
                                              String address, int metro, String phone) {
        clickOrderButton(buttonLocation);
        fillName(name);
        fillSurname(surname);
        fillAddress(address);
        selectMetroStation(metro);
        fillPhone(phone);
        clickFurtherButton();
        return this;
    }

    private void clickOrderButton(String buttonLocation) {
        HomePageAScooter homePage = new HomePageAScooter(driver);
        homePage.clickOrderButton(buttonLocation);
    }

    // форма "про аренду"

    public OrderPageAScooter selectDate(int daysToAdd) {
        driver.findElement(INPUT_FIELD_DATE).click();

        DateFormat dateFormat = new SimpleDateFormat("d");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysToAdd);
        String targetDay = dateFormat.format(cal.getTime());

        String targetDayXpath = "//div[text()='" + targetDay + "']";

        if (driver.findElements(By.xpath(targetDayXpath)).isEmpty()) {
            driver.findElement(NEXT_MONTH_BUTTON).click();
        }

        driver.findElement(By.xpath(targetDayXpath)).click();
        return this;
    }

    public OrderPageAScooter selectRentalPeriod(String option) {
        driver.findElement(RENTAL_PERIOD).click();
        WebElement optionElement = driver.findElement(
                By.xpath("//div[@class='Dropdown-menu']//div[contains(text(), '" + option + "')]")
        );
        optionElement.click();
        return this;
    }

    public OrderPageAScooter selectColor(boolean black, boolean grey) {
        if (black) {
            driver.findElement(CHECKBOX_BLACK).click();
        }
        if (grey) {
            driver.findElement(CHECKBOX_GREY).click();
        }
        return this;
    }

    public OrderPageAScooter addComment(String comment) {
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(INPUT_FIELD_COMMENT).sendKeys(comment);
        }
        return this;
    }

    public OrderPageAScooter clickOrderButton() {
        driver.findElement(BUTTON_ORDER).click();
        return this;
    }

    public OrderPageAScooter clickYesButton() {
        driver.findElement(BUTTON_YES).click();
        return this;
    }

    public boolean isOrderConfirmed() {
        return driver.findElement(ORDER_CONFIRMATION).isDisplayed();
    }

    public OrderPageAScooter fillOrderDetails(int daysToAdd, String option,
                                              boolean black, boolean grey, String comment) {
        selectDate(daysToAdd);
        selectRentalPeriod(option);
        selectColor(black, grey);
        addComment(comment);
        clickOrderButton();
        clickYesButton();
        return this;
    }
}

