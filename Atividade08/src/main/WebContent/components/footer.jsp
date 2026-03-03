<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">

<footer class="hero-section">

    <p>&copy; 2026 Plataforma de Eventos - Todos os direitos reservados</p>
    <% java.util.Date data = new java.util.Date(); %>
    <small>Acessado em: <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(data) %></small>

</footer>