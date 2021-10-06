package com.automation.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class PivotalAPIStepDefinitions {

    private RequestSpecification request;
    private Response response;
    private String id;

    @When("I create a new request with the following headers")
    public void iCreateANewRequestWithTheFollowingHeaders(Map<String, String> headers) {
        request = RestAssured.given()
                .headers(headers)
                .when();
    }

    @And("I set the following request body")
    public void iSetTheFollowingRequestBody(String body) {
        request.body(body);
    }

    @And("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String endpoint) {
        response = request.post(endpoint);
    }

    @And("I send a PUT request to {string}")
    public void iSendAPUTRequestTo(String endpoint) {
        response = request.put(endpoint);
    }

    @Then("I verify that the response status code is {string}")
    public void iVerifyThatTheResponseStatusCodeIs(String expectedStatusCode) {
        String actualStatusCode = String.valueOf(response.getStatusCode());
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        id = response.jsonPath().getString("id");
    }

    @And("I verify that the response body should match the following Json Schema")
    public void iVerifyThatTheResponseBodyShouldMatchTheFollowingJsonSchema(String schemaPath) {
        File schemaFile = new File(schemaPath);
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }

    @And("I verify that the response field {string} should match the value {string}")
    public void iVerifyThatTheResponseFieldShouldMatchTheValue(String jsonPath, String expectedValue) {
        String actualValue = response.jsonPath().getString(jsonPath);
        Assert.assertEquals(actualValue, expectedValue);
    }

    @After(value = "@deleteProjectPostCondition")
    public void deleteProject() {
        String baseURL = "https://www.pivotaltracker.com/services/v5/projects/" + id;
        String token = "";

        Response response = RestAssured.given()
                .header("X-TrackerToken", token)
                .header("Content-Type", "application/json")
                .when()
                .delete(baseURL);

        int expectedStatusCode = 204;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }
}
