
# Weather-Info

Сервис предоставляет статистические данные о погоде. 
Прогноз  можно получить с текущей даты и времени не более чем на 5 дней.


## Сборка и запуск сервиса 

Необходимо поставить пакеты 
* [sbt для сборки Scala](http://www.scala-sbt.org/1.0/docs/Setup.html)
* [npm для сборки Js](https://nodejs.org/en/download/package-manager/)


```bash

sbt 'npm install'
sbt compile
sbt run

```


## Cборка docker  контейнера

Собрать локально

```bash

sbt docker

```

Собрать и запушить в hub.docker.com

```bash

sbt dockerBuildAndPush

```

Запуск контейнета (порт по умолчанию 8080)


```bash

docker run -p {port}:{port} acteek/weather-info:latest

```

Для возможности пушить на [hub.docker.com](https://hub.docker.com/r/acteek/weather-info/)

```bash

export DOCKER_ID_USER="username"
docker login

```
