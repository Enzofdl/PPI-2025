package DAO;

import Factory.ConnectionFactory;
import Model.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public void inserir(Evento e) {
        String sql = "INSERT INTO evento(nome, local_evento, data_evento, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getLocal());
            stmt.setDate(3, e.getData());
            stmt.setInt(4, e.getIdCategoria());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizar(int id, Evento e) {
        String sql = "UPDATE evento SET nome=?, local_evento=?, data_evento=?, id_categoria=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getLocal());
            stmt.setDate(3, e.getData());
            stmt.setInt(4, e.getIdCategoria());
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

    public List<Evento> listar() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM evento";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setLocal(rs.getString("local_evento"));
                e.setData(rs.getDate("data_evento"));
                e.setIdCategoria(rs.getInt("id_categoria"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
