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
import java.util.Enumeration;
import java.util.TimeZone;

@WebServlet("/api/inscricoes")
public class InscricaoServlet extends HttpServlet {

    private InscricaoDAO dao;

    @Override
    public void init() {
        System.out.println("InscricaoServlet: init() - Servlet inicializado");
        dao = new InscricaoDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out
                .println("InscricaoServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("InscricaoServlet: destroy() - Servlet destruído");
        dao = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Inscricao> inscricoes = dao.listarComDetalhes();
        PrintWriter out = resp.getWriter();

        // SimpleDateFormat com timezone UTC para não aplicar conversão
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < inscricoes.size(); i++) {
            Inscricao insc = inscricoes.get(i);

            json.append("{");
            json.append("\"id\":").append(insc.getId()).append(",");
            json.append("\"idUsuario\":").append(insc.getIdUsuario()).append(",");
            json.append("\"idEvento\":").append(insc.getIdEvento()).append(",");
            json.append("\"dataInscricao\":\"").append(sdf.format(insc.getDataInscricao())).append("\",");
            json.append("\"nomeUsuario\":\"").append(insc.getNomeUsuario()).append("\",");
            json.append("\"nomeEvento\":\"").append(insc.getNomeEvento()).append("\"");
            json.append("}");

            if (i < inscricoes.size() - 1)
                json.append(",");
        }
        json.append("]");
        out.print(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idUsuario = req.getParameter("idUsuario");
        String idEvento = req.getParameter("idEvento");
        String dataInscricao = req.getParameter("dataInscricao");

        // Validação
        if (idUsuario == null || idEvento == null || dataInscricao == null) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Parametros invalidos\"}");
            return;
        }

        try {
            Inscricao i = new Inscricao();
            i.setIdUsuario(Integer.parseInt(idUsuario));
            i.setIdEvento(Integer.parseInt(idEvento));
            i.setDataInscricao(Date.valueOf(dataInscricao));

            dao.inserir(i);

            resp.setStatus(201);
            resp.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
