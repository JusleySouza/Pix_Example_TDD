FROM maven:3.8.8-eclipse-temurin-17 as build
WORKDIR /workspace/app
COPY pom.xml mvnw* ./
COPY .mvn .mvn

RUN mvn -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=build /workspace/app/target/pix_example_tdd-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]