package me.mispz.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.mispz.crud.Crud;
import me.mispz.entities.*;
import me.mispz.util.LoggedIn;
import me.mispz.util.TIP_PLACANJA;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class TableSelection {

    @FXML
    private ComboBox<Sto> cmbPickTable;
    @FXML
    private ComboBox<Enum<TIP_PLACANJA>> cmbPayment;
    @FXML
    private TableView<Pice> tblSvaPica;
    @FXML
    private TableColumn<Pice, Integer> allId;
    @FXML
    private TableColumn<Pice, String> allName;
    @FXML
    private TableColumn<Pice, Boolean> allAlc;
    @FXML
    private TableColumn<Pice, Integer> allBalance;
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
    private TableColumn<Pice, Integer> stoBalance;
    @FXML
    private TableColumn<Pice, Boolean> stoPrice;
    @FXML
    private Label lblRacun;

    private final Crud crud = new Crud();
    private int selectedTable;
    private ObservableList<Pice> picaStola = FXCollections.observableArrayList();
    private ObservableList<Pice> pica = FXCollections.observableArrayList();

    public void initialize(){
        cmbPayment.setItems(FXCollections.observableArrayList(Arrays.stream(TIP_PLACANJA.values()).toList()));
        cmbPayment.getSelectionModel().select(0);
        crud.deleteMus2stoDate();
        crud.delNastup();

        cmbPickTable.setItems(crud.getTables());
        cmbPickTable.getSelectionModel().select(0);
        refreshAllDrinks();

        allId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        allBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        allPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        pica.setAll(crud.getPica());
        tblSvaPica.setItems(pica);
        tblSvaPica.getSelectionModel().select(0);

        stoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stoAlc.setCellValueFactory(new PropertyValueFactory<>("alc"));
        stoBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        stoPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblStoPica.setItems(picaStola);
    }

    @FXML
    public void refreshAllDrinks() {
        selectedTable = cmbPickTable.getValue().getId();
        ObservableList<Pice> p = FXCollections.observableArrayList();
        for(Pic2sto p2s : crud.getStoPica(crud.getTable(selectedTable))){
            p.add(crud.getPice(p2s));
        }
        picaStola.clear();
        picaStola.addAll(p);
    }
    @FXML
    public void onSetKonobar(){
        if(crud.getTableKonobar(selectedTable) != null && !Objects.equals(crud.getTableKonobar(selectedTable).getJmbg(), LoggedIn.getKonobar().getJmbg())) {
            Alert areYouSure = new Alert(Alert.AlertType.CONFIRMATION);
            areYouSure.setContentText("Da li želite da promenite konobara stola?");
            areYouSure.setHeaderText(null);
            Optional<ButtonType> result = areYouSure.showAndWait();
            if(result.get() == ButtonType.CANCEL) {
                return;
            }
        }
        crud.setTableKonobar(LoggedIn.getKonobar(), cmbPickTable.getValue().getId());

    }
    @FXML
    public void onAdd(){
        ObservableList<Mus2sto> listRez = crud.getMus2sto(crud.getTable(selectedTable));
        boolean checkAlc = false;
        boolean checkStock = false;
        boolean check;
        Mus2sto mus = listRez.getFirst();
        for(Mus2sto m : listRez){
            if(mus.getTime().compareTo(m.getTime()) > 0){
                mus = m;
            }
        }
        crud.setDateMus2sto(mus, new Timestamp(253402300799999L - selectedTable));
        for(Mus2sto m : listRez){
            if(mus.getTime().compareTo(m.getTime()) < 0){
                mus = m;
            }
        }
        checkAlc = crud.getMusterijaRez(mus).getAge() < 18 && tblSvaPica.getSelectionModel().getSelectedItem().isAlc();

        checkStock = tblSvaPica.getSelectionModel().getSelectedItem().getBalance() <= 0;

        check = !listRez.isEmpty() && !checkAlc && !checkStock;
        try {
            if(crud.getTableKonobar(selectedTable).getJmbg().equals(LoggedIn.getKonobar().getJmbg()) && check) {
                picaStola.add(tblSvaPica.getSelectionModel().getSelectedItem());
                crud.addPic2Sto(tblSvaPica.getSelectionModel().getSelectedItem(), crud.getTable(selectedTable));
                tblStoPica.setItems(picaStola);
                crud.delPice(tblSvaPica.getSelectionModel().getSelectedItem());
                crud.setRacun(selectedTable, cmbPayment.getValue());

                pica.setAll(crud.getPica());
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void onReservation() throws IOException {

        Scene scene = new Scene(new FXMLLoader(getClass().getResource("reservation.fxml")).load());
        Stage currStage = (Stage) cmbPickTable.getScene().getWindow();
        currStage.close();

        Stage stage = new Stage();
        stage.setTitle("Reservation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void onGenerateBill(){
        try {
            if(crud.getTableKonobar(selectedTable).getJmbg().equals(LoggedIn.getKonobar().getJmbg())) {
                crud.setRacun(selectedTable, cmbPayment.getValue());
                crud.setKonobarRacun(crud.getRacun(selectedTable), LoggedIn.getKonobar());
                lblRacun.setText(String.valueOf(crud.closeRacun(selectedTable).getBill()));
                crud.deletePicaPic2sto(selectedTable);
                refreshAllDrinks();
            }
        }catch(Exception ignore) {
        }
    }
    @FXML
    public void onBack() throws IOException {
        HelloApplication.fxmlStack.push("main-window.fxml");

        Scene scene = new Scene(new FXMLLoader(getClass().getResource(HelloApplication.fxmlStack.pop())).load());
        Stage currStage = (Stage) cmbPickTable.getScene().getWindow();
        currStage.close();

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        LoggedIn.loggout();
    }
}
