FROM openjdk:17
ADD target/pb-test.jar pb-test.jar
ENTRYPOINT ["java", "-jar", "pb-test.jar"]
EXPOSE 8080