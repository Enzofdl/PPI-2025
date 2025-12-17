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

@WebServlet("/api/eventos")
public class EventoServlet extends HttpServlet {

    private EventoDAO dao;

    @Override
    public void init() {
        System.out.println("EventoServlet: init() - Servlet inicializado");
        dao = new EventoDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EventoServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("EventoServlet: destroy() - Servlet destruído");
        dao = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Evento> eventos = dao.listarTodos();
        PrintWriter out = resp.getWriter();

        // SimpleDateFormat com timezone UTC para não aplicar conversão
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);

            json.append("{");
            json.append("\"id\":").append(e.getId()).append(",");
            json.append("\"nome\":\"").append(e.getNome()).append("\",");
            json.append("\"data\":\"").append(sdf.format(e.getData())).append("\",");
            json.append("\"local\":\"").append(e.getLocal()).append("\",");
            json.append("\"categoria\":\"").append(e.getCategoria().getNome()).append("\",");
            json.append("\"categoriaId\":").append(e.getCategoria().getId());
            json.append("}");

            if (i < eventos.size() - 1)
                json.append(",");
        }
        json.append("]");
        out.print(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nome = req.getParameter("nome");
        String data = req.getParameter("data");
        String local = req.getParameter("local");
        String catId = req.getParameter("categoria");

        Evento e = new Evento();
        e.setNome(nome);
        e.setData(Date.valueOf(data));
        e.setLocal(local);

        Categoria c = new Categoria();
        // Correção de cast/parse
        c.setId(Integer.parseInt(catId));
        e.setCategoria(c);

        dao.inserir(e);

        resp.setStatus(201);
    }
}