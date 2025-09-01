package me.mispz.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import me.mispz.crud.Crud;
import me.mispz.entities.Pic2sto;
import me.mispz.entities.Pice;
import me.mispz.entities.Racun;
import me.mispz.entities.Sto;
import me.mispz.util.LoggedIn;

public class TableSelection {

    @FXML
    private ComboBox<Sto> cmbPickTable;
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
    private Label lblRacun;

    private final Crud crud = new Crud();
    private int selectedTable;
    private ObservableList<Pice> pica = FXCollections.observableArrayList();

    public void initialize(){
        cmbPickTable.setItems(crud.getTables());
        cmbPickTable.getSelectionModel().select(0);
        refreshAllDrinks();

        allId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        allBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        allPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblSvaPica.setItems(crud.getPica());
        tblSvaPica.getSelectionModel().select(0);

        stoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stoAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        stoBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        stoPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void refreshAllDrinks() {
        crud.setTableKonobar(LoggedIn.getLogged(), cmbPickTable.getValue().getId());
        selectedTable = cmbPickTable.getValue().getId();
        ObservableList<Pice> p = FXCollections.observableArrayList();
        for(Pic2sto p2s : crud.getStoPica(crud.getTable(selectedTable))){
            p.add(p2s.getPice());
        }
        pica.clear();
        pica.addAll(p);
        tblStoPica.setItems(pica);
    }

    @FXML
    public void onAdd(){
        pica.add(tblSvaPica.getSelectionModel().getSelectedItem());
        crud.addPic2Sto(tblSvaPica.getSelectionModel().getSelectedItem(), crud.getTable(selectedTable));
        tblStoPica.setItems(pica);
    }

    @FXML
    public void onGenerateBill(){
        crud.setRacun(selectedTable);

        lblRacun.setText(String.valueOf(crud.getRacun(selectedTable).getBill()));
        crud.deletePicaPic2sto(selectedTable);
        refreshAllDrinks();
    }

}
