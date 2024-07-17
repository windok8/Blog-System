# Spring Boot 博客系统



## 项目简介



这是一个基于 Spring Boot 的简单博客系统，支持用户注册、登录，以及文章的创建、查看、更新和删除。



## 技术栈

- Java 17
- SPring Boot 3.3.1
- MyBatis
- MySQL
- Docker



## 功能描述

### 用户管理

- 用户注册
- 用户登录
- 获取用户详细信息

### 文章管理

- 创建新的文章
- 获取所有文章列表（可根据文章创建时间进行排序）
- 查看单个文章详情
- 更新文章内容
- 删除文章



## 数据库设计

### 用户表 `users`

```sql

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    profile_picture_url VARCHAR(255),
    bio TEXT
);

```



### 文章表 `article`

```sql

CREATE TABLE article (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    category VARCHAR(50),
    tags VARCHAR(255),
    status ENUM('published', 'draft') DEFAULT 'draft',
    view_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


```



## 项目结构

```css

src
├── main
│   ├── java
│   │   └── com
│   │       └── blog
│   │           ├── config
│   │           │   └── WebConfig.java
│   │           ├── controller
│   │           │   ├── AuthController.java
│   │           │   └── PostController.java
│   │           ├── dto
│   │           │   ├── AuthDto.java
│   │           │   ├── LoginInfo.java
│   │           │   └── PostDto.java
│   │           ├── filter
│   │           │   └── JwtAuthenticationFilter.java
│   │           ├── mapper
│   │           │   ├── ArticleMapper.java
│   │           │   └── UserMapper.java
│   │           ├── model
│   │           │   ├── Article.java
│   │           │   └── User.java
│   │           ├── service
│   │           │   ├── PostService.java
│   │           │   └── UserService.java
│   │           ├── util
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── JwtInfo.java
│   │           │   ├── JwtUtils.java
│   │           │   ├── PasswordUtil.java
│   │           │   ├── Result.java
│   │           │   └── ResultStatusEnum.java
│   │           └── BlogApplication.java
│   └── resources
│       ├── application.yml
│       └── logback-spring.xml


```



# 实现思路

## 用户服务

1. **用户注册**： 注册接口将接收用户的注册信息（用户名、密码、邮箱、姓名等主要信息）

   - 当注册数据提交至后端时，首先对各项数据进行校验，判断是否符合数据注册规则（例如：密码长度、密码组合、邮箱规范等）

   - 其次对用户名和邮箱数据需要进行数据库比对判定是否唯一

     如果用户名在数据库比对中不唯一，则响应引导用户跟换用户名防止重复

     如果邮箱在数据库比对中不唯一，则响应引导用户使用邮箱找回账户密码

   - 最后数据校验通过后，将用户数据存储至数据库中，其中密码需要进行MD5加密存储

2. **用户登录：** 登录接口将接收用户的登录信息（用户名和密码）

   - 根据接收的到的数据进行规则校验，是否符合数据规则（例如：密码长度，用户长度等）
   - 首先根据用户名进入数据库用户表进行检索是否存在，不存在则直接返回错误响应
   - 其次根据接收密码与用户名组成新字符串进行MD5加密与数据库表中的密码进行比对，不匹配则返回密码错误响应
   - 最后用户登录成功，生成一个 JWT 令牌用户会话管理，且设置过期时间，此令牌将发送回用户并在后续请求中使用，且 JWT 中存储 userID 字段 和 用户名信息

3. **用户 JWT 验证：**在每个需要认证的接口中，通过过滤器拦截请求，验证 JWT 的合法性以及时效性，并将用户信息存储到请求中。

4. **获取用户信息：** 当用户请求获取用户信息时，通过过滤器的拦截请求，请求中已经存储了用户ID，只需根据用户ID到数据库中进行检索即可获得用户信息。

### 文章服务

1. **创建新的文章：** 通过过滤器的拦截判断用户处于登录状态后，根据请求中的用户的ID结合提交的文章内容插入至文章表中默认状态为 草稿 状态
2. **获取某用户所有文章列表：**  通过过滤器的拦截判断用户处于登录状态后，根据某用户的ID在文章表中进行检索是否存在，不存在则返回错误信息，存在则继续进行动态SQL查询，如果没有指定排序，则默认查询输出，如果指定排序则按照 文章创建时间 进行升序和降序排序，使用 LIMIT 配合 OFFSET 实现分页查询
3. **获取单个文章详情：** 通过过滤器的拦截判断用户处于登录状态后，根据文章ID在文章表中进行检索是否存在，不存在则响应错误信息，存在则返回文章详情
4. **更新和删除文章：** 通过过滤器的拦截请求，请求中已经存储了用户的ID，根据用户ID和文章ID在数据库文章表中进行检索，判断是否存在，不存在则直接响应错误信息，如果存在则进行后续的更新删除操作。



# 快速开始

## 前置条件

- 安装 JDK 17
- 安装 MySQL8
- 安装 Docker （可选，用于容器化部署）

## 本地运行

1. 克隆项目到本地：

   ```bash
   git clone https://github.com/windok8/Blog-System.git
   cd Blog-System
   ```

2. 配置数据库连接：

   在 `src/main/resources/application.yml` 中配置你的 MySQL 数据库连接信息。

3. 创建数据库表：

   执行项目根目录下的 `blog.sql` 文件，创建数据库表结构。

4. 启动应用：

   ```bash
   ./mvnw spring-boot:run
   ```

## Docker 部署

1. 构建 Docker 镜像：

   ```bash
   docker build -t blog-system .
   ```

2. 启动 Docker 容器：

   ```bash
   docker run -d -p 8080:8080 --name blog-system blog-system
   ```

   