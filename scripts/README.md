scripts for automated deployment and launch of AWS instances

- initial setup: run first on new instances to download commonly used packages <\br>
- CassandraSetup: run to download and setup cassandra. Note: .yaml will need to be configured manually <\br>
- CassandraRestart: restart the node and remove all log files, use when addding node to cluster <\br>
- StormZookeeperSetupConfig: download and setup storm and zookeeper <\br>
- launch scripts must be run in same file as the secure .pem file. Make sure to change permissions to .pem file: chmod 400<\br>
