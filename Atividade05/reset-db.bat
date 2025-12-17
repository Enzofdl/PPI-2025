@echo off
echo.
echo Recriando banco de dados vazio...
echo.

REM Configurações
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=root

REM Executa script SQL
mysql -h%MYSQL_HOST% -P%MYSQL_PORT% -u%MYSQL_USER% -p%MYSQL_PASSWORD% < database-schema.sql 2>nul

if %errorlevel% equ 0 (
    echo.
    echo Banco de dados recriado com sucesso!
    echo.
    echo Banco criado:
    echo    - Nome: Plataforma_de_Eventos
    echo    - Tabelas: categoria, usuario, evento, inscricao
    echo    - Dados: Apenas exemplos basicos
    echo.
) else (
    echo.
    echo Erro ao recriar banco de dados!
    echo.
)

pause

