package Controller;

import DAO.EventoDAO;
import Model.Categoria;
import Model.Evento;

// NOVOS IMPORTS DE ARQUIVO
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

@WebServlet(urlPatterns = {"/api/eventos", "/api/eventos/acao"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class EventoServlet extends HttpServlet {

    private EventoDAO dao;

    @Override
    public void init() {
        System.out.println("EventoServlet: init() - Inicializando recursos...");
        dao = new EventoDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EventoServlet: service() - Método: " + req.getMethod() + " | URI: " + req.getRequestURI());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("EventoServlet: destroy() - Encerrando servlet...");
        dao = null;
    }

    // GET: Listar Eventos (Sem alterações lógicas, mantido igual)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Evento> eventos = dao.listarTodos();
            PrintWriter out = resp.getWriter();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < eventos.size(); i++) {
                Evento e = eventos.get(i);

                json.append("{");
                json.append("\"id\":").append(e.getId()).append(",");
                json.append("\"nome\":\"").append(tratarTexto(e.getNome())).append("\",");

                String dataFormatada = (e.getData() != null) ? sdf.format(e.getData()) : "";
                json.append("\"data\":\"").append(dataFormatada).append("\",");

                json.append("\"local\":\"").append(tratarTexto(e.getLocal())).append("\",");

                String nomeCategoria = (e.getCategoria() != null) ? tratarTexto(e.getCategoria().getNome()) : "";
                int idCategoria = (e.getCategoria() != null) ? e.getCategoria().getId() : 0;

                json.append("\"categoria\":\"").append(nomeCategoria).append("\",");
                json.append("\"categoriaId\":").append(idCategoria);

                String imagemStr = (e.getImagem() != null) ? tratarTexto(e.getImagem()) : "";
                json.append(",\"imagem\":\"").append(imagemStr).append("\"");

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
                int id = Integer.parseInt(req.getParameter("id"));
                dao.remover(id);

                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Evento removido\"}");

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

                // --- PROCESSAMENTO DA IMAGEM ---
                salvarImagemSeExistir(req, e);

                dao.atualizar(id, e);

                resp.setStatus(200);
                resp.getWriter().write("{\"success\":true, \"message\":\"Evento atualizado\"}");

            } else {
                String nome = req.getParameter("nome");
                String data = req.getParameter("data");
                String local = req.getParameter("local");
                String catId = req.getParameter("categoria");

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

                // --- PROCESSAMENTO DA IMAGEM ---
                salvarImagemSeExistir(req, e);

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

    // METODO AUXILIAR: Extraído para reaproveitamento limpo no Inserir e Alterar
    private void salvarImagemSeExistir(HttpServletRequest req, Evento e) {
        try {
            // Verifica se a requisição tem dados multipart (evita erro no deletar que é só form normal)
            if (req.getContentType() != null && req.getContentType().toLowerCase().startsWith("multipart/")) {
                Part filePart = req.getPart("imagem");

                if (filePart != null && filePart.getSize() > 0) {
                    // Mesma lógica de caminhos da sua UploadServlet
                    String uploadPath = getServletContext().getRealPath("/uploads");
                    if (uploadPath == null) {
                        uploadPath = System.getProperty("java.io.tmpdir") + File.separator + "uploads";
                    }
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String fileName = System.currentTimeMillis() + "_" + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
                    File storeFile = new File(uploadDir, fileName);

                    try (InputStream in = filePart.getInputStream()) {
                        Files.copy(in, storeFile.toPath());
                    }

                    // IMPORTANTE: Para isso salvar no banco de dados, certifique-se de que a
                    // classe Model.Evento possui o atributo 'imagem' e o método 'setImagem()'
                    // bem como foi adicionado no SQL do seu EventoDAO.
                    e.setImagem(fileName);
                }
            }
        } catch (Exception ex) {
            System.err.println("Aviso: Falha ao processar a imagem do evento: " + ex.getMessage());
        }
    }

    private String tratarTexto(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"").replace("\n", " ");
    }
}