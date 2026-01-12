Feature: Json contract of todos response

  Scenario: Check Json schema by getting todos
    Given I prepare new todos and send request to add new todos to the app
    When I send request to get all todos
    Then I receive response with status code 200
    And I check json contract
    And I delete all todos
