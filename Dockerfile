FROM eclipse-temurin:17.0.8_7-jre
WORKDIR /app
#COPY target/nameAddress-0.1.0-jar-with-dependencies.jar /tmp/app.jar
COPY target/moment-calc-0.1.0.jar /app/app.jar
EXPOSE 8077
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5004" ,"-jar", "app.jar"]

#-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 взять из remote дебага в идее

#FROM eclipse-temurin:17.0.8_7-jre
#EXPOSE 8080
#WORKDIR /opt/app
#COPY build/libs/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM openjdk:11-jre-slim AS builder