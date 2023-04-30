# syntax=docker/dockerfile:1
FROM gradle:7.2.0-jdk17-alpine
COPY . /app/
WORKDIR /app
RUN gradle bootJar --no-daemon --parallel

FROM eclipse-temurin:17.0.7_7-jre
EXPOSE 5000
COPY --from=0 /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
