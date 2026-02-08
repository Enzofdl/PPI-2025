package Controller;

import DAO.EventoDAO;
import Model.Categoria;
import Model.Evento;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

// Mapeamento duplo para suportar listagem e ações do front-end
@WebServlet(urlPatterns = {"/api/eventos", "/api/eventos/acao"})
public class EventoServlet extends HttpServlet {

    private EventoDAO dao;

    // 1. INIT: Inicialização do Servlet e do DAO
    @Override
    public void init() {
        System.out.println("EventoServlet: init() - Inicializando recursos...");
        dao = new EventoDAO();
    }

    // 2. SERVICE: Monitoramento das requisições
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EventoServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    // 3. DESTROY: Limpeza de recursos
    @Override
    public void destroy() {
        System.out.println("EventoServlet: destroy() - Encerrando servlet...");
        dao = null;
    }

    // GET: Listar Eventos (JSON)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Evento> eventos = dao.listarTodos();
            PrintWriter out = resp.getWriter();

            // Formatação de data para o JSON (padrão ISO yyyy-MM-dd)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < eventos.size(); i++) {
                Evento e = eventos.get(i);

                json.append("{");
                json.append("\"id\":").append(e.getId()).append(",");
                json.append("\"nome\":\"").append(tratarTexto(e.getNome())).append("\",");
                // Evita erro se a data vier nula do banco
                String dataFormatada = (e.getData() != null) ? sdf.format(e.getData()) : "";
                json.append("\"data\":\"").append(dataFormatada).append("\",");

                json.append("\"local\":\"").append(tratarTexto(e.getLocal())).append("\",");

                // Tratamento para categoria (caso venha nula)
                String nomeCategoria = (e.getCategoria() != null) ? tratarTexto(e.getCategoria().getNome()) : "";
                int idCategoria = (e.getCategoria() != null) ? e.getCategoria().getId() : 0;

                json.append("\"categoria\":\"").append(nomeCategoria).append("\",");
                json.append("\"categoriaId\":").append(idCategoria);
                json.append("}");

                if (i < eventos.size() - 1)
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
                resp.getWriter().write("{\"success\":true, \"message\":\"Evento removido\"}");

            } else if ("alterar".equals(acao)) {
                // --- AÇÃO: ALTERAR ---
                int id = Integer.parseInt(req.getParameter("id"));
                String nome = req.getParameter("nome");
                String data = req.getParameter("data");
                String local = req.getParameter("local");
                String catId = req.getParameter("categoria");

                Evento e = new Evento();
                e.setId(id);
                e.setNome(nome);
                e.setData(Date.valueOf(data));
                e.setLocal(local);

                Categoria c = new Categoria();
                c.setId(Integer.parseInt(catId));
                e.setCategoria(c);

                dao.atualizar(id, e);

                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Evento atualizado\"}");

            } else {
                // --- AÇÃO: INSERIR (Padrão) ---
                String nome = req.getParameter("nome");
                String data = req.getParameter("data");
                String local = req.getParameter("local");
                String catId = req.getParameter("categoria");

                // Validação básica
                if(nome == null || data == null || catId == null) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"error\":\"Dados incompletos\"}");
                    return;
                }

                Evento e = new Evento();
                e.setNome(nome);
                e.setData(Date.valueOf(data));
                e.setLocal(local);

                Categoria c = new Categoria();
                c.setId(Integer.parseInt(catId));
                e.setCategoria(c);

                dao.inserir(e);

                resp.setStatus(201);
                resp.getWriter().write("{\"success\":true, \"message\":\"Evento criado\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + tratarTexto(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // Método auxiliar para proteger o JSON contra aspas e quebras de linha
    private String tratarTexto(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"").replace("\n", " ");
    }
}