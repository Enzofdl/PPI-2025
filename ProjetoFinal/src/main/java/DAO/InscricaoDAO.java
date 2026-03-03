package DAO;

import Factory.ConnectionFactory;
import Model.Inscricao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscricaoDAO {

    public void inserir(Inscricao i) {
        String sql = "INSERT INTO inscricao(id_usuario, id_evento, data_inscricao) VALUES (?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, i.getIdUsuario());
            stmt.setInt(2, i.getIdEvento());
            stmt.setDate(3, i.getDataInscricao());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizar(int id, Inscricao i) {
        String sql = "UPDATE inscricao SET id_usuario=?, id_evento=?, data_inscricao=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, i.getIdUsuario());
            stmt.setInt(2, i.getIdEvento());
            stmt.setDate(3, i.getDataInscricao());
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM inscricao WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Inscricao> listar() {
        List<Inscricao> lista = new ArrayList<>();
        String sql = "SELECT * FROM inscricao";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setId(rs.getInt("id"));
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setIdEvento(rs.getInt("id_evento"));
                i.setDataInscricao(rs.getDate("data_inscricao"));
                lista.add(i);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Inscricao> listarComDetalhes() {
        List<Inscricao> lista = new ArrayList<>();
        String sql = "SELECT i.*, u.nome as nome_usuario, e.nome as nome_evento " +
                     "FROM inscricao i " +
                     "INNER JOIN usuario u ON i.id_usuario = u.id " +
                     "INNER JOIN evento e ON i.id_evento = e.id";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setId(rs.getInt("id"));
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setIdEvento(rs.getInt("id_evento"));
                i.setDataInscricao(rs.getDate("data_inscricao"));
                i.setNomeUsuario(rs.getString("nome_usuario"));
                i.setNomeEvento(rs.getString("nome_evento"));
                lista.add(i);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
