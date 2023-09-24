# orange-tech-calendar-api

## EndPoints
### Autenticação
  - Registrar Usuário: POST - 
    https://orange-tech-calendar-api-production.up.railway.app/auth/registrar<br><br>
    O corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
          "login": "example@email.com",
          "nome": "nome",
          "password": "senha",
          "userRole": "ADMIN" ou "USER"
    }
    ```
    <br><br>
  - Login: POST -
    https://orange-tech-calendar-api-production.up.railway.app/auth/login<br><br>
    O corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
          "login": "example@email.com",
          "password": "senha",
    }
    ```
    <br><br>
    Esta requisição devolve um token, que deve ser armazenado no navegador, e enviado no Header das próximas requisições como um Authorization: Bearer token.<br><br>
  - Esqueceu senha: POST -
    https://orange-tech-calendar-api-production.up.railway.app/auth/esqueceu-senha<br><br>
    O corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
          "email": "example@email.com",
    }
    ```
    <br><br>
  - Resetar senha: POST -
    https://orange-tech-calendar-api-production.up.railway.app/auth/resetar-senha/token<br><br>
    O link de recuperação será enviado. Na url requisição é enviado um token de reset. No corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
          "novaSenha": "novaSenha",
    }
    ```
    <br><br>
<hr>

### Eventos
  - Registrar Evento: POST - 
    https://orange-tech-calendar-api-production.up.railway.app/eventos
    O corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
      "nome": "Nome do evento",
      "descricao": "Descricao do evento",
      "local": "Local do evento",
      "link": "Site do evento",
      "dataInicio": "2023-12-30T19:00:00.000",
      "dataFinal": "2023-12-30T22:00:00.000",
      "gratuito": true,
      "preco": 0,
      "tipo": "hackathon",
      "modalidade": "online"
    }
    ```
    Tipos: evento, conferencia, palestra, workshop, hackathon, bootcamp<br>
    Modalidades: presencial, online<br>
    <br><br>
  - Listar Evento: GET - 
    https://orange-tech-calendar-api-production.up.railway.app/eventos
    <br><br>
  - Deletar Evento: DELETE - 
    https://orange-tech-calendar-api-production.up.railway.app/eventos/idEvento
    <br><br>
  - Adicionar usuário ao Evento: POST - 
    https://orange-tech-calendar-api-production.up.railway.app/eventos/idEvento/inscricao
    No endpoint é passado o id do evento, no corpo da requisição o id do usuário que está se cadastrando.
    O corpo da requisição deve ser enviado com um json no formato:<br>
    ```
    {
      "id": id
    }
    ```
    <br><br>
