<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">

<style>
    .hero-section {
        position: relative;
    }

    /* Container para alinhar o nome e o botão de sair */
    .header-user-info {
        position: absolute;
        top: 20px;
        right: 20px;
        display: flex;
        align-items: center;
        gap: 15px;
    }

    /* Estilo bacana para o nome do usuário */
    .user-greeting {
        font-size: 0.95rem;
        font-weight: 500;
        color: white;
        background: rgba(255, 255, 255, 0.2); /* Fundo translúcido */
        padding: 6px 15px;
        border-radius: 20px;
        backdrop-filter: blur(5px);
    }

    .btn-logout-circle {
        width: 50px;
        height: 50px;
        background-color: var(--color-card);
        color: var(--color-danger);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        text-decoration: none;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
        transition: all 0.3s ease;
    }

    .btn-logout-circle:hover {
        box-shadow: 0 8px 25px rgba(220, 38, 38, 0.3);
        background: linear-gradient(135deg, #ff758c 0%, var(--color-danger) 100%);
        color: white;
    }
</style>

<header class="hero-section">

    <div class="header-user-info">
        <%-- Recupera o nome da Sessão dinamicamente --%>
        <% if (session.getAttribute("usuarioLogado") != null) { %>
        <span class="user-greeting">
                👋 Olá, <%= session.getAttribute("usuarioLogado") %>
            </span>
        <% } %>

        <a href="login.jsp" class="btn-logout-circle" title="Sair do Sistema">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                <polyline points="16 17 21 12 16 7"></polyline>
                <line x1="21" y1="12" x2="9" y2="12"></line>
            </svg>
        </a>
    </div>

    <h1>🎉 Plataforma de Eventos</h1>
    <p>Sistema completo de gerenciamento de eventos, usuários, categorias e inscrições</p>
</header>