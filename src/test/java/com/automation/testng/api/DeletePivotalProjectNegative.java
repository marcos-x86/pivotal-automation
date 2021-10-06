package com.automation.testng.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class DeletePivotalProjectNegative {

    @Test
    public void deleteInvalidProject() {
        String baseURL = "https://www.pivotaltracker.com/services/v5/projects/999991";
        String token = "";

        Response response = RestAssured.given()
                .header("X-TrackerToken", token)
                .header("Content-Type", "application/json")
                .when()
                .delete(baseURL);

        int expectedStatusCode = 404;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);

        File schemaFile = new File("src/test/resources/schemas/deleteInvalidProjectResponseSchema.txt");
        response.then().assertThat().body(matchesJsonSchema(schemaFile));

        String expectedCode = "unfound_resource";
        String actualCode = response.jsonPath().getString("code");
        Assert.assertEquals(actualCode, expectedCode);

        String expectedKind = "error";
        String actualKind = response.jsonPath().getString("kind");
        Assert.assertEquals(actualKind, expectedKind);
    }
}
