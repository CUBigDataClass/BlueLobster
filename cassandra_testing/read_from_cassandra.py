from cassandra.cluster import Cluster

cluster = Cluster()
session = cluster.connect('json_data')
results = session.execute("SELECT * FROM tweets")
for x in results:
    rows = x

    tweets_id = rows[0]
    tweets_contents = rows[1]
    tweets_name = rows[2]

    print 'The twitter id is: ', tweets_id
    print 'The tweet contents are: '
    print tweets_contents
    print 'The twitter user name is: ', tweets_name
