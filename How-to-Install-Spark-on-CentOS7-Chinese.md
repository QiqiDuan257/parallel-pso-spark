# 基于CentOS7操作系统安装Spark集群

## Spark集群硬件配置示例

集群规模：1个主节点（master），3个从节点（slave nodes）。

所有计算节点（无论是主节点还是从节点）都有着基本相同的硬件配置：

  * 服务器型号：Dell® PowerEdge R730机架式。
  
  * 操作系统：CentOS Linux release 7.3.1611 (Core) <<< ```cat /etc/redhat-release```。
  
  * 内存空间：64GB <<< ```free -h```。
  
  * 固态硬盘：1TB <<< ```df -h```（只有主节点进行RAID1+0处理，其他子节点没有进行RAID处理）。
  
  * CPU型号：40  Intel(R) Xeon(R) CPU E5-2640 v4 @ 2.40GHz <<< ```cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c``` + ```lscpu```。

    * CPU个数：2 <<< ```cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l```。

    * 单个CPU物理核数：cpu cores: 10 <<< ```cat /proc/cpuinfo | grep "cores" | uniq```。

    * 单个CPU逻辑核数：siblings: 20 <<< ```cat /proc/cpuinfo | grep "siblings" | uniq```。

    * ```inxi -C``` <<< ```sudo yum install inxi```。
  
  * 网络环境：1Gbps LAN。

## 配置用户与网络环境

### 用户管理

在所有节点，都需要创建专门的用户，用于运行spark集群。特别注意：必须使用相同的用户名，否则会导致spark集群无法正常开启。

```
$ sudo useradd dis # 创建专门的用户，名为dis
$ sudo passwd dis # 配置密码
```

Spark集群程序官方下载地址：http://spark.apache.org/downloads.html

在所有节点，在该用户的主目录下都需要创建专门的文件夹，用于运行Spark集群。并将下载好的Spark集群程序放入此文件夹内（建议使用FTP）。

```
$ mkdir spark-env # 创建专门的文件夹
$ cd spark-env/ 
$ tar -zxvf spark-2.3.0-bin-hadoop2.7.tgz
```

### 网络端口与防火墙管理

```
开通主节点的网络端口：4040（Spark context Web UI）、6066（cluster mode）、7077（master）、8080（WEB UI）。
$ sudo firewall-cmd --zone=public --add-port=4040/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=6066/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=7077/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
$ sudo firewall-cmd --reload
$ sudo firewall-cmd --zone=public --list-all

开通从节点的网络端口：8081（WEB UI）。
$ sudo firewall-cmd --zone=public --add-port=8081/tcp --permanent
$ sudo firewall-cmd --reload
$ sudo firewall-cmd --zone=public --list-all

在所有节点，关闭防火墙，防止```Initial job has not accepted any resources```错误。
$ sudo systemctl stop firewalld.service
$ sudo systemctl disable firewalld.service

在所有节点，关闭SELINUX。
$ sudo setenforce 0
$ sudo vi /etc/selinux/config
SELINUX=disabled
```

### 免密登录配置

在所有节点，都需要进行免密登录设置。

```
将所有节点的主机名与对应IP地址的绑定关系，存入/etc/hosts文件中。
$ sudo vi /etc/hostname # 设置主机名
$ sudo vi /etc/hosts # 主机名与IP地址绑定关系示例
127.0.0.1 localhost localhost.localdomain localhost4 localhost4.localdomain4
10.20.51.154 dc001 dc001.syhlab
10.20.42.194 dc002 dc002.syhlab
10.20.42.177 dc003 dc003.syhlab
10.20.42.175 dc004 dc004.syhlab

主节点免密登录设置。
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ cat ~/.ssh/authorized_keys

从节点免密登录设置。
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
$ cat ~/.ssh/id_rsa.pub # 复制此内容，添加到主节点的文件~/.ssh/authorized_keys中

将主节点免密登录认证复制到每个从节点。
$ chmod 600 ~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc002.syhlab:~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc003.syhlab:~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc004.syhlab:~/.ssh/authorized_keys

在所有节点上，都需要验证免密登录是否成功（共计12次）。
$ ssh dis@dc001.syhlab
$ exit
$ ssh dis@dc002.syhlab
$ exit
$ ssh dis@dc003.syhlab
$ exit
$ ssh dis@dc004.syhlab
$ exit
```

### Spark配置文件设置

```
$ cd ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ cp slaves.template slaves
$ vi slaves # 添加所有从节点的主机名到此文件中
dc002.syhlab
dc003.syhlab
dc004.syhlab
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc002.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc003.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc004.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
```

## 安装软件开发环境

在所有节点，都需要安装以下的软件开发环境：JAVA + Scala + Python + R。

### 安装JAVA开发环境

```
查看CentOS7默认安装的JAVA版本。
$ java -version
openjdk version "1.8.0_131"
OpenJDK Runtime Environment (build 1.8.0_131-b12)
OpenJDK 64-Bit Server VM (build 25.131-b12, mixed mode)

如果是openjdk，删除它。
$ sudo yum -y remove java*

获取Oracle公司提供的JAVA RPM版本：jdk-8u131-linux-x64.rpm。
$ sudo yum install wget
$ wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" 
  http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm?

安装JAVA RPM软件包。
$ sudo yum localinstall jdk-8u131-linux-x64.rpm

验证JAVA是否被成功安装。
$ java -version
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)

查看JAVA实际安装位置。
$ which java
$ ls -l /usr/bin/java
$ ls -l /etc/alternatives/java
$ ls -l /usr/java/jdk1.8.0_131/jre/bin/java

如存在多个JAVA版本，需要进行配置。
$ sudo alternatives --config java

配置JAVA的系统环境变量。
$ sudo vi /etc/profile.d/java.sh
export JAVA_HOME=/usr/java/jdk1.8.0_131
export PATH=${JAVA_HOME}/bin:${PATH}

使得以上的系统环境变量即刻生效，并加以验证。
$ . /etc/profile.d/java.sh
$ echo $JAVA_HOME
$ echo $PATH

查看JAVA实际安装位置。
$ which java
/usr/java/jdk1.8.0_131/bin/java
```

