<p align="center"><img src="http://castlemock.com/images/fm-logo-small.png"></div></p>

# Castle Mock: Mock RESTful APIs and SOAP web services

[![][travis img]][travis]
[![][codecov img]][codecov]
[![][release img]][release]
[![][license img]][license]
[![][docker stars img]][docker stars]
[![][docker pulls img]][docker pulls]

## About

Castle Mock is a web application that provides the functionality to mock out RESTful APIs and SOAP web services. This functionality allows client-side developers to completely mimic a server side behaviour and shape the responses themselves.

Castle Mock enables you to upload both WSDL or WADL files. The web services defined within the files will be mocked automatically by Castle Mock. Once the mocks for the web services are created, they can be configured to mock the service or forward the request to the original endpoint. The response from the forwarded requests can be recorded automatically and used to create new mocked responses.

Castle Mock is completely free and open source (Apache License). It is built with Java and the application itself is deployed to an Apache Tomcat server.

## Downloads

Non-source downloads such as WAR files can be found on our website: [http://www.castlemock.com/](http://www.castlemock.com/).

## Usage

Castle Mock can be downloaded as Java Web Archive (.war) file and be deployed on a Apache Tomcat server (Apache Tomcat 7.0 or higher). Castle Mock can also be deployed by using Docker. For more information go to the offical Castle Mock Docker page: [https://hub.docker.com/r/castlemock/castlemock/](https://hub.docker.com/r/castlemock/castlemock/).

Upon successful installation and deployment, Castle Mock can be accessed from the web browser:

    http://localhost:8080/castlemock
    
This will prompt you the login screen. When logging for the first time use the following credentials: 

    Username: admin 
    Password: admin 

It is recommended that the administrator profile gets updated with a more secure password. This is accomplish by going to the user page and choosing to update the profile.

Upon successful login, you will be able to create both SOAP and REST projects. SOAP and REST resources can either be created manually or created by importing resource descriptions, such as WSDL and WADL. All created resources can be mocked multiple times. Each resource can also be configured to have different response strategies, such as random and sequence

## Source

Our latest and greatest source of Castle Mock can be found on [GitHub](https://github.com/castlemock/castlemock/).

## Full Documentation

Full documentation will be provided shortly.

## Communication
- Website: [http://www.castlemock.com/](http://www.castlemock.com/)
- Google Group: [CastleMock](http://groups.google.com/d/forum/castlemock)
- Twitter: [@CastleMock](http://twitter.com/CastleMock)
- [GitHub Issues](https://github.com/castlemock/castlemock/issues)
- [Docker](https://hub.docker.com/r/castlemock/castlemock/)

## Bugs and Feedback

For bugs, questions and discussions please use the [GitHub Issues](https://github.com/castlemock/castlemock/issues)

## Continuous integration

Castle Mock's continuous integration environment is publicly available and can be access on the following link: [Travis CI](https://travis-ci.org/castlemock/castlemock)

## News and Website

All information about Castle Mock can be found on our website. Follow us on Twitter: [CastleMock](http://twitter.com/CastleMock).

## License

Castle Mock is **licensed** under the **[Apache License](https://github.com/castlemock/castlemock/blob/master/LICENSE)**. The terms of the license are as follows:

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

[travis]:https://travis-ci.org/castlemock/castlemock
[travis img]:https://travis-ci.org/castlemock/castlemock.svg?branch=develop

[release]:https://github.com/castlemock/castlemock/releases
[release img]:https://img.shields.io/github/release/castlemock/castlemock.svg

[license]:LICENSE
[license img]:https://img.shields.io/badge/license-Apache%202-blue.svg

[codecov]:https://codecov.io/github/castlemock/castlemock
[codecov img]:https://img.shields.io/codecov/c/github/castlemock/castlemock/develop.svg

[docker stars]:https://hub.docker.com/r/castlemock/castlemock/
[docker stars img]:https://img.shields.io/docker/stars/castlemock/castlemock.svg

[docker pulls]:https://hub.docker.com/r/castlemock/castlemock/
[docker pulls img]:https://img.shields.io/docker/pulls/castlemock/castlemock.svg
