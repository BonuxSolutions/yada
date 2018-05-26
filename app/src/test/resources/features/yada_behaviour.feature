Feature: YADA behaviour

  Scenario: Create todo item
    Given user "admin"
    When try to create todo item
    Then great success

  Scenario: Fail to create todo item with non-admin user
    Given user "user"
    When try to create todo item
    Then fail with 403