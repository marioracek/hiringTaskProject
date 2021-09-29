package test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseClass {
    public static AndroidDriver<AndroidElement> capabilities(String device, boolean isInstalled) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (device.equals("real"))
            capabilities.setCapability("deviceName", "Android device");
        else if (device.equals("emulator"))
            capabilities.setCapability("deviceName", "emulator");

        if (isInstalled) {
            capabilities.setCapability("appPackage", "it.feio.android.omninotes.alpha");
            capabilities.setCapability("appActivity", "it.feio.android.omninotes.MainActivity");
        } else {
            File app = new File("src", "OmniNotes-alphaDebug-6.1.0 Beta 3.apk");
            capabilities.setCapability("app", app.getAbsolutePath());
        }

        return new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
}
