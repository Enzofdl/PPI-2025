@echo off
echo.
echo Recriando banco de dados...
echo.

REM Verifica se MySQL está acessível
mysql --version >nul 2>nul
if %errorlevel% neq 0 (
    echo MySQL nao encontrado no PATH!
    echo Verifique a instalacao do MySQL
    pause
    exit /b 1
)

REM Solicita senha
set /p MYSQL_PASSWORD=Digite a senha do MySQL (root): 

REM Executa script SQL
mysql -u root -p%MYSQL_PASSWORD% < recreate-database.sql

if %errorlevel% equ 0 (
    echo.
    echo Banco de dados recriado com sucesso!
    echo.
    echo Dados de exemplo inseridos:
    echo    - 5 Categorias
    echo    - 4 Usuarios
    echo    - 5 Eventos
    echo    - 5 Inscricoes
    echo.
) else (
    echo.
    echo Erro ao recriar banco de dados!
    echo Verifique a senha e tente novamente.
    echo.
)

pause

