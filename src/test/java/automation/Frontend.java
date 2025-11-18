package automation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Frontend {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginAndAddItem() throws InterruptedException {
        driver.get("https://newdevsrv.matgry.net/Login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUserName")));
        username.sendKeys("Testercandidate");

        WebElement password = driver.findElement(By.id("txtPassword"));
        password.sendKeys("Abc_123");

        WebElement loginBtn = driver.findElement(By.id("btnLogin"));
        loginBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='push-menu']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("lnk_Stock"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ItemsRegister"))).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("headerBtnAdd"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("detailsModal")
        ));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("Category-select-modal")));
        Select select = new Select(dropdown);
        select.selectByIndex(3);

        driver.findElement(By.id("ItemCode")).sendKeys("15999");
        driver.findElement(By.id("NameAR")).sendKeys("ايهاب");
        driver.findElement(By.id("Name")).sendKeys("ehab");

        WebElement unitDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("ItemUnit-select-modal")));
        Select selectUnit = new Select(unitDropdown);
        selectUnit.selectByIndex(3);

        driver.findElement(By.id("RedialLimit")).sendKeys("5");
        driver.findElement(By.id("saveDetails")).click();

        WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='swal-modal']")));

        assertTrue(successModal.isDisplayed(), "Success modal is not displayed");
        WebElement modalText = successModal.findElement(By.xpath(".//div[@class='swal-text']"));
        assertEquals(modalText.getText(), "Item Saved", "Success message text is incorrect");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}