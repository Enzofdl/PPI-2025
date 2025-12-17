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
import java.sql.Date;

@WebServlet("/api/eventos/acao")
public class EventoAcaoServlet extends HttpServlet {
    private EventoDAO dao;

    @Override
    public void init() {
        System.out.println("EventoAcaoServlet: init() - Servlet inicializado");
        dao = new EventoDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EventoAcaoServlet: service() - Ação: " + req.getParameter("acao"));
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("EventoAcaoServlet: destroy() - Servlet destruído");
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
        }
    }
}