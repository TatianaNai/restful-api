Feature: Delete todos

  Scenario: Delete existing todo
    Given I prepare todo with correct fields
    And I send request to add todo to the app
    When I send request to delete todo in the app
    Then I receive response with status code 204
    And I check if the app does not contain deleted todo

  Scenario: Delete not existing todo
    Given I prepare todo with correct fields
    When I send request to delete todo in the app
    Then I receive response with status code 404