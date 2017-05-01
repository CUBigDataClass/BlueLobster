import json
import random
from cassandra.cluster import Cluster

with open('states.json') as data_file:
        data = json.load(data_file)

codes = data.values()

# Doing mod 100 because it isn't an int otherwise

count = 0
cluster = Cluster()
for i in range(0, len(codes)-1):
    count += 1
    rand_sent = random.randint(-5, 5)
    session = cluster.connect('storm')
    session.execute(
            """
            INSERT INTO storm_data (id, state_name, state_sentiment)
            VALUES (%s, %s, %s) USING TTL 30
            """,
            (count, codes[i], rand_sent)
            )

for i in range(0, len(codes)-1):
    count += 1
    rand_sent = random.randint(-5, 5)
    session = cluster.connect('storm')
    session.execute(
            """
            INSERT INTO storm_data (id, state_name, state_sentiment)
            VALUES (%s, %s, %s) USING TTL 30
            """,
            (count, codes[i], rand_sent)
            )
