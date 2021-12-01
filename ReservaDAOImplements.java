package src.com.example.projetoaeroporto.DAO;

import src.com.example.projetoaeroporto.entity.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImplements implements ReservaDAO{

    @Override
    public void salvar(Reserva r) {

        Connection con = Context.getConnection();

        try {
            
            String sql = "INSERT INTO reserva (id_cliente, id_voo) VALUES ( ?, ?);";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, r.getCliente());
            stmt.setInt(2, r.getVoo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }
    }

    @Override
    public List<Reserva> pesquisarPorId(Integer id) {
        List<Reserva> lista = new ArrayList<Reserva>();

        Connection con = Context.getConnection();

        try {
            String sql = "SELECT * FROM voo WHERE id like ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + id + "%");
            ResultSet rs = stmt.executeQuery();

            while( rs.next() ) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setCliente(rs.getInt("id do cliente"));
                r.setVoo(rs.getInt("id do voo"));
                lista.add(r);
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

            String sql = "DELETE FROM voo WHERE id = ?";
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
    public void atualizar(int id, Reserva r) {

        Connection con = Context.getConnection();

        try {
            String sql = "UPDATE reserva SET id = ?, cliente = ?, voo = ? WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, r.getId());
            stmt.setInt(2, r.getCliente());
            stmt.setInt(3, r.getVoo());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Context.closeConnection(con);
        }
    }
}
