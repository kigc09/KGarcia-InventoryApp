# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace

# Copy the POM first so dependencies can be cached
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests
# Give the resulting JAR a predictable name
RUN cp target/*.jar target/application.jar

# Stage 2: Run the completed application
FROM eclipse-temurin:25-jre
WORKDIR /app

# Create a non-root account for the application
RUN groupadd --system spring \
    && useradd --system --gid spring spring \
    && mkdir -p /app/logs \
    && chown -R spring:spring /app
# Copy only the completed JAR from the build stage
COPY --from=build \
     --chown=spring:spring \
     /workspace/target/application.jar \
     application.jar

USER spring
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
