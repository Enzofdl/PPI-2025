# Plataforma de Eventos

Sistema web para gerenciamento de eventos, usuários, categorias e inscrições.

## Estrutura do Projeto

```
src/main/
├── java/
│   ├── Controller/      # Servlets (API REST)
│   │   ├── CategoriaServlet.java
│   │   ├── EventoServlet.java
│   │   ├── EventoAcaoServlet.java
│   │   ├── UsuarioServlet.java
│   │   ├── InscricaoServlet.java
│   │   └── InscricaoAcaoServlet.java
│   ├── DAO/            # Camada de acesso a dados
│   │   ├── CategoriaDAO.java
│   │   ├── EventoDAO.java
│   │   ├── UsuarioDAO.java
│   │   └── InscricaoDAO.java
│   ├── Model/          # Modelos de dados
│   │   ├── Categoria.java
│   │   ├── Evento.java
│   │   ├── Usuario.java
│   │   └── Inscricao.java
│   └── Factory/
│       └── ConnectionFactory.java
└── WebContent/
    ├── evento.html     # Interface de eventos
    ├── inscricao.html  # Interface de inscrições
    ├── js/
    │   ├── evento.js
    │   └── inscricao.js
    └── css/
        └── styles.css
```

## Tecnologias

- Java 21
- Java Servlets
- MySQL 8.0
- Apache Tomcat 10
- HTML5 + CSS3 + JavaScript (Vanilla)

## Como Rodar

### 1. Configurar MySQL

**Opção A: MySQL via Docker (Recomendado)**

```bash
./docker-mysql.sh
# Inicia MySQL em container Docker automaticamente
# Porta: 3306, User: root, Senha: admin
```

**Opção B: MySQL Local**

```bash
# Instalar MySQL (se não tiver)
brew install mysql  # macOS
# ou apt install mysql-server  # Linux

# Iniciar MySQL
mysql.server start  # macOS
# ou sudo service mysql start  # Linux
```

**Criar/Recriar Banco de Dados:**

```bash
# Detecta automaticamente MySQL local ou Docker
./recreate-db.sh

# Ou manualmente
mysql -u root -p < database-schema.sql
```

O script `recreate-db.sh` detecta automaticamente se MySQL está em Docker ou local e se integra ao `run.sh`.

### 2. Configurar Credenciais do Banco

Edite `src/main/java/Factory/ConnectionFactory.java`:

```java
private static final String USER = "root";
private static final String PASS = "admin";  // Mude para sua senha
```

### 3. Rodar o Projeto

#### Opção A: Via Terminal (Maven - Recomendado)

**macOS/Linux:**

```bash
# Instalar Maven (se não tiver)
brew install maven  # macOS
# ou apt install maven  # Linux

# Executar script de inicialização
./run.sh
```

**Windows:**

```cmd
# Instalar Maven: https://maven.apache.org/download.cgi

# Executar script de inicialização
run.bat
```

**Manualmente (qualquer SO):**

```bash
# Compilar
mvn clean package

# Rodar servidor
mvn tomcat7:run
```

#### Opção B: Via IntelliJ IDEA

1. Abra o projeto no IntelliJ
2. Configure o Tomcat:
   - **Run** → **Edit Configurations** → **+** → **Tomcat Server** → **Local**
   - **Deployment** tab → **+** → **Artifact** → `Atividade05:war exploded`
   - **Application context**: `/` ou `/Atividade05`
3. Clique em **Run**

### 4. Acessar o Sistema

Abra no navegador:

- **Home**: http://localhost:8080/ ou http://localhost:8080/index.html
- **Categorias**: http://localhost:8080/categoria.html
- **Usuários**: http://localhost:8080/usuario.html
- **Eventos**: http://localhost:8080/evento.html
- **Inscrições**: http://localhost:8080/inscricao.html

## APIs Disponíveis

### Eventos

- `GET /api/eventos` - Lista todos os eventos
- `POST /api/eventos` - Cria novo evento
- `POST /api/eventos/acao?acao=alterar` - Atualiza evento
- `POST /api/eventos/acao?acao=deletar` - Remove evento

### Categorias

- `GET /api/categorias` - Lista todas as categorias
- `POST /api/categorias` - Cria nova categoria
- `POST /api/categorias/acao?acao=alterar` - Atualiza categoria
- `POST /api/categorias/acao?acao=deletar` - Remove categoria

### Usuários

- `GET /api/usuarios` - Lista todos os usuários
- `POST /api/usuarios` - Cria novo usuário
- `POST /api/usuarios/acao?acao=alterar` - Atualiza usuário
- `POST /api/usuarios/acao?acao=deletar` - Remove usuário

### Inscrições

- `GET /api/inscricoes` - Lista todas as inscrições
- `POST /api/inscricoes` - Cria nova inscrição
- `POST /api/inscricoes/acao?acao=alterar` - Atualiza inscrição
- `POST /api/inscricoes/acao?acao=deletar` - Remove inscrição

## Estrutura do Banco

```sql
categoria (id, nome, descricao)
usuario (id, nome, email, telefone)
evento (id, nome, local_evento, data_evento, id_categoria)
inscricao (id, id_usuario, id_evento, data_inscricao)
```

## Funcionalidades

### Categorias

- ✅ Listar categorias cadastradas
- ✅ Adicionar nova categoria
- ✅ Editar categoria existente
- ✅ Remover categoria

### Usuários

- ✅ Listar usuários cadastrados
- ✅ Adicionar novo usuário
- ✅ Editar usuário existente
- ✅ Remover usuário
- ✅ Validação de e-mail único

### Eventos

- ✅ Listar eventos cadastrados
- ✅ Adicionar novo evento
- ✅ Editar evento existente
- ✅ Remover evento
- ✅ Filtrar por categoria

### Inscrições

- ✅ Listar inscrições cadastradas
- ✅ Adicionar nova inscrição
- ✅ Editar inscrição existente
- ✅ Remover inscrição
- ✅ Visualizar usuário e evento relacionados

## Troubleshooting

### Erro de conexão com banco

```
Erro: Falha ao conectar com o banco
```

**Solução**: Verifique se o MySQL está rodando e se as credenciais estão corretas em `ConnectionFactory.java`

### Driver JDBC não encontrado

```
Erro: Driver JDBC nao encontrado
```

**Solução**: Verifique se `mysql-connector-java-8.0.11.jar` está em `src/main/WebContent/WEB-INF/lib/`

### Erro 404

**Solução**: Verifique o Application Context configurado no Tomcat
