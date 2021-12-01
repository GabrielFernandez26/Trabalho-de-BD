package src.com.example.projetoaeroporto.control;

import src.com.example.projetoaeroporto.DAO.VooDAO;
import src.com.example.projetoaeroporto.DAO.VooDAOImplements;
import src.com.example.projetoaeroporto.entity.Voo;
import javafx.beans.property.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VooControl {

    private VooDAOImplements vooDAO = new VooDAOImplements();

    public IntegerProperty id = new SimpleIntegerProperty(0);
    public StringProperty origem = new SimpleStringProperty("");
    public StringProperty destino = new SimpleStringProperty("");
    public ObjectProperty decolagem = new SimpleObjectProperty(LocalDate.now());
    public ObjectProperty pouso = new SimpleObjectProperty(LocalDate.now());
    public DoubleProperty preco= new SimpleDoubleProperty(0.0);

    private ObservableList<Voo>  voos = FXCollections.observableArrayList();

    public VooControl() {

        PopularTabela();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pesquisar() {
        voos.clear();
        List<Voo> encontrados = vooDAO.ListarPorDestino(destino.get());
        voos.addAll( encontrados );
        if (!voos.isEmpty()) {
            fromEntity(voos.get(0));
        }
    }

    public Voo toEntity() {
        Voo v = new Voo();
        v.setId(id.get());
        v.setOrigem(origem.get());
        v.setDestino(destino.get());
        v.setDecolagem((LocalDate)decolagem.get());
        v.setPreco(preco.get());
        return v;
    }
    public void atualizar() {
        Voo voo = toEntity();
        if (voo.getId() == 0) {
            vooDAO.salvar(voo);
        } else {
            vooDAO.atualizar( id.get(), voo );
        }
        PopularTabela();
    }

    public void fromEntity(Voo v) {
        id.set(v.getId());
        origem.set(v.getOrigem());
        destino.set(v.getDestino());
        decolagem.set(v.getDecolagem());
        preco.set(v.getPreco());
    }

    private void PopularTabela() {
        voos.clear();

        voos.addAll(vooDAO.ListarPorDestino(""));

        if(!voos.isEmpty())
            fromEntity(voos.get(0));
    }

    public void remover(int id) {
        vooDAO.remover(id);
        PopularTabela();
    }

    public void salvar(){

        Voo voo = toEntity();

        if(voo.getId() == 0)
            vooDAO.salvar(voo);
        else
            vooDAO.atualizar(voo.getId(), voo);

        PopularTabela();
    }

    public ObservableList<Voo> getLista() {
        return voos;
    }
}
