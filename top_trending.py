import tweepy
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import json

consumer_key = "84rkPla1s8RwhMes4i7DJh0KW"
consumer_secret = "113JMGoTaw1loYPyH31vWIrxkH8WmSD59UZzaFfdk7q7XHSiff"
access_token = "174192901-LPIE59jibzDWqI1q6ZIwjYPWyr2RThMPSTzOK7M0"
access_secret = "gM0OzHZ3zaxycmEIE34m7CqtSg5SUsciI1mp7nLFfeHcP"

class StdOutListener(StreamListener):

    def on_data(self, data):
        my_data = json.loads(data)
        print my_data['text']
        return True

    def on_error(self, status):
        print status
    

if __name__ == '__main__':
    #handle twitter auth
    l = StdOutListener()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_secret)
    api = tweepy.API(auth)
    
    #get top trends
    trends = api.trends_place(1)
    
    #load data
    data = trends[0]
    trends = data['trends']

    #truncate extra data, extract names of tweets
    names = [trend['name'] for trend in trends]

    #do what every with the top trends
    for i in range(0, 4):
        print names[i]





