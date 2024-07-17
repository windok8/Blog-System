# 基础镜像
FROM openjdk:17-jdk-slim
# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 设置工作目录
WORKDIR /blog
# 拷贝jar包
COPY blog-system-demo-1.0-SNAPSHOT.jar blog.jar
# 暴露端口
EXPOSE 8080
# 入口
ENTRYPOINT ["java", "-jar", "/blog.jar"]