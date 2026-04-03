package pageobjectmodel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestOrderAScooter extends BaseTest {

    private String buttonLocation;
    private String name;
    private String surname;
    private String address;
    private int metro;
    private String phone;
    private int daysToAdd;
    private String option;
    private boolean checkboxBlack;
    private boolean checkboxGrey;
    private String commentText;

    public TestOrderAScooter(String buttonLocation, String name, String surname,
                             String address, int metro, String phone, int daysToAdd,
                             String option, boolean checkboxBlack, boolean checkboxGrey,
                             String commentText) {
        this.buttonLocation = buttonLocation;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.daysToAdd = daysToAdd;
        this.option = option;
        this.checkboxBlack = checkboxBlack;
        this.checkboxGrey = checkboxGrey;
        this.commentText = commentText;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"header", "Ирина", "Семенова", "Кирочная, 41", 5, "79554443322", 1, "сутки", true, false, "оставить у двери"},
                {"middle", "Марина", "Тимкова", "Садовая, 51", 5, "79554443322", 1, "сутки", false, true, "позвонить за 30 минут"},
        };
    }

    @Test
    public void testOrderWithValidData() {
        // Переход на главную страницу
        HomePageAScooter homePage = new HomePageAScooter(driver);

        // Переход на страницу заказа
        OrderPageAScooter orderPage = homePage.goToOrderPage(buttonLocation);

        // Заполнение формы заказа
        orderPage.fillCustomerForm(buttonLocation, name, surname, address, metro, phone);
        orderPage.fillOrderDetails(daysToAdd, option, checkboxBlack, checkboxGrey, commentText);

        // Проверка успешного оформления
        assertTrue("Окно 'Заказ оформлен' отсутствует", orderPage.isOrderConfirmed());
    }
}

