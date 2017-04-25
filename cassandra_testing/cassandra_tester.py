import json
from cassandra.cluster import Cluster

with open('twitter.json') as data_file:
        data = json.load(data_file)

twitter = data["text"]
twitter_id = data["id"]
screen_name = data["user"]["screen_name"]

cluster = Cluster()
session = cluster.connect('json_data')
session.execute(
        """
        INSERT INTO tweets (tweets_id, tweets_name, tweets_contents)
        VALUES (%s, %s, %s)
        """,
        (0, 'ewright362', 'just inserting into cassandra #BigDataLyfe')
        )
