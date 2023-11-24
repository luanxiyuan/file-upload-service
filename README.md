#### 1. Configurations
We are able to setup application port, image file path, file max size, log path in file src/resources/application.properties
#### 2. How to package application
The application will be packaged to jar file, it's generated in path: target/file-upload-service-0.0.1-SNAPSHOT.jar
> mvn clean package
#### 3. How to run the jar
Locally, run below command in the project root path
> java -jar .\target\file-upload-service-0.0.1-SNAPSHOT.jar

In the server side, run
> setsid java -jar .\target\file-upload-service-0.0.1-SNAPSHOT.jar &