# Eureka

- http://localhost:8761/ - Eureka
- TODO: make 2 copies and describe

# Config Server

- https://github.com/vduzh/spring-cloud-app-config - GitHub источник свойств для Config Server

## Foo service

- http://localhost:8888/foo-service/default - конфигурации от Config Server для foo-service если
  профиль не задан
- http://localhost:8888/foo-service/prod - конфигурации от Config Server для foo-service для prod
  профиля

## Bar service

- http://localhost:8888/bar-service/default - конфигурации от Config Server для bar-service если
  профиль не задан

# GateWay

- http://localhost:8765 - Gateway
- http://localhost:8765/actuator

## Foo service

- http://localhost:8765/api/v1/foo/foos/info
- http://localhost:8765/api/v1/foo/foos

## Bar service

- http://localhost:8765/api/v1/bar/bars
- http://localhost:8765/api/v1/bar/bars/foos

# Services

## Foo

- TODO: make 2 instances and describe

## Bar

- TODO: make 2 instances and describe