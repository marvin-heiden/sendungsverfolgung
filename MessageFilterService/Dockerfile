FROM adoptopenjdk/openjdk11:alpine
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]
