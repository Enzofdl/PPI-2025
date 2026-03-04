package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        String nomeLogado = null;

        // 1. Verificação de Segurança (Fallback / Backdoor)
        if ("admin@teste.com".equals(email) && "123456".equals(senha)) {
            nomeLogado = "Administrador";
        } else {
            // 2. Se não for o admin, vai no banco verificar as credenciais reais
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuarioValido = dao.autenticar(email, senha);

            if (usuarioValido != null) {
                nomeLogado = usuarioValido.getNome();
            }
        }

        // 3. Validação Final e Encaminhamento
        if (nomeLogado != null) {
            // Sucesso: Cria a SESSÃO para o header.jsp
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", nomeLogado);

            // Sucesso: Cria o REQUEST para o dashboard.jsp não ficar "null"
            req.setAttribute("usuarioLogado", nomeLogado);

            req.setAttribute("destinoFinal", "dashboard.jsp");
        } else {
            // Erro
            req.setAttribute("msgErro", "Usuário ou senha inválidos!");
            req.setAttribute("destinoFinal", "erro.jsp");
        }

        // Roteador
        RequestDispatcher dispatcher = req.getRequestDispatcher("router.jsp");
        dispatcher.forward(req, resp);
    }
}