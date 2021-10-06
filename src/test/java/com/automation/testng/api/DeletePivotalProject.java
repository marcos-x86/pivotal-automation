package com.automation.testng.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

public class DeletePivotalProject {

    String id;

    @BeforeMethod
    public void setupEnvironment() {
        String baseURL = "https://www.pivotaltracker.com/services/v5/projects";
        String token = "";
        String projectName = "Executioner " + new Date().getTime();
        String requestBody = "{\"name\": \"" + projectName + "\"}";

        Response response = RestAssured.given()
                .header("X-TrackerToken", token)
                .header("Content-Type", "application/json")
                .when()
                .body(requestBody)
                .post(baseURL);

        int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);

        id = response.jsonPath().getString("id");
    }

    @Test
    public void deleteProjectTest() {
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

        String actualBody = response.body().asString();
        Assert.assertTrue(actualBody.isEmpty());
    }
}
