FROM adoptopenjdk/openjdk14:ubi as builder
WORKDIR application
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} application.war
RUN java -Djarmode=layertools -jar application.war extract

FROM adoptopenjdk/openjdk14:ubi
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.WarLauncher"]