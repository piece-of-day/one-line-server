# Builder
FROM openjdk:17-slim-buster AS builder

WORKDIR /usr/src/app
COPY build/libs/one-line-server-0.0.1-SNAPSHOT.jar app.jar

# Runner
FROM openjdk:17-slim-buster AS runner
WORKDIR /usr/bin/app
COPY --from=builder /usr/src/app/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

