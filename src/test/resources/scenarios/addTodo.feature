Feature: Add todos

  Scenario: Add new todo
    Given I prepare new todo with correct parameters
    When I send request to add new todo to the app
    Then I receive response with status code 201

  Scenario: Try to add new todo without parameter id
    Given I prepare new todo without parameter id
    When I send request to add new todo to the app
    Then I receive response with status code 400

  Scenario: Try to add new todo without parameter text
    Given I prepare new todo without parameter text
    When I send request to add new todo to the app
    Then I receive response with status code 400

  Scenario: Try to add new todo without parameter completed
    Given I prepare new todo without parameter completed
    When I send request to add new todo to the app
    Then I receive response with status code 400
    