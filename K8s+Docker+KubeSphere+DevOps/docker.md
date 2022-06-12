# 安装docker

```shell
卸载之前的docker
yum remove docker*
安装 yum工具
yum install -y yum-utils
配置docker 安装地址
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
安装docker
yum install -y docker-ce docker-ce-cli containerd.io
安装k8s时使用下面的命令
yum install -y docker-ce-20.10.7 docker-ce-cli-20.10.7 containerd.io-1.4.6
启动
systemctl enable docker --now
查看docker信息
docker info 

sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
"registry-mirrors": ["https://e9l6587k.mirror.aliyuncs.com"],
"exec-opts": ["native.cgroupariver=systemd"],
"log-driver": "json-file",
"log-opts": {
	"max-size": "100m"
 },
"storage-driver": "overlay2"
}
EOF

sudo systemctl daemon-reload
sudo systemctl restart docker

```

找镜像 <a> hub.docker.com</a>

```shell
docker pull nginx  # 下载最新版
镜像名:版本名(标签)
docker pull nginx:1.21.4-perl
下载的镜像都在本地
docker images # 查看所有镜像
docker rmi nginx  删除镜像
启动容器
docker run [options] images [common] [arg...]
docker run 设置项  镜像名  镜像启动的命令，一般会有
docker ps  查看正在运行的程序
docker ps -a  查看所有容器
docker stop 容器名称/容器id
docker restart 容器名称/容器id
docker rm mynginx 删除容器
docker start 容器名称/容器id
docker run -d ...  后台运行
docker run --restart=always ... 机器重启后，docker 自启动容器
docker update 容器名称/容器id  --restart=always  更新容器设置项（不能更新端口映射）



```

进入容器修改内容

```shell
docker exec -it 容器名称/容器id /bin/bash  进入容器

docker commit -a "leifengyang" -m "备注" 容器id 名称:版本号  提交到远程

```

镜像传输

```shell
docker save -o abc.tar 镜像名称:版本号  将镜像保存成压缩包   
docker load -i abc.tar 从压缩包读取镜像 

```

推送到远程仓库

```shell
docker tag local-image:tagname new-repo:tagname
docker push new-repo:tagname  

帮旧镜像的名字 ，改成仓库要求的名字
docker tag guignginx:v1.0 leifejij/guugasud:v1.0

登陆到docker hub 
docker login
docker logout 登出
# 推送
docker push lefsakjfa/guiasgud:v1.0


docker pull asdasdas/asfasd:v1.0
```

挂载数据到本地

```shell
docker run -d --restart==always -v 主机地址:容器里面的地址 ro  ...
```

其他命令

```
docker logs 容器id   查看日志

docker cp 容器名:地址  本机地址  可以反向写  


```

redis使用自定义配置文件启动

```shell
docker run -v /data/redis/redis.conf:/usr/local/etc/redis/redis.conf \
-v /data/redis/data:/data \
-d --name=myredis \
-p 6379:9379
redis:latest redis-server /usr/local/etc/redis/redis.conf





```

redis 密码配置![image-20211122223816422](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211122223816422.png)

将自己应用打包镜像

```
编写dockerFile 文件
from openjdk:1.8  环境
label maintainer=asdasd  作者
COPY target/*.jar /data/jar/app.jar  
ENTRYPOINT["java","-jar","/data/jar/app.jar"]

制作镜像
docker build -t java-demo:v1.0 .    点为此目录
```



