Feature: Json contract of todos response

  Scenario: Validate Json schema for get all todos response
    Given I prepare todos and send request to add todos to the app
    When I send request to get all todos
    Then I receive response with status code 200
    And I check json contract todoResponseSchema.json
    And I delete all todos
