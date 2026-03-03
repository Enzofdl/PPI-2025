# Guia Rápido - Rodar sem IntelliJ

## 🚀 Setup Rápido (5 minutos)

### 1️⃣ Instalar Dependências

**macOS:**
```bash
brew install mysql maven
```

**Linux:**
```bash
sudo apt install mysql-server maven
```

**Windows:**
- MySQL: https://dev.mysql.com/downloads/installer/
- Maven: https://maven.apache.org/download.cgi

### 2️⃣ Configurar Banco

**MySQL via Docker (Recomendado):**
```bash
./docker-mysql.sh
# Inicia MySQL em container automaticamente
```

**MySQL Local:**
```bash
mysql.server start  # macOS
# ou: sudo service mysql start  # Linux
# ou: net start MySQL80  # Windows
```

**Criar banco:**
```bash
# Detecta automaticamente MySQL local ou Docker
./recreate-db.sh  # macOS/Linux
# ou: recreate-db.bat  # Windows
```

**Nota:** O `run.sh` detecta MySQL em Docker/local automaticamente e pergunta se quer recriar o banco.

### 3️⃣ Configurar Senha do Banco

Abra `src/main/java/Factory/ConnectionFactory.java` e ajuste:

```java
private static final String PASS = "sua_senha_aqui";
```

### 4️⃣ Rodar Aplicação

**macOS/Linux:**
```bash
./run.sh
```

**Windows:**
```cmd
run.bat
```

### 5️⃣ Acessar no Navegador

- **Home**: http://localhost:8080/
- **Categorias**: http://localhost:8080/categoria.html
- **Usuários**: http://localhost:8080/usuario.html
- **Eventos**: http://localhost:8080/evento.html
- **Inscrições**: http://localhost:8080/inscricao.html

---

## 🔧 Comandos Úteis

### Compilar projeto
```bash
mvn clean package
```

### Rodar servidor
```bash
mvn tomcat7:run
```

### Parar servidor
```
Ctrl + C
```

### Limpar build
```bash
mvn clean
```

---

## ❌ Problemas Comuns

### Maven não encontrado
```bash
# Verifique instalação
mvn -version

# Se não funcionar, instale novamente
brew install maven  # macOS
```

### MySQL não conecta
```bash
# Verifique se está rodando
mysql.server status  # macOS
# ou: sudo service mysql status  # Linux

# Teste conexão manual
mysql -u root -p
```

### Porta 8080 já em uso
```bash
# Encontre processo usando porta 8080
lsof -i :8080  # macOS/Linux
# ou: netstat -ano | findstr :8080  # Windows

# Mate o processo
kill -9 PID  # macOS/Linux
```

### Erro de compilação
```bash
# Limpe e compile novamente
mvn clean
mvn clean package
```

---

## 📦 Gerar WAR para Deploy

```bash
mvn clean package
```

O arquivo `eventos.war` estará em `target/eventos.war`

Pode copiar para `$TOMCAT_HOME/webapps/` de qualquer Tomcat.

