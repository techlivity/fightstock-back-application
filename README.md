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

## Documentação da API

Para visualizar as entidades que devem ser envidas e retornadas, pode ser consultado através do swagger a documentação
completa da API com exemplos de entidades para criação de massa de dados localmente com docker. Para isso basta acessar a roda `http://localhost:8080/swagger-ui/index.html` e através dos exemplos da documentação usar postmain, insomnia ou cURL para enviar e receber respostas da API em tempo de execução.