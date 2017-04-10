FROM openjdk:8-jdk-alpine

MAINTAINER Matthias Balke <mathias.balke@googlemail.com>

VOLUME /tmp

ADD application.properties application.properties

ADD harvester.war app.war

RUN touch /app.war

EXPOSE 9090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.war"]
