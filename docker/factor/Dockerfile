FROM python:3.8.6-slim

COPY apps/factor/requirements.txt /requirements.txt
RUN pip install -r requirements.txt

COPY apps/factor/main.py /main.py

CMD ["python", "/main.py"]
