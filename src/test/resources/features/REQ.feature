@All
Feature: Requirement

    @test
    Scenario: 1 Successful request
      Given I have a "successful" json:
       | Field | Request1 |
      When I make a post call
      Then The response status should be 200
      Then Check response:
      | Field | Value |
      | test  | test  |
