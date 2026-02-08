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

// Mapeamento duplo para suportar listagem e ações do front-end
@WebServlet(urlPatterns = {"/api/categorias", "/api/categorias/acao"})
public class CategoriaServlet extends HttpServlet {

    private CategoriaDAO dao;

    // 1. INIT: Inicialização
    @Override
    public void init() {
        System.out.println("CategoriaServlet: init() - Inicializando recursos...");
        dao = new CategoriaDAO();
    }

    // 2. SERVICE: Monitoramento
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CategoriaServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    // 3. DESTROY: Limpeza
    @Override
    public void destroy() {
        System.out.println("CategoriaServlet: destroy() - Encerrando servlet...");
        dao = null;
    }

    // GET: Listar Categorias
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Nota: Verifique se no seu DAO o método chama 'listar' ou 'listarTodas'
            // Mantive 'listarTodas' conforme seu código original.
            List<Categoria> categorias = dao.listarTodas();
            PrintWriter out = resp.getWriter();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < categorias.size(); i++) {
                Categoria c = categorias.get(i);

                json.append("{");
                json.append("\"id\":").append(c.getId()).append(",");
                json.append("\"nome\":\"").append(tratarTexto(c.getNome())).append("\",");
                json.append("\"descricao\":\"").append(tratarTexto(c.getDescricao())).append("\"");
                json.append("}");

                if (i < categorias.size() - 1)
                    json.append(",");
            }
            json.append("]");
            out.print(json.toString());

        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    // POST: Inserir, Alterar e Deletar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String acao = req.getParameter("acao");

        try {
            if ("deletar".equals(acao)) {
                // --- AÇÃO: DELETAR ---
                int id = Integer.parseInt(req.getParameter("id"));
                dao.remover(id);

                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Categoria removida\"}");

            } else if ("alterar".equals(acao)) {
                // --- AÇÃO: ALTERAR ---
                int id = Integer.parseInt(req.getParameter("id"));
                String nome = req.getParameter("nome");
                String descricao = req.getParameter("descricao");

                // Validações
                String erro = validarDados(nome, descricao);
                if (erro != null) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"error\":\"" + erro + "\"}");
                    return;
                }

                Categoria c = new Categoria();
                c.setId(id);
                c.setNome(nome);
                c.setDescricao(descricao.trim());

                dao.atualizar(id, c);

                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Categoria atualizada\"}");

            } else {
                // --- AÇÃO: INSERIR (Padrão) ---
                String nome = req.getParameter("nome");
                String descricao = req.getParameter("descricao");

                // Validações
                String erro = validarDados(nome, descricao);
                if (erro != null) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"error\":\"" + erro + "\"}");
                    return;
                }

                Categoria c = new Categoria();
                c.setNome(nome);
                c.setDescricao(descricao.trim());

                dao.inserir(c);

                resp.setStatus(201);
                resp.getWriter().write("{\"success\":true, \"message\":\"Categoria criada\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + tratarTexto(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // Método auxiliar para validação (Evita repetir código no Inserir e Alterar)
    private String validarDados(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome da categoria é obrigatório";
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Descrição da categoria é obrigatória";
        }
        if (descricao.trim().length() > 500) {
            return "Descrição deve ter no máximo 500 caracteres";
        }
        return null; // Sem erros
    }

    // Método auxiliar para proteger o JSON
    private String tratarTexto(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"").replace("\n", " ");
    }
}