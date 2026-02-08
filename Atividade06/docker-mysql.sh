#!/bin/bash

echo "🐳 Iniciando MySQL em Docker..."
echo ""

# Verifica se Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não encontrado!"
    echo "Instale em: https://www.docker.com/get-started"
    exit 1
fi

# Nome do container
CONTAINER_NAME="mysql-eventos"
MYSQL_PASSWORD="admin"

# Verifica se já existe
if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "Container já existe. Iniciando..."
    docker start $CONTAINER_NAME
else
    echo "Criando novo container MySQL..."
    docker run -d \
        --name $CONTAINER_NAME \
        -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD \
        -p 3306:3306 \
        mysql:8.0
    
    echo ""
    echo "⏳ Aguardando MySQL inicializar (30 segundos)..."
    sleep 30
fi

echo ""
echo "✅ MySQL rodando em Docker!"
echo ""
echo "📝 Configurações:"
echo "   Container: $CONTAINER_NAME"
echo "   Porta: 3306"
echo "   Usuário: root"
echo "   Senha: $MYSQL_PASSWORD"
echo ""
echo "Para parar: docker stop $CONTAINER_NAME"
echo "Para remover: docker rm $CONTAINER_NAME"

