### 问题背景
最近无意间需要用到eclipse中调试一个页面，然后新增一个tomcat到STS中，此时就发现每次tomcat都重启两次，报了如下错误：

```java
Cannot assign requested address: JVM_Bind
```

### 解决过程

* 查看tomcat下部署的所有应用，并且把所有的webapps下所有的应用都删除了，未解决。
* 新解压一个从官网下载的tomcat压缩包，启动发现也报错。

### 最终的结果

* 看到网上有人说是本地的HOSTS文件配置了多余的IP地址，结果打开自己电脑上的一看，果然有多余的ip，不知道那个软件加上去的，删除之后，启动STS中的tomcat就没有任何的问题了。