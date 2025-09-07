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
import me.mispz.entities.Musterija;
import me.mispz.entities.Nastup;
import me.mispz.entities.Sto;
import me.mispz.util.Rezervacija;

import java.io.IOException;
import java.sql.Timestamp;

public class Reservation {
	@FXML
	private ComboBox<Sto> cmbTables;
	@FXML
	private TextField txfName;
	@FXML
	private TextField txfPhone;
	@FXML
	private TextField txfDate;
	@FXML
	private TextField txfAge;
	@FXML
	private TableView<Rezervacija> tblRez;
	@FXML
	private TableColumn<Rezervacija, Integer> stoId;
	@FXML
	private TableColumn<Rezervacija, String> musName;
	@FXML
	private TableColumn<Rezervacija, String> musBroj;
	@FXML
	private TableColumn<Rezervacija, Timestamp> rezTime;
	@FXML
	private TextField txfSearch;
	@FXML
	private TableView<Nastup> tblNastupi;
	@FXML
	private TableColumn<Nastup, String> nastupIme;
	@FXML
	private TableColumn<Nastup, Timestamp> nastupDatum;
	private final Crud crud = new Crud();
	ObservableList<Rezervacija> listRez;
	ObservableList<Rezervacija> filteredRez;

	ObservableList<Nastup> listNastupa;
	ObservableList<Nastup> filteredNastup;

	private Sto sto;


	public void initialize(){
		cmbTables.setItems(crud.getTables());
		cmbTables.getSelectionModel().select(0);
		onChange();
		stoId.setCellValueFactory(new PropertyValueFactory<>("stoId"));
		musName.setCellValueFactory(new PropertyValueFactory<>("musName"));
		musBroj.setCellValueFactory(new PropertyValueFactory<>("musBroj"));
		rezTime.setCellValueFactory(new PropertyValueFactory<>("rezTime"));

		nastupIme.setCellValueFactory(new PropertyValueFactory<>("nastupName"));
		nastupDatum.setCellValueFactory(new PropertyValueFactory<>("nastupDate"));

		refresh();
		txfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredRez.clear();
			filteredNastup.clear();
			if(newValue.isEmpty()){
				filteredRez.setAll(listRez);
				filteredNastup.setAll(listNastupa);
			} else{
				for(Rezervacija rez : listRez){
					if(rez.getRezTime().toString().contains(newValue)){
						filteredRez.add(rez);
					}

				}
				for(Nastup rez : listNastupa){
					if(rez.getNastupDate().toString().contains(newValue)){
						filteredNastup.add(rez);
					}

				}
			}
		});
	}

	@FXML
	public void onChange() {
		sto = cmbTables.getValue();
	}

	@FXML
	public void onReserve() {
		try {
			Musterija musterija = crud.addMusterija(txfName.getText(), txfPhone.getText(), Integer.parseInt(txfAge.getText()));
			crud.addMus2sto(sto, musterija, Timestamp.valueOf(txfDate.getText()));
			refresh();
			txfName.clear();
			txfPhone.clear();
			txfAge.clear();
			txfDate.clear();
		} catch(Exception ignored){
		}
	}

	@FXML
	public void onDelete(){
		crud.delMus2sto(tblRez.getSelectionModel().getSelectedItem());
		refresh();
		String s = txfSearch.getText();
		txfSearch.setText("");
		txfSearch.setText(s);
	}
	@FXML
	public void onBack() throws IOException {
		HelloApplication.fxmlStack.push("table-selection.fxml");

		Scene scene = new Scene(new FXMLLoader(getClass().getResource(HelloApplication.fxmlStack.pop())).load());
		Stage currStage = (Stage) cmbTables.getScene().getWindow();
		currStage.close();

		Stage stage = new Stage();
		stage.setTitle("Tables");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	public void refresh(){
		crud.deleteMus2stoDate();
		crud.delNastup();
		listRez = FXCollections.observableArrayList(crud.getMus2stos());
		filteredRez = FXCollections.observableArrayList(listRez);

		listNastupa = FXCollections.observableArrayList(crud.getNastupi());
		filteredNastup = FXCollections.observableArrayList(listNastupa);

		tblRez.setItems(filteredRez);
		tblNastupi.setItems(filteredNastup);

	}
}
