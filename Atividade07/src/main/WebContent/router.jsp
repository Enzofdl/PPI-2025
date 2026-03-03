<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Recupera o destino que o Servlet definiu
    String paginaDestino = (String) request.getAttribute("destinoFinal");

    // Segurança: se por algum motivo vier nulo, manda pro login
    if (paginaDestino == null) {
        paginaDestino = "login.jsp";
    }
%>

<jsp:forward page="<%= paginaDestino %>">
    <jsp:param name="origem" value="Redirecionado via Router.jsp" />
</jsp:forward>