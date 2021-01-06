import pika, sys, os

QUEUE_NAME='primes_results'

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq.posir'))
    channel = connection.channel()

    channel.queue_declare(queue=QUEUE_NAME, durable=True)

    def callback(ch, method, properties, body):
        print(" [x] Received %r" % body)

    channel.basic_consume(queue=QUEUE_NAME, on_message_callback=callback)

    print(' [*] Waiting for messages. To exit press CTRL+C')
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