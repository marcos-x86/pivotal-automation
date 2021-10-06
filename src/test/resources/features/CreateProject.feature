Feature: Create Project

  @deleteProjectPostCondition
  Scenario: Create a new Project
    When I create a new request with the following headers
      | X-TrackerToken |                  |
      | Content-Type   | application/json |
    And I set the following request body
    """
    {
      "name": "Executioner 100"
    }
    """
    And I send a POST request to "https://www.pivotaltracker.com/services/v5/projects"
    Then I verify that the response status code is "200"
    And I verify that the response body should match the following Json Schema
    """
    src/test/resources/schemas/createProjectResponseSchema.txt
    """
    And I verify that the response field "name" should match the value "Executioner 100"

  Scenario: Create a new Project without name
    When I create a new request with the following headers
      | X-TrackerToken |                  |
      | Content-Type   | application/json |
    And I set the following request body
    """
    {
      "public": true
    }
    """
    And I send a POST request to "https://www.pivotaltracker.com/services/v5/projects"
    Then I verify that the response status code is "400"
