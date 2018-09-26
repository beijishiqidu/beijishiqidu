### 如何在主机上查看docker启动的容器的ip地址
```shell
docker inspect --format '{{ .NetworkSettings.IPAddress }}' <container-ID>
```
或者
```shell
docker inspect <container id>
```
或者
```shell
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
```
### 要获取所有容器名称及其IP地址只需一个命令
```shell
docker inspect -f '{{.Name}} - {{.NetworkSettings.IPAddress }}' $(docker ps -aq)
```

### 显示所有容器IP地址
```shell
docker inspect --format='{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -aq)
```
### 举个栗子
![](assets/003/20180808-02c9d173.png)  

![](assets/003/20180808-14ace573.png)  
