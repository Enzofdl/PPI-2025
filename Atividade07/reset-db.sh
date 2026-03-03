#!/bin/bash

echo "🔄 Recriando banco de dados vazio..."
echo ""

# Configurações
MYSQL_HOST="localhost"
MYSQL_PORT="3306"
MYSQL_USER="root"
MYSQL_PASSWORD="root"

# Detecta se MySQL está em Docker
DOCKER_CONTAINER=$(docker ps --filter "ancestor=mysql" --filter "status=running" --format "{{.Names}}" 2>/dev/null | head -n 1)

if [ -z "$DOCKER_CONTAINER" ]; then
    DOCKER_CONTAINER=$(docker ps --filter "name=mysql" --filter "status=running" --format "{{.Names}}" 2>/dev/null | head -n 1)
fi

if [ ! -z "$DOCKER_CONTAINER" ]; then
    # MySQL em Docker
    echo "🐳 Executando no container Docker: $DOCKER_CONTAINER"
    
    if docker exec -i "$DOCKER_CONTAINER" mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" < database-schema.sql 2>/dev/null; then
        echo ""
        echo "✅ Banco de dados recriado com sucesso!"
        echo ""
        echo "📊 Banco criado:"
        echo "   - Nome: Plataforma_de_Eventos"
        echo "   - Tabelas: categoria, usuario, evento, inscricao"
        echo "   - Dados: Apenas exemplos básicos"
        echo ""
    else
        echo ""
        echo "❌ Erro ao recriar banco de dados!"
        exit 1
    fi
else
    # MySQL local
    echo "💻 Executando no MySQL local"
    
    if mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" < database-schema.sql 2>/dev/null; then
        echo ""
        echo "✅ Banco de dados recriado com sucesso!"
        echo ""
        echo "📊 Banco criado:"
        echo "   - Nome: Plataforma_de_Eventos"
        echo "   - Tabelas: categoria, usuario, evento, inscricao"
        echo "   - Dados: Apenas exemplos básicos"
        echo ""
    else
        echo ""
        echo "❌ Erro ao recriar banco de dados!"
        exit 1
    fi
fi

