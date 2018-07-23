### Error:java: 无效的源发行版: 8
错误截图如下：
![](assets/007/20180723-68594b81.png)  
### 解决办法
上网查看了解决办法，如下图：
![](assets/007/20180723-ef6e4b42.png)  
### 原因
我引用了别人的工程，里面的pom定义的是<java.version>1.8</java.version>，我的IDEA启动的时候也用的是jdk1.8，但是当前项目依赖的是jdk1.7

### 参考
网址：https://stackoverflow.com/questions/25878045/errorjava-invalid-source-release-8-in-intellij-what-does-it-mean

网上很多人都遇到这个了：
https://blog.csdn.net/lisongjia123/article/details/56304505
