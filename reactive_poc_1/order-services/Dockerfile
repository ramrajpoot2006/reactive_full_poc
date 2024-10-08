FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /var/app
COPY ["pom.xml","/var/app"]
COPY ["src","/var/app/src"]
RUN mvn install

FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
WORKDIR /var/app
COPY --from=builder /var/app/target/*.jar /var/app/app.jar
ENTRYPOINT [ \
    "sh", \
    "-c", \
    "java -Dsun.net.inetaddr.ttl=60 -Djava.security.egd=file:/dev/./urandom \
    -XX:+IgnoreUnrecognizedVMOptions \
    -XX:+PerfDisableSharedMem \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:+ExitOnOutOfMemoryError \
    -XX:+UseG1GC \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:MaxRAMFraction=2 \
    -XX:+UseStringDeduplication \
    -DETG_ENV=${ENVIRONMENT} \
    -Dspring.profiles.active=${SPRING_BOOT_PROFILE} \
    ${JVM_DEBUG_PORT:+-agentlib:jdwp=transport=dt_socket,address=$JVM_DEBUG_PORT,server=y,suspend=n} \
    -jar /var/app/app.jar"]//