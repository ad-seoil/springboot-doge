FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN ./gradlew build
CMD ["java", "-jar", "C:\workspace\doge\build\libs\doge-0.0.1-SNAPSHOT.jar"]