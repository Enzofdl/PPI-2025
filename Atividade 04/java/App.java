import DAO.*;
import Model.*;
import java.sql.Date;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        EventoDAO eventoDAO = new EventoDAO();
        InscricaoDAO inscricaoDAO = new InscricaoDAO();

        // 1) Cadastro de categoria
        categoriaDAO.inserir(new Categoria("Tecnologia", "Eventos de TI"));

        // 2) Cadastro de usuário
        usuarioDAO.inserir(new Usuario("Maria Silva", "maria@email.com", "99999-0000"));

        // 3) Cadastro de evento
        eventoDAO.inserir(new Evento(
                "Workshop Java",
                "Auditório Central",
                Date.valueOf(LocalDate.of(2025, 1, 15)),
                1  // id da categoria
        ));

        // 4) Cadastro de inscrição
        inscricaoDAO.inserir(new Inscricao(
                1, // usuário
                1, // evento
                Date.valueOf(LocalDate.now())
        ));

        // Listar
        System.out.println("USUÁRIOS:");
        usuarioDAO.listar().forEach(u ->
                System.out.println(u.getId() + " - " + u.getNome())
        );
    }
}
