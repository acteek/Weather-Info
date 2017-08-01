
# Weather-Info

Сервис предоставляет статистические данные о погоде

сборка docker  контейнера

```bash

sbt dockerBuildAndPush

```
Ручной запуск без докера 

```bash

sbt run

```

Ручной запуск в контейнета 

```bash

docker run -p {port}:{port} acteek/weather-info:{version}

```
Для возмодности пушить на hub.docker.com

```bash

export DOCKER_ID_USER="username"
docker login

```
