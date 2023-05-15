# Use an official OpenJDK runtime as the base image
FROM openjdk:11-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/spring-microservice-project-0.0.1-SNAPSHOT.jar /app/spring-microservice-project-0.0.1-SNAPSHOT.jar

# Set environment variables for PostgreSQL connection
# ENV POSTGRES_HOST=pgdatabase
# ENV POSTGRES_PORT=5432
# ENV POSTGRES_DB=my_db
# ENV POSTGRES_USER=username
# ENV POSTGRES_PASSWORD=password


# Set environment variables for PostgreSQL connection
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://pgdatabase:5432/my_db
# ENV SPRING_DATASOURCE_USERNAME=username
# ENV SPRING_DATASOURCE_PASSWORD=password

# Expose the port your application listens on
EXPOSE 8081

# Set the command to run your application when the container starts
CMD ["java", "-jar", "spring-microservice-project-0.0.1-SNAPSHOT.jar"]