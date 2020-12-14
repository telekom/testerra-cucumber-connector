# Cucumber Connector

This module provides you the opportunity to use Cucumber and Gherkin to specify `.feature` files and combine it with the advantages
of Testerra, like reporting, WebDriver management, GuiElement and other. The module will register automatically by
using `ModuleHook`.

---

## Releases

* Latest Release: `1.0-RC-1`

## Requirements

* Testerra in Version `1.0-RC-10`

## Usage

Include the following dependency in your project.

Gradle:

````groovy
implementation 'eu.tsystems.mms.tic.testerra:cucumber-connector:1.0-RC-1'
````

Maven:

````xml

<dependency>
  <groupId>eu.tsystems.mms.tic.testerra</groupId>
  <artifactId>cucumber-connector</artifactId>
  <version>1.0-RC-1</version>
</dependency>
````

## Setup

The Cucumber connector provide some simple utilities for extending Cucumber with Testerra by using their defaults for TestNG
enhancement. You have to set up a test class and importing the Testerra Cucumber connector report plugin,
called `TesterraReportPlugin` like the following code example shows.

Please take note, that the `AbstractTestNGCucumberTests` must be extended.

````java

@Listeners(TesterraListener.class)
@CucumberOptions(
        plugin = {"eu.tsystems.mms.tic.testerra.plugins.cucumber.TesterraReportPlugin"}
        , features = "src/test/resources/features/"
        , glue = {"steps", "eu.tsystems.mms.tic.testerra.plugins.cucumber"}
)
public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {

}
````

You can then write down your `.feature` files and store them into the `src/test/resources/features/` directory, and the associated
glue code in `src/test/java/steps` for example.

## Features

### Fails tag

Similar to the `@Fails` annotation from Testerra an `@Fails` tag can be added to Scenarios that have known bugs. This will mark them
as expected failed in the report and add a message if the scenario is successful again. This doesn't allow adding a description of
the bug or a link to the ticked id though. To do this you can add the Testerra `@Fails` annotation to a test step and fill it with
the info. As a side effect any test which uses this step and fails on it will be shown in the report as expected failed
automatically.

To use `@Fails` from Testerra on steps definitions the step definitions are required to be in a package which has
`eu.tsystems.mms.tic` as prefix.

## Demo

Feel free to try out our little project 'Google Search' from the Testerra Cucumber connector repository on Github. It is a complete
Cucumber project and demonstrates the integration.

You will find the showcase in the folder `src/test/java`.

## Known Issues

- All features are group as one class in the Testerra report.
- Screenshots of failed tests will be taken after Cucumber @after (method,steps). Current implementation will most likely take two
  screenshots.
- Annotations from Testerra (e.g. @Fails) cannot be used on a test method level. It can be used on methods within the test though.

---

## Publication

### ... to a Maven repo

```sh
gradle publishToMavenLocal
```

or pass then properties via. CLI

```sh
gradle publish -DdeployUrl=<repo-url> -DdeployUsername=<repo-user> -DdeployPassword=<repo-password>
```

Set a custom version

```shell script
gradle publish -DmoduleVersion=<version>
```

### ... to Bintray

Upload and publish this module to Bintray:

````sh
gradle bintrayUpload -DmoduleVersion=<version> -DBINTRAY_USER=<bintray-user> -DBINTRAY_API_KEY=<bintray-api-key>
```` 
