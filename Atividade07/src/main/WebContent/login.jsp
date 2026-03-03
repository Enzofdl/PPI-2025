<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login - Plataforma de Eventos</title>
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>

<div class="login-container">
    <h2 style="margin-bottom: 20px;">Plataforma de Eventos</h2>

    <% if (request.getAttribute("msgErro") != null) { %>
    <div class="error-msg">
        <%= request.getAttribute("msgErro") %>
    </div>
    <% } %>

    <form action="auth" method="post" style="display: flex; flex-direction: column; gap: 15px;">

        <div style="text-align: left;">
            <label>E-mail</label>
            <input type="email" name="email" required
                   style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px;">
        </div>

        <div style="text-align: left;">
            <label>Senha</label>
            <input type="password" name="senha" required
                   style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px;">
        </div>

        <button type="submit" class="btn-primary"
                style="padding: 10px; background: #75e2f0; border: none; cursor: pointer; font-weight: bold;">
            Entrar
        </button>
    </form>
</div>

</body>
</html>