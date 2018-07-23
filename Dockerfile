FROM ubuntu:18.04
LABEL maintainer="didier.breedt@gmail.com"
RUN apt-get update
RUN apt-get install -y openjdk-8-jdk
RUN apt-get install -y maven
ADD . /code
WORKDIR /code
RUN mvn package
ENTRYPOINT ["mvn", "spring-boot:run"]
