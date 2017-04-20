sudo yum -y update
sudo yum -y install java
sudo vi /etc/yum.repos.d/datastax.repo
sudo yum -y install dsc20
sudo service cassandra start
sudo systemctl status cassandra
sudo systemctl enable cassandra
sudo nodetool status                       
