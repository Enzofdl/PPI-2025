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

@WebServlet("/api/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO dao;

    @Override
    public void init() {
        System.out.println("UsuarioServlet: init() - Servlet inicializado");
        dao = new UsuarioDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsuarioServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("UsuarioServlet: destroy() - Servlet destruído");
        dao = null;
    }

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
            json.append("\"nome\":\"").append(u.getNome()).append("\",");
            json.append("\"email\":\"").append(u.getEmail()).append("\",");
            json.append("\"telefone\":\"").append(u.getTelefone()).append("\"");
            json.append("}");

            if (i < usuarios.size() - 1) json.append(",");
        }
        json.append("]");
        out.print(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setTelefone(telefone);

        dao.inserir(u);

        resp.setStatus(201);
    }
}

