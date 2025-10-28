FROM openjdk:21-jdk-slim
#donde esta el ejecutable
ARG JAR_FILE=target/gimnasio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_gimnasio.jar
#puerto por el que voy a acceder al docker
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app_gimnasio.jar"]