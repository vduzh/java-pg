# Docker Compose

- `docker-compose up -d`- запускает все сервисы
- `docker-compose up -d --no-deps --build foo-service` - обновить только foo-service
- `docker-compose build` - собирает образы
- `docker-compose up --build` - собирает образы и запускает 
- `docker-compose down` - Останавливает и удаляет контейнеры
- `docker-compose logs` - Показывает логи сервисов

# Kafka

- `http://localhost:8080/` - админ консоль
- `docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092` - список топиков
- `docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092  --topic foo-events --from-beginning` - запустить клиента выполняющего чтение сообщений

# RabbitMQ

- `http://localhost:15672/` - админ консоль
- `guest`/`guest` - пользователь

# Grafana

- `http://localhost:3000/` - админ консоль
- `admin`/`admin` - пользователь

