How to get a cassandra server set up running test data: </br>

Install Cassandra Locally </br>

Install CQLSH locally </br>

pip install cassandra-driver

Type command: </br>
<code>
cqlsh
</code>
</br>

Once you are in the cqlsh shell type: </br>
<code>
CREATE KEYSPACE storm WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
</code>
</br>

Start using the new keyspace: </br>
<code>
USE storm;
</code>
</br>
Now we create the table: </br>
<code>
CREATE TABLE storm_data ( 
    id int PRIMARY KEY,   
    state_name text,      
    state_sentiment int
    );
</code>
</br>

Once the table has been created, type exit to get out of the shell. We can now
pipe in some mock-data to emulate storm. Running the following python script to
enter the data: </br>
<code>
python states_to_cassandra.py
</code>
</br>

Now the table is fully formatted and the java file CassandraJava will properly
interface with the local database.

