FROM openjdk:17-alpine
WORKDIR /tmp
COPY target/moment-calc-0.1.0.jar /tmp/moment-calc-0.1.0.jar
EXPOSE 8080/tcp
#CMD["java", "-jar", "moment-calc-0.1.0.jar"]
