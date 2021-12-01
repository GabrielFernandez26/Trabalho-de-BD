package src.com.example.projetoaeroporto.boundary;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import src.com.example.projetoaeroporto.Telas.TelaVoo;
import src.com.example.projetoaeroporto.control.VooControl;
import src.com.example.projetoaeroporto.entity.Voo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class VooBoundary extends TelaVoo {
    private TextField txtId = new TextField();
    private TextField txtOrigem = new TextField();
    private TextField txtDestino = new TextField();
    private TextField txtDecolagem = new TextField();
    private TextField txtPouso = new TextField();
    private TextField txtAssentos = new TextField();
    private TextField txtPreco = new TextField();

    private TableView<Voo> table=new TableView<>();

    public final VooControl control = new VooControl();

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Button btnSalvar= new Button("Salvar");
    private Button btnRemover= new Button("Remover");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnAtualizar = new Button("Limpar");

    private void criarTabela(){
        TableColumn<Voo,Integer> col1=new TableColumn<>("Id");
        col1.setCellValueFactory(
                new PropertyValueFactory<Voo,Integer>("id")
        );
        TableColumn<Voo,String> col2=new TableColumn<>("Origem");
        col2.setCellValueFactory(
                new PropertyValueFactory<Voo,String>("Origem")
        );
        TableColumn<Voo,String> col3=new TableColumn<>("Destino");
        col3.setCellValueFactory(
                new PropertyValueFactory<Voo,String>("Destino")
        );
        TableColumn<Voo, String> col4=new TableColumn<>("Decolagem");
        col4.setCellValueFactory((item) -> {
                    LocalDate d = item.getValue().getDecolagem();
                    return new ReadOnlyStringWrapper(d.format(fmt));
                }
        );

        TableColumn<Voo, Double> col5 = new TableColumn<>("Preco(R$)");
        col5.setCellValueFactory(
                new PropertyValueFactory<Voo,Double>("Preco")
        );

        TableColumn<Voo, String> col6 = new TableColumn<>("Ações");
        col6.setCellFactory( (tbcol) -> {
                    Button btnRemover = new Button("Remover");
                    TableCell<Voo, String> tcell = new TableCell<Voo, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnRemover.setOnAction( (e) -> {
                                    Voo v = getTableView().getItems().get(getIndex());
                                    control.remover(v.getId());
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
        table.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        table.setItems(control.getLista());
    }
    @Override
    public Pane render() {
        BorderPane panPrincipal = new BorderPane();
        GridPane panCampos = new GridPane();
        txtId.setEditable(false);
        criarTabela();

        panPrincipal.setTop(panCampos);
        panPrincipal.setCenter(table);


        Bindings.bindBidirectional(txtId.textProperty(), control.id, new NumberStringConverter());
        Bindings.bindBidirectional(txtOrigem.textProperty(), control.origem);
        Bindings.bindBidirectional(txtDestino.textProperty(), control.destino);
        Bindings.bindBidirectional(txtDecolagem.textProperty(), control.decolagem, new LocalDateStringConverter());
        Bindings.bindBidirectional(txtPreco.textProperty(), control.preco, new NumberStringConverter());

        panCampos.add(new Label("Id:"), 0, 0);
        panCampos.add(txtId, 1, 0);
        panCampos.add(new Label("Origem:"), 0, 1);
        panCampos.add(txtOrigem, 1, 1);
        panCampos.add(new Label("Destino:"), 0, 2);
        panCampos.add(txtDestino, 1, 2);
        panCampos.add(new Label("Decolagem:"), 0, 3);
        panCampos.add(txtDecolagem, 1, 3);
        panCampos.add(new Label("Preco:"), 0, 4);
        panCampos.add(txtPreco, 1, 4);

        panCampos.add(btnSalvar, 0, 5);
        panCampos.add(btnPesquisar, 2, 5);
        panCampos.add(btnAtualizar,1,5);

        btnSalvar.setOnAction((e) -> {
            control.salvar();
            new Alert(Alert.AlertType.INFORMATION, "Voo cadastrado com sucesso");
        });

        btnAtualizar.setOnAction((e)->{
            txtId.setText("0");
            txtAssentos.setText("0");
            txtDecolagem.setText("");
            txtOrigem.setText("");
            txtDestino.setText("");
            txtPreco.setText("");
        });

        btnPesquisar.setOnAction( (e) -> {
            control.pesquisar();
        });

    return panPrincipal;
    }


}
