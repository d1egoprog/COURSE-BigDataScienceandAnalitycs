#!/bin/bash

#Pre Stage Installation 
#Descargar e Instalar 
yum -y update
yum -y upgrade
yum clean all
yum -y install nmap net-tools yum-utils wget rsync
yum -y install https://centos7.iuscommunity.org/ius-release.rpm
yum -y install python36u python36u-pip python36u-devel
yum -y groupinstall development
yum clean all
#Probar la versión de JAVA
java -version
#Buscar el paquete en http://www.scala-lang.org/
#Instalar Scala
cd /opt/
wget https://downloads.lightbend.com/scala/2.12.4/scala-2.12.4.tgz
tar -zxf scala-2.12.4.tgz
chown -R root:root scala-2.12.4/
ln -s scala-2.12.4 scala
#Buscar el paquete http://spark.apache.org/downloads.html
wget http://www-us.apache.org/dist/spark/spark-2.3.2/spark-2.3.2-bin-hadoop2.7.tgz
tar -zxf spark-2.3.2-bin-hadoop2.7.tgz
chown -R root:root spark-2.3.2-bin-hadoop2.7/
vi /etc/profile
export SCALA_HOME=/opt/scala
export PATH=$PATH:$SCALA_HOME/bin
export SPARK_HOME=/opt/spark-2.3.2-bin-hadoop2.7/
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin
# Reiniciar para que tome los cambios
reboot
#Probar la Vrsión del paquete
scala -version
#Iniciar el Cluster
cd $SPARK_HOME/sbin
./start-master.sh
#Entrar en el navegador por localhost:8080
#Arrancar el esclavo o workers
./start-slave.sh <master-spark-URL>
#Probar la disponibilidad del cluster
jps
#Correr El primer Ejemplo
# –class: Clase que aranca su aplicación
# –master: IP o URL de su cluster
# –executor-memory: Cantidad de Memoria para el trabajo
# –total-executor-cores: Numero de CPU's disponibles para el trabajo
spark-submit --class org.apache.spark.examples.SparkPi --master spark://192.168.2.135:7077 --executor-memory 1G --total-executor-cores 1 /opt/spark-2.3.2-bin-hadoop2.7/examples/jars/spark-examples_2.11-2.3.2.jar 10

#Acceso a la consola de Spark
spark-shell spark://192.168.2.135:7077
