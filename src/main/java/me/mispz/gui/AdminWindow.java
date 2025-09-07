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


import java.io.IOException;
import java.sql.Timestamp;

public class AdminWindow {

	@FXML
	private TableView<Sto> tblSto;
	@FXML
	private TableColumn<Sto, Integer> stoId;
	@FXML
	private TextField txfKJmbg;
	@FXML
	private TextField txfKName;
	@FXML
	private TextField txfKLast;
	@FXML
	private TextField txfKPass;
	@FXML
	private TextField txfNName;
	@FXML
	private TextField txfNDate;
	@FXML
	private TextField txfNSearch;
	@FXML
	private TableView<Nastup> tblNastup;
	@FXML
	private TableColumn<Object, String> nastupIme;
	@FXML
	private TableColumn<Object, Timestamp> nastupDatum;
	@FXML
	private TextField txfOMessage;
	@FXML
	private TextField txfODate;
	@FXML
	private TextField txfOSearch;
	@FXML
	private TableView<Obavestenje> tblObav;
	@FXML
	private TableColumn<Object, String> obavPoruka;
	@FXML
	private TableColumn<Object, Timestamp> obavDatum;
	@FXML
	private TextField txfPName;
	@FXML
	private ToggleButton isAlc;
	@FXML
	private TextField txfPBalance;
	@FXML
	private TextField txfPPrice;
	@FXML
	private TextField txfPDodajKol;
	@FXML
	private TableView<Pice> tblPPica;
	@FXML
	private TableColumn<Pice, String> piceIme;
	@FXML
	private TableColumn<Pice, Integer> piceStanje;

	private ObservableList<Sto> stolovi;
	private ObservableList<Pice> pica;
	private ObservableList<Nastup> nastupi;
	private ObservableList<Nastup> nastupiFilter;
	private ObservableList<Obavestenje> obavestenja;
	private ObservableList<Obavestenje> obavestenjaFilter;

	private final Crud crud = new Crud();
	public void initialize(){


		stolovi = FXCollections.observableArrayList(crud.getTables());
		pica = FXCollections.observableArrayList(crud.getPica());
		stoId.setCellValueFactory(new PropertyValueFactory<>("Id"));

		tblSto.setItems(stolovi);

		piceIme.setCellValueFactory(new PropertyValueFactory<>("name"));
		piceStanje.setCellValueFactory(new PropertyValueFactory<>("balance"));

		tblPPica.setItems(pica);

		nastupIme.setCellValueFactory(new PropertyValueFactory<>("nastupName"));
		nastupDatum.setCellValueFactory(new PropertyValueFactory<>("nastupDate"));

		obavPoruka.setCellValueFactory(new PropertyValueFactory<>("text"));
		obavDatum.setCellValueFactory(new PropertyValueFactory<>("date"));

		refresh();

		txfNSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			nastupiFilter.clear();
			if(newValue.isEmpty()){
				nastupiFilter.setAll(nastupi);
			} else{
				for(Nastup rez : nastupi){
					if(rez.getNastupDate().toString().contains(newValue)){
						nastupiFilter.add(rez);
					}
				}
			}
		});
		txfOSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			obavestenjaFilter.clear();
			if(newValue.isEmpty()){
				obavestenjaFilter.setAll(obavestenja);
			} else{
				for(Obavestenje rez : obavestenja){
					if(rez.getDate().toString().contains(newValue)){
						obavestenjaFilter.add(rez);
					}
				}
			}
		});
	}

	@FXML
	public void onTAdd(){
		crud.addTable();
		stolovi.setAll(crud.getTables());
	}
	@FXML
	public void onKAdd(){
		try {
			crud.addKonobar(txfKJmbg.getText(), txfKName.getText(), txfKLast.getText(), txfKPass.getText());
			txfKJmbg.clear();
			txfKName.clear();
			txfKLast.clear();
			txfKPass.clear();
		}catch(Exception ignored){
		}
	}
	@FXML
	public void onPAdd(){
		try {
			crud.addPice(txfPName.getText(), isAlc.isSelected(), Integer.parseInt(txfPBalance.getText()), Integer.parseInt(txfPPrice.getText()));
			txfPName.clear();
			isAlc.setSelected(false);
			txfPBalance.clear();
			txfPPrice.clear();
			pica.setAll(crud.getPica());
		} catch(Exception ignored){
		}
	}
	@FXML
	public void onDodajKol(){
		try {
			crud.addKolicina(tblPPica.getSelectionModel().getSelectedItem(), Integer.parseInt(txfPDodajKol.getText()));
			pica.setAll(crud.getPica());
			txfPDodajKol.clear();
		} catch(Exception ignored){
		}
	}
	@FXML
	public void onNDelete(){
		try {
			inform(tblNastup.getSelectionModel().getSelectedItem().getNastupDate());
			crud.delSelNastup(tblNastup.getSelectionModel().getSelectedItem());
			refresh();
			String s = txfNSearch.getText();
			txfNSearch.setText("");
			txfNSearch.setText(s);
		}catch(Exception ignored){
		}
	}
	@FXML
	public void onODelete(){
		try {
			crud.delSelObavestenje(tblObav.getSelectionModel().getSelectedItem());
			refresh();
			String s = txfOSearch.getText();
			txfOSearch.setText("");
			txfOSearch.setText(s);
		}catch(Exception ignored){
		}
	}
	@FXML
	public void onNAdd(){
		try {
			crud.addNastup(txfNName.getText(), Timestamp.valueOf(txfNDate.getText()));
			txfNName.clear();
			txfNDate.clear();
			refresh();
		}catch(Exception ignored){
		}
	}
	@FXML
	public void onOAdd(){
		try {
			inform(Timestamp.valueOf(txfODate.getText()));
			crud.addObavestenje(txfOMessage.getText(), Timestamp.valueOf(txfODate.getText()));
			txfOMessage.clear();
			txfODate.clear();
			refresh();
		}catch(Exception ignored){
		}
	}
	@FXML
	public void onBack() throws IOException {
		HelloApplication.fxmlStack.push("main-window.fxml");
		Scene scene = new Scene(new FXMLLoader(getClass().getResource(HelloApplication.fxmlStack.pop())).load());
		Stage currStage = (Stage) tblNastup.getScene().getWindow();
		currStage.close();
		Stage stage = new Stage();
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		LoggedIn.loggout();
	}
	public void refresh(){
		crud.deleteMus2stoDate();
		crud.delNastup();
		crud.delObavestenje();
		nastupi = FXCollections.observableArrayList(crud.getNastupi());
		nastupiFilter = FXCollections.observableArrayList(nastupi);
		obavestenja = FXCollections.observableArrayList(crud.getObavestenja());
		obavestenjaFilter = FXCollections.observableArrayList(obavestenja);
		tblNastup.setItems(nastupiFilter);
		tblObav.setItems(obavestenjaFilter);
	}
	public void inform(Timestamp timestamp){
		ObservableList<Musterija> list = crud.getMus2sto(timestamp);
		StringBuilder telefoni = new StringBuilder();
		for(Musterija m : list){
			telefoni.append(m.getName()).append(": ").append(m.getPhone_number()).append("\n");
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText("Obavestiti musterije");
		alert.setContentText(telefoni.toString());
		alert.showAndWait();
	}

}
