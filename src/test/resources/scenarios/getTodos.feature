Feature: get todos

  Scenario: Get all todos
    Given I prepare todos and send request to add todos to the app
    When I send request to get all todos
    Then I receive response with status code 200 and todos with all required fields
    And I delete all todos

  Scenario Outline: Get todos with positive parameter "<paramName>"
    Given I prepare todos and send request to add todos to the app
    When I send request to get todos with positive parameter "<paramName>"
    Then I receive response with status code 200
    And I check amount of todos in response with parameter "<paramName>"
    And I delete all todos
    Examples:
      | paramName |
      | offset    |
      | limit     |

  Scenario Outline: Get todos with negative parameter "<paramName>"
    Given I prepare todos and send request to add todos to the app
    When I send request to get todos with negative parameter "<paramName>"
    Then I receive response with status code 400
    And I delete all todos
    Examples:
      | paramName |
      | offset    |
      | limit     |
