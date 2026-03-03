<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Usuários</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<jsp:include page="components/header.jsp"/>
<div class="container">

    <header class="header">
        <div style="display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap; gap: 15px;">
            <div>
                <h1>Gerenciamento de Usuários</h1>
                <p>Sistema Integrado ao Banco de Dados</p>
            </div>
            <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none; display: inline-flex; align-items: center; gap: 8px;">
                <span>🏠</span>
                <span>Voltar para Home</span>
            </a>
        </div>
    </header>

    <section class="form-section">
        <h2>Adicionar Novo Usuário</h2>
        <form id="form-usuario" class="user-form">
            <div class="form-group">
                <label for="nome">Nome Completo</label>
                <input type="text" id="nome" name="nome" placeholder="Ex: João Silva" required>
            </div>

            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" placeholder="Ex: joao@email.com" required>
            </div>

            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="tel" id="telefone" name="telefone" placeholder="Ex: (11) 99999-9999" required>
            </div>

            <button type="submit" class="btn btn-primary">
                <span class="btn-icon">+</span>
                Adicionar Usuário
            </button>
        </form>
    </section>

    <section class="list-section">
        <div class="list-header">
            <h2>Usuários Cadastrados</h2>
            <span class="user-count" id="usuario-count">Carregando...</span>
        </div>
        <div class="user-list" id="usuario-list"></div>
    </section>

</div>

<!-- MODAL DE EDICAO -->
<div id="modal-edicao" class="modal hidden">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Editar Usuário</h3>
            <button class="modal-close" onclick="fecharModal()">&times;</button>
        </div>
        <form id="form-edicao" class="user-form">
            <input type="hidden" id="edit-id">

            <div class="form-group">
                <label for="edit-nome">Nome Completo</label>
                <input type="text" id="edit-nome" name="nome" required>
            </div>

            <div class="form-group">
                <label for="edit-email">E-mail</label>
                <input type="email" id="edit-email" name="email" required>
            </div>

            <div class="form-group">
                <label for="edit-telefone">Telefone</label>
                <input type="tel" id="edit-telefone" name="telefone" required>
            </div>

            <div class="modal-actions">
                <button type="button" class="btn btn-secondary" onclick="fecharModal()">
                    Cancelar
                </button>
                <button type="submit" class="btn btn-primary">
                    Salvar Alterações
                </button>
            </div>
        </form>
    </div>
</div>

<script src="js/usuario.js"></script>
<jsp:include page="components/footer.jsp"/>
</body>
</html>

