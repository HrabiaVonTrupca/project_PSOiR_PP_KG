import json

import pika
import random

QUEUE_NAME='primes_requests'
RANDOM_BITS=200

random_number=random.getrandbits(RANDOM_BITS)
print(f'Number: {random_number}')

connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq.posir'))
channel = connection.channel()
channel.queue_declare(queue=QUEUE_NAME, durable=True)
channel.basic_publish(exchange='',
                      routing_key=QUEUE_NAME,
                      body=json.dumps({'number': random_number}),
                      properties=pika.BasicProperties(
                          delivery_mode=2,  # make message persistent
                      ))
print("Sent")
connection.close()
