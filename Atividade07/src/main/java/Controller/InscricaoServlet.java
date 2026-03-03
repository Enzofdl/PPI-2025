package Controller;

import DAO.InscricaoDAO;
import Model.Inscricao;

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

// Mapeia as duas rotas (Listar/Inserir e Ações de Alterar/Deletar)
@WebServlet(urlPatterns = {"/api/inscricoes", "/api/inscricoes/acao"})
public class InscricaoServlet extends HttpServlet {

    private InscricaoDAO dao;

    // 1. INIT: Inicializa o DAO uma única vez
    @Override
    public void init() {
        System.out.println("InscricaoServlet: init() - Inicializando recursos...");
        dao = new InscricaoDAO();
    }

    // 2. SERVICE: Intercepta todas as requisições
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("InscricaoServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    // 3. DESTROY: Limpa a referência do DAO ao desligar
    @Override
    public void destroy() {
        System.out.println("InscricaoServlet: destroy() - Liberando recursos...");
        dao = null;
    }

    // LISTAR (GET): Retorna o JSON com os dados detalhados (joins)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Inscricao> inscricoes = dao.listarComDetalhes();
            PrintWriter out = resp.getWriter();

            // Formatação de data para JSON
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < inscricoes.size(); i++) {
                Inscricao insc = inscricoes.get(i);

                json.append("{");
                json.append("\"id\":").append(insc.getId()).append(",");
                json.append("\"idUsuario\":").append(insc.getIdUsuario()).append(",");
                json.append("\"idEvento\":").append(insc.getIdEvento()).append(",");

                // Tratamento para data (evita null pointer)
                String dataFmt = (insc.getDataInscricao() != null) ? sdf.format(insc.getDataInscricao()) : "";
                json.append("\"dataInscricao\":\"").append(dataFmt).append("\",");

                // Tratamento de aspas para nomes (segurança básica de JSON)
                json.append("\"nomeUsuario\":\"").append(tratarTexto(insc.getNomeUsuario())).append("\",");
                json.append("\"nomeEvento\":\"").append(tratarTexto(insc.getNomeEvento())).append("\"");
                json.append("}");

                if (i < inscricoes.size() - 1)
                    json.append(",");
            }
            json.append("]");
            out.print(json.toString());

        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    // POST: Centraliza Inserir, Alterar e Deletar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String acao = req.getParameter("acao");

        try {
            if ("deletar".equals(acao)) {
                // --- DELETAR ---
                int id = Integer.parseInt(req.getParameter("id"));
                dao.remover(id);
                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Inscrição removida\"}");

            } else if ("alterar".equals(acao)) {
                // --- ALTERAR ---
                int id = Integer.parseInt(req.getParameter("id"));
                String idUsuario = req.getParameter("idUsuario");
                String idEvento = req.getParameter("idEvento");
                String dataInscricao = req.getParameter("dataInscricao");

                Inscricao i = new Inscricao();
                i.setId(id);
                i.setIdUsuario(Integer.parseInt(idUsuario));
                i.setIdEvento(Integer.parseInt(idEvento));
                // O valueOf espera formato yyyy-MM-dd
                i.setDataInscricao(Date.valueOf(dataInscricao));

                dao.atualizar(id, i);
                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Inscrição atualizada\"}");

            } else {
                // --- INSERIR (PADRÃO) ---
                String idUsuario = req.getParameter("idUsuario");
                String idEvento = req.getParameter("idEvento");
                String dataInscricao = req.getParameter("dataInscricao"); // Data pode vir automática ou manual

                // Validação básica
                if (idUsuario == null || idEvento == null) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"error\":\"Parâmetros inválidos\"}");
                    return;
                }

                Inscricao i = new Inscricao();
                i.setIdUsuario(Integer.parseInt(idUsuario));
                i.setIdEvento(Integer.parseInt(idEvento));

                // Se vier data, usa. Se não, o banco ou construtor pode definir a atual.
                if (dataInscricao != null && !dataInscricao.isEmpty()) {
                    i.setDataInscricao(Date.valueOf(dataInscricao));
                } else {
                    i.setDataInscricao(new Date(System.currentTimeMillis()));
                }

                dao.inserir(i);
                resp.setStatus(201);
                resp.getWriter().write("{\"success\":true, \"message\":\"Inscrição realizada\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}");
            e.printStackTrace();
        }
    }

    // Auxiliar para JSON manual
    private String tratarTexto(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"").replace("\n", " ");
    }
}