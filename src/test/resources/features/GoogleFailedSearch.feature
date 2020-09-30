Feature: search page with fail

  Scenario: basic failing scenario
    When the user searches for "T-Systems MMS"
    Then it fails

  @Fails
  Scenario: failing scenario with fails tag and no fails on step
    Then it fails

  @Fails
  Scenario: expected failing search with Fails Annotation
    When the user searches for "T-Systems MMS"
    Then an entry for "SMM smetsyS-T" is shown

  Scenario: scenario with fails on failing step implementation
    When the user searches for "T-Systems MMS"
    Then an entry for "SMM smetsyS-T" is shown

  @Fails
  Scenario: successful expected failing search
    When the user searches for "T-Systems MMS"
    Then an entry for "T-Systems Multimedia Solutions" is shown
