## 报错信息：
```java
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:2.3.2:compile (default-compile) on project wms: Compilation failure
[ERROR] Failure executing javac, but could not parse the error:
[ERROR] 错误: 读取C:\Users\Administrator\.m2\repository\net\sf\jasperreports\jasperreports\4.7.1\jasperreports-4.7.1.jar时出错; error in opening zip file
........................................................................
```
## 解决方案：
删除仓库中该报错包(此项目中是jasperreports-4.7.1.jar),重新下载即可解决。

补充：若是maven没有权限导致下载的jar不完整，需要检查并修改配置文件后重新下载或者直接手动下载完整包放在指定目录下。

## 问题分析
1.如果出现“ error in opening zip file”这样的错误，多半是这个包下载得有问题；仓库中虽然有jar文件，但是用解压软件是会报错的，建议删除重试。

2.另外maven有时会有这种情况也会报上面的错误：仓库设置了用户名密码，但是maven没有配置，正常情况maven应该提示没有权限，但有时maven不提示而直接下载jar，下载到一半却发现没有权限，这时会导致下载的jar不完整。这种情况及时重新下载仍有问题，需要修改maven配置文件，或者干脆手动下载。

## 附言
本人在做测试的时候也遇到来这个问题，就直接把报错的几个对应的maven仓库的文件删除掉，然后重新执行mvn命令就可以了。
参考链接：
https://www.cnblogs.com/sylvia-liu/p/3819836.html
