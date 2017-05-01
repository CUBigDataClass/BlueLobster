#this script conifgures instances to what we want (wutomated this since we had to restart so many time)
sudo yum install git 
sudo yum install vim
#install epel and pip
curl -O https://bootstrap.pypa.io/get-pip.py
python get-pip.py --user
pip install awscli --upgrade --user
#cassandra driver
sudo pip install cassandra-driver

sudo yum update
