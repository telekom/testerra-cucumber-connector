# Testerrra Cucumber Connector

This module provides you the opportunity to use Cucumber and Gherkin to specify `.feature` files and combine it with the advantages of Testerra, like reporting, WebDriver management, GuiElement and other.
The module will register automatically by using Testerra Hooks.

## Setup

Current release: image:https://img.shields.io/bintray/v/testerra-io/Testerra/eu.tsystems.mms.tic.testerra:cucumber-connector?label=Testerra%Cucumber%20connector[Bintray]

Gradle
````groovy
implementation 'eu.tsystems.mms.tic.testerra:cucumber-connector:1.0'
````

.Maven
````xml
<!-- pom.xml -->
<dependencies>
    <dependency>
        <groupId>eu.tsystems.mms.tic.testerra</groupId>
        <artifactId>cucumber-connector</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
````

## Usage
The Cucumber connector provide some simple utilities for extending Cucumber with Testerra by using their defaults for TestNG enhancement.
You have to set up a test class and importing the Testerra Cucumber connector report plugin, called `TesterraReportPlugin` like the following code example shows.
Please take note, that the `AbstractTestNGCucumberTests` must be extended.

````java
@Listeners(TesterraListener.class)
@CucumberOptions(
        plugin = {"eu.tsystems.mms.tic.testerra.plugins.cucumber.TesterraReportPlugin"}
        , features = "src/test/resources/features/"
        , glue = {"steps","eu.tsystems.mms.tic.testerra.plugins.cucumber"}
)
public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {
}
````

You can then write down your `.feature` files and store them into the `src/test/resources/features/` directory, and the associated glue code in `src/test/java/steps` for example.

#### Known Issues
- All features are group as one class in the Testerra report.
- Annotations from Testerra (e.g. @Fails) cannot be used.