package dao;

import conexao.Conexao;
import entidade.Medicamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public boolean inserir(Medicamento medicamento) {
        String sql = "INSERT INTO medicamento (nome, fabricante, categoria, preco, quantidade_estoque, data_validade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNome());
            stmt.setString(2, medicamento.getFabricante());
            stmt.setString(3, medicamento.getCategoria());
            stmt.setDouble(4, medicamento.getPreco());
            stmt.setInt(5, medicamento.getQuantidadeEstoque());
            stmt.setDate(6, medicamento.getDataValidade());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar medicamento: " + e.getMessage());
            return false;
        }
    }

    public List<Medicamento> listar() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamento";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setFabricante(rs.getString("fabricante"));
                m.setCategoria(rs.getString("categoria"));
                m.setPreco(rs.getDouble("preco"));
                m.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                m.setDataValidade(rs.getDate("data_validade"));

                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar medicamentos: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Medicamento medicamento) {
        String sql = "UPDATE medicamento SET nome = ?, fabricante = ?, categoria = ?, preco = ?, quantidade_estoque = ?, data_validade = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNome());
            stmt.setString(2, medicamento.getFabricante());
            stmt.setString(3, medicamento.getCategoria());
            stmt.setDouble(4, medicamento.getPreco());
            stmt.setInt(5, medicamento.getQuantidadeEstoque());
            stmt.setDate(6, medicamento.getDataValidade());
            stmt.setInt(7, medicamento.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar medicamento: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM medicamento WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir medicamento: " + e.getMessage());
            return false;
        }
    }
}