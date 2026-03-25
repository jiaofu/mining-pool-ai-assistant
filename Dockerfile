FROM eclipse-temurin:21-jdk-alpine

ARG JAR_FILE=mining-pool-ai-assistant-controller-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY mining-pool-ai-assistant-controller/target/${JAR_FILE} /app/ai-assistant.jar

ENTRYPOINT ["java"]

CMD ["-server", "-Duser.timezone=GMT", "-Xms516M", "-Xmx1024M", "-XX:MetaspaceSize=512M", "-jar", "/app/ai-assistant.jar"]