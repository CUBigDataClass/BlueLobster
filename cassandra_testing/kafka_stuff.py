from kafka import KafkaConsumer
import json

consumer = KafkaConsumer('twitter-topic')
for message in consumer:
    print '----------------'
    print '----------------'
    print '----------------'
    twitter = json.loads(message.value)
    print twitter["text"]
    print twitter["id"]
    print twitter["user"]["screen_name"]




