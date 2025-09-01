package me.mispz.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.mispz.crud.Crud;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.mispz.entities.Konobar;
import me.mispz.entities.Sto;
import me.mispz.util.LoggedIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainWindow {

    @FXML
    private PasswordField pfPass;
    @FXML
    private TextField txfName;

    private final Crud crud = new Crud();

    @FXML
    protected void onLog(ActionEvent event) throws IOException {
        if(crud.checkLog(txfName.getText(), pfPass.getText())){
            LoggedIn.logIn(crud.getKonobar(txfName.getText(), pfPass.getText()));
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("table-selection.fxml")).load());
            Stage currStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            currStage.setTitle("Tables");
            currStage.setScene(scene);
        }
    }
    
}
