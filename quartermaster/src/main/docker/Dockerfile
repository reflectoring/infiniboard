FROM java:8

MAINTAINER Matthias Balke <mathias.balke@googlemail.com>

VOLUME /tmp

ADD application.properties application.properties

ADD quartermaster.war app.war

RUN bash -c 'touch /app.war'

EXPOSE 8080
EXPOSE 8090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.war"]
