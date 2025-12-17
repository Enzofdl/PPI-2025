package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/usuarios/acao")
public class UsuarioAcaoServlet extends HttpServlet {
    private UsuarioDAO dao;

    @Override
    public void init() {
        System.out.println("UsuarioAcaoServlet: init() - Servlet inicializado");
        dao = new UsuarioDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsuarioAcaoServlet: service() - Ação: " + req.getParameter("acao"));
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("UsuarioAcaoServlet: destroy() - Servlet destruído");
        dao = null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        if ("deletar".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.remover(id);
        } else if ("alterar".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            Usuario u = new Usuario();
            u.setId(id);
            u.setNome(nome);
            u.setEmail(email);
            u.setTelefone(telefone);

            dao.atualizar(id, u);
        }
    }
}

