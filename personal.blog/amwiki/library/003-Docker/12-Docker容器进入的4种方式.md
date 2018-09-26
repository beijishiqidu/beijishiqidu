### Docker容器进入的4种方式
* 使用docker attach
* 使用SSH
* 使用nsenter
* 使用exec

#### 使用docker attach进入Docker容器
弊端：使用该命令有一个问题。当多个窗口同时使用该命令进入该容器时，所有的窗口都会同步显示。如果有一个窗口阻塞了，那么其他窗口也无法再进行操作。因为这个原因，所以docker attach命令不太适合于生产环境，平时自己开发应用时可以使用该命令。

#### 使用SSH进入Docker容器
弊端：需要单独安装ssh服务，这样相比attach方式来说，好处是能保证多人进入。容器且相互之间不受干扰了，相信大家在当前的生产环境中（没有使用Docker的情况）也是这样做的。但是使用了Docker容器之后不建议使用ssh进入到Docker容。

#### 使用nsenter进入Docker容器
使用方法：
* 获取容器的id（用docker ps -a就可以查询到所有容器的信息，包括已经stop的容器）
```java
PID=$(docker inspect --format {{.State.Pid}} <container_name_or_ID>)
```
* 通过下述命令来进入容器
```java
nsenter --target $PID --mount --uts --ipc --net --pid
```

PS：这个命令应该是默认安装好的，如果没有可以参考如下链接进行安装

https://github.com/jpetazzo/nsenter


#### 使用docker exec进入Docker容器
除了上面几种做法之外，docker在1.3.X版本之后还提供了一个新的命令exec用于进入容器，这种方式相对更简单一些，下面我们来看一下该命令的使用：
```java
sudo docker exec -it <container_name_or_ID> /bin/bash
```
