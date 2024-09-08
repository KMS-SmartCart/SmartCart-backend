FROM openjdk:17-alpine
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/smartcart-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod,secret", "-jar", "app.jar"]