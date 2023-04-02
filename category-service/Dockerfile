FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/category-service-1.0.jar CategoryService.jar
ENTRYPOINT ["java","-jar","CategoryService.jar"]
