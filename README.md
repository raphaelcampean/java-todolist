# java-todolist

# ToDo List API - Spring Boot 

Esta é uma API RESTful de gerenciamento de tarefas (To-Do List) desenvolvida durante o curso de Spring Boot da **Rocketseat**. O projeto foca em conceitos sólidos de Back-end, como autenticação customizada, persistência de dados e segurança de senhas.

## 🛠️ Tecnologias Utilizadas

* **Java 17** (ou a versão que você usou)
* **Spring Boot 3**
* **Spring Data JPA**
* **H2 Database** (Banco de dados em memória para desenvolvimento)
* **BCrypt** (Criptografia de senhas)
* **Lombok** (Produtividade e código limpo)
* **Maven** (Gerenciador de dependências)

## Funcionalidades e Conceitos Aplicados

* **Autenticação Basic Auth:** Implementação de um `Filter` customizado para interceptar requisições e validar credenciais.
* **Criptografia:** Uso do algoritmo BCrypt para garantir que as senhas não sejam salvas em texto simples no banco de dados.
* **Relacionamentos:** Associação entre `Usuários` e `Tarefas` utilizando UUIDs para maior segurança e escalabilidade.
* **Validações:** Regras de negócio para impedir que tarefas sejam criadas com datas retroativas ou datas de término anteriores ao início.
* **Update Parcial (PATCH):** Implementação de uma classe utilitária (`Utils`) para realizar o merge de dados, permitindo atualizar apenas campos específicos de uma tarefa sem sobrescrever os demais com nulo.

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/raphaelcampean/todolist-spring-boot.git](https://github.com/raphaelcampean/todolist-spring-boot.git)

2. Entre na pasta do projeto:

cd todolist-spring-boot

3. Execute o Maven para baixar as dependências e rodar a aplicação:

mvn spring-boot:run

4. A API estará disponível em http://localhost:8080.

Endpoints Principais
POST /users: Cadastro de novos usuários.

POST /tasks: Criação de tarefas (Requer autenticação).

GET /tasks: Listagem de tarefas do usuário logado.

PUT /tasks/{id}: Atualização completa de uma tarefa.

PATCH /tasks/{id}: Atualização parcial de uma tarefa.
