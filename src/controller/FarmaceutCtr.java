package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FarmaceutCtr implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void odjavaFr (ActionEvent ev){
        Login.LoggedUser = null;
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Login.fxml",
                    "Prijavite se", 800, 350, 550, 200);
        } catch (IOException e) {
            System.out.println("Došlo je do pogreške.");
        }
    }
}
