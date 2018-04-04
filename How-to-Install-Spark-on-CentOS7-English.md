# A Practical Guidance to Install the Spark Clustering Computing Environment on CentOS 7

## A Hardware Provisioning Sample for the Spark Commodity Cluster

This small Spark commodity cluster consists of a master node and three slave nodes. All nodes have the almost same hardware and software configurations, as presented below:

  * Server: Dell® PowerEdge R730 rack server.

  * OS: CentOS Linux release 7.3.1611 (core).

  * RAM: 64GB.

  * SSD Hard disk: 1TB *without* RAID. (Except that the master node has four 480GB SSD hard disks working in **RAID 1+0** for high-availability)

  * CPU: 40 Intel(R) Xeon(R) CPU E5-2640 v4 @ 2.40GHz.

  * Network: 1Gbps LAN.

## User and Network Configuration

### User Management

For all nodes, a new user needs to be added to the system, aiming specially at running Spark. Note that the same name should be used for different nodes; otherwise, the Spark commodity cluster can't be started.

```
$ sudo useradd dis # a name sample: dis
$ sudo passwd dis
```

The office download website of Spark is: http://spark.apache.org/downloads.html. Note that the Spark version **spark-2.3.0-bin-hadoop2.7** is used in the guidance.

For all nodes, a new folder needs to be created in the home directory of the new user, in order to run the Spark program. Please put the *tgz* file downloaded from the Spark office website in this new folder. 

```
$ mkdir spark-env # a folder sample: spark-env
$ cd spark-env/ 
$ tar -zxvf spark-2.3.0-bin-hadoop2.7.tgz
```

### Network Management

```
Open the network ports for the master node (i.e., 4040、6066、7077、8080).
$ sudo firewall-cmd --zone=public --add-port=4040/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=6066/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=7077/tcp --permanent
$ sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
$ sudo firewall-cmd --reload
$ sudo firewall-cmd --zone=public --list-all

Open the network port for all the slave nodes (i.e., 8081).
$ sudo firewall-cmd --zone=public --add-port=8081/tcp --permanent
$ sudo firewall-cmd --reload
$ sudo firewall-cmd --zone=public --list-all

For all nodes, please close the firewall to avoid the common error "Initial job has not accepted any resources".
$ sudo systemctl stop firewalld.service
$ sudo systemctl disable firewalld.service

For all nodes, please close SELINUX.
$ sudo setenforce 0
$ sudo vi /etc/selinux/config
SELINUX=disabled
```

### Password-free Login Management

For all nodes, the password-free login should be set in advance.

```
Put the binding of the hostname and ip address of all nodes in the file named /etc/hosts.
$ sudo vi /etc/hostname # set the hostname
$ sudo vi /etc/hosts # a binding sample
127.0.0.1 localhost localhost.localdomain localhost4 localhost4.localdomain4
10.20.51.154 dc001 dc001.syhlab
10.20.42.194 dc002 dc002.syhlab
10.20.42.177 dc003 dc003.syhlab
10.20.42.175 dc004 dc004.syhlab

In the master node, set the password-free login.
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ cat ~/.ssh/authorized_keys

For all slave nodes, set the password-free login.
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
$ cat ~/.ssh/id_rsa.pub # copy it added to the file named ~/.ssh/authorized_keys of the master node

Copy the password-free login information to all the slave nodes.
$ chmod 600 ~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc002.syhlab:~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc003.syhlab:~/.ssh/authorized_keys
$ scp ~/.ssh/authorized_keys dis@dc004.syhlab:~/.ssh/authorized_keys

On all nodes, check whether the password-free login can work well (a total of 12 times).
$ ssh dis@dc001.syhlab
$ exit
$ ssh dis@dc002.syhlab
$ exit
$ ssh dis@dc003.syhlab
$ exit
$ ssh dis@dc004.syhlab
$ exit
```

### Set the Configuration File of Spark

```
$ cd ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ cp slaves.template slaves
$ vi slaves # add the names of all slave nodes to this file
dc002.syhlab
dc003.syhlab
dc004.syhlab
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc002.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc003.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
$ scp ~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/slaves dis@dc004.syhlab:~/spark-env/spark-2.3.0-bin-hadoop2.7/conf/
```

## Install the Software Development Environment

For all nodes, four main software development environments (i.e., JAVA, Scala, Python, and R) should be installed.

### Install JAVA

