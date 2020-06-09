# 参考

https://www.baeldung.com/spring-boot-app-as-a-service

# 服务器


# 服务器配置

```sh
# 拷贝 jar
scp ./build/libs/api-0.0.1-SNAPSHOT.jar root@{server ip}:/etc/news/

# 登录服务器
ssh root@{server ip}

# 加上执行权限
# sudo chown news:news /etc/news/api-0.0.1-SNAPSHOT.jar
# sudo chmod 500 /etc/news/api-0.0.1-SNAPSHOT.jar

# 链接成服务
# ln -s /etc/news/api-0.0.1-SNAPSHOT.jar /etc/init.d/monitor

# 启动服务
service monitor start
# 停止服务
service monitor stop
# 查看 Spring 日志
cat /var/log/monitor.log
```

# 验证结果

http://{server ip}:8081/monitor 
http://{server ip}:8081/monitor?minTime=2020-6-9
