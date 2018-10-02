#!/bin/bash

#Pre Stage Installation 
#Installing Packages
yum -y update
yum -y upgrade
yum clean all
yum -y install nmap net-tools yum-utils wget rsync
yum -y install https://centos7.iuscommunity.org/ius-release.rpm
yum -y install python36u python36u-pip python36u-devel
yum -y groupinstall development
yum clean all
#Disabling Services and Setup Network Stuff
systemctl disable NetworkManager 
systemctl stop NetworkManager
sudo hostnamectl set-hostname hadoop.ucatolica.edu.co --static
sudo hostnamectl set-hostname hadoop.ucatolica.edu.co --transient
##NO POLICY (DANGER!!!)
sudo systemctl disable firewalld
sudo systemctl stop firewalld
#Firewall Policy
sudo systemctl enable firewalld
sudo systemctl start firewalld
sudo firewall-cmd --permanent --add-port=22/tcp
sudo firewall-cmd --permanent --add-port=80/tcp
sudo firewall-cmd --permanent --add-port=443/tcp
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --permanent --add-port=8443/tcp

#Adding Users
useradd d1egoprog
useradd caobaAdmin
useradd hadoop
passwd d1egoprog
passwd caobaAdmin
passwd hadoop
#Downloaging Packages
cd /opt
wget http://download.oracle.com/otn/java/jdk/7u80-b15/jdk-7u80-linux-x64.tar.gz
http://download.oracle.com/otn/java/jdk/7u80-b15/jdk-7u80-linux-x64.tar.gz
tar -zxf  jdk-8u181-linux-x64.tar.gz
chown -R root:root jdk1.8.0_181/

wget http://www-us.apache.org/dist/hadoop/common/hadoop-3.0.2/hadoop-3.0.2.tar.gz
tar -zxf hadoop-3.0.2.tar.gz 
chown -R root:root hadoop-3.0.2/
ln -s hadoop-3.0.2 hadoop
#Setting Up Variables
vi /etc/profile
export JAVA_HOME=/opt/jdk1.8.0_181
export HADOOP_HOME=/opt/hadoop
export PATH=$JAVA_HOME/bin:$PATH
export HADOOP_COMMON_HOME=$HADOOP_HOME 
export HADOOP_HDFS_HOME=$HADOOP_HOME 
export YARN_HOME=$HADOOP_HOME 
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native 
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin 
export HADOOP_INSTALL=$HADOOP_HOME
export HDFS_NAMENODE_USER="root"
export HDFS_DATANODE_USER="root"
export HDFS_SECONDARYNAMENODE_USER="root"
export YARN_RESOURCEMANAGER_USER="root"
export YARN_NODEMANAGER_USER="root"
#EOF

#SSH Generation
ssh-keygen -t rsa 
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys 
chmod 0600 ~/.ssh/authorized_keys 
#Installing JAVA
http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads1880260.html
alternatives --install /usr/bin/java java usr/local/java/bin/java 2
alternatives --install /usr/bin/javac javac usr/local/java/bin/javac 2
alternatives --install /usr/bin/jar jar usr/local/java/bin/jar 2
alternatives --set java usr/local/java/bin/java
alternatives --set javac usr/local/java/bin/javac
alternatives --set jar usr/local/java/bin/jar
#Installing HADOOP

cd $HADOOP_HOME/etc/hadoop

cd $HADOOP_HOME/etc/hadoop/hadoop-env.sh

vi core-site.xml
<configuration>
   <property>
      <name>fs.hadoop.ucatolica.edu.co</name>
      <value>hdfs://192.168.2.135:9000</value> 
   </property>
</configuration>
#OEF
vi hdfs-site.xml
<configuration>
   <property>
      <name>dfs.replication</name>
      <value>1</value>
   </property>
   <property>
      <name>dfs.name.dir</name>
      <value>file:///home/hadoop/hdfs/namenode </value>
   </property>
   <property>
      <name>dfs.data.dir</name> 
      <value>file:///home/hadoop/hdfs/datanode </value> 
   </property>
</configuration>

#EOF
vi yarn-site.xml

<configuration>
 
   <property>
      <name>yarn.nodemanager.aux-services</name>
      <value>mapreduce_shuffle</value> 
   </property>
  
</configuration>

#EOF
cp mapred-site.xml.template mapred-site.xml 
vi mapred-site.xml 
<configuration>
 
   <property> 
      <name>mapreduce.framework.name</name>
      <value>yarn</value>
   </property>
   
</configuration>
#EOF

#Verifying Hadoop Installation
hdfs namenode -format 
start-dfs.sh 
start-yarn.sh 


#Browser http://<IP>:50070/
#Browser http://<IP>:8088/
#List
$HADOOP_HOME/bin/hadoop fs -ls <args>

#Insert
#Create /home/file.txt and write something
$HADOOP_HOME/bin/hadoop fs -mkdir /user/input 
$HADOOP_HOME/bin/hadoop fs -put /home/file.txt /user/input 
$HADOOP_HOME/bin/hadoop fs -ls /user/input 
#Read From
$HADOOP_HOME/bin/hadoop fs -cat /user/output/outfile 
$HADOOP_HOME/bin/hadoop fs -get /user/output/ /home/hadoop_tp/ 
#HELP!!!!
$HADOOP_HOME/bin/hadoop fs -help

#Stop
stop-dfs.sh 