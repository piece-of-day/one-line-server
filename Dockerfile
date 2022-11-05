# Builder
FROM openjdk:17-slim-buster AS builder

WORKDIR /usr/src/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN ["./gradlew", "build"]

# Runner
FROM openjdk:17-slim-buster AS runner
WORKDIR /usr/bin/app
COPY --from=builder /usr/src/app/build/libs/one-line-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

