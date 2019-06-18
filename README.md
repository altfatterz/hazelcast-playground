1. Start Eureka

```bash
$ spring cloud eureka
```

2. Start up 2 instances of `customer-service`

```bash
$ java -jar target/customer-service*.jar --server.port=9091
$ java -jar target/customer-service*.jar --server.port=9092
```

They will join a `Hazelcast` cluster

```bash
Members {size:2, ver:4} [
	Member [10.44.66.132]:5701 - 7e8e4b73-30fa-4a29-a7bb-95075955f84b
	Member [10.44.66.132]:5702 - c6dca00d-0941-4f20-9f46-f7ab5f7e2c96 this
]
```

3. Start up an instance of `order-service`

```bash
$ java -jar target/order-service*.jar
```

This will create its own `Hazelcast` cluster

```bash
Members {size:1, ver:1} [
	Member [10.44.66.132]:5703 - 730f6d78-527d-402f-be99-3c5acddabc7f this
]
```

4. Verify that the services are registered into `Eureka`

![eureka.png](images/eureka.png)


Resources:

1. https://hazelcast.com/blog/hazelcast-auto-discovery-with-eureka/