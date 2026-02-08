package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// MAPEIA AS DUAS URLS PARA O MESMO SERVLET (Para manter compatibilidade com seu Front)
@WebServlet(urlPatterns = {"/api/usuarios", "/api/usuarios/acao"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO dao;

    // 1. INIT: Ciclo de vida - Inicialização
    @Override
    public void init() {
        System.out.println("UsuarioServlet: init() - Inicializando recursos e DAO...");
        dao = new UsuarioDAO();
    }

    // 2. SERVICE: Ciclo de vida - Monitoramento de requisições
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsuarioServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());

        // Chamada obrigatória ao super.service para distribuir para doGet/doPost
        super.service(req, resp);
    }

    // 3. DESTROY: Ciclo de vida - Finalização
    @Override
    public void destroy() {
        System.out.println("UsuarioServlet: destroy() - Encerrando servlet e liberando recursos...");
        dao = null;
    }

    // GET: Lista os usuários (JSON)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Usuario> usuarios = dao.listar();
        PrintWriter out = resp.getWriter();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            json.append("{");
            json.append("\"id\":").append(u.getId()).append(",");
            // Tratamento simples para evitar quebra de JSON com aspas no nome
            json.append("\"nome\":\"").append(u.getNome() != null ? u.getNome().replace("\"", "\\\"") : "").append("\",");
            json.append("\"email\":\"").append(u.getEmail() != null ? u.getEmail() : "").append("\",");
            json.append("\"telefone\":\"").append(u.getTelefone() != null ? u.getTelefone() : "").append("\"");
            json.append("}");

            if (i < usuarios.size() - 1) json.append(",");
        }
        json.append("]");
        out.print(json.toString());
    }

    // POST: Gerencia Inserção, Edição e Exclusão
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Verifica se veio uma "acao" específica (do JS)
        String acao = req.getParameter("acao");

        if ("deletar".equals(acao)) {
            // Lógica de Remover
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.remover(id);
                resp.setStatus(200);
            } catch (NumberFormatException e) {
                resp.setStatus(400); // Erro na requisição
            }

        } else if ("alterar".equals(acao)) {
            // Lógica de Alterar
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                String nome = req.getParameter("nome");
                String email = req.getParameter("email");
                String telefone = req.getParameter("telefone");

                Usuario u = new Usuario();
                u.setId(id); // Importante setar o ID para o UPDATE funcionar
                u.setNome(nome);
                u.setEmail(email);
                u.setTelefone(telefone);

                dao.atualizar(id, u);
                resp.setStatus(200);
            } catch (Exception e) {
                resp.setStatus(400);
            }

        } else {
            // Se não tiver ação, é um cadastro NOVO (Inserir)
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            Usuario u = new Usuario();
            u.setNome(nome);
            u.setEmail(email);
            u.setTelefone(telefone);

            dao.inserir(u);
            resp.setStatus(201); // Created
        }
    }
}