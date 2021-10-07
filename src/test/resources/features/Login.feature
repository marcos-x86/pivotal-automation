Feature: Login

  Scenario: Login with valid credentials
    Given I go to login page
    When I enter the username "marcos.x86@outlook.com"
    And I click next button
    And I enter the password ""
    And I click sign in button
    Then I verify that the Pivotal Tracker Logo is displayed
    And I verify that Create Project button is displayed
