import json

with open('states.json') as data_file:
        data = json.load(data_file)

text = data.keys()
for i in range(0, len(data) - 1):
    print text[i]
