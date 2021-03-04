# Testerrra Cucumber Connector

This module provides you the opportunity to use Cucumber and Gherkin to specify `.feature` files and combine it with the advantages of Testerra, like reporting, WebDriver management, GuiElement and other.
The module will register automatically by using Testerra Hooks.

## Installation

Current release: image:https://img.shields.io/bintray/v/testerra-io/Testerra/eu.tsystems.mms.tic.testerra:cucumber-connector?label=Testerra%Cucumber%20connector[Bintray]

The Testerra Cucumber extension is published to a Bintray repository https://bintray.com/testerra-io.
Include the following dependency in your project.

Gradle:
````groovy
implementation 'eu.tsystems.mms.tic.testerra:cucumber-connector:1.0'
````

Maven:
````xml
<dependency>
    <groupId>eu.tsystems.mms.tic.testerra</groupId>
    <artifactId>cucumber-connector</artifactId>
    <version>1.0</version>
</dependency>
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

### Known Issues
- All features are group as one class in the Testerra report.
- Annotations from Testerra (e.g. @Fails) cannot be used.
- Screenshots of failed tests will be taken after Cucumber @after (method,steps). Current implementation will most 
likely take two screenshots. 
- Anootations from Testerra (e.g. @ExpectedFailed) cannot be used.

## Publication

### ... to a Maven repo

_Publishing to local repo_
```shell
gradle publishToMavenLocal
```

_Publishing to remote repo_
```shell
gradle publish -DdeployUrl=<repo-url> -DdeployUsername=<repo-user> -DdeployPassword=<repo-password>
```

_Set a custom version_
```shell
gradle publish -DmoduleVersion=<version>
```
### ... to GitHub Packages

Some hints for using GitHub Packages as Maven repository

* Deploy URL is https://maven.pkg.github.com/OWNER/REPOSITRY
* As password generate an access token and grant permissions to ``write:packages`` (Settings -> Developer settings -> Personal access token)
