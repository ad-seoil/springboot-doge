FROM amazoncorretto:17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build


# FROM이 2개면 기존거 지우고 이 이미지로 새롭게 시작
FROM amazoncorretto:17 AS runtime
WORKDIR /app
# --from=이름 -> 이전 이미지에서 생성된 파일을 COPY가능
COPY --from=build /app/build/libs/*.jar /app/server.jar
CMD ["java", "-jar", "/app/server.jar"]

# 뭔가 되고 있음 해결한듯 2024.12.24 HSJ