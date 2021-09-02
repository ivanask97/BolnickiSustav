package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Table.create(User.class);
        Table.create(Lijek.class);
        Table.create(Operacija.class);
        Table.create(Odjel.class);
        Table.create(Pacijent.class);
        Table.create(Sestra.class);
        Table.create(Doktor.class);
        Table.create(Inpacijent.class);
        Table.create(Boravak.class);
        Table.create(Pregled.class);
        Table.create(Terminpregleda.class);
        Table.create(Terminoperacija.class);
        Pacijente samostalno upisati u bazu podataka, jer nema sučelja za to;
        User admin = new User();
        admin.setFirstName("Ivana");
        admin.setLastName("Skobic");
        admin.setEmail("ivana.skobic@fpmoz.sum.ba");
        admin.setPassword("ivana");
        admin.setUserName("ivanask");
        admin.setRole("admin");
        admin.save();
        User doktor = new User();
        doktor.setFirstName("Ivona");
        doktor.setLastName("Skobic");
        doktor.setEmail("ivona.skobic@fpmoz.sum.ba");
        doktor.setPassword("ivona");
        doktor.setUserName("ivonask");
        doktor.setRole("doktor");
        doktor.save();*/
        Main.primaryStage=primaryStage;
        Main.showWindow(
                getClass(),
                "../view/login.fxml",
                "Prijava na sustav",800,350,550,200
        );}
        public static void showWindow(Class windowClass, String viewName, String title, int w, int h,int x, int y) throws IOException{
            Parent root = FXMLLoader.load(windowClass.getResource(viewName));
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root, w,h));
            primaryStage.show();
            primaryStage.setX(x);
            primaryStage.setY(y);
        }

    public static void main(String[] args) {
        launch(args);
    }
}
