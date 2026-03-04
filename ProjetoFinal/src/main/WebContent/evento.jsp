<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Eventos</title>
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
                <h1>Gerenciamento de Eventos</h1>
                <p>Sistema Integrado ao Banco de Dados</p>
            </div>
            <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none; display: inline-flex; align-items: center; gap: 8px;">
                <span>🏠</span>
                <span>Voltar para Home</span>
            </a>
        </div>
    </header>

    <section class="form-section">
        <h2>Adicionar Novo Evento</h2>
        <form id="form-evento" class="user-form">
            <div class="form-group">
                <label for="nome">Nome do Evento</label>
                <input type="text" id="nome" name="nome" placeholder="Ex: Tech Conference 2025" required>
            </div>

            <div class="form-group">
                <label for="data">Data</label>
                <input type="date" id="data" name="data" required>
            </div>

            <div class="form-group">
                <label for="local">Local</label>
                <input type="text" id="local" name="local" placeholder="Ex: Centro de Convenções" required>
            </div>

            <div class="form-group">
                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria" style="padding: 10px; border: 1px solid #ddd; border-radius: 4px; width: 100%;" required>
                    <option value="" disabled selected>Carregando categorias...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="imagem">Banner/Imagem do Evento</label>
                <input type="file" id="imagem" name="imagem" accept="image/*">
            </div>

            <button type="submit" class="btn btn-primary">
                <span class="btn-icon">+</span>
                Adicionar Evento
            </button>
        </form>
    </section>

    <section class="list-section">
        <div class="list-header">
            <h2>Eventos Cadastrados</h2>
            <span class="user-count" id="event-count">Carregando...</span>
        </div>
        <div class="user-list" id="event-list"></div>
    </section>

</div>

<div id="modal-edicao" class="modal hidden">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Editar Evento</h3>
            <button class="modal-close" onclick="fecharModal()">&times;</button>
        </div>
        <form id="form-edicao" class="user-form">
            <input type="hidden" id="edit-id">

            <div class="form-group">
                <label for="edit-nome">Nome do Evento</label>
                <input type="text" id="edit-nome" name="nome" required>
            </div>

            <div class="form-group">
                <label for="edit-data">Data</label>
                <input type="date" id="edit-data" name="data" required>
            </div>

            <div class="form-group">
                <label for="edit-local">Local</label>
                <input type="text" id="edit-local" name="local" required>
            </div>

            <div class="form-group">
                <label for="edit-categoria">Categoria</label>
                <select id="edit-categoria" name="categoria" style="padding: 10px; border: 1px solid #ddd; border-radius: 4px; width: 100%;" required>
                    <option value="" disabled selected>Carregando...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="edit-imagem">Nova Imagem (Opcional)</label>
                <input type="file" id="edit-imagem" name="imagem" accept="image/*">
            </div>

            <div class="modal-actions">
                <button type="button" class="btn btn-secondary" onclick="fecharModal()">
                    Cancelar
                </button>
                <button type="submit" class="btn btn-primary">
                    Salvar Alteracoes
                </button>
            </div>
        </form>
    </div>
</div>

<script src="js/evento.js"></script>
<jsp:include page="components/footer.jsp"/>
</body>
</html>