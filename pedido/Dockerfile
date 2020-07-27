FROM maven:3.5.2-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:8
COPY --from=build /usr/src/app/target/pedido-0.0.1-SNAPSHOT.jar /usr/app/pedido.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/pedido.jar"]
