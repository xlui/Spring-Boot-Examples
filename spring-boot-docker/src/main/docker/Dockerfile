FROM openjdk:8-jdk-alpine
# 在宿主机的 /var/lib/docker 目录下创建一个临时文件并把它链接到容器的 /tmp 目录
VOLUME /tmp
ADD docker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dserver.port=8080", "/app.jar"]