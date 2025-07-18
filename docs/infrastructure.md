# Docker Compose

- `docker-compose up -d`- запускает все сервисы
- `docker-compose up -d --no-deps --build foo-service` - обновить только foo-service
- `docker-compose build` - собирает образы
- `docker-compose up --build` - собирает образы и запускает 
- `docker-compose down` - Останавливает и удаляет контейнеры
- `docker-compose logs` - Показывает логи сервисов

# Kafka

- `http://localhost:9080/` - админ консоль
- Kafka CLI

## Create topic

- `docker exec kafka kafka-topics --create -bootstrap-server localhost:9092 --topic test-topic - --partitions 1 --replication-factor 1`
    - `docker exec kafka kafka-topics --create -bootstrap-server localhost:9092 --topic incorrect-type-topic-dlt - --partitions 1 --replication-factor 1`

## Get topic info

- `docker exec kafka kafka-topics --describe --topic test-topic --bootstrap-server localhost:9092`

## Get topics 

- `docker exec kafka kafka-topics --list --bootstrap-server localhost:9092`

## Delete topic

Работает, только если `delete.topic.enable=true` в конфиге брокера.

- `docker exec kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic test-topic`

## Публикация сообщения
- `docker exec -it kafka kafka-console-producer --broker-list localhost:9092 --topic test-topic` - вводить сообщение в консоли и нажать Enter
  - `docker exec kafka bash -c "echo 'Hello Kafka' | kafka-console-producer --topic test-topic --bootstrap-server localhost:9092"`

## Чтение сообщения

### Без указания группы

- `docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092  --topic test-topic` - читает с текущего конца топика
- `docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092  --topic test-topic --from-beginning` - читает с начала топика
  - `docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092  --topic foo-events --from-beginning`

### С указанием группы

Будет читать с начала для группы (при повторном запуске from-beginning уже не влияет, т.к. будет читать с того места, где остановился)

- `docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092  --topic test-topic --from-beginning --group test-group`

## Consumer groups

### List

- `docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --list`

### Describe - состояние группы

- `docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group test-group`

### Delete

- `docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --delete --group test-group`

# Offset-ы

## Просмотр offset-ов

- `docker exec kafka kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic test-topic --time -1` - последний offset
- `docker exec kafka kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic test-topic --time -2` - самый ранний offset

## Сбросить offset-ы у группы на начало

- `docker exec kafka kafka-consumer-groups --bootstrap-server localhost:9092 --group test-group --topic test-topic --reset-offsets --to-earliest --execute`

Проверка
- `docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --group test-group`

# RabbitMQ

- `http://localhost:15672/` - админ консоль
- `guest`/`guest` - пользователь

# Grafana

- `http://localhost:3000/` - админ консоль
- `admin`/`admin` - пользователь

