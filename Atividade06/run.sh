#!/bin/bash

echo "🚀 Iniciando Plataforma de Eventos..."
echo ""

# Verifica se Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado!"
    echo "Instale com: brew install maven"
    exit 1
fi

# Pergunta se quer recriar banco (opcional)
# Verifica MySQL local ou Docker
# MYSQL_RUNNING=false
# if pgrep -x "mysqld" > /dev/null 2>&1; then
#     MYSQL_RUNNING=true
# elif docker ps --filter "ancestor=mysql" --filter "status=running" -q 2>/dev/null | grep -q .; then
#     MYSQL_RUNNING=true
# elif docker ps --filter "name=mysql" --filter "status=running" -q 2>/dev/null | grep -q .; then
#     MYSQL_RUNNING=true
# fi

# if [ "$MYSQL_RUNNING" = true ]; then
#     echo "🔄 Deseja recriar o banco de dados? (Apaga todos os dados existentes)"
#     read -p "(s/n) " -n 1 -r
#     echo ""
#     if [[ $REPLY =~ ^[Ss]$ ]]; then
#         ./recreate-db.sh
#         if [ $? -ne 0 ]; then
#             echo "❌ Falha ao recriar banco. Continuando mesmo assim..."
#         fi
#     fi
# fi

echo "📦 Compilando projeto..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "❌ Erro ao compilar projeto"
    exit 1
fi

echo ""
echo "✅ Compilação concluída!"
echo ""
echo "🌐 Iniciando servidor Tomcat na porta 8080..."
echo ""
echo "📍 URLs disponíveis:"
echo "   - Home: http://localhost:8080/"
echo "   - Categorias: http://localhost:8080/categoria.html"
echo "   - Usuários: http://localhost:8080/usuario.html"
echo "   - Eventos: http://localhost:8080/evento.html"
echo "   - Inscrições: http://localhost:8080/inscricao.html"
echo ""
echo "Pressione Ctrl+C para parar o servidor"
echo ""

mvn tomcat7:run

