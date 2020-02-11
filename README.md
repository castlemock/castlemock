<p align="center"><img src="https://castlemock.com/images/fm-logo-small.png"></div></p>

# Castle Mock: Mock RESTful APIs and SOAP web services

<p align="center">
    <a href="https://travis-ci.org/castlemock/castlemock"><img src="https://travis-ci.org/castlemock/castlemock.svg?branch=master"></a>
    <a href="https://snyk.io/test/github/castlemock/castlemock"><img src="https://snyk.io/test/github/castlemock/castlemock/badge.svg"></a>
    <a href="https://codecov.io/github/castlemock/castlemock"><img src="https://img.shields.io/codecov/c/github/castlemock/castlemock/master.svg"></a>
    <a href="https://github.com/castlemock/castlemock/releases"><img src="https://img.shields.io/github/release/castlemock/castlemock.svg"></a>
    <a href="LICENSE"><img src="https://img.shields.io/badge/license-Apache%202-blue.svg"></a>
    <a href="https://hub.docker.com/r/castlemock/castlemock/"><img src="https://img.shields.io/docker/pulls/castlemock/castlemock.svg"></a>
</p>


**Castle Mock** is a web application that provides the functionality to mock out RESTful APIs and SOAP web services. This functionality allows client-side developers to completely mimic a server side behaviour and shape the responses themselves.

Table Of Content
----

- [About](#about)
- [What to Use Castle Mock for and When to Use It](#what-to-use-castle-mock-for-and-when-to-use-it)
- [Download](#download)
- [Installation](#installation)
- [Docker](#docker)
- [Source](#source)
- [Full Documentation](#source)
- [Communication](#communication)
- [Bugs and Feedback](#bugs-and-feedback)
- [Continuous integration](#continuous-integration)
- [News and Website](#news-and-website)
- [License](#license)

## About

**Castle Mock** can create mocked services based on WSDL, WADL, Swagger and RAML definition files. The web-services defined within the files will be mocked automatically by **Castle Mock**. Once the mocks for the web-services are created, they can be configured to mock the service or forward the request to the original endpoint. The response from the forwarded requests can be recorded automatically and used to create new mocked responses.

**Castle Mock** is completely free and open source (Apache License). It is built with Java and the application itself is deployed to an Apache Tomcat server.

## What to Use Castle Mock for and When to Use It

Use **Castle Mock** to mock out RESTful APIs and SOAP web services for testing purposes for when either performing system or integration tests. It is recommended to only use **Castle Mock** on an internal network and never be used publically. **Castle Mock** is not developed or meant for anything else other than for testing purposes. **Castle Mock** is commonly used for:

- Mock RESTful APIs and SOAP web services for system or integration tests.
- Network analysis between systems. 

## Download

Non-source downloads such as WAR files can be found on our website: [https://www.castlemock.com/](https://www.castlemock.com/). The WAR files can also be located and downloaded from [GitHub Releases](https://github.com/castlemock/castlemock/releases).

## Installation

**Castle Mock** can be downloaded as Java Web Archive (.war) file and be deployed on a Apache Tomcat server (Apache Tomcat 7.0 or higher). **Castle Mock** can also be deployed by using Docker. For more information go to the offical **Castle Mock** Docker page: [https://hub.docker.com/r/castlemock/castlemock/](https://hub.docker.com/r/castlemock/castlemock/).

Upon successful installation and deployment, **Castle Mock** can be accessed from the web browser:

    http://localhost:8080/castlemock
    
This will prompt you the login screen. When logging for the first time use the following credentials: 

    Username: admin 
    Password: admin 

It is recommended that the administrator profile gets updated with a more secure password. This is accomplish by going to the user page and choosing to update the profile.

Upon successful login, you will be able to create both SOAP and REST projects. SOAP and REST resources can either be created manually or created by importing resource descriptions, such as WSDL and WADL. All created resources can be mocked multiple times. Each resource can also be configured to have different response strategies, such as random and sequence

## Docker

> Docker is an open-source project that automates the deployment of applications inside software containers, by providing an additional layer of abstraction and automation of operating-system-level virtualization on Linux.

Castle Mock absolutely loves Docker. Docker allows you to simply setup and deploy your own instance of Castle Mock, by just typing one line. Download and install Docker by visiting their web page: https://docker.com

Use our official Docker image to setup and test Castle Mock:
```
docker run -d -p 8080:8080 castlemock/castlemock
```

Castle Mock can be accessed from the following address after the installation is finished:
```
http://{CONTAINER IP}:8080/castlemock
```

For more information and details: https://hub.docker.com/r/castlemock/castlemock

## Source

Our latest and greatest source of **Castle Mock** can be found on [GitHub](https://github.com/castlemock/castlemock/).

## Full Documentation

Full documentation can be found under our [GitHub Wiki](https://github.com/castlemock/castlemock/wiki). 

## Communication
- Website: [https://www.castlemock.com/](https://www.castlemock.com/)
- Twitter: [@CastleMock](http://twitter.com/CastleMock)
- [GitHub Issues](https://github.com/castlemock/castlemock/issues)
- [Docker](https://hub.docker.com/r/castlemock/castlemock/)

## Bugs and Feedback

For bugs, questions and discussions please use the [GitHub Issues](https://github.com/castlemock/castlemock/issues)

## Continuous integration

**Castle Mock's** continuous integration environment is publicly available and can be access on the following link: [Travis CI](https://travis-ci.org/castlemock/castlemock)

## News and Website

All information about **Castle Mock** can be found on our [website](https://www.castlemock.com/). Follow us on Twitter: [CastleMock](http://twitter.com/CastleMock).

## License

**Castle Mock** is **licensed** under the **[Apache License](https://github.com/castlemock/castlemock/blob/master/LICENSE)**. The terms of the license are as follows:

    Apache License

    Copyright 2015 Karl Dahlgren and a number of other contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