```
Check the default JAVA version of CentOS.
$ java -version
openjdk version "1.8.0_131"
OpenJDK Runtime Environment (build 1.8.0_131-b12)
OpenJDK 64-Bit Server VM (build 25.131-b12, mixed mode)

If openjdk, remove it. (note that you can also use openjdk)
$ sudo yum -y remove java*

Get the JAVA RPM version from Oracle (i.e., jdk-8u131-linux-x64.rpm).
$ sudo yum install wget
$ wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" 
  http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm?

Install the JAVA RPM version.
$ sudo yum localinstall jdk-8u131-linux-x64.rpm

Check whether JAVA can work well.
$ java -version
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)

Check the installation location of JAVA.
$ which java
$ ls -l /usr/bin/java
$ ls -l /etc/alternatives/java
$ ls -l /usr/java/jdk1.8.0_131/jre/bin/java

If there are multiple JAVA versions, configure JAVA.
$ sudo alternatives --config java

Set the system environment variables for JAVA.
$ sudo vi /etc/profile.d/java.sh
export JAVA_HOME=/usr/java/jdk1.8.0_131
export PATH=${JAVA_HOME}/bin:${PATH}

Make all the system environment variables work.
$ . /etc/profile.d/java.sh
$ echo $JAVA_HOME
$ echo $PATH

Check the installation location of JAVA again.
$ which java
/usr/java/jdk1.8.0_131/bin/java
```

### Install Scala 

```
Get the Scala-2.11.11 RPM.
$ wget https://downloads.lightbend.com/scala/2.11.11/scala-2.11.11.rpm

Install Scala.
$ sudo yum localinstall -y scala-2.11.11.rpm

Check whether Scala can work well.
$ scala -version
Scala code runner version 2.11.11 -- Copyright 2002-2017, LAMP/EPFL

Check the installation location of Scala.
$ which scala
$ ls -l /usr/bin/scala
$ ls -l /usr/share/scala/bin/scala

Set the system environment variables for Scala.
$ sudo vi /etc/profile.d/scala.sh
export SCALA_HOME=/usr/share/scala
export PATH=${SCALA_HOME}/bin:${PATH}

Make all the system environment variables work.
$ . /etc/profile.d/scala.sh
$ echo $SCALA_HOME
$ echo $PATH
```

### Install Python

```
Check the default Python version of CentOS.
$ python -V
Python 2.7.5

Check the installation location of Python.
$ which python
$ ls -l /usr/bin/python
$ ls -l /usr/bin/python2
$ ls -l /usr/bin/python2.7

Install Python 3.4.
$ sudo yum install python34
$ sudo yum install python34-setuptools
$ sudo easy_install-3.4 pip
$ sudo yum install python34-devel -y

Install pip3.
$ pip -V
$ sudo yum install python-pip
$ pip -V
$ pip3 -V

Check whether Python3.4 can work well.
$ python3 --version
Python 3.4.5
```

### Install R

```
Install and update EPEL.
$ sudo yum install epel-release
$ sudo yum update

Install R.
$ sudo yum install R -y

Check whether R can work well.
$ R --version
R version 3.4.0 (2017-04-21) -- "You Stupid Darkness"
Copyright (C) 2017 The R Foundation for Statistical Computing
Platform: x86_64-redhat-linux-gnu (64-bit)
```

## Start the Spark Commodity Cluster

### Manually Start the Spark Commodity Cluster

```
Start the master node.
$ cd ~/spark-env/spark-2.3.0-bin-hadoop2.7
$ ./sbin/start-master.sh
starting org.apache.spark.deploy.master.Master, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.master.Master-1-dc001.syhlab.out

Open the WEB UI of the master node in the personal computer (e.g., http://MASTER-IP:8080). Note that the personal computer should be the same LAN with the Spark commodity cluster.

Stop the Spark commodity cluster.
$ ./sbin/stop-all.sh
```

### Automatically Start the Spark Commodity Cluster

```
Open all the nodes.
$ ./sbin/start-all.sh
starting org.apache.spark.deploy.master.Master, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.master.Master-1-dc001.syhlab.out
dc004.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc004.syhlab.out
dc002.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc002.syhlab.out
dc003.syhlab: starting org.apache.spark.deploy.worker.Worker, logging to /home/dis/spark-env/spark-2.3.0-bin-hadoop2.7/logs/spark-dis-org.apache.spark.deploy.worker.Worker-1-dc003.syhlab.out

Run Spark Shell.
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

Stop the Spark commodity cluster.
$ ./sbin/stop-all.sh
```

## Install sbt and breeze

*sbt* is an interactive build tool for Scala. *breeze* a numerical processing library for Scala.

```
$ curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
$ sudo yum install sbt
$ cd | mkdir sbtProj # build a folder for sbt 
$ cd sbtProj
$ sbt # maybe wait a long time
$ sbt about
[info] This is sbt 1.0.1
```

Create a file named *build.sbt*, in order to configure the *sbt* project.

```
$ vi build.sbt
libraryDependencies += "org.scalanlp" % "breeze_2.11" % "0.12"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

scalaVersion := "2.11.11"
```

Check whether *breeze* can work well.

```
$ sbt
> console
scala > import breeze.linalg._
```

## Reference

http://spark.apache.org/docs/latest/spark-standalone.html

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

https://www.scala-lang.org/download/install.html

https://www.python.org/

https://packaging.python.org/guides/installing-using-linux-tools/

https://cran.r-project.org/index.html

https://fedoraproject.org/wiki/EPEL

https://mirrors.ustc.edu.cn/epel/7/x86_64/r/

http://www.scala-sbt.org/index.html

https://github.com/scalanlp/breeze
