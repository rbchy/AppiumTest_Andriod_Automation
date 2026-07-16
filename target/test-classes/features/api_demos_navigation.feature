Feature: ApiDemos main screen navigation
  As a QA engineer
  I want the ApiDemos app's main screen to load and navigate correctly
  So that I know the core navigation flow still works

  Background:
    Given the ApiDemos app is launched

  Scenario: Main screen loads with all menu items
    Then the main screen should show 12 menu items

  Scenario Outline: Opening a menu item keeps the app in the foreground
    When I open the "<menuItem>" menu item
    Then the app package should still be "io.appium.android.apis"
    When I navigate back
    Then the main screen should show 12 menu items

    Examples:
      | menuItem      |
      | Accessibility |
      | Animation     |
      | Graphics      |
