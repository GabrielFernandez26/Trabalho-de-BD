package src.com.example.projetoaeroporto.boundary;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;
import src.com.example.projetoaeroporto.Telas.TelaCliente;
import src.com.example.projetoaeroporto.control.ClienteControl;
import src.com.example.projetoaeroporto.entity.Cliente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CadastroCliente extends TelaCliente {

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtNascimento = new TextField();
    private TextField txtCpf = new TextField();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private TableView<Cliente> table=new TableView<>();

    private final ClienteControl control = new ClienteControl();

    private Button btnSalvar= new Button("Salvar");
    private Button btnLimpar= new Button("Limpar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnAtualizar = new Button("Atualizar");

    private void criarTabela(){
        TableColumn<Cliente, Integer> col1=new TableColumn<>("Id");
        col1.setCellValueFactory(
                new PropertyValueFactory<Cliente,Integer>("id")
        );

        TableColumn<Cliente, String> col2=new TableColumn<>("nome");
        col2.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("nome")
        );

        TableColumn<Cliente,String> col3=new TableColumn<>("cpf");
        col3.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("cpf")
        );

        TableColumn<Cliente, String> col4 = new TableColumn<>("nascimento");
        col4.setCellValueFactory( tb->
        {
            LocalDate date = tb.getValue().getNascimento();

            return new ReadOnlyStringWrapper(date.format(fmt));
        });

        TableColumn<Cliente, String> col5 = new TableColumn<>("Ações");
        col5.setCellFactory( (tbcol) -> {
                    Button btnRemover = new Button("Remover");
                    TableCell<Cliente, String> tcell = new TableCell<Cliente, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnRemover.setOnAction( (e) -> {
                                    Cliente c = getTableView().getItems().get(getIndex());
                                    control.remover(c.getId());
                                });
                                setGraphic(btnRemover);
                                setText(null);
                            }
                        }
                    };
                    return tcell;
                }
        );

        table.getSelectionModel().selectedItemProperty().addListener( (obs, old, novo) -> {
                    control.fromEntity(novo);
                }
        );

        table.getColumns().addAll(col1, col2, col3, col4, col5);

        table.setItems(control.getLista());
    }
    @Override
    public Pane render() {
        BorderPane panPrincipal = new BorderPane();
        GridPane panCampos = new GridPane();

        panPrincipal.setTop(panCampos);
        panPrincipal.setCenter(table);

        criarTabela();

        txtId.setEditable(false);

        Bindings.bindBidirectional(txtId.textProperty(), control.id, new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
        Bindings.bindBidirectional(txtCpf.textProperty(), control.cpf);
        Bindings.bindBidirectional(txtNascimento.textProperty(), control.nascimento, new LocalDateStringConverter());

        panCampos.add(new Label("Id:"), 0, 0);
        panCampos.add(txtId, 1, 0);
        panCampos.add(new Label("Nome do cliente:"), 0, 1);
        panCampos.add(txtNome, 1, 1);
        panCampos.add(new Label("Cpf do cliente:"), 0, 2);
        panCampos.add(txtCpf, 1, 2);
        panCampos.add(new Label("Data de nascimento: "), 0, 3);
        panCampos.add(txtNascimento, 1, 3);

        panCampos.add(btnSalvar, 0, 5);
        panCampos.add(btnPesquisar, 2, 5);
        panCampos.add(btnLimpar, 1, 5);

        btnSalvar.setOnAction((e) -> {
            control.salvar();
            new Alert(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso").showAndWait();
        });

        btnAtualizar.setOnAction((e)->{
            control.alterar();
            new Alert(Alert.AlertType.INFORMATION, "Cadastro atualizado com sucesso").showAndWait();
        });

        btnPesquisar.setOnAction( (e) -> {
            control.pesquisar();
        });

        btnLimpar.setOnAction( a ->
        {
            txtCpf.setText("");
            txtNome.setText("");
            txtNascimento.setText("");
            txtId.setText("");
        });

        return panPrincipal;
    }
}
