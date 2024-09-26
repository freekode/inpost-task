FROM amazoncorretto:21-alpine
EXPOSE 8080

ARG JAR_PATH='boot/build/libs/inpost-task-0.0.1-SNAPSHOT.jar'
COPY $JAR_PATH /app/app.jar
COPY scripts /scripts
ENTRYPOINT java -jar /app/app.jar
