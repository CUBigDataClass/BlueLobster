import tweepy
import time
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import json
import sys

consumer_key = "84rkPla1s8RwhMes4i7DJh0KW"
consumer_secret = "113JMGoTaw1loYPyH31vWIrxkH8WmSD59UZzaFfdk7q7XHSiff"
access_token = "174192901-LPIE59jibzDWqI1q6ZIwjYPWyr2RThMPSTzOK7M0"
access_secret = "gM0OzHZ3zaxycmEIE34m7CqtSg5SUsciI1mp7nLFfeHcP"

class StdOutListener(StreamListener):

    def on_data(self, data):
        return True

    def on_error(self, status):
        print status
    

if __name__ == '__main__':
    #handle OAuth parameters with tweepy API
    l = StdOutListener()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_secret)
    api = tweepy.API(auth)
    while(True):    
        #get top trends
        trends = api.trends_place(1)
        
        #load data
        data = trends[0]
        trends = data['trends']

        #truncate extra data, extract names of tweets
        names = [trend['name'] for trend in trends]

        #do what every with the top trends
        results = ''
        counter = 0
        add = 0

        #Loop until 5 things have been added to csv (wont get added if not ascii)
        while(counter < 5):
            try:
                #This is checking to confirm if ascii or not
                names[add].decode("ascii")
                results += names[add] + ','
                add += 1
                counter += 1
            except:
                #If not ascii move on to next thing to add
                print 'not an ascii-encoded string'
                add += 1


        
        #write the string to the file, currently results.csv
        target = open('results.csv', 'w')
        print results
        target.write(results)
        target.close() 

        #make the call every 3 minutes
        time.sleep(180)

