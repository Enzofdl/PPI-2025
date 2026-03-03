import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            request.setAttribute("message", "Não é um formulário de upload válido.");
            request.getRequestDispatcher("configuracoes.jsp").forward(request, response);
            return;
        }

        // pasta de upload dentro da aplicação (exploded war). Funciona em desenvolvimento.
        String uploadPath = getServletContext().getRealPath("/uploads");
        if (uploadPath == null) {
            uploadPath = System.getProperty("java.io.tmpdir") + File.separator + "uploads";
        }
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        HttpSession session = request.getSession();

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            String identityStoredName = null;
            String bankStoredName = null;

            for (FileItem item : items) {
                if (item.isFormField()) continue;

                String fieldName = item.getFieldName();
                String originalName = Paths.get(item.getName()).getFileName().toString();
                if (originalName == null || originalName.trim().isEmpty()) continue;

                String contentType = item.getContentType();
                boolean allowed = false;
                if (contentType != null) {
                    if (contentType.equalsIgnoreCase("application/pdf") || contentType.toLowerCase().startsWith("image/")) {
                        allowed = true;
                    }
                }
                // fallback by extension
                String lower = originalName.toLowerCase();
                if (!allowed && (lower.endsWith(".pdf") || lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif"))) {
                    allowed = true;
                }

                if (!allowed) {
                    request.setAttribute("message", "Tipo de arquivo não permitido: " + originalName);
                    request.getRequestDispatcher("configuracoes.jsp").forward(request, response);
                    return;
                }

                // gerar nome armazenado para evitar colisões
                String storedName = System.currentTimeMillis() + "_" + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
                File storeFile = new File(uploadDir, storedName);

                try (InputStream in = item.getInputStream()) {
                    Files.copy(in, storeFile.toPath());
                }

                if ("identityFile".equals(fieldName)) {
                    identityStoredName = storedName;
                } else if ("bankDataFile".equals(fieldName)) {
                    bankStoredName = storedName;
                }
            }

            if (identityStoredName != null) session.setAttribute("identityFileName", identityStoredName);
            if (bankStoredName != null) session.setAttribute("bankDataFileName", bankStoredName);

            request.setAttribute("message", "Arquivos enviados com sucesso!");
        } catch (Exception e) {
            request.setAttribute("message", "Erro ao enviar arquivos: " + e.getMessage());
        }

        request.getRequestDispatcher("configuracoes.jsp").forward(request, response);
    }
}