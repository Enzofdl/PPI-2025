@echo off
echo.
echo Iniciando Plataforma de Eventos...
echo.

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Maven nao encontrado!
    echo Instale em: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

echo Compilando projeto...
call mvn clean package

if %errorlevel% neq 0 (
    echo Erro ao compilar projeto
    pause
    exit /b 1
)

echo.
echo Compilacao concluida!
echo.
echo Iniciando servidor Tomcat na porta 8080...
echo.
echo URLs disponiveis:
echo    - Home: http://localhost:8080/
echo    - Categorias: http://localhost:8080/categoria.html
echo    - Usuarios: http://localhost:8080/usuario.html
echo    - Eventos: http://localhost:8080/evento.html
echo    - Inscricoes: http://localhost:8080/inscricao.html
echo.
echo Pressione Ctrl+C para parar o servidor
echo.

call mvn tomcat7:run

