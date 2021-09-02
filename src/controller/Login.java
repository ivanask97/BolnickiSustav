package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login implements Initializable {
    public static User LoggedUser;

    @FXML
    Button loginBtn;

    @FXML
    TextField userNameTxt;

    @FXML
    PasswordField passwordTxt;

    @FXML
    Label greskaLbl;

    @FXML
    Label greska1Lbl;

    @FXML
    public void login(ActionEvent ev) throws SQLException {
        String userName = this.userNameTxt.getText().toString();
        String password = this.passwordTxt.getText().toString();

        if (userName.equals("") || password.equals("")) {
            greskaLbl.setVisible(true);
        } else {
            greskaLbl.setVisible(false);

            try {
                Login.LoggedUser = User.login(userName, password);
                if (Login.LoggedUser != null) {
                    if (Login.LoggedUser.getRole().equals("admin")) {
                        Main.showWindow(
                                getClass(),
                                "/view/administrator.fxml",
                                "Administracija",
                                1000, 700,150,10
                        );
                    } else if (Login.LoggedUser.getRole().equals("doktor")) {
                        Main.showWindow(
                                getClass(),
                                "/view/doktor.fxml",
                                "Doktor",
                                1000, 700,150,10
                        );
                    } else if (Login.LoggedUser.getRole().equals("sestra")) {
                        Main.showWindow(
                                getClass(),
                                "/view/sestra.fxml",
                                "Sestra",
                                1000, 700,150,10
                        );
                    } else {
                        Main.showWindow(
                                getClass(),
                                "/view/farmaceut.fxml",
                                "Farmaceut",
                                1000, 700,150,10
                        );
                    }
                } else {
                    greska1Lbl.setVisible(true);
                }
            } catch (Exception e) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("Došlo je do pogreške");
            }

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}