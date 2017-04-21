from cassandra.cluster import Cluster

cluster = Cluster()
session = cluster.connect('json_data')
session.execute(
        """
        INSERT INTO tweets (tweets_id, tweets_name, tweets_contents)
        VALUES (%s, %s, %s)
        """, 
        (0, 'ewright362', 'just inserting into cassandra #BigDataLyfe')
        )


