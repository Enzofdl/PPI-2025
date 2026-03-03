<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Configurações do Usuário</title>
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
                <h1>Configurações do Usuário</h1>
                <p>Envie seus documentos de identidade e dados bancários.</p>
            </div>
            <a href="index.jsp" class="btn btn-secondary" style="text-decoration: none;">
                <span>🏠</span> Voltar
            </a>
        </div>
    </header>

    <section class="form-section">
        <h2>Enviar Documentos</h2>
        <form action="UploadServlet" method="post" enctype="multipart/form-data" class="user-form">
            <div class="form-group">
                <label for="identityFile">Arquivo de Identidade (PDF ou Imagem)</label>
                <input type="file" id="identityFile" name="identityFile" accept="application/pdf,image/*" required />
            </div>

            <div class="form-group">
                <label for="bankDataFile">Dados Bancários (PDF ou Imagem)</label>
                <input type="file" id="bankDataFile" name="bankDataFile" accept="application/pdf,image/*" required />
            </div>

            <button type="submit" class="btn btn-primary">Enviar</button>
        </form>
    </section>

    <section class="list-section">
        <h2>Arquivos Enviados</h2>
        <div class="list-header">
            <p>Se você já enviou arquivos, poderá baixá-los abaixo.</p>
        </div>
        <div class="user-list">
            <%-- Exibe links para download se os nomes estiverem armazenados na sessão --%>
            <%
                String identityName = (String) session.getAttribute("identityFileName");
                String bankName = (String) session.getAttribute("bankDataFileName");
                if (identityName == null && bankName == null) {
            %>
                <p>Nenhum arquivo enviado ainda.</p>
            <%
                } else {
            %>
                <ul>
                    <%
                        if (identityName != null) {
                    %>
                    <li>Identidade: <a href="DownloadServlet?file=<%= java.net.URLEncoder.encode(identityName, "UTF-8") %>"><%= identityName %></a></li>
                    <%
                        }
                        if (bankName != null) {
                    %>
                    <li>Dados Bancários: <a href="DownloadServlet?file=<%= java.net.URLEncoder.encode(bankName, "UTF-8") %>"><%= bankName %></a></li>
                    <%
                        }
                    %>
                </ul>
            <%
                }
            %>
        </div>
    </section>
</div>

<jsp:include page="components/footer.jsp"/>
</body>
</html>