### 解决Linux中Ubuntu16.04声音一直静音的方法
#### 第一种办法
1、在/etc/profile文件中的最后一行加入如下代码(这样就能保证下次重启之后依然生效了)，不要问我为什么，因为我也不知道，能解决问题就行。
```shell
pulseaudio --start --log-target=syslog
```
2、然后执行如下命令：
```shell
source /etc/profile
```
#### 第二种办法
1、打开终端输入以下代码，安装需要的软件
```shell
sudo apt install pavucontrol
```
2、安装完后，继续在终端输入软件名称打开软件
```shell
pavucontrol
```
配置界面如下：

![](assets/007/20180810-25ff00e7.png)  
#### 第三种办法
1、安装alsa-utils工具
```shell
apt-get install alsa-utils
```
2、在终端查看是否有声卡
```shell
aplay -l
```
如下图：

![](assets/007/20180810-69207a45.png)  

3、上述如果有输出的话，继续下一步动作，如果没有输出的话，请参考https://blog.csdn.net/lina_acm/article/details/51626258中 的步骤。
执行如下命令重新配置下
```shell
dpkg-reconfigure alsa-source
```
将会有一个蓝色窗口询问你是否安装。
选择“是” 
接着会询问你安装的声卡型号。
如果你清楚你要安装的型号就选你要装的，不然就选“all”全选。
如果没有出错的话就是成功了。

#### 问题解决过程
本人也是在寻找过程中上述三个步骤都执行了，但是发现，我的场景下，只有第一种办法适合我，所以我把最有可能的方法放在第一位上，避免走弯路。也有可能我第一种方法生效的前提，可能是依赖了另外两种。

#### 参考链接

https://blog.csdn.net/qq_20081893/article/details/80482308

https://blog.csdn.net/zhuiqiuk/article/details/74542498

https://blog.csdn.net/lina_acm/article/details/51626258
