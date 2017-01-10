# kubernetes-mongo-docker-spring-boot 
[<img src="https://travis-ci.org/elizabetht/kubernetes-mongo-docker-spring-boot.svg?branch=master">] (https://travis-ci.org/elizabetht/kubernetes-mongo-docker-spring-boot.svg?branch=master)

This is a sample spring-boot application that talks to mongoDB for GET/POST REST APIs and deployed in Kubernetes cluster with ElasticSearch and Kibana.
#### TODO:
- [ ] Connect MongoDB with ElasticSearch so that the data can be indexed and viewed

### Prerequisites
* minikube
* kubectl

### Installation
* Clone this repository and run the following command to run the application
```
kubectl apply -f deployment.yml
```

### Test
#### MongoDB Service
---
```
minikube service springboot-docker
```
This service allows to access the mongoDB using GET/POST APIs.

* Insert items
Using the IP/Port the springboot-docker service provides, insert items
```
curl -XPOST 'http://192.168.99.101:32249/items' -H 'Content-type:application/json' -d'
{"price":2345,"description":"item3"}'
```
* Retrieve items
Using the IP/Port the springboot-docker service provides, retrieve items
```
curl -XGET 'http://192.168.99.101:32249/items' -H 'Content-type:application/json'
```

#### Elastic Search + Kibana Service
---
##### Elastic Search
```
minikube service elasticsearch
```
This service allows to access the Elastic Search.

* Insert data (in bulk) to ElasticSearch using the IP/Port the service provides
```
curl -XPOST 'http://192.168.99.101:30229/_bulk?pretty' -d'
{ "index" : { "_index" : "test", "_type" : "type1", "_id" : "1" } }
{ "field1" : "value1" }
{ "delete" : { "_index" : "test", "_type" : "type1", "_id" : "2" } }
{ "create" : { "_index" : "test", "_type" : "type1", "_id" : "3" } }
{ "field1" : "value3" }
{ "update" : {"_id" : "1", "_type" : "type1", "_index" : "test"} }
{ "doc" : {"field2" : "value2"} }'
```
* Search data from ElasticSearch using the IP/Port the service provides
```
curl -XGET http://192.168.99.101:30229/test/_search?q=_type:type1
```

##### Kibana
```
minikube service kibana
```
This service allows to access the Kibana UI that is setup on top of Elastic Search to retrieve the items

### Persistent Volume Storage
If MongoDB document collections are not attached to a persistent storage volume, if the mongoDB node goes down for any reason, the data attached to the node would be lost.
In these scenarios, Kubernetes presents many types of persistent volume storages. For this example, the hostPath storage is assumed for easy usage.
By attaching the MongoDB node to the MongoDB Persistent Storage Volume, the document collection is persisted even when the node fails

In Production setup, the hostPath setup has to be replaced with either GCE Persistent Storage Volumes or AWS EBS or NFS or other such storage options discussed under Kubernetes Persistent Volume

To test the above scenario:
1. Get the Mongo Controller Pod name using ```kubectl get pods```
2. Verify the MongoDB has still the items persisted in the above step (listed under MongoDB Service) ```curl -XGET 'http://192.168.99.101:32249/items' -H 'Content-type:application/json'```
3. Kill the Mongo Controller Pod using ```kubectl delete pod <mongo-controller-pod-name>```

By default, data will be stored within the pod. When the Pod is deleted or goes down for any reason, the data associated will be lost. Since the mongoDB Pod configured in this example is attached with persistent volume, even when the Pod comes alive after a downtime, the data is still available.
Due to the replication controller setup in the deployment YML file, when a Pod goes down, the replication controller makes sure to bring the Pod up to maintain the required number of replicas. Once the Pod becomes available, the data becomes available again. Expect a temporary error to be thrown while accessing /items when the Pod is still coming up.

The above mechanism applies to the persistent storage volumes attached to the Elastic Search Pod as well in this setup.

### Travis CI
The repository has a .travis.yml file that takes care of automatically building the application jar, containerizing it using docker from a gradle task and pushing the tagged docker image to docker hub.

