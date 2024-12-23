FROM openjdk:11-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY facadeConfig.json .
ENTRYPOINT ["java","-jar","/app.jar"]