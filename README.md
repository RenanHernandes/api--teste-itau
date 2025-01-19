# Microsserviço Spring Boot - Gestão de Produtos

Este repositório contém a implementação de um microsserviço desenvolvido em **Spring Boot** para gerenciar uma lista de produtos. Ele foi projetado com foco em **operações CRUD**, integração com **RabbitMQ**, e aderência aos **princípios SOLID**. Além disso, o projeto inclui testes automatizados, integração contínua, e suporte para execução com Docker.

## Funcionalidades
1. **CRUD de Produtos**: API RESTful para gerenciar produtos.
2. **Testes Automatizados**: Testes unitários com JUnit e Mockito.
3. **Mensageria**: Publicação de mensagens em RabbitMQ para novos produtos.
4. **Integração com Banco de Dados MySQL**: Banco de dados com migrações Flyway.
5. **Escalabilidade**: Configuração para rodar na AWS (ECS ou Lambda).
6. **Adesão a SOLID**: Código refatorado para boas práticas de design.

---

## Exercícios Implementados

### Exercício 1: CRUD de Produtos
- API RESTful com operações de criação, leitura, atualização e exclusão de produtos.

### Exercício 2: Testes e CI/CD
- Testes unitários utilizando **JUnit** e **Mockito**.
- Pipeline de integração contínua configurado com **GitHub Actions**.

### Exercício 3: Mensageria e AWS
- Publicação de mensagens em **RabbitMQ** ao criar produtos.
- Consumidor para ler mensagens da fila e registrar logs.
- Configuração para execução em **AWS ECS** ou **AWS Lambda**.

### Exercício 4: Banco de Dados e Filtros
- Integração com banco de dados **MySQL** utilizando **Flyway**.
- Endpoint para buscar produtos com filtros (nome, categoria, preço).

---

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 2.7.6**
- **Flyway**
- **Swagger/OpenAPI**
- **GitLab CI/CD**
- **Docker e Docker Compose**
- **PostgreSQL**
- **RabbitMQ**

## Pré-requisitos
Certifique-se de ter instalado na sua máquina:
- **Java 17**
- **Maven ou Gradle**
- **Docker**
- **Docker Compose**
- **Git**

---

## Como Rodar o Projeto Localmente com Docker

