# Dockerized MySQL

https://hub.docker.com/_/mysql

## Ephemeral MySQL instances
This is the quickest way to get started:
```
docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=<secret> -e MYSQL_PASSWORD=<secret> -e MYSQL_DATABASE=<micronaut> -p 3306:3306 -d mysql:8.0
```

* User: root
* Password: secret
* Database: micronaut

Note: for easy access we are using the root user.

## Docker Compose
Execute from file directory:
```
docker-compose up -d
```

