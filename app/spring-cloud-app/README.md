# Eureka

- http://localhost:8761/ - Eureka

## TODO
- make 2 copies and describe

# Config Server

## GitHub Config Server

- https://github.com/vduzh/spring-cloud-app-config - GitHub источник свойств для Config Server

## Native Config Server

- Run with the `native` active profile

## Foo service Config

- http://localhost:8888/foo-service/default - конфигурации от Config Server для foo-service если
  профиль не задан
- http://localhost:8888/foo-service/prod - конфигурации от Config Server для foo-service для prod
  профиля

## Bar service Config

- http://localhost:8888/bar-service/default - конфигурации от Config Server для bar-service если
  профиль не задан

# Microservices (clients)

## Foo service

- http://localhost:8765/api/v1/foo/foos/info
  - `source` property contains info where the configuration comes from (native, github)
- http://localhost:8765/api/v1/foo/foos

### TODO:

- create 2 instances and describe

## Bar service

- http://localhost:8765/api/v1/bar/bars
- http://localhost:8765/api/v1/bar/bars/foos

### TODO:

- create 2 instances and describe

# API Gateway

- http://localhost:8765 - Gateway
- http://localhost:8765/actuator

## TODO:

- add logging
- add authentication
- add caching

