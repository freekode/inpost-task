FROM amazoncorretto:21-alpine
EXPOSE 8080

ARG JAR_PATH='build/libs/inpost-task-0.0.1-SNAPSHOT.jar'
COPY $JAR_PATH /app/app.jar
ENTRYPOINT java -jar /app/app.jar
