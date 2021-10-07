package com.automation.testng.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPivotalUI {

    @Test
    public void loginWithCredentials() {
        String username = "marcos.x86@outlook.com";
        String password = "";
        String loginUrl = "https://www.pivotaltracker.com/signin";

        WebDriverManager.firefoxdriver().setup();

        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(loginUrl);

        WebElement usernameInput = driver.findElement(By.id("credentials_username"));
        usernameInput.sendKeys(username);

        WebElement nextButton = driver.findElement(By.className("app_signin_action_button"));
        nextButton.click();

        WebElement passwordInput = driver.findElement(By.id("credentials_password"));
        passwordInput.sendKeys(password);

        WebElement signInButton = driver.findElement(By.className("app_signin_action_button"));
        signInButton.click();

        WebElement pivotalTrackerLogo = driver.findElement(By.className("tc_header_text_logo"));
        Assert.assertTrue(pivotalTrackerLogo.isDisplayed());

        WebElement createProjectButton = driver.findElement(By.id("create-project-button"));
        Assert.assertTrue(createProjectButton.isDisplayed());
    }
}
