#!/bin/bash

echo "🔄 Recriando banco de dados..."
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Detecta se MySQL está em Docker
DOCKER_CONTAINER=$(docker ps --filter "ancestor=mysql" --filter "status=running" --format "{{.Names}}" 2>/dev/null | head -n 1)

if [ -z "$DOCKER_CONTAINER" ]; then
    DOCKER_CONTAINER=$(docker ps --filter "name=mysql" --filter "status=running" --format "{{.Names}}" 2>/dev/null | head -n 1)
fi

if [ ! -z "$DOCKER_CONTAINER" ]; then
    # MySQL em Docker
    echo -e "${BLUE}🐳 MySQL detectado em Docker (container: $DOCKER_CONTAINER)${NC}"
    echo -e "${YELLOW}Digite a senha do MySQL (root):${NC}"
    read -s MYSQL_PASSWORD
    
    # Executa via Docker
    if docker exec -i "$DOCKER_CONTAINER" mysql -u root -p"$MYSQL_PASSWORD" < recreate-database.sql 2>/dev/null; then
        echo ""
        echo -e "${GREEN}✅ Banco de dados recriado com sucesso!${NC}"
        echo ""
        echo "📊 Dados de exemplo inseridos:"
        echo "   - 5 Categorias"
        echo "   - 4 Usuários"
        echo "   - 5 Eventos"
        echo "   - 5 Inscrições"
        echo ""
    else
        echo ""
        echo -e "${RED}❌ Erro ao recriar banco de dados!${NC}"
        echo "Verifique a senha e tente novamente."
        exit 1
    fi
else
    # MySQL local
    if ! pgrep -x "mysqld" > /dev/null; then
        echo -e "${RED}❌ MySQL não está rodando!${NC}"
        echo "Inicie com: mysql.server start"
        echo "Ou inicie o container Docker"
        exit 1
    fi
    
    echo -e "${YELLOW}Digite a senha do MySQL (root):${NC}"
    read -s MYSQL_PASSWORD
    
    # Executa localmente
    if mysql -u root -p"$MYSQL_PASSWORD" < recreate-database.sql 2>/dev/null; then
        echo ""
        echo -e "${GREEN}✅ Banco de dados recriado com sucesso!${NC}"
        echo ""
        echo "📊 Dados de exemplo inseridos:"
        echo "   - 5 Categorias"
        echo "   - 4 Usuários"
        echo "   - 5 Eventos"
        echo "   - 5 Inscrições"
        echo ""
    else
        echo ""
        echo -e "${RED}❌ Erro ao recriar banco de dados!${NC}"
        echo "Verifique a senha e tente novamente."
        exit 1
    fi
fi

