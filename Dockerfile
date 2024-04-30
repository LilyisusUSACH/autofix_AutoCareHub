FROM openjdk:17
ARG JAR_FILE=build/libs/AutoCareHub-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} AutoCareHub-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/AutoCareHub-0.0.1-SNAPSHOT.jar"]
