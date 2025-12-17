package Controller;

import DAO.CategoriaDAO;
import Model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/categorias/acao")
public class CategoriaAcaoServlet extends HttpServlet {
    private CategoriaDAO dao;

    @Override
    public void init() {
        System.out.println("CategoriaAcaoServlet: init() - Servlet inicializado");
        dao = new CategoriaDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CategoriaAcaoServlet: service() - Ação: " + req.getParameter("acao"));
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("CategoriaAcaoServlet: destroy() - Servlet destruído");
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
            String descricao = req.getParameter("descricao");

            // Validação do nome
            if (nome == null || nome.trim().isEmpty()) {
                resp.setStatus(400);
                resp.getWriter().write("{\"error\":\"Nome da categoria é obrigatório\"}");
                return;
            }

            // Validação da descrição
            if (descricao == null || descricao.trim().isEmpty()) {
                resp.setStatus(400);
                resp.getWriter().write("{\"error\":\"Descrição da categoria é obrigatória\"}");
                return;
            }

            if (descricao.trim().length() > 500) {
                resp.setStatus(400);
                resp.getWriter().write("{\"error\":\"Descrição deve ter no máximo 500 caracteres\"}");
                return;
            }

            Categoria c = new Categoria();
            c.setId(id);
            c.setNome(nome);
            c.setDescricao(descricao.trim());

            dao.atualizar(id, c);
        }
    }
}
