package me.mispz.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.mispz.crud.Crud;
import me.mispz.util.LoggedIn;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Crud crud = new Crud();
    @Override
    public void start(Stage stage) throws IOException {

        LoggedIn.logIn(crud.getKonobar("w", "111"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("table-selection.fxml")));
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}
