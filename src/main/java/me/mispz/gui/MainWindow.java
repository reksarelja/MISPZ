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
import me.mispz.util.LoggedIn;

import java.io.IOException;

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
            currStage.close();

            Stage stage = new Stage();
            stage.setTitle("Tables");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
    
}
