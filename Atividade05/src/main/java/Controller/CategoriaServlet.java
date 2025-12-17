package Controller;

import DAO.CategoriaDAO;
import Model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/categorias")
public class CategoriaServlet extends HttpServlet {

    private CategoriaDAO dao;

    @Override
    public void init() {
        System.out.println("CategoriaServlet: init() - Servlet inicializado");
        dao = new CategoriaDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CategoriaServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("CategoriaServlet: destroy() - Servlet destruído");
        dao = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Categoria> categorias = dao.listarTodas();

        PrintWriter out = resp.getWriter();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < categorias.size(); i++) {
            Categoria c = categorias.get(i);
            String descricao = c.getDescricao() != null ? c.getDescricao() : "";
            json.append(String.format("{\"id\":%d, \"nome\":\"%s\", \"descricao\":\"%s\"}", 
                c.getId(), c.getNome(), descricao));
            if (i < categorias.size() - 1) json.append(",");
        }
        json.append("]");

        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

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

        try {
            Categoria c = new Categoria();
            c.setNome(nome);
            c.setDescricao(descricao.trim());

            dao.inserir(c);

            resp.setStatus(201);
            resp.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}