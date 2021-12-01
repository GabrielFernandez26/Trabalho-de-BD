package src.com.example.projetoaeroporto.boundary;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;
import src.com.example.projetoaeroporto.Telas.TelaReserva;
import src.com.example.projetoaeroporto.control.ClienteControl;
import src.com.example.projetoaeroporto.control.ReservaControl;
import src.com.example.projetoaeroporto.control.VooControl;
import src.com.example.projetoaeroporto.entity.Cliente;

public class ReservaBoundary extends TelaReserva {

    private TextField txtId = new TextField();
    private TextField txtOrigem = new TextField();
    private TextField txtDestino = new TextField();
    private TextField txtDecolagem = new TextField();
    private TextField txtPouso = new TextField();
    private TextField txtAssentos = new TextField();
    private TextField txtPreco = new TextField();

    private TextField txtClienteId = new TextField();
    private TextField txtClienteNome = new TextField();
    private TextField txtClienteCpf = new TextField();

    private TextField txtPesquisa = new TextField();

    private final ReservaControl reservaControl = new ReservaControl();
    private final ClienteControl clienteControl = new ClienteControl();

    private Button btnSalvar= new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");

    @Override
    public Pane render(VooControl control) {
        BorderPane panPrincipal = new BorderPane();
        GridPane panCampos = new GridPane();
        txtId.setEditable(false);
        txtClienteCpf.setEditable(false);
        txtClienteNome.setEditable(false);
        panPrincipal.setTop(panCampos);

        Bindings.bindBidirectional(txtId.textProperty(), reservaControl.voo, new NumberStringConverter());
        Bindings.bindBidirectional(txtClienteId.textProperty(), reservaControl.cliente, new NumberStringConverter());

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
        panCampos.add(new Label("Id Cliente:"), 0, 5);
        panCampos.add(txtClienteId, 1, 5);
        panCampos.add(new Label("Nome do cliente:"), 0, 6);
        panCampos.add(txtClienteNome, 1, 6);
        panCampos.add(new Label("Cpf do cliente:"), 0, 7);
        panCampos.add(txtClienteCpf, 1, 7);

        panCampos.add(btnSalvar, 0, 8);
        panCampos.add(btnPesquisar, 2, 5);

        btnSalvar.setOnAction((e) -> {

            reservaControl.reservar();

            new Alert(Alert.AlertType.INFORMATION, "Reserva feita com sucesso").showAndWait();
        });

        btnPesquisar.setOnAction( (e) -> {
            Cliente c = clienteControl.getById(txtClienteId.getText());
            txtClienteNome.setText(c.getNome());
            txtClienteCpf.setText(c.getCpf());

        });

        return panPrincipal;
    }
}