注意：/usr/java/jdk1.8.0_131/bin/java与/usr/java/jdk1.8.0_131/jre/bin/java之间的区别，具体解释见[参考资料](https://unix.stackexchange.com/questions/134985/whats-the-difference-between-java-located-inside-jdk-bin-and-jdk-jre-bin)。

### 安装Scala开发环境

```
获取Scala-2.11.11的RPM安装包。
$ wget https://downloads.lightbend.com/scala/2.11.11/scala-2.11.11.rpm

安装Scala命令。
$ sudo yum localinstall -y scala-2.11.11.rpm

查看安装Scala是否成功。
$ scala -version
Scala code runner version 2.11.11 -- Copyright 2002-2017, LAMP/EPFL

查看Scala实际安装位置。
$ which scala
$ ls -l /usr/bin/scala
$ ls -l /usr/share/scala/bin/scala

配置Scala的系统环境变量。
$ sudo vi /etc/profile.d/scala.sh
export SCALA_HOME=/usr/share/scala
export PATH=${SCALA_HOME}/bin:${PATH}

使得以上的系统环境变量即刻生效，并加以验证。
$ . /etc/profile.d/scala.sh
$ echo $SCALA_HOME
$ echo $PATH
```

### 安装Python开发环境

```
查看CentOS7默认安装的Python版本。
$ python -V
Python 2.7.5

查看Python具体安装位置。
$ which python
$ ls -l /usr/bin/python
$ ls -l /usr/bin/python2
$ ls -l /usr/bin/python2.7

安装Python 3.4版本。
$ sudo yum install python34

安装Python 3.4版本必要的开发工具。
$ sudo yum install python34-setuptools
$ sudo easy_install-3.4 pip
$ sudo yum install python34-devel -y

升级pip版本，可同时维持pip与pip3的方式。
$ pip -V
$ sudo yum install python-pip
$ pip -V
$ pip3 -V

验证Python3.4版本是否安装成功。
$ python3 --version
Python 3.4.5
```

### 安装R开发环境

```
安装EPEL软件库。
$ sudo yum install epel-release

更新EPEL软件库。
$ sudo yum update

安装R。
$ sudo yum install R -y

验证R是否被正确安装。
$ R --version
R version 3.4.0 (2017-04-21) -- "You Stupid Darkness"
Copyright (C) 2017 The R Foundation for Statistical Computing
Platform: x86_64-redhat-linux-gnu (64-bit)
```

## 启动Spark集群

### 手动开启模式

```
开启主节点。
$ cd ~/spark-env/spark-2.3.0-bin-hadoop2.7
$ ./sbin/start-master.sh
starting org.apache.spark.deploy.master.Master, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.master.Master-1-dc001.syhlab.out

打开主节点WEB UI监控Spark集群的运行状况，网址为：MASTER-IP:PORT（示例：http://10.20.51.154:8080/）。
查看开启的网络端口。<<< sudo yum install nmap
$ nmap 127.0.0.1
PORT     STATE SERVICE
8080/tcp open  http-proxy
8081/tcp open  blackice-icecap

关闭Spark集群。
$ ./sbin/stop-all.sh
```

### 脚本运行模式

```
开启所有节点。
$ ./sbin/start-all.sh
starting org.apache.spark.deploy.master.Master, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.master.Master-1-dc001.syhlab.out
dc004.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc004.syhlab.out
dc002.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc002.syhlab.out
dc003.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc003.syhlab.out

运行Spark Shell程序。
$ ./bin/spark-shell --master spark://dc001.syhlab:7077
2018-04-04 14:30:20 WARN  NativeCodeLoader:62 - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel). For SparkR, use setLogLevel(newLevel).
Spark context Web UI available at http://dc001:4040
Spark context available as 'sc' (master = spark://dc001.syhlab:7077, app id = app-20180404143025-0000).
Spark session available as 'spark'.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.3.0
      /_/

Using Scala version 2.11.8 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_131)
Type in expressions to have them evaluated.
Type :help for more information.
scala>

关闭Spark集群。
$ ./sbin/stop-all.sh
```

### 安装sbt开发环境与breeze线性代数包

```
$ curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
$ sudo yum install sbt
$ cd | mkdir sbtProj # 建立sbt项目文件夹
$ cd sbtProj
$ sbt # 可能需要等待较长时间
$ sbt about
[info] This is sbt 1.0.1
```

配置build.sbt，导入breeze数值计算库。

```
$ vi build.sbt
libraryDependencies += "org.scalanlp" % "breeze_2.11" % "0.12"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

scalaVersion := "2.11.11"
```

测试breeze数值计算库是否导入成功。

```
$ sbt
> console
scala > import breeze.linalg._
```

## 参考资料

[Spark官网](http://spark.apache.org/docs/latest/spark-standalone.html)

[Java SE Development Kit 8下载官网](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[Scala官网](https://www.scala-lang.org/download/install.html)

[Python官网](https://www.python.org/)

[pip官网](https://packaging.python.org/guides/installing-using-linux-tools/)

[R官网](https://cran.r-project.org/index.html)

[EPEL官网](https://fedoraproject.org/wiki/EPEL)

[R RPM中科大镜像](https://mirrors.ustc.edu.cn/epel/7/x86_64/r/)

[sbt官网](http://www.scala-sbt.org/index.html)

[breeze数值计算库官网](https://github.com/scalanlp/breeze)
