sudo yum install wget
sudo yum install java-1.7.0-openjdk
sudo yum install java-1.7.0-openjdk-devel
#install Zookeper
cd /opt
sudo wget http://apache-mirror.rbc.ru/pub/apache/zookeeper/zookeeper-3.4.6/zookeeper-3.4.6.tar.gz
sudo tar -zxf zookeeper-3.4.6.tar.gz
cd zookeeper-3.4.6
sudo mkdir data
#add config options for zookeeper manually
sudo vi conf/zoo.cfg
bin/zkServer.sh start
#download Storm
cd /opt
sudo wget https://archive.apache.org/dist/incubator/storm/apache-storm-0.9.2-incubating/apache-storm-0.9.2-incubating.tar.gz
sudo tar -xvf apache-storm-0.9.2-incubating.tar.gz
sudo mv apache-storm-0.9.2-incubating /usr/local
cd /usr/local/apache-storm-0.9.2-incubating
#add config options for storm manually
sudo mkdir data
sudo vi conf/storm.yaml
bin/storm nimbus
bin/storm supervisor
bin/storm ui
