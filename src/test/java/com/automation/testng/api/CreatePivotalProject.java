package com.automation.testng.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CreatePivotalProject {

    String id;

    @BeforeMethod
    public void setupEnvironment() {

    }

    @Test
    public void createProjectTest() {
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

        File schemaFile = new File("src/test/resources/schemas/createProjectResponseSchema.txt");
        response.then().assertThat().body(matchesJsonSchema(schemaFile));

        String expectedProjectName = projectName;
        String actualProjectName = response.jsonPath().getString("name");
        Assert.assertEquals(actualProjectName, expectedProjectName);
    }

    @AfterMethod
    public void cleanEnvironment() {
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
