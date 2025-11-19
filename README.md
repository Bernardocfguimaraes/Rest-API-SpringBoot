API REST - Spring Boot Example
Este projeto é uma API REST desenvolvida em Java com Spring Boot focada em demonstrar a implementação de segurança com Spring Security e JWT (JSON Web Token).

Tecnologias Utilizadas
Java

Spring Boot (Web, Data JPA, Security)

JWT (Jjwt) para autenticação stateless

BCrypt para criptografia de senhas

H2 / PostgreSQL / MySQL (Dependendo da configuração do seu application.properties)

Funcionalidades
Cadastro de usuários (Signup).

Autenticação de usuários (Signin) com geração de Token JWT.

Proteção de rotas (/ws/**) exigindo token válido.

Exemplo de endpoint seguro (Greeting).
