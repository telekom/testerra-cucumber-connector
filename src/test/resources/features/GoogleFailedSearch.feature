Feature: search page with fail

  @automaticScreenshot
  Scenario: failing search
    When the user searches for "T-Systems MMS"
    Then an entry for "SMM smetsyS-T" is shown
