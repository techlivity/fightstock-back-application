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

Depois que subir a app local com o docker em execução será criado as tabelas no banco de dados, agora o próximo passo será manualmente a inserção dos dados de regras de usuario, na tabela `tb_role` manualmente colocar regras, sendo as seguintes:
```shell
id:1 role:USER
id:1 role:USER_ADMIN
```
Apos criar as roles necessários, em todo método POST,PUT,PATCH,DELETE será necessário carregar o header Authorization BASIC com as credenciais em base64 conforme o exemplo abaixo:

```shell
curl --request POST \
  --url http://localhost:8080/products \
  --header 'Authorization: Basic YnJ1bm9AZ21haWwuY29tOmJydW5vMTIz' \
  --header 'Content-Type: application/json' \
  --data '{
  "nome": "iphone 7",
  "image_url": "image_url_d249f4775ccd",
  "descrição": "descrição_5c6ee4c94383",
  "em_destaque": false,
  "em_promoção": false
}'
```

Para criar as credenciais de acesso basta seguir para a controller de autenticação.

TODO: complementar documentação.
## Documentação da API

Para visualizar as entidades que devem ser envidas e retornadas, pode ser consultado através do swagger a documentação
completa da API com exemplos de entidades para criação de massa de dados localmente com docker. Para isso basta acessar a roda `http://localhost:8080/swagger-ui/index.html` e através dos exemplos da documentação usar postmain, insomnia ou cURL para enviar e receber respostas da API em tempo de execução.