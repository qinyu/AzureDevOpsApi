# 参考

https://www.baeldung.com/spring-boot-app-as-a-service

# 服务器

Vultr：108.61.174.136

# 服务器配置

```sh
# 拷贝 jar
scp ./build/libs/news-0.0.1-SNAPSHOT.jar root@108.61.174.136:/etc/news/

# 登录服务器
ssh root@108.61.174.136

# 加上执行权限
# sudo chown news:news /etc/news/news-0.0.1-SNAPSHOT.jar
# sudo chmod 500 /etc/news/news-0.0.1-SNAPSHOT.jar

# 链接成服务
# ln -s /etc/news/news-0.0.1-SNAPSHOT.jar /etc/init.d/news

# 启动服务
service news start
# 停止服务
service news stop
# 查看 Spring 日志
cat /var/log/news.log
```

# 验证结果

http://108.61.174.136:8080/news/list?id=1  
http://108.61.174.136:8080/news/type
