import DAO.CategoriaDAO;
import Model.Categoria;
import java.util.List;

public class App {
    public static void main(String[] args) {
        CategoriaDAO dao = new CategoriaDAO();

        List<Categoria> lista = dao.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("Banco vazio. Criando categorias padrão...");
            dao.inserir(new Categoria("Corporativo"));
            dao.inserir(new Categoria("Show / Festival"));
            dao.inserir(new Categoria("Workshop"));
            dao.inserir(new Categoria("Esportivo"));
            System.out.println("Categorias criadas com sucesso!");
        } else {
            System.out.println("Categorias já existem no banco:");
            for (Categoria c : lista) {
                System.out.println("- " + c.getId() + ": " + c.getNome());
            }
        }
    }
}