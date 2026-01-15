Feature: Add todos

  Scenario: Add todo to the app
    Given I prepare todo with correct fields
    When I send request to add todo to the app
    Then I receive response with status code 201

  Scenario: Try to add todo without field id
    Given I prepare todo without field id
    When I send request to add todo to the app
    Then I receive response with status code 400

  Scenario: Try to add todo without field text
    Given I prepare todo without field text
    When I send request to add todo to the app
    Then I receive response with status code 400

  Scenario: Try to add todo without field completed
    Given I prepare todo without field completed
    When I send request to add todo to the app
    Then I receive response with status code 400
    