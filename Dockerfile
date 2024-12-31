FROM amazoncorretto:17 AS build
WORKDIR /app
COPY . .
# Gradle Wrapper에 실행 권한 부여
RUN chmod +x ./gradlew

RUN ./gradlew build

# FROM이 2개면 기존거 지우고 이 이미지로 새롭게 시작
FROM amazoncorretto:17 AS runtime
WORKDIR /app
# --from=이름 -> 이전 이미지에서 생성된 파일을 COPY가능
COPY --from=build /app/build/libs/*.jar /app/server.jar
CMD ["java", "-jar", "/app/server.jar"]

# db랑 연결하면서 docker에 문제가 생김 나중에 해결해야함 2024.12.30 HSJ