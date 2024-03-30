# fightstock-back-application

## Requisitos minimos para executar o projeto:

- jdk 17
- docker
- insomnia/postman ou cURL
- gradle 7.x+

## Como executar a aplicação localmente.

Para preparar o ambiente local simulado com docker basta seguir o exemplo abaixo:

mover para o diretório `/infra-local`

```shell
cd ./infra-local
```
no diretório infra-local basta executar o `docker-compose` com o seguinte comando no terminal:

```shell
docker-compose up
```

Com isso o banco de dados `MySQL` estará disponivel assim que aparecer o seguinte log:
```shell
mysqld: ready for connections.
```