FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/resume-service-1.0.jar ResumeService.jar
ENTRYPOINT ["java","-jar","ResumeService.jar"]
