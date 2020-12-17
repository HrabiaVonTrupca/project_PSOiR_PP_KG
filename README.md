## rabbitmq

```
kubectl apply -f k8s/rabbitmq
```

## apps

### prerequisite

```
docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

### example producer

```
docker build -t localhost:5000/example-producer -f docker/example-producer/Dockerfile .
docker push localhost:5000/example-producer
kubectl run -it --restart=Never --image=localhost:5000/example-producer --rm producer
```

### example consumer

```
docker build -t localhost:5000/example-consumer -f docker/example-consumer/Dockerfile .
docker push localhost:5000/example-consumer
kubectl run -it --restart=Never --image=localhost:5000/example-consumer --rm consumer
```
