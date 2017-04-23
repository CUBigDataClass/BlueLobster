sudo yum -y update
sudo yum -y install java
sudo vi /etc/yum.repos.d/datastax.repo
sudo yum -y install dsc30
sudo yum install cassandra30-tools
sudo service cassandra start
sudo systemctl status cassandra
sudo systemctl enable cassandra
sudo nodetool status                     
