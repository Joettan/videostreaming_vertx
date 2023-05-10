# 使用官方的 Maven 镜像作为构建环境
FROM maven:3.8.2-openjdk-17-slim AS build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和 src 文件夹到工作目录
COPY pom.xml .
COPY src ./src

# 执行 Maven 构建
RUN mvn -f pom.xml clean package

# 使用官方的 OpenJDK 镜像作为运行环境
FROM openjdk:17-slim

# 将构建产物从构建环境复制到运行环境
COPY --from=build /app/target/video-1.0.0-SNAPSHOT-fat.jar /vertx-app.jar

# 暴露 8888 端口
EXPOSE 8888

# 设置启动命令
CMD ["java", "-jar", "/vertx-app.jar"]
