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
│   │   ├── LoginRequest.java
│   │   └── UsuarioRequestDTO.java
│   ├── response
│   │   ├── LoginResponse.java
│   │   └── UsuarioResponseDTO.java
│   └── UsuarioController.java
├── entity
│   └── Usuario.java
├── enums
│   ├── Perfil.java
│   └── Status.java
├── exception
│   ├── ErrorResponse.java
│   ├── UserInactiveException.java
│   ├── UsernameOrPasswordInvalidException.java
│   └── UserNotLoggedInException.java
├── mapper
│   └── UsuarioMapper.java
├── repository
│   └── UsuarioRepository.java
└── service
    ├── AuthorizationService.java
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
  datasource:
    url: jdbc:mariadb://localhost:3307/sistemas_avaliacao
    username: root
    password: ${DB_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400
```

---

## 🔗 Endpoints

### 🔓 Públicos (sem autenticação)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/datanorte/users/register` | Cadastro de novo usuário |
| `POST` | `/datanorte/users/login` | Login e geração do token JWT |

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

---

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
