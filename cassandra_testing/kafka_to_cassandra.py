import json
from cassandra.cluster import Cluster
from kafka import KafkaConsumer

cluster = Cluster()
session = cluster.connect('json_data')

consumer = KafkaConsumer('twitter-topic')
for message in consumer:
    twitter = json.loads(message.value)
    twitter_text = twitter["text"]
    twitter_id = twitter["id"]
    twitter_id = twitter_id % 100
    screen_name = twitter["user"]["screen_name"]
    session.execute(
            """
            INSERT INTO tweets (tweets_id, tweets_name, tweets_contents)
            VALUES (%s, %s, %s)
            """,
            (twitter_id, screen_name, twitter_text)
            )
    print twitter_text

# Doing mod 100 because it isn't an int otherwise
