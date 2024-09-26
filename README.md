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

* Only one or no discount policy for given product is defined
* Same discount policy can be applied only one time (not repeatable)
* All defined discount policies are valid: values are greater than 0 and don't discount 100% of price
* The best (smallest) price for customer will be always chosen 
* No db required, all data stored in memory
