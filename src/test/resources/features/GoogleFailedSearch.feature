Feature: search page with fail

  Scenario: failing search
    Given an entry for "test" is shown
    When the user searches for "T-Systems MMS"
    Then an entry for "SMM smetsyS-T" is shown
