package test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseClass {
    public static AndroidDriver<AndroidElement> capabilities() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("deviceName", "Samsung A40");
        capabilities.setCapability("platformName", "Android");

        //use this when app is installed on device
        capabilities.setCapability("appPackage", "it.feio.android.omninotes.alpha");
        capabilities.setCapability("appActivity", "it.feio.android.omninotes.MainActivity");

        return new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
}
