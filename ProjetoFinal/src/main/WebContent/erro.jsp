<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Erro de Autenticação</title>
    <link rel="stylesheet" href="css/global.css">
    <style>
        .error-container {
            max-width: 500px;
            margin: 100px auto;
            text-align: center;
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(220, 38, 38, 0.15);
            border: 1px solid #fee2e2;
        }
        .icon-error {
            font-size: 4rem;
            margin-bottom: 20px;
            display: block;
        }
        .timer-text {
            margin-top: 20px;
            color: #666;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

<div class="error-container">
    <span class="icon-error">🚫</span>

    <h2 style="color: var(--color-danger); margin-bottom: 15px;">Acesso Negado</h2>

    <p style="font-size: 1.1rem; color: var(--color-text);">
        <%= request.getAttribute("msgErro") %>
    </p>

</div>

<script>
    // Lógica do Timer
    let tempo = 3;
    const timer = setInterval(() => {
        tempo--;
        if (tempo <= 0) {
            clearInterval(timer);
            window.location.href = 'login.jsp'; // Redireciona de volta
        }
    }, 1000);
</script>

</body>
</html>