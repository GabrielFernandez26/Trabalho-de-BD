package src.com.example.projetoaeroporto.control;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.com.example.projetoaeroporto.DAO.ClienteDAO;
import src.com.example.projetoaeroporto.DAO.ClienteDAOImplements;
import src.com.example.projetoaeroporto.entity.Cliente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteControl {

        private ClienteDAOImplements clienteDAO = new ClienteDAOImplements();

        public IntegerProperty id = new SimpleIntegerProperty(0);
        public StringProperty nome = new SimpleStringProperty("");
        public ObjectProperty nascimento = new SimpleObjectProperty(null);
        public StringProperty cpf = new SimpleStringProperty("");

        private ObservableList<Cliente> obsCliente = FXCollections.observableArrayList();

        public ClienteControl() {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch(Exception e) {
                e.printStackTrace();
            }

            PopularTabela();
        }

        public void pesquisar() {
            obsCliente.clear();
            List<Cliente> encontrados = clienteDAO.pesquisarPorNome(nome.get());
            obsCliente.addAll( encontrados );
            if (!obsCliente.isEmpty()) {
                fromEntity(obsCliente.get(0));
            }
        }

        public void salvar() {
            Cliente cliente = toEntity();
            if (cliente.getId() == 0) {
                clienteDAO.salvar(cliente);
            } else {
                clienteDAO.atualizar( id.get(), cliente );
            }
            PopularTabela();
        }

        public void alterar() {

            Cliente cliente = toEntity();

            if (cliente.getId() == 0) {
                clienteDAO.salvar(cliente);
            } else {
                clienteDAO.atualizar( id.get(), cliente );
            }
            PopularTabela();
        }

        public void remover(int id) {
            clienteDAO.remover(id);
            PopularTabela();
        }
        private void PopularTabela() {
            obsCliente.clear();
            obsCliente.addAll(clienteDAO.pesquisarPorNome(""));

            if(!obsCliente.isEmpty())
                fromEntity(obsCliente.get(0));
        }

        public Cliente getById(String id)
        {
            return clienteDAO.getById(id);
        }

        public ObservableList<Cliente> getLista() {
            return obsCliente;
        }

        public Cliente toEntity() {
            Cliente c = new Cliente();
            c.setId(id.get());
            c.setNome(nome.get());
            c.setNascimento((LocalDate) nascimento.get());
            c.setCpf(cpf.get());
            return c;
        }

        public void fromEntity(Cliente c) {
            id.set(c.getId());
            nome.set(c.getNome());
            nascimento.set(c.getNascimento());
            cpf.set(c.getCpf());
        }
}
