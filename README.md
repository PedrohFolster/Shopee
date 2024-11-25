# Shopee

Neste projeto, estamos desenvolvendo um sistema web responsivo, com o intuito de representar um marketplace, onde usuário podem procurar produtos para comprar ou então criar sua própria loja, para venderem seus produtos.


### Funcionalidades Principais:
- **Compra de produtos**: O usuário poderá procurar por um produto específico no site, adicioná-lo no seu carrinho e realizar o pedido.
- **Monte sua loja**: Você também poderá montar sua loja, e vender os produtos que desejar.

## Tecnologias Utilizadas

- **Backend**: Java com Spring Boot
- **Frontend**: React com JavaScript, HTML e CSS
- **Outras Tecnologias**: Docker

## API Endpoints

### Authentication
- `POST /authenticate`: Autentica o usuário e retorna um token JWT.

### Private
- `GET /private`: Retorna "Hello World".

### Public
- `GET /public`: Retorna "Ola, visualização publica".

### Categoria Produto
- `GET /categorias-p`: Retorna todas as categorias.
- `GET /categorias-p/{id}`: Retorna uma categoria por ID.
- `POST /categorias-p`: Cria uma nova categoria.
- `PUT /categorias-p/{id}`: Atualiza uma categoria existente.
- `DELETE /categorias-p/{id}`: Deleta uma categoria por ID.

### Login
- `POST /login`: Realiza o login e retorna um token JWT.

### Logout
- `POST /logout`: Realiza o logout.

### Loja
- `GET /lojas`: Retorna todas as lojas.
- `GET /lojas/{id}`: Retorna uma loja por ID.
- `GET /lojas/minha-loja`: Retorna a loja do usuário autenticado.
- `POST /lojas`: Cria uma nova loja.
- `PUT /lojas/{id}`: Atualiza uma loja existente.
- `DELETE /lojas/{id}`: Deleta uma loja por ID.
- `GET /lojas/verificar-loja`: Verifica se o usuário possui uma loja.
- `GET /lojas/search`: Pesquisa lojas por nome.
- `GET /lojas/{id}/publico`: Retorna informações públicas de uma loja.
- `GET /lojas/minha-loja/info`: Retorna informações da loja do usuário.

### Pedido
- `POST /pedidos/finalizar-compra`: Finaliza uma compra.
- `GET /pedidos`: Retorna pedidos do usuário autenticado.
- `GET /pedidos/itens-pedidos/{lojaId}`: Retorna itens vendidos por loja.
- `PUT /pedidos/{pedidoId}/itens/{itemId}/status`: Atualiza o status de um item do pedido.

### Produto
- `GET /produtos`: Retorna todos os produtos.
- `GET /produtos/{id}`: Retorna um produto por ID.
- `POST /produtos`: Cria um novo produto.
- `PUT /produtos/{id}`: Atualiza um produto existente.
- `DELETE /produtos/{id}`: Deleta um produto por ID.
- `GET /produtos/loja/{lojaId}`: Retorna produtos por loja e usuário.
- `GET /produtos/ativos`: Retorna produtos ativos.
- `GET /produtos/categoria/{categoriaProdutoId}`: Retorna produtos por categoria.
- `GET /produtos/{id}/loja`: Retorna o nome da loja de um produto.
- `GET /produtos/loja/{lojaId}/todos`: Retorna todos os produtos de uma loja.
- `GET /produtos/{produtoId}/imagem`: Retorna a imagem de um produto.

### Session
- `GET /validate-session`: Valida a sessão do usuário.

### Status
- `GET /status`: Retorna todos os status.
- `GET /status/{id}`: Retorna um status por ID.
- `POST /status`: Cria um novo status.
- `PUT /status/{id}`: Atualiza um status existente.
- `DELETE /status/{id}`: Deleta um status por ID.

### Status Pedido
- `GET /status-pedidos`: Retorna todos os status de pedidos.
- `GET /status-pedidos/{id}`: Retorna um status de pedido por ID.
- `POST /status-pedidos`: Cria um novo status de pedido.
- `PUT /status-pedidos/{id}`: Atualiza um status de pedido existente.
- `DELETE /status-pedidos/{id}`: Deleta um status de pedido por ID.

### Usuario
- `GET /usuarios`: Retorna todos os usuários.
- `GET /usuarios/{id}`: Retorna um usuário por ID.
- `POST /usuarios`: Cria um novo usuário.
- `PUT /usuarios/{id}`: Atualiza um usuário existente.
- `DELETE /usuarios/{id}`: Deleta um usuário por ID.
- `GET /usuarios/perfil`: Retorna o perfil do usuário autenticado.
- `POST /usuarios/validar-senha`: Valida a senha do usuário.
- `GET /usuarios/verificar`: Verifica a existência de email ou CPF.
- `PUT /usuarios/{id}/senha`: Atualiza a senha do usuário.
- `PUT /usuarios/{id}/endereco`: Atualiza o endereço do usuário.

## Swagger
A documentação da API pode ser acessada em [Swagger UI](http://localhost:8080/swagger-ui.html).
