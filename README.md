## rabbitmq

```
kubectl apply -f k8s/rabbitmq
```

## factorization workers

```
docker build -t localhost:5000/factor -f docker/factor/Dockerfile .
docker push localhost:5000/factor
kubectl apply -f k8s/factor
```

## apps

### prerequisite

```
docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

### example producer
sends N-bit random number to requests queue

```
docker build -t localhost:5000/example-producer -f docker/example-producer/Dockerfile .
docker push localhost:5000/example-producer
kubectl run -it --restart=Never --image=localhost:5000/example-producer --rm producer
```

### example consumer
receives from results queue (does not ack)

```
docker build -t localhost:5000/example-consumer -f docker/example-consumer/Dockerfile .
docker push localhost:5000/example-consumer
kubectl run -it --restart=Never --image=localhost:5000/example-consumer --rm consumer
```

### factor
factorization worker (run as a standalone container)

```
docker build -t localhost:5000/factor -f docker/factor/Dockerfile .
docker push localhost:5000/factor
kubectl run -it --restart=Never --image=localhost:5000/factor --rm factor
```

### api
example request 
GET /factorize?number=100

```
Build app using :
    mvnw clean package

docker build -t localhost:5000/api -f docker/api/Dockerfile .
docker push localhost:5000/api
kubectl apply -f k8s/api
```