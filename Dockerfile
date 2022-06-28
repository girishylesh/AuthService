FROM openjdk:11.0.7-jre-slim
EXPOSE 8080
ADD target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar 
ENTRYPOINT ["java","-jar","/auth-service.jar"]