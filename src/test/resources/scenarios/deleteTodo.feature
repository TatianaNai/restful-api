Feature: Delete todos

  Scenario: Delete existing todo
    Given I prepare new todo with correct parameters
    And I send request to add new todo to the app
    When I send request to delete new todo in the app
    Then I receive response with status code 204
    And I check if the app does not contain deleted todo

  Scenario: Delete not existing todo
    Given I prepare new todo with correct parameters
    When I send request to delete new todo in the app
    Then I receive response with status code 404