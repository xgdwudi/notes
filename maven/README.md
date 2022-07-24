<center style="font-size:36px">maven</center>

------

[详细笔记网址](http://heavy_code_industry.gitee.io/code_heavy_industry/pro002-maven/chapter10/)

# 1、安装、配置

[官网下载](https://maven.apache.org/download.cgi)

解压到非中文路径下，修改conf 文件夹下的setting.xml文件，

setting.xml

配置远程仓库

```xml
   <mirror>
        <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
    </mirror>

```

配置本地仓库

```xml
<localRepository>F:/maven/repository-newOA1</localRepository>
```

配置限制java版本

```xml
<profiles>
  <profile>
    <id>jdk-1.8</id>
    <activation>
      <activeByDefault>true</activeByDefault>
      <jdk>1.8</jdk>
    </activation>
    <properties>
      <maven.compiler.source>1.8</maven.compiler.source>
      <maven.compiler.target>1.8</maven.compiler.target>
      <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
    </properties>
  </profile>
  </profiles>
```

配置环境变量

![image-20220708102132605](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708102132605.png)

------



# 2、概念

![image-20220708102300386](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708102300386.png)

1. Maven中的坐标
   1.  向量说明
      使用三个T向量」在Maven的仓库」中唯一的定位到一个jar小包，
      - groupld：公司或组织的id
      - artifactld：一个项目或者是项目中的一个横块的id
      - version：版本号
   2. 三个向量的取值方式
      - groupld：公司或组织城名的倒序，通常也会加上项目名称o例如：com，atgulgu，maven
      - artifactld：横炔的名称，将来作为Maven工程的工程名
      - version：横块的版本号，根据自己的需要设定
        - 例如：SNAPSHOT表示快照版本，正在迭代过程中，不稳定的版本
        - 例如：RELEASE表示正式版本![image-20220708102814828](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708102814828.png)
   3. 坐标和仓库中jar包的存储路径之间的对应关系![image-20220708102843566](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708102843566.png)

![image-20220708102907457](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708102907457.png)

------



# 3、使用

## 1、创建maven的java工程

- 使用命令生成maven工程
  - mvn archetype:generate![image-20220708103435186](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708103435186.png)![image-20220708103504706](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708103504706.png)

pom文件解读

```xml
<!-- 根标签，project ；表示对当前工程进行配置、管理-->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!--modelVersion 标签；从maven2 开始就固定是4.0.0 -->
<!-- 代表当前pom。xml所采用的标签结构-->
  <modelVersion>4.0.0</modelVersion>
<!-- 坐标信息-->
    <!--gruopid标签：坐标向量之一；代表公司或者组织开发的11某个项目 -->
  <groupId>com.wudidemiao.maven</groupId>
    <!--artfactId 标签：坐标向量之一；代表项目下的某一个模块 -->
  <artifactId>pr01-maven-java</artifactId>
    <!--version 标签；坐标向量之一；代表当前模块的版本 -->
  <version>1.0-SNAPSHOT</version>
    <!-- 当前模块打包的方式，默认为jar包，取值为war 为war工程 ，pom，说明这个工程用来管理其他工程的工程，一般为父工程-->
  <packaging>jar</packaging>

  <name>pr01-maven-java</name>
  <url>http://maven.apache.org</url>
<!-- 在maven中定义属性值-->
  <properties>
      <!-- 在构建过程中读取源码时使用的字符集-->
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
<!--配置具体的依赖信息，可以包含多个dependency信息 -->
  <dependencies>
      <!-- 配置具体的依赖信息-->
    <dependency>
        <!-- 坐标信息：导入那个jar包，就配置它的坐标信息即可-->
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
        <!--scope 标签，配置当前依赖的范围 -->
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

### 1、POM文件

①含义

POM:Project Object Model，项目对象横型，和POM类似的是：DOM（Document Object Model），文档对象模型。它们都是模型化思想的具体体现，

②模型化思想

POM表示将工程抽象为一个横型，再用程序中的对象来描述这个横型。这样我们就可以用程序来管理项目了，我们在开发过程中，最基本的做法就是将现实生活中的事物抽象为横型，然后封装横型相关的数据作为一个对象，这样就可以在程序中计算与现实事物相关的数据，

③对应的配置文件

POM理念集中体现在Maven工程根目录下pom，xml这个配置文件中，所以这个pom，xml配置文件就是Maven工程的核心配置文件。其实学习Maven就是学这个文件怎么配置，各个配置有什么用，

### 2、约定的目录结构

![image-20220708111708393](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708111708393.png)

另外还有一个target目录专门存放构建操作输出的结果

![](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708112310007.png)

### 3、maven构建

写点代码

执行maven构建命令

![image-20220708113113895](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708113113895.png)

- 清理操作： mvn clean    (效果删除target文件夹)
- 编译操作
  - 主程序编译： mvn compile 
  - 测试程序编译：mvn test-compile 
  - 主体程序编译结果存放的目录： target/classes
  - 测试程序编译结果存放的目录：target/test-compile
- 测试操作 mvn test  测试报告存放的目录：target/surefire-reports
- 打包操作： mvn package 打包存放目录 target/*.jar
- 安装命令：mvn install  (安装的效果是将本地构建过程中生成的jar包存入Maven本地仓库。这个Jar包在Maven仓库中的路径是根据它的坐标生成的。

![image-20220708120207822](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708120207822.png)

## 2、创建maven的web工程

命令创建工程

```shell
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.4
```

![image-20220708121053491](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708121053491.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.wudidemiao.maven</groupId>
  <artifactId>pro02-maven-web</artifactId>
  <version>1.0-SNAPSHOT</version>
    <!--打包方式-->
  <packaging>war</packaging>

  <name>pro02-maven-web Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```



[查询jar包依赖](https://mvnrepository.com/)

也可以配置对自己打包为jar，并安装到maven本地仓库的包，也可以在其他工程的pom中去使用

![image-20220708122235240](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708122235240.png)

- maven 查看依赖列表  mvn dependency:list

![image-20220708122652985](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708122652985.png)

- maven 查看依赖列表 以树形的结构展示  mvn dependency:tree

![image-20220708122813703](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708122813703.png)

- 测试依赖的范围 

  1. 标签的范围

  标签的位置：dependencies/dependency/scopes

  标签的可选值：compile/test/provided/system/runtime/import

  

  ①compile和test对比

  ![image-20220708123232959](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708123232959.png)

  ​	

  ②compile和provided对比

![image-20220708123412429](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708123412429.png)

![image-20220708124239626](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708124239626.png)

## 3、依赖的传递性


①**概念**

A依赖B，B依赖C，那么在A没有配置对C的依赖的情况下，A里面能不能直接使用C？

②传递的原则

在A依赖B，B依赖C的前提下，C是否能够传递到A，取决于B依赖C时便用的依赖范围。

- B依赖C时使用compile范国：可以传递
- B依赖C时使用test或provided范围：不能传递，所以需要这样的jar包时，就必须在需要的地方明确配置依赖才可以。
  

测试一下：

![image-20220708125131267](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708125131267.png)

- 排除一些别的依赖

  ```xml
  <dependencies>
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <!--配置依赖的排除-->  
          <exclusions>
              <!--配置具体排除信息，让junit 不要传递到当前工程-->
              <exclusion>
                  <groupId>junit</groupId>
       			<artifactId>junit</artifactId>
              </exclusion>
          </exclusions>
      </dependency>
    </dependencies>
  ```

## 4、继承、聚合的本质

Maven.工程之间，A工程继承B工程

- B工程：父工程
- A工程：子工程

本质上是A工程的pom.Xml中的配置继承了B工程中pom，Xml的配置，

在父工程中统一管理项目中的依赖信息，具体来说是管理依赖信息的版本。

<img src="https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708130714218.png" alt="image-20220708130714218" style="zoom:150%;" />

![image-20220708130818925](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708130818925.png)



创建三个模块工程，先创建一个父工程

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.wudidemiao.maven</groupId>
  <artifactId>pro03-maven-parent</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <name>pro03-maven-parent Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
      <!--创建我们自定义的属性标签-->
      <!--标签名：属性名-->
      <!--标签名：属性值-->
      <wudidemiao.version>3.8.1</wudidemiao.version>
  </properties>
    <!--在夫工程中管理依赖信息-->
    <!--注意，即使在夫工程配置了对依赖的管理，子工程需要使用具体哪一个依赖还是要明确配置的，对于在夫版本已经管理的jar包，子工程在申明时可以不写版本号，如果写的话，已自己的优先选择，但规范的写法，是要夫工程去做版本统一的管理-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                 <groupId>junit</groupId>
      			 <artifactId>junit</artifactId>
                <!--通过引用属性表达式设定版本版本号，这样版本号就成为了动态值。通过属性解析后才知道是什么值-->
      			 <version>${wudidemiao.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>



<modules>  <module>pro04-maven-module</module>
    <module>pro05-maven-module</module>
  </modules>
</project>

```



子工程pom

```xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
    <!--parent标签给当前工程配置夫工程-->
  <parent>
      <!--通过指定夫工程的坐标找到夫工程-->
    <groupId>com.wudidemiao.maven</groupId>
    <artifactId>pro03-maven-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
    <!--子工程的groupId 如果和夫工程一样，则可以省略-->
   <!--<groupId>com.wudidemiao.maven</groupId>-->
  <artifactId>pro04-maven-module</artifactId>
      <!--子工程的version 如果和夫工程一样，则可以省略-->
    <!--<version>1.0-SNAPSHOT</version>-->
  <name>pro04-maven-module</name>
  <url>http://maven.apache.org</url>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

![image-20220708133504622](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708133504622.png)



![image-20220708134025735](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220708134025735.png)

```shell
mvn clean install -Dmaven.test.skip=true  跳过测试
```



## 5.maven的生命周期

![image-20220724175440129](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724175440129.png)

![image-20220724175525912](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724175525912.png)

## 5.插件和目标

![image-20220724175844270](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724175844270.png)

## 6、仓库

![image-20220724180147234](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724180147234.png)

### 深入了解pom

![image-20220724182238248](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724182238248.png)

![image-20220724182251876](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724182251876.png)

![image-20220724182353959](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724182353959.png)

![image-20220724182802365](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724182802365.png)

## 7build标签

在实际使用Maven的过程中，我们会发现buld标签有时候有，有时候没，这是怎么回事呢？其实通过有效POM我们能够看到，bud标签的相关配置其实一直都在，只是在我们需要定制构建过程的时候才会通过配置bud标签覆盖默认值或补充配置。这一点我们可以通过打印有效POM来看到。

所以本质上来说：我们配置的build标签都是对超级POM配置的叠加。我们又为什么要在默认配置的甚础上叠加呢？很简单，在默认配置无法满足需求的时候定制构建过程。

### build标签组成

- 定义约定的目录结构

![image-20220724183301753](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183301753.png)

![image-20220724183310413](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183310413.png)

- 备用插件管理

![image-20220724183327576](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183327576.png)

![image-20220724183340834](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183340834.png)

![image-20220724183352245](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183352245.png)

- 生命周期插件
  plugin标签存放的是默认生命周期中实际会用到的插件，这些插件想必大家都不陌生，所以抛开插件本身不谈，我们来看看plugin标签的结构：

![image-20220724183458206](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183458206.png)

![image-20220724183448542](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183448542.png)

![image-20220724183629723](https://raw.githubusercontent.com/xgdwudi/images/master/git-img/image-20220724183629723.png)