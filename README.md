# Tutorial do Projeto.
Projeto feito com Spring Boot Start Web, Data e Security, com Maven, servidor embutido Tomcat e base de dados embutida H2.

## 1 - Ao baixar o projeto:
Executar o Maven update project, Run As Maven clean, (aqui é bom fazer um build ou Project Clean) ,Run As Maven install.

## 2 - Iniciar o projeto:
### 2.1 - IDE: 
Foi usada a nova versão do Eclipse, se tiver o plugin do Spring Boot - Run As Spring Boot App.
### 2.2 - IDE: 
Senão, executar o Run As Java Application, na classe main (/ApiMSSpringBootSecurityMavenH2/src/main/java/com/apimsspringbootsecuritymavenh2/ApiMsSpringBootSecurityMavenH2Application.java). 
### 2.3 - Jar: 
Executar o comando: java -jar, no arquivo jar do projeto (/ApiMSSpringBootSecurityMavenH2/target/ApiMSSpringBootSecurityMavenH2-0.0.1-SNAPSHOT.jar).

## 3 - Acessar a base, criar tabelas e inserir dados:
URL para acesso a base:
### http://localhost:8080/ApiMSSpringBootSecurityMavenH2/h2

Dados para acessar o console sql da base:
 - Generic H2 (Embedded)
 - org.h2.Driver
 - jdbc:h2:~/user_h2_db
 - sa
 - sa
 
### 3.1 Executar conteúdo do script:
/ApiMSSpringBootSecurityMavenH2/src/main/resources/scripts/script01.sql

## 4 Utilização via Postman - Acessar o Postman, e importar o arquivo:
/ApiMSSpringBootSecurityMavenH2/src/main/resources/scripts/ApiMSSpringBootSecurityMavenH2.postman_collection.json
### 4.1 Ordem de utilização do Postman:
 - Criar um usuário (Post), não tem validação (não precisa do token), tipo singUp das paginas de cadastro.
 - Logar na API (Login), no headers de retorno, copiar o Token que vem no atributo Authorization (ex: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb2FvIFRlc3RlIE9LIiwiZXhwIjoxNTYyOTEyMjAzfQ.4YIsOj04S9cf47qkOqypqmeB-Cj2QIdYpK5bVzj2KnfxvIWSMKCDS2e1s6bLd6Hc9c5v-BQdCl5uQt_9Cm1JLw), ele tem tempo de expiração (5min).
 - Usar os demais métodos conforme exemplos em json do Postman.
 
#Fim
