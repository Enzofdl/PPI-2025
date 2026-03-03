package Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // Validação
        if ("admin@teste.com".equals(email) && "123456".equals(senha)) {
            // Sucesso: Define atributos e o destino final
            req.setAttribute("usuarioLogado", "Administrador");
            req.setAttribute("destinoFinal", "dashboard.jsp");
        } else {
            // Erro: Define mensagem e o destino final
            req.setAttribute("msgErro", "Usuário ou senha inválidos!");
            req.setAttribute("destinoFinal", "erro.jsp");
        }

        // Encaminha para o JSP que contém a tag <jsp:forward>
        RequestDispatcher dispatcher = req.getRequestDispatcher("router.jsp");
        dispatcher.forward(req, resp);
    }
}