FROM adoptopenjdk/openjdk:21-jdk
ARG EXTRACTED=build/libs
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

