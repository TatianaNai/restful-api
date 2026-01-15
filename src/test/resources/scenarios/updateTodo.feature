Feature: Update todos

  Scenario: Update existing todo
    Given I prepare todo with correct fields
    And I send request to add todo to the app
    And I prepare todo to replace existing todo
    When I send request to update todo in the app
    Then I receive response with status code 200
    And I check if todo is updated
