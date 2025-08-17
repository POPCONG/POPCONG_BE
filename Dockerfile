# amazoncorretto:21-alpine 이미지를 베이스로 사용 / 컨테이너 내부 작업 디렉토리는 app으로 함
FROM amazoncorretto:21-alpine AS builder
WORKDIR /app

# Gradle 캐시 최적화를 위한 복사
COPY gradlew gradle.properties* settings.gradle* build.gradle* /app/
COPY gradle /app/gradle

# Gradle Wrapper 실행 권한 부여 및 버전 확인
RUN chmod +x gradlew && ./gradlew --version

# 실제 소스코드 복사
COPY . /app

# 이미지 빌드
RUN ./gradlew clean bootJar -x test

# Spring Boot layertools로 레이어 추출
RUN java -Djarmode=layertools -jar $(find build/libs -name "*.jar" | head -n 1) extract --destination /app/layers

# Runtime
# 런타임에서도 같은 openjdk 이미지 사용
FROM amazoncorretto:21-alpine

# 일반 사용자로 실행
RUN addgroup -S popcong && adduser -S popcong -G popcong -u 1001

# 런타임 작업 디렉토리 지정
WORKDIR /app

# Spring Boot layertools 사용한 경우 설정하는 부분
# 의존성 레이어
COPY --from=builder /app/layers/dependencies/ ./
# Spring Boot 런처 레이어
COPY --from=builder /app/layers/spring-boot-loader/ ./
# 스냅샷 의존성 레이어
COPY --from=builder /app/layers/snapshot-dependencies/ ./
# 애플리케이션 코드 레이어: 가장 자주 바뀌는 부분
COPY --from=builder /app/layers/application/ ./

# 한국 환경 기본 환경변수 설정
ENV TZ=Asia/Seoul \
    LANG=ko_KR.UTF-8 \
    LANGUAGE=ko_KR:ko:en \
    LC_ALL=ko_KR.UTF-8 \
    SPRING_PROFILES_ACTIVE=prod \
    SERVER_PORT=8080 \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=50 -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Seoul"

# 컨테이너에서 노출할 포트
EXPOSE 8080

# root가 아닌 spring 사용자로 실행
USER popcong

# JarLauncher로 레이어 디렉토리 구조에 맞춰 앱 부팅 (Spring Boot layertools 사용한 경우)
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -cp /app org.springframework.boot.loader.launch.JarLauncher"]