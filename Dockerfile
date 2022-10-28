FROM openjdk:11-jdk
ARG JAR_FILE=/build/libs/ssd-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ssd-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ssd-backend-0.0.1-SNAPSHOT.jar"]