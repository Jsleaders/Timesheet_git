FROM ubuntu:latest
RUN apt-get update 
RUN apt-get -y upgrade
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-client openjdk-8-jdk mysql-server apache2
ADD target/Timesheet-Jsleaders-2.0.0-RELEASE.war Timesheet-Jsleaders.war
EXPOSE 8090
COPY ./script.sh /
COPY ./src/main/resources/application.properties /
RUN chmod +x /script.sh
RUN chmod 777 /application.properties
ENTRYPOINT ["/bin/bash", "/script.sh"]