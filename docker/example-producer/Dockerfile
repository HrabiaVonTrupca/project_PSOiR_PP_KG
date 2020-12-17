FROM python:3.8.6-slim

RUN pip install pika
COPY apps/example-producer/main.py /main.py

CMD ["python", "/main.py"]
