Feature: search page with fail

  @gui
  Scenario: failing search
    When the user searches for "T-Systems MMS"
    Then an entry for "SMM smetsyS-T" is shown
