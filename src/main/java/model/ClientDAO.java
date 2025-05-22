/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author enzoc
 */
public class ClientDAO {
    /**
     * Insere novo cliente.
     */
    public void addClient(Client client) throws SQLException {
        String sql = "INSERT INTO clients (name, cpf, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Vincula parâmetros de forma segura (evita SQL injection)
            ps.setString(1, client.getName());
            ps.setString(2, client.getCpf());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.executeUpdate();
        }
    }
    
    /**
     * Busca cliente por CPF exato.
     */
    public Client getClientByCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM clients WHERE cpf = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToClient(rs);
            }
        }
        return null;
    }
    
    /**
     * Atualiza nome, telefone e email dado o CPF.
     */
    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE clients SET name = ?, phone = ?, email = ? WHERE cpf = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getName());
            ps.setString(2, client.getPhone());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getCpf());
            ps.executeUpdate();
        }
    }
    
    /**
     * Exclui cliente por CPF.
     */
    public void deleteClient(String cpf) throws SQLException {
        String sql = "DELETE FROM clients WHERE cpf = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ps.executeUpdate();
        }
    }
    
    /**
     * Filtro: obtém lista de clientes cujo nome contenha o texto.
     */
    public List<Client> getClientsByName(String nameFilter) throws SQLException {
        String sql = "SELECT * FROM clients WHERE name LIKE ?";
        List<Client> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nameFilter + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToClient(rs));
            }
        }
        return list;
    }
    
     /**
     * Extrai um objeto Client a partir de um ResultSet.
     */
    private Client mapToClient(ResultSet rs) throws SQLException {
        return new Client(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("cpf"),
            rs.getString("phone"),
            rs.getString("email")
        );
    }
}

