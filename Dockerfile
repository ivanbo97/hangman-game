FROM adoptopenjdk/openjdk14:jdk-14.0.2_12-alpine-slim as builder
ENV hangman_db_pass=ivanbproxiad
WORKDIR application
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} application.war
RUN java -Djarmode=layertools -jar application.war extract

FROM adoptopenjdk/openjdk14:jdk-14.0.2_12-alpine-slim
ENV hangman_db_pass=ivanbproxiad
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.WarLauncher"]