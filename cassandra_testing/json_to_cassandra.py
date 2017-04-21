import json
from cassandra.cluster import Cluster

with open('twitter.json') as data_file:
        data = json.load(data_file)

twitter_text = data["text"]
twitter_id = data["id"]
screen_name = data["user"]["screen_name"]

# Doing mod 100 because it isn't an int otherwise
twitter_id = twitter_id % 100

cluster = Cluster()
session = cluster.connect('json_data')
session.execute(
        """
        INSERT INTO tweets (tweets_id, tweets_name, tweets_contents)
        VALUES (%s, %s, %s)
        """,
        (twitter_id, screen_name, twitter_text)
        )
