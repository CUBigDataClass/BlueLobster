from cassandra.cluster import Cluster

states = ["Alabama", "Alaska", "Arizona ", "Arkansas ", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
                "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
                "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
                "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
                "Washington", "West Virginia", "Wisconsin", "Wyoming"]
cluster = Cluster()
session = cluster.connect('storm')
for x in range(0, len(states)):
    results = session.execute("SELECT avg(state_sentiment) FROM storm.storm_data WHERE state_name = %s",states[x])

    session.execute(
            """
            INSERT INTO states_average (id, states_name, state_sentiment)
            VALUES (%s, %s, %s) USING TTL 6000
            """,
            (x, states[x], results[0])
            )

