![image-20211122231808549](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211122231808549.png)

https://www.bilibili.com/video/BV13Q4y1C7hS?p=30&spm_id_from=pageDriver

准备三台服务器

安装docker 参照  docker.md

安装k8s

## 安装kubeadm

![image-20211124221922612](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211124221922612.png)

```xshell
# 设置主机名
hostnamectl set-hostname node2_k8s
临时禁用
setenforce 0
永久禁用
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

关闭swap
swapoff -a
sed -ri 's/.*swap.*/#&/' /etc/fstab

允许桥接
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF

cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables=1
net.bridge.bridge-nf-call-iptables=1
EOF
sudo sysctl --system
```

![image-20211124223209040](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20211124223209040.png)

安装kubelet、kubeadm 、kubectl

```shell
cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseUrl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enable=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
exclude=kubelet kubeadm kubectl
EOF

sudo yum install -y kubelet-1.20.9 kubeadm-1.20.9 kubectl-1.20.9 --disableexcludes=kubernetes

sudo systemctl enable --now kubelet

```

下载各个机器需要的镜像

```
sudo tee ./images.sh <<-'EOF'
#!/bin/bash
images=(
kube-apiserver:v1.20.9
kube-proxy:v1.20.9
kube-controller-manager:v1.20.9
kube-scheduler:v1.20.9
coredns:1.7.0
etcd:3.4.13-0
pause:3.2
)
for imageName in ${images[@]} ; do
docker pull registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images/$imageName 
done
EOF

chmod +x ./images.sh && ./images.sh
```

```
关联主节点
# 所有机器添加master域名映射，以下需要修改为自己的
echo "172.31.0.3  endpointss" >> /etc/hosts

# 主节点初始化
kubeadm init \
--apiserver-advertise-address=172.31.0.3 \
--control-plane-endpoint=endpointss \
--image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images \
--kubernetes-version v1.20.9 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=192.168.0.0/16

# 所有网络范围不重叠

```

// TODO