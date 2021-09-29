package test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTest extends BaseClass {

    private static AndroidDriver<AndroidElement> driver;
    TouchAction touchActions = new TouchAction(driver);
    TouchAction touchActions1 = new TouchAction(driver);
    TouchAction touchActions2 = new TouchAction(driver);
    WebDriverWait wait = new WebDriverWait(driver, 5);

    private final By fabButton = MobileBy.id("fab_expand_menu_button");
    private final By fabNote = MobileBy.id("fab_note");
    private final By detailTitle = MobileBy.id("detail_title");
    private final By detailContent = MobileBy.id("detail_content");
    private final By backOrMenu = MobileBy.AccessibilityId("drawer open");
    private final By categoryTitle = MobileBy.id("category_title");
    private final By save = MobileBy.id("save");
    private final By delete = MobileBy.id("delete");
    private final By card = MobileBy.id("card_layout");
    private final By cardTitle = MobileBy.id("note_title");
    private final By menuCategory = MobileBy.id("menu_category");
    private final By addCategoryOrConfirm = MobileBy.id("md_buttonDefaultPositive");
    private final By drawerTagList = MobileBy.id("drawer_tag_list");
    private final By drawerCategoryItem = MobileBy.className("android.widget.LinearLayout");
    private final By moreOptions = MobileBy.xpath("//android.widget.ImageView[@content-desc='More options']");
    private final By archiveButton = MobileBy.xpath("//android.widget.TextView[@text='Archive']");

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        //use emulator if app you are using emulator, else use real if you are using real device
        //use true, if app is installed on device, else use false if you want to install it
        driver = capabilities("real", false);
    }

    @Test
    public void basicTest() {
        addNote("abc", "def");

        Assert.assertEquals("Title of added note is correct", "abc", driver.findElements(cardTitle).get(0).getText());

        addCategoryToCard(0, "testCategory");

        wait.until(ExpectedConditions.visibilityOf(driver.findElements(card).get(0)));

        archiveNote();

        driver.findElement(backOrMenu).click();

        deleteCategory();

        driver.findElement(archiveButton).click();

        Assert.assertEquals("Title of archived note is correct", "abc", driver.findElements(cardTitle).get(0).getText());

    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    private void deleteCategory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(archiveButton));

        touchActions1.longPress(LongPressOptions.longPressOptions()
                                                .withElement(ElementOption.element(driver.findElement(drawerTagList).findElements(drawerCategoryItem).get(0)))
                                                .withDuration(Duration.ofSeconds(2))).perform().release();

        driver.findElement(delete).click();
        driver.findElement(addCategoryOrConfirm).click();
    }

    private void archiveNote() {
        touchActions2.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(driver.findElements(card).get(0)))
                                                .withDuration(Duration.ofSeconds(2))).perform().release();

        driver.findElement(moreOptions).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(archiveButton).click();
    }

    private void addNote(String title, String content) {
        driver.findElement(fabButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(fabNote));

        driver.findElement(fabNote).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle));

        driver.findElement(detailTitle).sendKeys(title);
        driver.findElement(detailContent).sendKeys(content);

        driver.findElement(backOrMenu).click();
    }

    private void addCategoryToCard(int cardNumber, String categoryName) {
        touchActions.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(driver.findElements(card).get(cardNumber)))
                                               .withDuration(Duration.ofSeconds(2))).perform().release();

        driver.findElement(menuCategory).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addCategoryOrConfirm));

        driver.findElement(addCategoryOrConfirm).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryTitle));

        driver.findElement(categoryTitle).click();

        //empty title note should be visible
        driver.findElement(save).click();

        driver.findElement(categoryTitle).sendKeys(categoryName);

        driver.findElement(save).click();
    }
}
