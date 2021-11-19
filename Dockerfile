# syntax=docker/dockerfile:1
FROM gradle:7.2.0-jdk17-alpine
COPY . /app/
WORKDIR /app
RUN gradle bootJar

FROM debian:11-slim
RUN apt-get update && apt-get install -y openjdk-17-jre-headless
EXPOSE 5000
COPY --from=0 /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
