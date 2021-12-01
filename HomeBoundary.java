package src.com.example.projetoaeroporto.boundary;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import src.com.example.projetoaeroporto.control.VooControl;
import src.com.example.projetoaeroporto.entity.Voo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Home extends src.com.example.projetoaeroporto.Telas.Home {
    private ReservaBoundary reservaBoundary = new ReservaBoundary();
    private TableView<Voo> table=new TableView<>();
    BorderPane bp = new BorderPane();
    private VooControl control;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<Voo>  voos ;

    private static boolean first = true;

    public Home(VooControl cont)
    {
        control = cont;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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
                    Button btnReservar = new Button("Reservar");
                    TableCell<Voo, String> tcell = new TableCell<Voo, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnReservar.setOnAction( (e) -> {
                                    Voo v = getTableView().getItems().get(getIndex());
                                    bp.setCenter(reservaBoundary.render(control));
                                });
                                setGraphic(btnReservar);
                                setText(null);
                            }
                        }
                    };
                    return tcell;
                }
        );

        table.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        table.setItems(control.getLista());
    }

    @Override
    public Pane render() {

        if(first) {

            criarTabela();

            bp.setCenter(table);

            first = false;

        }else{
            table.setItems(control.getLista());
        }
        return bp;
    }
}
