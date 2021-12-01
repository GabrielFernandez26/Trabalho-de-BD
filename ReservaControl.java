package src.com.example.projetoaeroporto.control;

import src.com.example.projetoaeroporto.DAO.ReservaDAO;
import src.com.example.projetoaeroporto.DAO.ReservaDAOImplements;
import src.com.example.projetoaeroporto.entity.Reserva;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class ReservaControl {
    private ReservaDAO reservaDAO = new ReservaDAOImplements();

    public IntegerProperty id = new SimpleIntegerProperty(0);
    public IntegerProperty cliente = new SimpleIntegerProperty(0);
    public IntegerProperty voo = new SimpleIntegerProperty(0);

    private List<Reserva> reservaGeral = new ArrayList<Reserva>();
    private ObservableList<Reserva>  reserva = FXCollections.observableArrayList();

    public ReservaControl() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pesquisar() {
        reserva.clear();
        List<Reserva> encontrados = reservaDAO.pesquisarPorId( id.get() );
        reserva.addAll( encontrados );
        if (!reserva.isEmpty()) {
            fromEntity(reserva.get(0));
        }
    }

    public Reserva toEntity() {
        Reserva r = new Reserva();
        r.setId(id.get());
        r.setCliente(cliente.get());
        r.setVoo(voo.get());
        return r;
    }

    public void fromEntity(Reserva r) {
        id.set(r.getId());
        cliente.set(r.getCliente());
        voo.set(r.getVoo());
    }
    public void reservar() {

        Reserva r = new Reserva();

        reservaDAO.salvar(r);
    }

    public void atualizar() {

    }
    public void remover(int id) {
        reservaDAO.remover(id);
    }
    public ObservableList<Reserva> getLista() {
        return reserva;
    }
}
