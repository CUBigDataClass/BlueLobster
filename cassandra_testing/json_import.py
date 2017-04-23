import json

with open('twitter.json') as data_file:
        data = json.load(data_file)

json_data = 'text'
text = data[json_data].keys()
print text
