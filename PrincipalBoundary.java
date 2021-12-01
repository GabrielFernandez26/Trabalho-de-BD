package src.com.example.projetoaeroporto.boundary;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.com.example.projetoaeroporto.Telas.ExecutorComandos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.com.example.projetoaeroporto.control.VooControl;
import src.com.example.projetoaeroporto.entity.Voo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrincipalBoundary extends Application implements ExecutorComandos {

    private VooBoundary vooBoundary = new VooBoundary();
    private ReservaBoundary reservaBoundary = new ReservaBoundary();
    private CadastroCliente cadastroCliente = new CadastroCliente();
    private Home home = new Home(vooBoundary.control);
    private BorderPane bp = new BorderPane();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public void start(Stage stage) throws Exception {
        Scene scn = new Scene(bp, 1024, 768);

        MenuBar menuBar = new MenuBar();

        Menu mnuVoos = new Menu("Cadastro de Vôo");
        //Menu mnuReservas = new Menu("Cadastro de Reserva");
        Menu mnuCliente = new Menu("Cadastro de Cliente");
        Menu mnuSair = new Menu("Sair");
        Menu mnuHome = new Menu("Home");

        MenuItem mnItemHome = new MenuItem("Home");
        mnuHome.getItems().addAll(mnItemHome);

        menuBar.getMenus().addAll(mnuHome, mnuVoos,mnuCliente, mnuSair);

        MenuItem mnuItemSair = new MenuItem("Logout");
        mnuItemSair.setOnAction((e) -> {
            executarComando("SAIR");
        });
        MenuItem mnuItemVoo = new MenuItem("Vôos");
        mnuItemVoo.setOnAction((e) -> {
            executarComando("TELA-VOO");
        });
        MenuItem mnuItemReserva = new MenuItem("Reservas");
        mnuItemReserva.setOnAction((e) -> {
            executarComando("TELA-RESERVA");
        });
        MenuItem mnuItemCliente = new MenuItem("Cliente");
        mnuItemCliente.setOnAction((e) -> {
            executarComando("TELA-CLIENTE");
        });

        mnItemHome.setOnAction(e ->
        {
            executarComando("HOME");
        });

        vooBoundary.adicionarExecutor(this);
        reservaBoundary.adicionarExecutor(this);
        cadastroCliente.adicionarExecutor(this);
        mnuVoos.getItems().addAll(mnuItemVoo);
        mnuSair.getItems().addAll(mnuItemSair);

        mnuCliente.getItems().addAll(mnuItemCliente);

        bp.setTop(menuBar);
        executarComando("HOME");
        stage.setScene( scn );

        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }

    @Override
    public void executarComando(String comando) {
        if ("TELA-VOO".equals(comando)) {
            bp.setCenter(vooBoundary.render() );
        } else if("TELA-CLIENTE".equals(comando)){
            bp.setCenter(cadastroCliente.render());
        }else if ("HOME".equals(comando)) {
            bp.setCenter(home.render());
        }else if ("SAIR".equals(comando)) {
            Platform.exit();
            System.exit(0);
        }
    }
}