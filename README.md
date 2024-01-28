<h1 align="center">Auth-service</h1>

Compilation

```sh
mvn install
```

Run using docker-compose

```sh
docker-compose up -d
```

Token generation

```sh
curl --header "Content-Type: application/json"  \
  --request POST  \
  --data '{"user":"demo","password":"demo"}' \
  localhost:8095/api/v1/auth/login
```
