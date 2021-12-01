package src.com.example.projetoaeroporto.DAO;

import src.com.example.projetoaeroporto.entity.Voo;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VooDAOImplements implements VooDAO{

    @Override
    public void salvar(Voo v) {

        Connection con = Context.getConnection();
        try {
            
            String sql = "INSERT INTO voo (origem, destino, decolagem, pouso, preco) " +
                    "VALUES (?, ?, ?, ?, ?)";
                    
            System.out.println(sql);
            
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, v.getOrigem());
            stmt.setString(2, v.getDestino());
            stmt.setDate(3, java.sql.Date.valueOf(v.getDecolagem()));
            stmt.setDate(4, java.sql.Date.valueOf(v.getDecolagem()));
            stmt.setDouble(5, v.getPreco());

            stmt.executeUpdate();
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Voo> pesquisarPorId(Integer id) {
        List<Voo> lista = new ArrayList<Voo>();

        Connection con = Context.getConnection();

        try {
            
            String sql = "SELECT * FROM voo WHERE id = ?";

            System.out.println(sql);

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id );

            ResultSet rs = stmt.executeQuery();

            while( rs.next() ) {
                Voo v = new Voo();
                v.setId(rs.getInt("id"));
                v.setOrigem(rs.getString("Origem"));
                v.setDestino(rs.getString("Destino"));

                v.setDecolagem(rs.getDate("Decolagem").toLocalDate());

                v.setPouso(rs.getDate("pouso").toLocalDate());

                v.setPreco(rs.getDouble("Preço"));

                v.setAssentosDisponiveis(rs.getInt("assentos"));

                lista.add(v);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }
        return lista;
    }

    public List<Voo> ListarPorDestino(String destino) {
        List<Voo> lista = new ArrayList<Voo>();

        Connection con = Context.getConnection();

        try {

            String sql = "SELECT * FROM voo where destino like ?";

            System.out.println(sql);

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "%"+destino+"%");

            ResultSet rs = stmt.executeQuery();

            while( rs.next() ) {
                Voo v = new Voo();
                v.setId(rs.getInt("id"));
                v.setOrigem(rs.getString("origem"));
                v.setDestino(rs.getString("destino"));

                v.setDecolagem(rs.getDate("decolagem").toLocalDate());

                v.setPouso(rs.getDate("pouso").toLocalDate());

                v.setPreco(rs.getDouble("preco"));

                v.setAssentosDisponiveis(rs.getInt("assentos"));

                lista.add(v);
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
        try {
            Connection con = Context.getConnection();
            String sql = "DELETE FROM voo WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(int id, Voo v) {
        try {
            Connection con = Context.getConnection();
            String sql = "UPDATE voo SET origem = ?, destino = ?, decolagem = ?, preco = ? WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(5, v.getId());
            stmt.setString(1, v.getOrigem());
            stmt.setString(2, v.getDestino());
            stmt.setDate(3, java.sql.Date.valueOf(v.getDecolagem()));
            stmt.setDouble(4, v.getPreco());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
