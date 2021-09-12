import os
from google.cloud import pubsub_v1
from concurrent.futures import TimeoutError


credentials_path = 'static/halifax-foodie-private-key.json'
os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = credentials_path


publisher = pubsub_v1.PublisherClient()
subscriber = pubsub_v1.SubscriberClient()


topic_path = 'projects/serverless-project-320710/topics/Customer_topic'
subscription_path = 'projects/serverless-project-320710/subscriptions/Service_agent-sub'


def publisher_foodie(statement):
    data = statement
    data = data.encode('utf-8')
    attributes = {
        'Restaurant_name': 'Halifax_foodie',
        'locaation': 'Halifax',

    }

    future = publisher.publish(topic_path, data, **attributes)
    print(f'published message id {future.result()}')
    # startSubscription()


def callback(message):
    # print(f'Received message: {message}')
    data = str(message.data)
    # print(f'data: {message.data}')
    print(data)
    print("Please wait...")
    message.ack()
    statement = input("Type your message:")
    publisher_foodie(statement)


def startSubscription():
    streaming_pull_future = subscriber.subscribe(
        subscription_path, callback=callback)
    print(f'Listening for messages on {subscription_path}')

    with subscriber:
        try:
            streaming_pull_future.result()
        except TimeoutError:
            streaming_pull_future.cancel()
            streaming_pull_future.result()
