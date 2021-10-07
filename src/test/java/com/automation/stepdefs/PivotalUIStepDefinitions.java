package com.automation.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class PivotalUIStepDefinitions {

    String loginUrl = "https://www.pivotaltracker.com/signin";
    WebDriver driver;

    @Given("I go to login page")
    public void iGoToLoginPage() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(loginUrl);
    }

    @When("I enter the username {string}")
    public void iEnterTheUsername(String username) {
        WebElement usernameInput = driver.findElement(By.id("credentials_username"));
        usernameInput.sendKeys(username);
    }

    @And("I click next button")
    public void iClickNextButton() {
        WebElement nextButton = driver.findElement(By.className("app_signin_action_button"));
        nextButton.click();
    }

    @And("I enter the password {string}")
    public void iEnterThePassword(String password) {
        WebElement passwordInput = driver.findElement(By.id("credentials_password"));
        passwordInput.sendKeys(password);
    }

    @And("I click sign in button")
    public void iClickSignInButton() {
        WebElement signInButton = driver.findElement(By.className("app_signin_action_button"));
        signInButton.click();
    }

    @Then("I verify that the Pivotal Tracker Logo is displayed")
    public void iVerifyThatThePivotalTrackerLogoIsDisplayed() {
        WebElement pivotalTrackerLogo = driver.findElement(By.className("tc_header_text_logo"));
        Assert.assertTrue(pivotalTrackerLogo.isDisplayed());
    }

    @And("I verify that Create Project button is displayed")
    public void iVerifyThatCreateProjectButtonIsDisplayed() {
        WebElement createProjectButton = driver.findElement(By.id("create-project-button"));
        Assert.assertTrue(createProjectButton.isDisplayed());
    }
}
