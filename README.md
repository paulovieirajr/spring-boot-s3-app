# Aplicação Spring Boot 3 com Java 17, AWS S3 e Postgres

Este README fornece uma visão geral do funcionamento da aplicação Spring Boot, utilizando Java 17, integração com o Amazon S3 para manipulação de arquivos 
e Postgres como banco de dados. A aplicação é projetada para gerenciar ebooks, incluindo capas e conteúdos em formato PDF, com manipulação direta de URLs 
pré-assinadas geradas pelo S3.

## Funcionamento

### Estrutura da Aplicação

A aplicação segue uma arquitetura baseada em Spring Boot, dividida em três principais controladores:

- **EbookController:** Responsável por operações básicas relacionadas aos ebooks, como criação, atualização, e recuperação por ID.

- **UploadController:** Gerencia o upload de documentos (PDFs) e imagens (capas de ebooks) utilizando o serviço de armazenamento, gerando URLs pré-assinadas para acesso direto ao Amazon S3.

- **DownloadController:** Facilita o download de ebooks privados, gerando URLs pré-assinadas para acesso direto ao conteúdo armazenado no Amazon S3.

### Banco de Dados e AWS S3

A aplicação utiliza o PostgreSQL como banco de dados para armazenar informações sobre os ebooks. As informações sensíveis, como arquivos PDF e imagens, 
são armazenadas no Amazon S3. A integração com o S3 permite o acesso direto aos recursos, aliviando a carga do backend.

Instale o [AWS CLI]('https://aws.amazon.com/pt/cli/') para configurar o seu usuário, e rode o comando:

```bash
aws configure
```

É necessário o `AWS_ACCESS_KEY_ID` e `AWS_SECRET_ACCESS_KEY` que é gerado no console da AWS para o usuário que vai ter as permissões para o S3.

### Configuração

O arquivo `application.properties` (ou `application.yml`) deve ser configurado com informações sensíveis, como credenciais do banco de dados, 
credenciais do Amazon S3.

Exemplo de configuração para o banco de dados e o Amazon S3:

```yaml
# POSTGRES
spring.datasource.url=${POSTGRES_URL}
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# S3
aws.storage.s3.key-id=${AWS_ACCESS_KEY_ID}
aws.storage.s3.secret-key=${AWS_SECRET_ACCESS_KEY}
aws.storage.s3.bucket-name=${AWS_BUCKET_NAME}
aws.storage.s3.region=us-east-1
```
### Postgres

Seria interessante usar uma instância docker do Postgres, basta rodar o seguinte comando: 

```bash
docker run -d \
  --name nome-do-seu-container \
  -e POSTGRES_USER=seu-usuario \
  -e POSTGRES_PASSWORD=sua-senha \
  -e POSTGRES_DB=seu-banco-de-dados \
  -p 5432:5432 \
  postgres:latest
```
