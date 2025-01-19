# OpenJDK 17을 사용하는 이미지 기반
FROM openjdk:17-jdk-slim

# 로컬에서 업로드한 JAR 파일을 컨테이너의 /app 폴더로 복사
COPY scoretracker-0.0.1-SNAPSHOT.jar /app/scoretracker.jar

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 실행
CMD ["java", "-jar", "scoretracker.jar"]
