scripts for automated deployment and launch of AWS instances

- initial setup: run first on new instances to download commonly used packages 
- CassandraSetup: run to download and setup cassandra. Note: .yaml will need to be configured manually
- CassandraRestart: restart the node and remove all log files, use when addding node to cluster
- StormZookeeperSetupConfig: download and setup storm and zookeeper
- launch scripts must be run in same file as the secure .pem file. Make sure to change permissions to .pem file: chmod 400
