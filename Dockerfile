FROM openjdk:8
ADD ./app-suite/app-a/target/app-a-0.1.jar /usr/bin/app-a.jar
ENTRYPOINT ["java", "-jar", "/usr/bin/app-a.jar"]
