<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Login com Sucesso</title>
    <link rel="stylesheet" href="css/global.css">
    <style>
        .timer-box {
            background-color: #f0fdf4;
            color: #166534;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
            font-weight: bold;
            border: 1px solid #bbf7d0;
        }
    </style>
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container" style="text-align: center; padding: 50px;">

    <div class="hero-section" style="background: linear-gradient(135deg, #059669 0%, #34d399 100%);">
        <h1>Bem-vindo, <%= request.getAttribute("usuarioLogado") %>! 👋</h1>
        <p>Login realizado com sucesso via Servlet Forwarding.</p>
    </div>

    <div style="margin-top: 30px;">
        <p>Agora você tem acesso administrativo total.</p>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

<script>
    // 1. Define o tempo inicial
    let segundos = 3;

    // 2. Cria o intervalo que roda a cada 1000 milissegundos (1 segundo)
    const contagem = setInterval(function() {
        segundos--; // Diminui 1
        // 4. Se chegar em 0, redireciona
        if (segundos <= 0) {
            clearInterval(contagem); // Para o timer
            window.location.href = 'index.jsp'; // Redireciona
        }
    }, 1000);
</script>

</body>
</html>