FROM java:8

MAINTAINER Matthias Balke <mathias.balke@googlemail.com>

VOLUME /tmp

ADD application.properties

ADD harvester.jar app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 9090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
