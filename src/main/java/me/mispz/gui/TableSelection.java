package me.mispz.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import me.mispz.crud.Crud;
import me.mispz.entities.Pice;
import me.mispz.entities.Racun;
import me.mispz.entities.Sto;
import me.mispz.util.LoggedIn;

public class TableSelection {
    @FXML
    private ComboBox<Sto> cmbxPickTable;
    @FXML
    private TableView<Pice> tblSvaPica;
    @FXML
    private TableColumn<Pice, Integer> allId;
    @FXML
    private TableColumn<Pice, String> allName;
    @FXML
    private TableColumn<Pice, Boolean> allAlc;
    @FXML
    private TableColumn<Pice, Boolean> allBalance;
    @FXML
    private TableColumn<Pice, Boolean> allPrice;
    @FXML
    private TableView<Pice> tblStoPica;
    @FXML
    private TableColumn<Pice, Integer> stoId;
    @FXML
    private TableColumn<Pice, String> stoName;
    @FXML
    private TableColumn<Pice, Boolean> stoAlc;
    @FXML
    private TableColumn<Pice, Boolean> stoBalance;
    @FXML
    private TableColumn<Pice, Boolean> stoPrice;
    @FXML
    private Button btnSelect;
    private final Crud crud = new Crud();
    @FXML
    private Button btnGenerateBill;
    @FXML
    private Label lblRacun;
    private int selectedTable;
    private ObservableList<Pice> pica = FXCollections.observableArrayList();

    public void initialize(){
        System.out.println(crud.getPica());
        cmbxPickTable.setItems(crud.getTables());
        allId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        allBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        allPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblSvaPica.setItems(crud.getPica());

        stoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stoAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        stoBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        stoPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    @FXML
    public void onSelectClick(){
        crud.setTableKonobar(LoggedIn.getLogged(), cmbxPickTable.getValue().getId());
        selectedTable = cmbxPickTable.getValue().getId();
    }
    @FXML
    public void onAdd(){
        System.out.println(selectedTable);
        pica.add(tblSvaPica.getSelectionModel().getSelectedItem());
        crud.addPic2Sto(tblSvaPica.getSelectionModel().getSelectedItem(), crud.getTable(selectedTable));
        tblStoPica.setItems(pica);
        crud.setRacun(crud.getTable(selectedTable));
    }
    @FXML
    public void onGenerateBill(){
        Racun racun = crud.getRacun(crud.getTable(selectedTable));
        lblRacun.setText(String.valueOf(racun.getBill()));
    }
}
