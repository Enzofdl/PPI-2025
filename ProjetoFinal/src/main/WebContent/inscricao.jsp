<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Gerenciamento de Inscrições</title>
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
            rel="stylesheet"
    />
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<jsp:include page="components/header.jsp"/>
<div class="container">
    <header class="header">
        <div style="display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap; gap: 15px;">
            <div>
                <h1>Gerenciamento de Inscrições</h1>
                <p>Sistema Integrado ao Banco de Dados</p>
            </div>
            <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none; display: inline-flex; align-items: center; gap: 8px;">
                <span>🏠</span>
                <span>Voltar para Home</span>
            </a>
        </div>
    </header>

    <section class="form-section">
        <h2>Adicionar Nova Inscrição</h2>
        <form id="form-inscricao" class="user-form">
            <div class="form-group">
                <label for="idUsuario">Usuário</label>
                <select
                        id="idUsuario"
                        name="idUsuario"
                        style="
								padding: 10px;
								border: 1px solid #ddd;
								border-radius: 4px;
								width: 100%;
							"
                        required
                >
                    <option value="" disabled selected>Carregando usuários...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="idEvento">Evento</label>
                <select
                        id="idEvento"
                        name="idEvento"
                        style="
								padding: 10px;
								border: 1px solid #ddd;
								border-radius: 4px;
								width: 100%;
							"
                        required
                >
                    <option value="" disabled selected>Carregando eventos...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="dataInscricao">Data da Inscrição</label>
                <input
                        type="date"
                        id="dataInscricao"
                        name="dataInscricao"
                        required
                />
            </div>

            <button type="submit" class="btn btn-primary">
                <span class="btn-icon">+</span>
                Adicionar Inscrição
            </button>
        </form>
    </section>

    <section class="list-section">
        <div class="list-header">
            <h2>Inscrições Cadastradas</h2>
            <span class="user-count" id="inscricao-count">Carregando...</span>
        </div>
        <div class="user-list" id="inscricao-list"></div>
    </section>

</div>

<!-- MODAL DE EDICAO -->
<div id="modal-edicao" class="modal hidden">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Editar Inscrição</h3>
            <button class="modal-close" onclick="fecharModal()">&times;</button>
        </div>
        <form id="form-edicao" class="user-form">
            <input type="hidden" id="edit-id"/>

            <div class="form-group">
                <label for="edit-idUsuario">Usuário</label>
                <select
                        id="edit-idUsuario"
                        name="idUsuario"
                        style="
								padding: 10px;
								border: 1px solid #ddd;
								border-radius: 4px;
								width: 100%;
							"
                        required
                >
                    <option value="" disabled selected>Carregando...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="edit-idEvento">Evento</label>
                <select
                        id="edit-idEvento"
                        name="idEvento"
                        style="
								padding: 10px;
								border: 1px solid #ddd;
								border-radius: 4px;
								width: 100%;
							"
                        required
                >
                    <option value="" disabled selected>Carregando...</option>
                </select>
            </div>

            <div class="form-group">
                <label for="edit-dataInscricao">Data da Inscrição</label>
                <input
                        type="date"
                        id="edit-dataInscricao"
                        name="dataInscricao"
                        required
                />
            </div>

            <div class="modal-actions">
                <button
                        type="button"
                        class="btn btn-secondary"
                        onclick="fecharModal()"
                >
                    Cancelar
                </button>
                <button type="submit" class="btn btn-primary">
                    Salvar Alterações
                </button>
            </div>
        </form>
    </div>
</div>

<script src="js/inscricao.js"></script>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
