Feature: Update todos

  Scenario: Update existing todo
    Given I prepare new todo with correct parameters
    And I send request to add new todo to the app
    And I prepare todo to update existing todo
    When I send request to update new todo in the app
    Then I receive response with status code 200
    And I check if todo is updated
