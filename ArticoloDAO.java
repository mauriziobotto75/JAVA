
package com.example.magazzino.dao;

import com.example.magazzino.db.DBConnection;
import com.example.magazzino.model.Articolo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticoloDAO {

    public List<Articolo> getAll() {
        String sql = "SELECT id, nome, prezzo FROM articoli ORDER BY id";
        List<Articolo> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Articolo a = new Articolo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("prezzo")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            showSqlError("Errore nel recupero articoli", e);
        }
        return lista;
    }

    public Articolo insert(Articolo a) {
        String sql = "INSERT INTO articoli (nome, prezzo) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getNome());
            ps.setDouble(2, a.getPrezzo());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            showSqlError("Errore nell'inserimento articolo", e);
        }
        return a;
    }

    public boolean update(Articolo a) {
        String sql = "UPDATE articoli SET nome = ?, prezzo = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getNome());
            ps.setDouble(2, a.getPrezzo());
            ps.setInt(3, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            showSqlError("Errore nell'aggiornamento articolo", e);
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM articoli WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            showSqlError("Errore nella cancellazione articolo", e);
            return false;
        }
    }

    public List<Articolo> searchByName(String nome) {
        String sql = "SELECT id, nome, prezzo FROM articoli WHERE LOWER(nome) LIKE LOWER(?) ORDER BY id";
        List<Articolo> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Articolo(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("prezzo")
                    ));
                }
            }
        } catch (SQLException e) {
            showSqlError("Errore nella ricerca per nome", e);
        }
        return lista;
    }

    private void showSqlError(String msg, SQLException e) {
        System.err.println(msg + ": " + e.getMessage());
    }
}
