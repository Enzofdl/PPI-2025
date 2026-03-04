package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/plataforma_de_eventos?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    private static final String USER = "root";

    private static final String PASS = "admin";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro: Driver do banco não encontrado na biblioteca.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro: Falha ao conectar. Verifique se o MySQL está rodando e se a senha está certa.", e);
        }
    }
}