# <center>Gradle</center>

## 简介

**Ant：**

2ooo年Apache推出的纯Java编写构建工具，通过xmlCbuild.xml文件管理项目优点：使用灵活，速度快（快于gradle和maven），缺点：At没有强加任何编码约定的项目目录结构，开发人员需编写繁杂XML文件构建指令对开发人员是一个挑战。
**Maven：**

2oo4年Apache组织推出的再次使用xml文件pom.xml管理项目的构建工具。
优点：遵循一套约定大于配置的项目目录结构，使用统一的GAV坐标进行依赖管理，侧重于包管理。缺点：项目构建过程僵化.配置文件编写不够灵活、不方便自定义组件，构建速度慢于gadl©。
**Gradle：**

2o12年Google推出的基于Groovy语言的全新项目构建工具，集合了Ant和Maven各自的优势。
		优点：集Ant脚本的灵活性+Maven约定大于配置的项目目录优势，支持多种远程仓库和插件，侧重于大项目构建。
		缺点：学习成本高、资料少、脚本灵活、版本兼容性差等。

![image-20220706133933302](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706133933302.png)

![image-20220706134031956](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706134031956.png)

## 安装

![image-20220706134228877](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706134228877.png)

- 查看和idea适配的版本，在此处查看 ![image-20220706144156094](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706144156094.png)
- 点击下载[gradle](https://gradle.org/install/)  下载适配版本
- 下载下来解压到指定文件夹，最好不要带中文；
- 配置如下变量
  - gradle的文件夹变量 **GRADLE_HOME**
  - gradle底下bin目录的变量配置进path 
  - gradle仓库的变量   **GRADLE_USER_HOME**

![image-20220706144841520](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706144841520.png)

- 验证 命令行窗口  gradle -v

![image-20220706144926897](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706144926897.png)

## gradle 项目初始化

- 通过spring init 初始化 
- 通过gradle init 初始化
- 通过idea初始化

## gradle 常用命令

![image-20220706150830984](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706150830984.png)

## 修改maven下载源

Gradle自带的Maven源地址是国外的，该Maven源在国内的访问速度是很慢的，除非使用了特别的手段。一般情况下，我们建议使用国内的第三方开放的Maven源或企业内部自建Maven源。

认识init.d文件夹

我们可以在gradle的init.d目录下创建以gradle结尾的文件，gradle文件可以实现在build开始之前执行，所以你可以在这个文件配置一些你想预先加载的操作。

在init.d文件夹创建init.gradle文件

```json
pluginManagement {
	repositories {
        // maven本地仓库
		mavenLocal()
		maven {
            name "Alibaba"; url 'https://maven.aliyun.com/repository/public/'
		}
		maven {
            name "Bstek"; url 'https://nexus.bsdn.org/content/groups/public/'
		}
		mavenCentral()
	}
    buildscript {
        repositories{
          maven  {name "Alibaba"; url 'https://maven.aliyun.com/repository/public' }
          maven  {name "Bstek"; url 'https://nexus.bsdn.org/content/groups/public/' }
          maven { name "M2"; url 'https://plugins.gradle.org/m2/'}
        }
    }  
}
```

![image-20220706173103915](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706173103915.png)

![image-20220706173345725](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706173345725.png)

![image-20220706173826470](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706173826470.png)

![image-20220706174657994](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220706174657994.png)





https://www.bilibili.com/video/BV1yT41137Y7?p=9&vd_source=ff22f7f5aae723b8bc3fe7f772fa1cdc