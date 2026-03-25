# 🍽️ FoodHub Microservices

## 📌 Descrição
Sistema de pedidos online baseado em microserviços, utilizando Clean Architecture, DDD, comunicação síncrona (gRPC) e assíncrona (Kafka), com autenticação via JWT.

---

## 🏗️ Arquitetura

- Microservices Architecture  
- Clean Architecture + DDD  
- Comunicação síncrona: gRPC  
- Comunicação assíncrona: Kafka  
- Banco de dados: MongoDB  

---

## 📦 Serviços

- Auth Service  
- User Service  
- Restaurant Service  
- Order Service  
- Payment Service  

---

## 📊 Tecnologias

- Java 21  
- Spring Boot  
- GraphQL  
- MongoDB  
- Apache Kafka  
- Docker / Docker Compose  
- gRPC  

---

## 🔗 Endpoint

```
http://localhost:{port}/graphql
```

---

## 📚 Operações

### 🔐 Auth Service

| Operação | Tipo | Descrição |
|----------|------|----------|
| login | Mutation | Autentica usuário e retorna JWT |

---

### 👤 User Service

| Operação | Tipo | Descrição |
|----------|------|----------|
| createUser | Mutation | Cria usuário |
| updateUser | Mutation | Atualiza usuário |
| listUsers | Query | Lista usuários |
| me | Query | Usuário autenticado |

#### Tipos de Usuário

| Operação | Tipo | Descrição |
|----------|------|----------|
| createUserType | Mutation | Cria tipo |
| updateUserType | Mutation | Atualiza tipo |
| deleteUserType | Mutation | Remove tipo |
| listUserTypes | Query | Lista tipos |

#### Endereços

| Operação | Tipo | Descrição |
|----------|------|----------|
| createUserAddress | Mutation | Cria endereço |
| updateUserAddress | Mutation | Atualiza endereço |
| deleteUserAddress | Mutation | Remove endereço |
| listUserAddresses | Query | Lista endereços |

---

### 🍽️ Restaurant Service

#### Restaurantes

| Operação | Tipo | Descrição |
|----------|------|----------|
| createRestaurant | Mutation | Cria restaurante |
| updateRestaurant | Mutation | Atualiza restaurante |
| deleteRestaurant | Mutation | Remove restaurante |
| restaurantById | Query | Busca por ID |
| listRestaurants | Query | Lista paginada |

#### Usuários

| Operação | Tipo | Descrição |
|----------|------|----------|
| addUserToRestaurant | Mutation | Vincula usuário |
| removeUserFromRestaurant | Mutation | Remove vínculo |

#### Horários

| Operação | Tipo | Descrição |
|----------|------|----------|
| changeOpeningHours | Mutation | Atualiza horário |
| listOpeningHours | Query | Lista horários |

#### Menus

| Operação | Tipo | Descrição |
|----------|------|----------|
| createMenu | Mutation | Cria menu |
| deleteMenu | Mutation | Remove menu |
| listRestaurantMenus | Query | Lista menus |

#### Itens

| Operação | Tipo | Descrição |
|----------|------|----------|
| createMenuItem | Mutation | Cria item |
| updateMenuItem | Mutation | Atualiza item |
| deleteMenuItem | Mutation | Remove item |
| menuItemById | Query | Busca item |

---

### 🛒 Order Service

| Operação | Tipo | Descrição |
|----------|------|----------|
| createOrder | Mutation | Cria pedido |
| confirmOrder | Mutation | Confirma pedido |
| cancelOrder | Mutation | Cancela pedido |
| orderById | Query | Busca pedido |
| listOrdersByUser | Query | Lista pedidos |

---

## 🔄 Fluxo Principal

1. Criação do pedido  
2. Confirmação do pedido  
3. Evento enviado ao Kafka  
4. Payment Service processa pagamento  
5. Evento de retorno atualiza status  

---

## 🐳 Execução

### Subir ambiente

```
docker compose up -d --build
```

### Parar ambiente

```
docker compose down
```

---

## 📊 Status

| Status | Descrição |
|--------|----------|
| 200 | Sucesso |
| 400 | Erro de validação |
| 403 | Acesso negado |
| 404 | Não encontrado |
| 409 | Conflito |
| 500 | Erro interno |

---

## 📌 Observações

- API baseada em GraphQL  
- Endpoint único `/graphql`  
- Erros retornados no campo `errors`  
- Status detalhado em `extensions`  

---

## 🚀 Autor

Joyce Santos Mendes
