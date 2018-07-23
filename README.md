# spring-boot-web-service

## Compile in Docker
You can use this method to both compile and run the application inside a  docker container.

This circumvents the need for java or maven system dependencies.

Please note the below examples are for a linux based operating system. You would need to create your own compile and startup scripts for other environments such as Windows.

The below commands assume you are in the root directory of this app.

###### Compile
`./build.sh`

This will run:

`docker build . -t spring-boot-web-service:latest`

This command may take some time to complete, because it has to download all packages. You could possibly avoid this by sharing your local maven repository with the container's.

###### Run
`./run.sh`

This will run:

`docker run -v "$PWD":/code -p 8080:8080 spring-boot-web-service`

## Run without docker
You may also run this application without using Docker, however this will introduce JDK 8 and Maven as system dependencies.

`mvn spring-boot:run`

## H2 Console
You may utilise the H2 built-in console @ http://localhost:8080/console
`jdbc:h2:file:./h2`

## Curl Example
You may run `curl.sh` to test the API calls using curl. Please see the script for details.

## Lombok usage
Please note this project uses https://projectlombok.org. It may interfere with you IDE linting if you don't have the necessary plugins installed.