# Projeto MongoDB com Spring Boot

API REST desenvolvida com **Java, Spring Boot e MongoDB**, com foco na construção de uma aplicação Back-end utilizando banco de dados NoSQL orientado a documentos.

O projeto aplica uma arquitetura em camadas, separando responsabilidades entre **Resources, Services e Repositories**, além de utilizar DTOs, tratamento de exceções e relacionamentos entre documentos no MongoDB.

---

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data MongoDB
- MongoDB
- Maven
- Postman
- Git
- GitHub

---

## Objetivo do projeto

O objetivo deste projeto foi praticar o desenvolvimento de uma **API REST utilizando Spring Boot integrada ao MongoDB**, entendendo as principais diferenças entre trabalhar com bancos relacionais e bancos NoSQL.

Durante o desenvolvimento foram trabalhados conceitos como:

- criação de endpoints REST;
- operações CRUD;
- persistência de documentos no MongoDB;
- arquitetura em camadas;
- uso de DTOs;
- relacionamento entre documentos;
- tratamento personalizado de exceções;
- consultas utilizando Spring Data MongoDB.

---

## Arquitetura

O projeto foi organizado seguindo uma estrutura em camadas:

```text
src/main/java
└── com.daviarruda.workshopmongo
    ├── config
    ├── domain
    ├── dto
    ├── repository
    ├── resources
    └── services
```

### domain

Contém as classes que representam os documentos armazenados no MongoDB, como:

- `User`
- `Post`

### dto

Contém os objetos utilizados para transportar apenas os dados necessários entre as camadas da aplicação.

Exemplos:

- `UserDTO`
- `AuthorDTO`

O uso de DTOs evita expor diretamente todos os dados das entidades da aplicação.

### repository

Responsável pela comunicação com o MongoDB através do Spring Data MongoDB.

Exemplo:

```java
public interface UserRepository extends MongoRepository<User, String> {
}
```

Com o `MongoRepository`, operações básicas como salvar, buscar e excluir documentos já ficam disponíveis automaticamente.

### services

Camada responsável pela lógica da aplicação.

Ela fica entre os Resources e os Repositories.

Exemplo de fluxo:

```text
Request HTTP
    ↓
Resource
    ↓
Service
    ↓
Repository
    ↓
MongoDB
```

### resources

Responsável por disponibilizar os endpoints da API.

Exemplo:

```java
@RestController
@RequestMapping(value = "/users")
public class UserResource {
}
```

Através dessa camada, o cliente pode realizar requisições HTTP para consultar ou manipular os dados.

---

## Banco de dados

O projeto utiliza o **MongoDB**, um banco de dados NoSQL orientado a documentos.

Diferentemente de um banco relacional, onde os dados são distribuídos entre tabelas e relacionados através de chaves, no MongoDB os dados são armazenados em documentos.

Exemplo:

```json
{
  "id": "1",
  "name": "Maria Brown",
  "email": "maria@gmail.com"
}
```

### Relacionamento entre documentos

O projeto utiliza `@DBRef` para relacionar documentos armazenados em diferentes coleções.

Exemplo:

```java
@DBRef(lazy = true)
private List<Post> posts = new ArrayList<>();
```

Nesse caso, um usuário possui uma lista de referências para seus posts.

O parâmetro:

```java
lazy = true
```

faz com que os posts sejam carregados apenas quando forem necessários.

---

## API REST

A aplicação disponibiliza endpoints REST para manipulação dos usuários.

### Buscar todos os usuários

```http
GET /users
```

Exemplo de resposta:

```json
[
  {
    "id": "1",
    "name": "Maria Brown",
    "email": "maria@gmail.com"
  }
]
```

### Buscar usuário por ID

```http
GET /users/{id}
```

Exemplo:

```http
GET /users/68c9f4c79a3f1a1234567890
```

### Criar usuário

```http
POST /users
```

Exemplo de corpo da requisição:

```json
{
  "name": "Davi Arruda",
  "email": "davi@email.com"
}
```

### Atualizar usuário

```http
PUT /users/{id}
```

Exemplo:

```json
{
  "name": "Davi Arruda",
  "email": "novoemail@email.com"
}
```

### Excluir usuário

```http
DELETE /users/{id}
```

---

## DTO

Um dos conceitos utilizados no projeto foi o padrão **DTO — Data Transfer Object**.

Exemplo:

```java
public class UserDTO {

    private String id;
    private String name;
    private String email;
}
```

O DTO permite definir exatamente quais informações serão enviadas ou recebidas através da API.

---

## Tratamento de exceções

A aplicação possui tratamento personalizado para casos onde um recurso não é encontrado.

Exemplo:

```java
public class ObjectNotFoundException extends RuntimeException {
}
```

Também foi utilizado um manipulador global de exceções para padronizar as respostas da API.

Uma resposta de erro pode seguir uma estrutura semelhante a:

```json
{
  "timestamp": "2026-09-19T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Objeto não encontrado",
  "path": "/users/123"
}
```

---

## Inicialização do banco

O projeto utiliza uma classe de configuração que implementa `CommandLineRunner`.

Ela permite inserir dados automaticamente no MongoDB quando a aplicação é iniciada.

Exemplo:

```java
@Configuration
public class Instantiation implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }
}
```

Essa configuração foi utilizada para popular inicialmente as coleções de usuários e posts.

---

## Executando o projeto

### Pré-requisitos

Para executar a aplicação é necessário possuir:

- Java instalado;
- Maven;
- MongoDB;
- MongoDB Compass (opcional);
- uma IDE como IntelliJ IDEA, Eclipse ou Spring Tool Suite.
