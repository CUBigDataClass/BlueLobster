from cassandra.cluster import Cluster
state_list = ["Alabama", "Alaska", "Arizona ", "Arkansas ", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
                "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
                "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
                "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
                "Washington", "West Virginia", "Wisconsin", "Wyoming"]

states = [0] * 50
total_seen = [0] * 50
cluster = Cluster()
session = cluster.connect('storm')
results = session.execute("SELECT * FROM storm_data")
for x in results:
    rows = x

    state_id = rows[0]
    state_name = rows[1]
    state_sentiment = rows[2]
    for i in range(0, len(state_list)-1):
        if(state_list[i] == state_name):
            states[i] += float(state_sentiment)
            total_seen[i] += 1.0


print 'The sentiment of Alabama is: ', states[0]

for i in range(0, len(states)-1):
    if(total_seen[i] == 0):
        total_seen[i] = 1
    print 'The sentiment of ', state_list[i], ' is ', states[i]
    print 'With an average of: ', states[i]/total_seen[i]
    print '------------', states[i], total_seen[i]


for i in range(0, len(states)-1):
    if(total_seen[i] == 0):
        total_seen[i] = 1
    average = states[i]/total_seen[i]
    insert = session.execute(
            """
            INSERT INTO website_data (id, state_name, state_sentiment)
            VALUES(%s, %s, %s)
            """,
            (i, state_list[i], average)
            )
