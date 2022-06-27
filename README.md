# Testerra Cucumber Connector

<p align="center">
    <a href="https://mvnrepository.com/artifact/io.testerra/cucumber-connector" title="MavenCentral"><img src="https://img.shields.io/maven-central/v/io.testerra/cucumber-connector/2?label=Maven%20Central"></a>
    <a href="/../../commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/telekom/testerra-cucumber-connector?style=flat"></a>
    <a href="/../../issues" title="Open Issues"><img src="https://img.shields.io/github/issues/telekom/testerra-cucumber-connector?style=flat"></a>
    <a href="./LICENSE" title="License"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg?style=flat"></a>
</p>

<p align="center">
  <a href="#setup">Setup</a> •
  <a href="#documentation">Documentation</a> •
  <a href="#support-and-feedback">Support</a> •
  <a href="#how-to-contribute">Contribute</a> •
  <a href="#contributors">Contributors</a> •
  <a href="#licensing">Licensing</a>
</p>

## About this module

This module provides additional features for [Testerra Framework](https://github.com/telekom/testerra) for automated tests.

This module provides you the opportunity to use Cucumber and Gherkin to specify `.feature` files and combine it with the advantages
of Testerra, like reporting, WebDriver management, GuiElement and other. The module will register automatically by
using `ModuleHook`.

## Setup

### Requirements

| Cucumber connector | Testerra   |
|--------------------|------------|
| `1.0.0`            | `1.0..1.8` |
| `1.1`              | `>=1.9`    |
| `2.0`              | `>=2.0`    |

![Maven Central](https://img.shields.io/maven-central/v/io.cucumber/cucumber-java/5.6.0?label=Cucumber)

### Usage

Include the following dependency in your project.

Gradle:

```groovy
implementation 'io.testerra:cucumber-connector:2.0'

cucumberVersion = '5.6.0'
implementation 'io.cucumber:cucumber-java:' + cucumberVersion
implementation('io.cucumber:cucumber-testng:' + cucumberVersion) {
  exclude group: 'com.google.guava', module: 'guava'
}
```

Maven:

```xml
<dependency>
  <groupId>io.testerra</groupId>
  <artifactId>cucumber-connector</artifactId>
  <version>2.0</version>
</dependency>
<dependency>
  <groupId>io.cucumber</groupId>
  <artifactId>cucumber-java</artifactId>
  <version>5.6.0</version>
</dependency>
<dependency>
  <groupId>io.cucumber</groupId>
  <artifactId>cucumber-testng</artifactId>
  <version>5.6.0</version>
</dependency>
```

## Documentation

The Cucumber connector provide some simple utilities for extending Cucumber with Testerra by using their defaults for TestNG
enhancement. You have to set up a test class and importing the Testerra Cucumber connector report plugin,
called `TesterraReportPlugin` like the following code example shows.

Please take note, that the `AbstractTestNGCucumberTests` must be extended.

```java
import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;

@Listeners(TesterraListener.class)
@CucumberOptions(
        plugin = {"eu.tsystems.mms.tic.testerra.plugins.cucumber.TesterraReportPlugin"},
        features = "src/test/resources/features/",
        glue = {"package.path.to.step.definitions", "eu.tsystems.mms.tic.testerra.plugins.cucumber"}
)
public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {

}
```

You can then write down your `.feature` files and store them into the `src/test/resources/features/` directory, and the associated
glue code in `src/test/java/steps` for example.

### Features

#### Fails tag

Similar to the `@Fails` annotation from Testerra an `@Fails` tag can be added to Scenarios that have known bugs. This will mark them
as expected failed in the report and add a message if the scenario is successful again. This doesn't allow adding a description of
the bug or a link to the ticked id though. To do this you can add the Testerra `@Fails` annotation to a test step and fill it with
the info. As a side effect any test which uses this step and fails on it will be shown in the report as expected failed
automatically.

To use `@Fails` from Testerra on steps definitions the step definitions are required to be in a package which has
`eu.tsystems.mms.tic` as prefix.

### Demo

Feel free to try out our little project 'Google Search' from the Testerra Cucumber connector repository on Github. It is a complete
Cucumber project and demonstrates the integration.

You will find the showcase in the folder `src/test/java`.

### Known Issues

- All features are group as one class in the Testerra report.
- Screenshots of failed tests will be taken after Cucumber @after (method,steps). Current implementation will most likely take two
  screenshots.
- Annotations from Testerra (e.g. @Fails) cannot be used on a test method level. It can be used on methods within the test though.

---

## Publication

This module is deployed and published to Maven Central. All JAR files are signed via Gradle signing plugin.

The following properties have to be set via command line or ``~/.gradle/gradle.properties``

| Property                      | Description                                         |
| ----------------------------- | --------------------------------------------------- |
| `moduleVersion`               | Version of deployed module, default is `1-SNAPSHOT` |
| `deployUrl`                   | Maven repository URL                                |
| `deployUsername`              | Maven repository username                           |
| `deployPassword`              | Maven repository password                           |
| `signing.keyId`               | GPG private key ID (short form)                     |
| `signing.password`            | GPG private key password                            |
| `signing.secretKeyRingFile`   | Path to GPG private key                             |

If all properties are set, call the following to build, deploy and release this module:
````shell
gradle publish closeAndReleaseRepository
````

## Code of Conduct

This project has adopted the [Contributor Covenant](https://www.contributor-covenant.org/) in version 2.0 as our code of conduct. Please see the details in our [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md). All contributors must abide by the code of conduct.

## Working Language

We decided to apply _English_ as the primary project language.  

Consequently, all content will be made available primarily in English. We also ask all interested people to use English as language to create issues, in their code (comments, documentation etc.) and when you send requests to us. The application itself and all end-user faing content will be made available in other languages as needed.


## Support and Feedback

The following channels are available for discussions, feedback, and support requests:

| Type                     | Channel                                                |
| ------------------------ | ------------------------------------------------------ |
| **Issues**   | <a href="/../../issues/new/choose" title="Issues"><img src="https://img.shields.io/github/issues/telekom/testerra-cucumber-connector?style=flat"></a> |
| **Other Requests**    | <a href="mailto:testerra@t-systems-mms.com" title="Email us"><img src="https://img.shields.io/badge/email-CWA%20team-green?logo=mail.ru&style=flat-square&logoColor=white"></a>   |

## How to Contribute

Contribution and feedback is encouraged and always welcome. For more information about how to contribute, the project structure, as well as additional contribution information, see our [Contribution Guidelines](./CONTRIBUTING.md). By participating in this project, you agree to abide by its [Code of Conduct](./CODE_OF_CONDUCT.md) at all times.

## Contributors

At the same time our commitment to open source means that we are enabling -in fact encouraging- all interested parties to contribute and become part of its developer community.

## Licensing

Copyright (c) 2021 Deutsche Telekom AG.

Licensed under the **Apache License, Version 2.0** (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the [LICENSE](./LICENSE) for the specific language governing permissions and limitations under the License.
