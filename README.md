🐾 API REST: Sistema de Adoção de Animais (Spring Boot/JWT)
Este projeto é uma API RESTful desenvolvida em Java com Spring Boot, que serve como backend para um sistema de cadastro e adoção de animais. O foco principal é demonstrar a implementação de um sistema de Autenticação Stateless utilizando Spring Security e JWT (JSON Web Token).

✨ Recursos Principais
Cadastro de animais com localização geográfica (PIN).

Suporte a Upload de Foto (Multipart) para o cadastro dos animais.

Lógica de adoção que marca animais como indisponíveis.

Segurança robusta com proteção de rotas via JWT.

💻 Tecnologias e Dependências
As principais tecnologias utilizadas neste projeto são:

Linguagem: Java

Framework: Spring Boot (Web, Data JPA, Security)

Segurança: Spring Security (Proteção de rotas e filtros).

Autenticação: JWT (JSON Web Token) para geração de Tokens.

Criptografia: BCrypt para hashing de senhas.

Banco de Dados: H2, PostgreSQL ou MySQL (configurável).

🌐 Endpoints da API
A API é acessada através da URL base http://localhost:8080.

🔑 Autenticação (Livre)
/api/auth/signin (POST): Autentica o usuário e gera o Token JWT.

/api/auth/signup (POST): Cadastra um novo usuário.

🛡️ Rotas Protegidas (Token Exigido)
Cadastro (POST /api/animais):

Envia dados do animal e arquivo de foto usando o formato multipart/form-data.

Localização (GET /api/animais/disponiveis):

Lista todos os animais não adotados, retornando as coordenadas (PINs) para exibição no mapa.

Adoção (PATCH /api/animais/{id}/adotar):

Atualiza o status do animal para adotado, associando-o ao User logado.

Exemplo (GET /greeting):

Endpoint de demonstração que exige um Token válido.

🛠️ Configuração e Instalação
1. Pré-requisitos
JDK 17 ou superior

Maven 3.6+

Banco de dados relacional (PostgreSQL recomendado)

2. Execução
Clone o repositório:

Bash

git clone https://github.com/Bernardocfguimaraes/Rest-API-SpringBoot.git
cd Rest-API-SpringBoot
Configure a conexão com seu banco de dados no arquivo src/main/resources/application.properties.

Execute a aplicação via Maven:

Bash

./mvnw spring-boot:run
A API estará disponível em http://localhost:8080.

3. Documentação (Swagger UI)
A documentação interativa da API, essencial para testar e entender o formato de envio de dados, está disponível no seu navegador em:

http://localhost:8080/swagger-ui.html
