import json
import os
import sys
import time

import pika
from sympy.ntheory import factorint

QUEUE_HOST = os.environ['QUEUE_HOST']
REQUEST_QUEUE_NAME = os.environ['REQUEST_QUEUE_NAME']
RESULT_QUEUE_NAME = os.environ['RESULT_QUEUE_NAME']


def produce_result(channel, message):
    channel.basic_publish(exchange='',
                          routing_key=RESULT_QUEUE_NAME,
                          body=message,
                          properties=pika.BasicProperties(
                              delivery_mode=2,  # make message persistent
                          ))


def factorize(number):
    start = time.time()
    primes = factorint(number, multiple=True)
    end = time.time()
    return end - start, primes


def callback(ch, method, properties, body):
    print("Received %r" % body)
    number = json.loads(body)['number']
    time, primes = factorize(number)
    return_message = json.dumps({'number': number, 'time': time, 'primes': primes})
    produce_result(ch, return_message)
    ch.basic_ack(delivery_tag=method.delivery_tag)


def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=QUEUE_HOST))
    channel = connection.channel()
    channel.queue_declare(queue=REQUEST_QUEUE_NAME, durable=True)
    channel.queue_declare(queue=RESULT_QUEUE_NAME, durable=True)
    channel.basic_qos(prefetch_count=1)  # accept max 1 message at time
    channel.basic_consume(queue=REQUEST_QUEUE_NAME, on_message_callback=callback)
    channel.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)
