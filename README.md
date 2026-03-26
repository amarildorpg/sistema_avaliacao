# 📋 Sistemas de Avaliação API

API REST desenvolvida com **Spring Boot** para gerenciamento de usuários com autenticação via **JWT** e controle de acesso com **Spring Security**.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **MariaDB**
- **Flyway** (migrations)
- **JWT** (Auth0)
- **Lombok**
- **BCrypt** (criptografia de senhas)

---

## 📁 Estrutura do Projeto

```
br.com.datanorte.sistemas_avaliacao
├── config
│   ├── ApplicationConfigAdvice.java
│   ├── JWTUserData.java
│   ├── SecurityConfig.java
│   ├── SecurityFilter.java
│   └── TokenConfig.java
├── controller
│   ├── request
│   │   ├── RedefinirSenhaRequest
│   │   ├── RecuperarSenhaRequest.java
│   │   ├── LoginRequest.java
│   │   └── UsuarioRequestDTO.java
│   ├── response
│   │   ├── LoginResponse.java
│   │   └── UsuarioResponseDTO.java
│   └── UsuarioController.java
├── entity
│   ├── UsuarioToken.java
│   └── Usuario.java
├── enums
│   ├── Perfil.java
│   ├── TokenTipo.java
│   └── Status.java
├── exception
│   ├── ErrorResponse.java
│   ├── UserInactiveException.java
│   ├── UsernameOrPasswordInvalidException.java
│   └── UserNotLoggedInException.java
├── mapper
│   └── UsuarioMapper.java
├── repository
│   ├── UsuarioTokenRepository.java
│   └── UsuarioRepository.java
└── service
    ├── AuthorizationService.java
    ├── EmailService.java
    └── UsuarioService.java
```

---

## ⚙️ Configuração

### Pré-requisitos

- Java 17+
- MariaDB rodando na porta `3307`
- Maven

### Variáveis de Ambiente

Configure as seguintes variáveis antes de rodar a aplicação:

| Variável | Descrição |
|---|---|
| `DB_PASSWORD` | Senha do banco de dados |
| `JWT_SECRET` | Chave secreta para geração do token JWT |

### application.yml

```yaml
spring:
  application:
    name: sistemas_avaliacao
  datasource:
    url: jdbc:mariadb://localhost:3307/sistemas_avaliacao
    username: usuario
    password: senha
    driver-class-name: org.mariadb.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  flyway:
    enabled: true
    locations: classpath:db/migration
  mail:
    host: smtp.gmail.com
    port: 587
    username: seu email
    password: sua senha gerada
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

jwt:
  secret: sua_secret
  expiration: 86400000
```

---

## 🔗 Endpoints

### 🔓 Públicos (sem autenticação)

| Método | Rota                                      | Descrição |
|---|-------------------------------------------|---|
| `POST` | `/datanorte/users/register`               | Cadastro de novo usuário(envia email de ativação) |
| `GET` | `/datanorte/users/activate?token=<token>` | Ativa conta via token recebido por email |
| `POST` | `/datanorte/users/login`                  | Login e geração do token JWT |
| `POST` | `/datanorte/users/recover-password`       | Solicita recuperação de senha (envia email com token) |
| `POST` | `/datanorte/users/reset-password`         | Reseta senha usando token enviado por email |

### 🔒 Protegidos (requer token JWT)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/datanorte/users/all` | Lista todos os usuários |
| `GET` | `/datanorte/users/{id}` | Busca usuário por ID |
| `PATCH` | `/datanorte/users/{id}` | Atualiza usuário |
| `DELETE` | `/datanorte/users/{id}` | Desativa usuário (soft delete) |

---

## 🔐 Autenticação

A API utiliza autenticação **JWT Bearer Token**.

1. Faça login em `POST /datanorte/users/login`
2. Copie o token retornado
3. Adicione no header das requisições protegidas:

```
Authorization: Bearer <seu-token>
```

## ✉️ Ativação de Conta por Email

- Ao se cadastrar, o usuário recebe um email com um link de ativação.
- O link tem a forma:
```
GET /datanorte/users/activate?token=<token>
```
- O token expira em algumas horas (configurável).
- Somente usuários ativados podem entrar no sistema.

---

## 🔑 Recuperação de Senha

1. Usuário solicita recuperação de senha:
```json
POST /datanorte/users/recover-password
{
  "email": "usuario@email.com"
}
```
2. Um token único é enviado para o email do usuário.
3. Usuário reseta a senha usando o token:
```json
POST /datanorte/users/reset-password
{
  "token": "<token_recebido>",
  "novaSenha": "senha123"
}
```
- O token expira em 2 horas.
- Após reset, o token é invalidado.

## 👤 Perfis de Usuário

| Perfil | Descrição |
|---|---|
| `ADMIN` | Administrador do sistema |
| `USER` | Usuário comum |

---

## 📦 Exemplo de Requisições

### Cadastro de usuário

```json
POST /datanorte/users/register
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123",
  "perfil": "USER",
  "status": "INATIVO"
}
```

### Login

```json
POST /datanorte/users/login
{
  "email": "joao@email.com",
  "password": "senha123"
}
```

### Resposta do login

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 🗄️ Banco de Dados

As migrations são gerenciadas pelo **Flyway** e ficam em:

```
src/main/resources/db/migration
```

---

## ▶️ Como Rodar

```bash
# Clone o repositório
git clone https://github.com/amarildorpg/sistemas-avaliacao.git

# Entre na pasta
cd sistemas-avaliacao

# Rode com Maven
./mvnw spring-boot:run
```

---

## 👨‍💻 Autor

Desenvolvido por **Amarildo Silva**
