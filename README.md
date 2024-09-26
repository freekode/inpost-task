# Product price calculator

## Start web server

Java 21 required

```shell
./gradlew bootRun
```

## Build docker image

```shell
./gradlew build
docker build .
```

## Assumptions

* There is only one or no discount policy for given product
* Same discount policy can be applied only one time (not repeatable)
* No db required, all data stored in memory