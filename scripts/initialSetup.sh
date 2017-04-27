#this script conifgures instances to what we want (wutomated this since we had to restart so many time)
sudo yum install git 
sudo yum install vim
#install epel and pip
sudo python easy_install pip
#cassandra driver
sudo pip install cassandra-driver

sudo yum update
