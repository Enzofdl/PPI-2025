<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plataforma de Eventos - Home</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/index.css">
</head>

<body>
<jsp:include page="components/header.jsp"/>

<div class="nav-cards">
    <a href="categoria.jsp" class="nav-card categorias">
        <span class="nav-card-icon">📁</span>
        <h2>Categorias</h2>
        <p>Gerencie as categorias</p>
    </a>

    <a href="usuario.jsp" class="nav-card usuarios">
        <span class="nav-card-icon">👥</span>
        <h2>Usuários</h2>
        <p>Gerencie usuários</p>
    </a>

    <a href="evento.jsp" class="nav-card eventos">
        <span class="nav-card-icon">📅</span>
        <h2>Eventos</h2>
        <p>Organize eventos</p>
    </a>

    <a href="inscricao.jsp" class="nav-card inscricoes">
        <span class="nav-card-icon">✅</span>
        <h2>Inscrições</h2>
        <p>Gerencie inscrições</p>
    </a>

    <a href="configuracoes.jsp" class="nav-card configuracoes">
        <span class="nav-card-icon">⚙️</span>
        <h2>Configurações</h2>
        <p>Envio de documentos e preferências</p>
    </a>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>