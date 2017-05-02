import json
import random
from cassandra.cluster import Cluster

with open('states.json') as data_file:
        data = json.load(data_file)

codes = data.values()

# Doing mod 100 because it isn't an int otherwise

states = ['alabama','alaska','arizona' ,'arkansas' ,'california','colorado','connecticut','delaware','florida','georgia','hawaii','idaho','illinois','indiana','iowa','kansas','kentucky','louisiana','maine','maryland','massachusetts','michigan','minnesota','mississippi','missouri','montana','nebraska','nevada','new hampshire','new jersey','new mexico','new york','north carolina','north dakota','ohio','oklahoma','oregon','pennsylvania','rhode island','south carolina','south dakota','tennessee','texas','utah','vermont','virginia','washington','west virginia','wisconsin','wyoming']
values = [-0.266666666667,0.0,0,0,-0.00900900900901,0.0967741935484,-0.5,0.333333333333,0.283018867925,0.285714285714,0.25,0.0,0.583333333333,1.08333333333,0.333333333333,0.333333333333,-0.45,0.266666666667,0.318181818182,0.0645161290323,0.0714285714286,0.535714285714,0.333333333333,-0.272727272727,-0.0588235294118,1.0,0.6,0.5,-2.0,0.7,1.0,0.0862619808307,0,0,0.363636363636,0.545454545455,-0.047619047619,0.4375,1.0,0.0,-0.333333333333,0.0526315789474,-0.457711442786,-0.12,-0.75,0.0769230769231,0.123595505618,0,0.555555555556,0.666666666667,-0.263157894737,0.0,0,0,-0.00558659217877,0.176470588235,-0.222222222222,-0.6,0.279069767442,0.222222222222,0.172839506173,0.5,0.478260869565,0.666666666667,0.285714285714,0.181818181818,0.0,0.125,0.473684210526,0.166666666667,0.25,0.375,0.28,0.5,-0.0645161290323,1.09090909091,-0.166666666667,0.625,-2.0,0.272727272727,1.2,0.096837944664,0,0,0.379310344828,0.533333333333,0.102564102564,0.458333333333,0.666666666667,0.148148148148,-0.625,0.333333333333,-0.360606060606,-0.0652173913043,0.5,0.615384615385,0.0851063829787,0,0.538461538462,-0.25]
real_data = []
for i in range(0, len(states)):
    real_data.append(values[i])


count = 0
cluster = Cluster()
for i in range(0, len(codes)-1):
    count += 1
    rand_sent = random.randint(-5, 5)
    session = cluster.connect('storm')
    session.execute(
            """
            INSERT INTO website_data (id, state_name, state_sentiment)
            VALUES (%s, %s, %s) USING TTL 30
            """,
            (count, states[i], real_data[i])
            )
