FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/app/src
WORKDIR /home/app/src
RUN mvn clean package

FROM tomcat:8.5-jdk11-openjdk
MAINTAINER Karl Dahlgren <karl.dahlgren@castlemock.com>

# Delete the default applications
RUN rm -rf /usr/local/tomcat/webapps/ROOT
RUN rm -rf /usr/local/tomcat/webapps/docs
RUN rm -rf /usr/local/tomcat/webapps/examples
RUN rm -rf /usr/local/tomcat/webapps/manager
RUN rm -rf /usr/local/tomcat/webapps/host-manager

# Change directory to Tomcat webapps folder and copy the war there
COPY --from=build /home/app/src/deploy/deploy-tomcat/deploy-tomcat-war/target/castlemock.war /usr/local/tomcat/webapps/

# Expose HTTP port 8080
EXPOSE 8080