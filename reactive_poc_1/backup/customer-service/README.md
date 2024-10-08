# Read Me First
This is a sample ping controller initial setup for the CI/CD setup

```
>>> http://localhost:8080/ping
{
    "ping": "pong"
}
```

### Run docker-compose
To run docker compose locally, run following commands
```
cd <project-directory>

cp local.env .env

docker-compose up -d --build
```
To stop running containers, run following command
```
cd <project-directory>

docker-compose down
```