# Gerenciamento de Livros

Aplicação Java com Spring Boot para gerenciar os livros de uma biblioteca pessoal.
O projeto oferece uma API REST com as operações de cadastrar, listar, consultar,
atualizar e excluir livros.

## Tecnologias

- Java 21
- Spring Boot 3.5.6
- Maven
- JUnit 5 e MockMvc

## Como abrir no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Selecione **Open** e escolha a pasta deste projeto.
3. Aguarde o Maven baixar as dependências.
4. Execute a classe `GerenciamentoDeLivrosApplication`.

A API ficará disponível em `http://localhost:8080`.

## Endpoints

| Método | Caminho | Função |
|---|---|---|
| `POST` | `/livros` | Cadastrar um livro |
| `GET` | `/livros` | Listar todos os livros |
| `GET` | `/livros/{id}` | Consultar um livro pelo identificador |
| `PUT` | `/livros/{id}` | Atualizar um livro |
| `DELETE` | `/livros/{id}` | Excluir um livro |

Exemplo de corpo para cadastro e atualização:

```json
{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "genero": "Romance",
  "anoPublicacao": 1899,
  "statusLeitura": "Lido"
}
```

Os campos são obrigatórios. O identificador é criado automaticamente.

## Executar pelo terminal

```bash
mvn spring-boot:run
```

## Executar os testes

```bash
mvn test
```

Os testes automatizados demonstram todas as operações do CRUD, o retorno para um
identificador inexistente e a validação dos campos obrigatórios.

## Armazenamento

Os livros são armazenados em memória para manter o foco nos conceitos de Java,
orientação a objetos e Spring Boot solicitados no trabalho. Por isso, os dados são
apagados quando a aplicação é encerrada.
