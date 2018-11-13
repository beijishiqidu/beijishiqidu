# 在Ubuntu系统中安装软件时报如下错误
sudo apt-get install git
E: 无法获得锁 /var/lib/dpkg/lock - open (11: 资源暂时不可用)
E: 无法锁定管理目录(/var/lib/dpkg/)，是否有其他进程正占用它？

# 参考方法如下：
## 第一种（我的就是用这种方式解决的）：
```java
sudo rm /var/cache/apt/archives/lock
sudo rm /var/lib/dpkg/lock
```
## 第二种（有的人反应说没有找到第一个的目录，那么用这种方式应该也是可以的）
```java
sudo rm /var/lib/apt/lists/lock
sudo rm /var/lib/dpkg/lock
```
## 参考链接：
https://www.cnblogs.com/fsong/p/5823826.html
