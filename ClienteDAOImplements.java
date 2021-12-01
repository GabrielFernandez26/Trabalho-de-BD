package src.com.example.projetoaeroporto.DAO;

import src.com.example.projetoaeroporto.entity.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImplements implements ClienteDAO {

    @Override
    public void salvar(Cliente c) {

        try {
            Connection con = Context.getConnection();
            String sql = "INSERT INTO cliente (nome, datanascimento, cpf) " +
                    "VALUES (?, ?, ?)";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,  c.getNome());
            stmt.setDate(2,  java.sql.Date.valueOf(c.getNascimento()));
            stmt.setString(3, c.getCpf());
            stmt.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente getById(String id){

        Connection con = Context.getConnection();
        Cliente c = new Cliente();
        try {
            String sql = "SELECT * FROM cliente WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while( rs.next() ) {
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setNascimento(rs.getDate("datanascimento").toLocalDate());
                c.setCpf(rs.getString("cpf"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }

        return c;
    }

    @Override
    public List<Cliente> pesquisarPorNome(String nome) {
        List<Cliente> lista = new ArrayList<Cliente>();

        Connection con = Context.getConnection();

        try {
            String sql = "SELECT * FROM cliente WHERE nome like ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+nome+"%");
            ResultSet rs = stmt.executeQuery();

            while( rs.next() ) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setNascimento(rs.getDate("datanascimento").toLocalDate());
                c.setCpf(rs.getString("cpf"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }
        return lista;
    }

    @Override
    public void remover(int id) {
        Connection con = Context.getConnection();
        try {
            String sql = "DELETE FROM cliente WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Context.closeConnection(con);
        }
    }

    @Override
    public void atualizar(int id, Cliente c) {

        Connection con = Context.getConnection();

        try {
            String sql = "UPDATE cliente SET nome = ?, datanascimento = ?, cpf =? WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(c.getNascimento()));
            stmt.setString(3, c.getCpf());
            stmt.setInt(4, c.getId());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }
    }
}
