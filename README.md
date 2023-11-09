  <h1 align="center">★StarTrip★</h1>
  <h3 align="center">★Que a diversão esteja com você!★</h3>

---
## Objetivo
Esse projeto foi inicialmente desenvolvido como trabalho final de um curso de desenvolvimento back-end,
através dele fui aprovado e contratado.
Com as experiências adquiridas no início da carreira, implementei algumas melhorias, como a atualização do java 8 para 
o java 17, a troca do banco de dados H2 pelo PostgreSQL e preparação para ser usado através do Docker.

## Tecnologias

- ``Java 17``
- ``Maven``
- ``Spring Boot``
- ``PostgreSql``
- ``JPA``
- ``Lombok``
- ``OpenFeign``
- ``Swagger``
- ``Docker``

## Funcionalidades

- CRUD para usuários;
- CRUD para imóveis;
- Anúncios de imóveis para aluguel, com opções de consulta e exclusão;
- Reservas de imóveis, com opções de consulta, pagamento, cancelamento e estorno.

## Como usar

Este projeto pode ser usado através do Docker, estando na pasta raiz do projeto basta seguir os passos abaixo:

1) Gerar o arquivo *.jar*: `mvn clean package`
2) Executar o comando: `docker compose up -d`
3) Abrir o swagger: http://localhost:8080/swagger-ui/

## Melhorias a serem feitas
1. Criar um CRUD para cadastrar os alunos;
2. Escrever as informações recebidas pela planilha numa mensageria
   (ex.: Kafka, Rabitt MQ) para enviar os e-mails posteriormente.
