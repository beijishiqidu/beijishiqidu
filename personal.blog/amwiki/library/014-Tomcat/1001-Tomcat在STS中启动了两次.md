### 问题背景
最近无意间需要用到eclipse中调试一个页面，然后新增一个tomcat到STS中，此时就发现每次tomcat都重启两次，报了如下错误：

```java
Cannot assign requested address: JVM_Bind
```

### 解决过程

* 查看tomcat下部署的所有应用，并且把所有的webapps下所有的应用都删除了，未解决。
* 