<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Gerenciamento de Categorias</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<jsp:include page="components/header.jsp"/>

<div class="container">
    <header class="header">
        <div style="display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap; gap: 15px;">
            <div>
                <h1>Gerenciamento de Categorias</h1>
                <p>Sistema Integrado ao Banco de Dados</p>
            </div>
            <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none;">
                <span>🏠</span> Voltar
            </a>
        </div>
    </header>

    <section class="form-section">
        <h2>Adicionar Nova Categoria</h2>
        <form id="form-categoria" class="user-form">
            <div class="form-group">
                <label for="nome">Nome da Categoria</label>
                <input type="text" id="nome" name="nome" required />
            </div>
            <div class="form-group">
                <label for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Adicionar</button>
        </form>
    </section>

    <section class="list-section">
        <div class="list-header">
            <h2>Categorias Cadastradas</h2>
            <span class="user-count" id="categoria-count">0</span>
        </div>
        <div class="user-list" id="categoria-list"></div>
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

<script src="js/categoria.js"></script>

<jsp:include page="components/footer.jsp"/>
</body>
</html>