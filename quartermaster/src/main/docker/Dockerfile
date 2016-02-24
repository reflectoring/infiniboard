FROM java:8

MAINTAINER Matthias Balke <mathias.balke@gmail.com>

VOLUME /tmp

ADD quartermaster.jar app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.data.mongodb.host=mongo","-jar","/app.jar"]
