FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY /target/t1-task-service-0.1.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]