### Pré-requisitos
- Instale o [Docker](https://www.docker.com/) e o [Docker Compose](https://docs.docker.com/compose/).

### Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/RenanHernandes/api--teste-itau.git
   cd api--teste-itau
   git checkout feature/flyway_GitLab
   ```

2. Suba os serviços com Docker Compose:
   ```bash
   docker-compose up -d
   ```
   Este comando iniciará os contêineres para **PostgreSQL** e **RabbitMQ**.

   3. Configure as variáveis de ambiente no arquivo `application.properties` ou como variáveis do sistema:
      ```properties
       spring.datasource.url=jdbc:h2:mem:productdb
       spring.datasource.driver-class-name=org.h2.Driver
       spring.datasource.username=sa
       spring.datasource.password=
       spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
       spring.h2.console.enabled=true
    
       spring.jpa.hibernate.ddl-auto=none
       spring.jpa.show-sql=true
    
       spring.rabbitmq.host=localhost
       spring.rabbitmq.port=5672
       spring.rabbitmq.username=guest
       spring.rabbitmq.password=guest
    
       spring.flyway.enabled=true
       spring.flyway.locations=classpath:db/migration
       spring.flyway.baseline-on-migrate=true
      ```

4. Execute as migrações do banco de dados com Flyway:
   ```bash
   ./gradlew flywayMigrate
   ```

### Execução
Para rodar a aplicação localmente:
```bash
./gradlew bootRun
```
A aplicação estará disponível em: `http://localhost:8080`

---

## Documentação da API
Acesse a documentação interativa da API via Swagger:
```
http://localhost:8080/swagger-ui.html
```

---

## Testes
Execute os testes unitários e de integração com:
```bash
./gradlew test
```

Os relatórios de teste serão gerados em:
`build/reports/tests/test/index.html`

---

## Pipeline CI/CD
O projeto está configurado para CI/CD no GitLab. Para isso, certifique-se de:

1. Configurar as seguintes variáveis no GitLab CI/CD:
- `DOCKER_AUTH_CONFIG`: Credenciais de autenticação para o Docker.
- `AWS_ACCESS_KEY_ID`: Chave de acesso AWS.
- `AWS_SECRET_ACCESS_KEY`: Chave secreta AWS.
- `AWS_REGION`: Região da AWS (ex.: us-east-1).
- `AWS_ECR_URI`: URI do repositório ECR (ex.: `123456789012.dkr.ecr.us-east-1.amazonaws.com`).
- `IMAGE_NAME`: Nome da imagem Docker (ex.: `api-produto`).

2. A pipeline é definida no arquivo `.gitlab-ci.yml`. Ela inclui etapas para:
- Build da imagem Docker
- Testes
- Deploy

Para validar e acompanhar as execuções, acesse **CI/CD > Pipelines** no GitLab.

---

## Endpoints da API

### 1. **Consultar produto por ID**
- **URL**: `/v1/produto/{id}`
- **Método**: `GET`
- **Descrição**: Retorna os detalhes de um produto específico com base no ID.
- **Resposta**:
  - **200 OK**: Produto encontrado.
  - **400 Bad Request**: Dados inválidos na requisição.
  - **500 Internal Server Error**: Erro no servidor.
- **Exemplo de resposta**:
  ```json
  {
    "id": 1,
    "nome": "Produto A",
    "categoria": "Categoria",
    "preco": 1000.0,
    "descricao": "Eletrônicos"
  }
  ```

### 2. **Atualizar produto existente**
- **URL**: `/v1/produto/{id}`
- **Método**: `PUT`
- **Descrição**: Atualiza as informações de um produto com base no ID fornecido.
- **Payload (Exemplo)**:
  ```json
  {
    "nome": "Produto A Atualizado",
    "categoria": "Eletrônicos",
    "descricao": "Eletrônicos",
    "preco": 1999.99
  }
  ```
- **Resposta**:
  - **200 OK**: Produto atualizado com sucesso.
  - **400 Bad Request**: Dados inválidos na requisição.
  - **500 Internal Server Error**: Erro no servidor.

### 3. **Deletar produto por ID**
- **URL**: `/v1/produto/{id}`
- **Método**: `DELETE`
- **Descrição**: Remove um produto do sistema utilizando o ID fornecido na requisição.
- **Resposta**:
  - **201 No Content**: Produto deletado com sucesso.
  - **400 Bad Request**: Dados inválidos na requisição.
  - **500 Internal Server Error**: Erro no servidor.

### 4. **Listar todos os produtos**
- **URL**: `/v1/produto`
- **Método**: `GET`
- **Descrição**: Retorna uma lista completa de todos os produtos cadastrados.
- **Resposta**:
  - **200 OK**: Lista de produtos.
  - **400 Bad Request**: Dados inválidos na requisição.
  - **500 Internal Server Error**: Erro no servidor.
- **Exemplo de resposta**:
  ```json
  [
    {
      "id": 1,
      "nome": "Produto A",
      "categoria": "Categoria A",
      "preco": 100.0,
      "descricao": "Descrição do Produto A"
    },
    {
      "id": 2,
      "nome": "Produto B",
      "categoria": "Eletrônicos",
      "preco": 100.0,
      "descricao": "Smartphone de última geração"
    }
  ]
  ```

### 5. **Cadastrar novo produto**
- **URL**: `/v1/produto`
- **Método**: `POST`
- **Descrição**: Realiza o cadastro de um novo produto utilizando os dados fornecidos no corpo da requisição.
- **Payload (Exemplo)**:
  ```json
  {
    "nome": "Produto C",
    "categoria": "Categoria C",
    "preco": 150.0,
    "descricao": "Descrição do Produto C"
  }
  ```
- **Resposta**:
  - **201 Created**: Produto cadastrado com sucesso.
  - **400 Bad Request**: Parâmetros inválidos.
  - **500 Internal Server Error**: Erro no servidor.

---

## Contribuição
1. Crie uma branch a partir da `feature/flyway_GitLab`:
   ```bash
   git checkout -b sua-feature
   ```
2. Faça as alterações desejadas.
3. Abra um pull request para a branch principal.

---

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).

---
Se tiver dúvidas ou problemas, sinta-se à vontade para abrir uma **issue** no repositório. 😊
