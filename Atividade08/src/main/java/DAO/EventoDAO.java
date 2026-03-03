package DAO;

import Factory.ConnectionFactory;
import Model.Categoria;
import Model.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public void inserir(Evento e) {
        String sql = "INSERT INTO evento(nome, local_evento, data_evento, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getLocal());
            stmt.setDate(3, new java.sql.Date(e.getData().getTime()));
            stmt.setInt(4, e.getCategoria().getId());
            stmt.execute();
        } catch (SQLException event) {
            throw new RuntimeException(event);
        }
    }

    public void atualizar(int id, Evento e) {
        String sql = "UPDATE evento SET nome=?, local_evento=?, data_evento=?, id_categoria=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getLocal());
            stmt.setDate(3, new java.sql.Date(e.getData().getTime()));
            stmt.setInt(4, e.getCategoria().getId());
            stmt.setInt(5, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM evento WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Evento> listarTodos() {
        List<Evento> eventos = new ArrayList<>();

        String sql = "SELECT e.*, c.nome as cat_nome FROM evento e INNER JOIN categoria c ON e.id_categoria = c.id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setData(rs.getDate("data_evento"));
                e.setLocal(rs.getString("local_evento"));

                Categoria c = new Categoria();
                c.setId(rs.getInt("id_categoria"));
                c.setNome(rs.getString("cat_nome"));
                e.setCategoria(c);

                eventos.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return eventos;
    }
}