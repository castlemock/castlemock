# Contributing to Castle Mock

This page provides information about contributing code to the **Castle Mock** codebase.

## Getting started

1. Fork the repository on GitHub
2. Clone the forked repository to your machine
3. Install the development tools. In order to develop **Castle Mock**, you need the following tools:
   * Java Development Kit (JDK) 11 or higher.
   * Maven 3.5.4 or above
    * NPM and Yarn for frontend
   * Any IDE which supports importing Maven projects.

## Building
The build flow for Castle Mock is built around Maven. To build and test Castle Mock you simply need to the run following Maven command:

    mvn clean install
    
Castle Mock can be executed in multiple ways. You can run Castle Mock by simply running any of the JAR applications (JarTomcatApplication or JarJettyApplication), or by deploying the application on an Apache Tomcat server. The recommended approach for deployment is to run Castle Mock by either running JarTomcatApplication or JarJettyApplication. Once either of them are up and running, you can easily start the frontend development server by running the following Yarn command:

    cd web/web-frontend
    yarn start

## Proposing Changes
The **Castle Mock** project source code repositories are hosted at GitHub. All proposed changes are submitted and code reviewed using the GitHub Pull Request process.

## Design Document
The Design Document for **Castle Mock** can be found on our wiki: [Design Document](https://github.com/castlemock/castlemock/wiki/Design-Document)

## Copyright and License

**Castle Mock** is **licensed** under the **[Apache License](https://github.com/castlemock/castlemock/blob/master/LICENSE)**. 
We consider all contributions as Apache License 2.0. We require all pull request submitters to sign the **[Individual Contributor
License Agreement ("Agreement") V2.0](https://www.apache.org/licenses/icla.pdf)** and provide it to us before we permit any pull requests to be merged into the main codebase. Please send the signed icla to the following email address: karl.dahlgren@castlemock.com

## Continuous Integration
**Castle Mock's** continuous integration environment is publicly available and can be access on the following link: [Travis CI](https://travis-ci.org/castlemock/castlemock)
