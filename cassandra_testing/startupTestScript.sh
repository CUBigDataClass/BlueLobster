#this script assumes installation of cassandra and pip, if not installed go to scripts directory in repo and use install scripts
cqlsh
CREATE KEYSPACE storm WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE storm;
CREATE TABLE storm_data ( id int PRIMARY KEY, state_name text, state_sentiment int );
python states_to_cassandra.py
