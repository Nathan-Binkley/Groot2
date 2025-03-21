FROM eclipse-temurin:17

WORKDIR /app

# Install required packages and download specific Gradle version
RUN apt-get update && \
    apt-get install -y wget unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    wget https://services.gradle.org/distributions/gradle-8.5-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-8.5-bin.zip && \
    ln -s /opt/gradle/gradle-8.5/bin/gradle /usr/bin/gradle && \
    rm /tmp/gradle-8.5-bin.zip

# Create Gradle home directory and set permissions
RUN mkdir -p /root/.gradle/init.d && \
    chmod -R 777 /root/.gradle

# Create initialization script with explicit repository URLs
RUN echo 'allprojects {\n  repositories {\n    maven { url "https://repo.maven.apache.org/maven2/" }\n    maven { url "https://plugins.gradle.org/m2/" }\n  }\n}' > /root/.gradle/init.d/repositories.gradle

# Create repository directory structure
RUN mkdir -p /repositories/maven-public

# Copy build configuration first (for better caching)
COPY build.gradle settings.gradle ./

# Add a runtime settings file to ensure proper repository configuration
RUN echo "rootProject.name = 'restaurant-service'" > settings.gradle
RUN echo "buildscript { repositories { maven { url 'https://repo.maven.apache.org/maven2/' } } }" > build.gradle.tmp && \
    cat build.gradle >> build.gradle.tmp && \
    mv build.gradle.tmp build.gradle

# Add PostgreSQL dependency to build.gradle properly
RUN grep -q "dependencies {" build.gradle && \
    sed -i '/dependencies {/a \    implementation "org.postgresql:postgresql:42.6.0"' build.gradle || \
    echo "dependencies { implementation 'org.postgresql:postgresql:42.6.0' }" >> build.gradle

# Copy the source code
COPY src ./src

# Ensure the resources directory exists
RUN mkdir -p src/main/resources

# Build the application (specifically using bootJar)
RUN gradle bootJar --no-daemon --info

# Set the JAR file as the entrypoint
EXPOSE 8080

# You can keep these as fallback, or remove them if you prefer to use only application.properties
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-cveq043qf0us7386ga8g-a.ohio-postgres.render.com:5432/consi
# ENV SPRING_DATASOURCE_USERNAME=consi_user
# ENV SPRING_DATASOURCE_PASSWORD=vL7kNtpc5B3RhlRDJrFieVjCugWlEfD0
# ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
# ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
# ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

CMD ["java", "-jar", "build/libs/restaurant-service-0.0.1-SNAPSHOT.jar"] 