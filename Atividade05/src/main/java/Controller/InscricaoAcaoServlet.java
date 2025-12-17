package Controller;

import DAO.InscricaoDAO;
import Model.Inscricao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/api/inscricoes/acao")
public class InscricaoAcaoServlet extends HttpServlet {
    private InscricaoDAO dao;

    @Override
    public void init() {
        System.out.println("InscricaoAcaoServlet: init() - Servlet inicializado");
        dao = new InscricaoDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("InscricaoAcaoServlet: service() - Ação: " + req.getParameter("acao"));
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("InscricaoAcaoServlet: destroy() - Servlet destruído");
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
            String idUsuario = req.getParameter("idUsuario");
            String idEvento = req.getParameter("idEvento");
            String dataInscricao = req.getParameter("dataInscricao");

            Inscricao i = new Inscricao();
            i.setId(id);
            i.setIdUsuario(Integer.parseInt(idUsuario));
            i.setIdEvento(Integer.parseInt(idEvento));
            i.setDataInscricao(Date.valueOf(dataInscricao));

            dao.atualizar(id, i);
        }
    }
}

