package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.Main;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorCtr  implements Initializable {
    @FXML
    private TextField txtIme,txtPrezime,txtEmail,txtKorisIme,txtLozinka,txtNazivLijeka,txtKolicina,txtProizvodjac,txtNazivSale,txtKrevet;
    @FXML
    private TextField txtNaziv,txtNovaLz,txtCijena;
    @FXML
    private Button btnKorisnici,btnOdjeli,btnUgasi,btnOperacije,btnLijekovi,btnPromjeni;
    @FXML
    private Label successLbl,errorLbl,errorDod,lblUspjehPass,lblSp,lblDrS,lblIspravka,lblPogreska,errorLijek,lblNeoznacenKor;
    @FXML
    private Label errorLj;
    @FXML
    private Label errorOdjel,errorSala,lblNeuspjehPass,hello,error,lblSpec,lblOdjel,lblDocImaPac,lblDocPacijenti;
    @FXML
    private Label errorSa,errorSalaNe,errorOdjelNe;
    @FXML
    private GridPane gridKorisnici,gridOdjeli,gridOperacija,gridLijekovi;
    @FXML
    private Pane pnlKorisnici,pnlUloga,pnlOdjeli,pnlOperacije,pnlLijekovi,pnlPass;
    @FXML
    private TableView <Lijek> tblLijekovi;
    @FXML
    private TableColumn <Odjel, String> tblClNazivLijek;
    @FXML
    private TableColumn <Odjel, String> tblClNazivProi;
    @FXML
    private TableColumn <Odjel, Integer> tblClKolicina;
    @FXML
    private TableColumn <Odjel, Float> tblClCijena;
    @FXML
    private TableView <Odjel> tblOdjeli;
    @FXML
    private TableColumn <Odjel, String> tblClNaziv;
    @FXML
    private TableColumn <Odjel, Integer> tblClBroj;
    @FXML
    private TableView <User> tblKorisnici;
    @FXML
    private TableView <Operacija> tblSale;
    @FXML
    private TableColumn <User, String> tblClNazivSale;
    @FXML
    private TableColumn <User, String> tblClName;
    @FXML
    private TableColumn <User, String> tblClLastName;
    @FXML
    private TableColumn <User, String> tblClUserName;
    @FXML
    private TableColumn <User, String> tblClEmail;
    @FXML
    private TableColumn <User, String> tblClUloga;
    @FXML
    private ChoiceBox<String> bxUloga;
    private String OdUloga;
    private String OdSpec;
    private String OdOdjel;
    private String[] uloge={"admin","sestra","doktor"};
    private List<String> odjeljenja=Odjel.odjeli();
    @FXML
    private ChoiceBox<String> bxSpec;
    @FXML
    private ChoiceBox<String> bxSp;
    @FXML
    private ChoiceBox<String> bxUl;
    private String[] specij={"neurokirurg","anesteziolog","kiroprakticar","opca praksa"};
    @FXML
    private ChoiceBox<String> bxOdjel;

    public AdministratorCtr() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.hello.setText("Dobrodošli," + "\n"  +Login.LoggedUser.getFirstName()+" "+ Login.LoggedUser.getLastName());
        pnlKorisnici.setVisible(true);
        pnlLijekovi.setVisible(false);
        pnlOdjeli.setVisible(false);
        pnlOperacije.setVisible(false);
        pnlPass.setVisible(false);
        gridKorisnici.setVisible(true);
        gridLijekovi.setVisible(false);
        gridOdjeli.setVisible(false);
        gridOperacija.setVisible(false);
        pnlUloga.setVisible(false);
        bxUloga.getItems().addAll(uloge);
        bxUloga.setValue("doktor");
        bxUl.getItems().addAll(odjeljenja);
        if(odjeljenja.size()>0){
            bxUl.setValue(odjeljenja.get(0));
            bxOdjel.setValue(odjeljenja.get(0));
        }
        bxOdjel.getItems().addAll(odjeljenja);
        bxUloga.setOnAction(this::getUloga);
        bxUl.setOnAction(this::getOdjel);
        bxOdjel.setOnAction(this::getOdjel);
        bxSp.setOnAction(this::getSpec);
        bxSp.getItems().addAll(specij);
        bxSp.setValue("neurokirurg");
        bxSpec.setOnAction(this::getSpec);
        bxSpec.getItems().addAll(specij);
        bxSpec.setValue("neurokirurg");
        this.tblClName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tblClLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.tblClEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.tblClUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.tblClUloga.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.populateTableView();
        this.tblKorisnici.setEditable(true);
        this.tblClName.setEditable(true);
        this.tblClLastName.setEditable(true);
        this.tblClName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tblClLastName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tblClNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.tblClBroj.setCellValueFactory(new PropertyValueFactory<>("brKreveta"));
        this.populateTableOdjel();
        this.tblOdjeli.setEditable(true);
        this.tblClNaziv.setEditable(true);
        this.tblClBroj.setEditable(true);
        this.tblClNaziv.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tblClBroj.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        this.tblClNazivSale.setCellValueFactory(new PropertyValueFactory<>("nazivSale"));
        this.populateTableSale();
        this.tblSale.setEditable(true);
        this.tblClNazivSale.setEditable(true);
        this.tblClNazivSale.setCellFactory(TextFieldTableCell.forTableColumn());


        this.tblClNazivLijek.setCellValueFactory(new PropertyValueFactory<>("nazivLijeka"));
        this.tblClNazivProi.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        this.tblClKolicina.setCellValueFactory(new PropertyValueFactory<>("količina"));
        this.tblClCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.populateTableLijekovi();
        this.tblLijekovi.setEditable(true);
        this.tblClNazivLijek.setEditable(true);
        this.tblClKolicina.setEditable(true);
        this.tblClCijena.setEditable(true);
        this.tblClNazivLijek.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tblClKolicina.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.tblClCijena.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
    }
    @FXML
    public void getUloga(ActionEvent event){
        OdUloga=bxUloga.getValue();
        if(OdUloga.equals("sestra")){
            bxSpec.setVisible(false);
            lblSpec.setVisible(false);
        }else if(OdUloga.equals("admin")){
            bxSpec.setVisible(false);
            lblSpec.setVisible(false);
            bxOdjel.setVisible(false);
            lblOdjel.setVisible(false);
        }else{
            bxSpec.setVisible(true);
            lblSpec.setVisible(true);
            bxOdjel.setVisible(true);
            lblOdjel.setVisible(true);
        }

    }
    @FXML
    public void getOdjel(ActionEvent event){
        OdOdjel=bxOdjel.getValue();

    }
    @FXML
    public void getSpec(ActionEvent event){
        OdSpec=bxSpec.getValue();

    }
    @FXML
    private void handleClicks(ActionEvent event) throws Exception {
        if(event.getSource()==btnKorisnici){
            errorDod.setVisible(false);
            error.setVisible(false);
            lblDocPacijenti.setVisible(false);
            lblNeoznacenKor.setVisible(false);
            bxUl.getItems().clear();
            bxOdjel.getItems().clear();
            odjeljenja=Odjel.odjeli();
            bxUl.getItems().addAll(odjeljenja);
            bxUl.setValue(odjeljenja.get(0));
            bxOdjel.getItems().addAll(odjeljenja);
            bxOdjel.setValue(odjeljenja.get(0));
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(true);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(true);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(false);
        }else if(event.getSource()==btnOdjeli){
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(false);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(true);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(false);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(true);
            gridOperacija.setVisible(false);
        }else if(event.getSource()==btnOperacije){
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(false);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(true);
            gridKorisnici.setVisible(false);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(true);
        }else if(event.getSource()==btnLijekovi){
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(false);
            pnlLijekovi.setVisible(true);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(false);
            gridLijekovi.setVisible(true);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(false);
        }else if(event.getSource()==btnPromjeni){
            lblNeuspjehPass.setVisible(false);
            lblUspjehPass.setVisible(false);
            pnlUloga.setVisible(false);
            pnlPass.setVisible(true);
            pnlKorisnici.setVisible(false);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(false);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(false);
        }else if(event.getSource()==btnUgasi){
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(true);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(true);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(false);
        }
        else{
            pnlUloga.setVisible(false);
            pnlPass.setVisible(false);
            pnlKorisnici.setVisible(false);
            pnlLijekovi.setVisible(false);
            pnlOdjeli.setVisible(false);
            pnlOperacije.setVisible(false);
            gridKorisnici.setVisible(false);
            gridLijekovi.setVisible(false);
            gridOdjeli.setVisible(false);
            gridOperacija.setVisible(false);
        }
    }
    @FXML
    public void odjava (ActionEvent ev){
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
    private void populateTableView() { try {
        this.tblKorisnici.getItems().setAll((Collection<?extends User>) User.users(User.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }

    }
    private void populateTableLijekovi() {
        try {
            this.tblLijekovi.getItems().setAll((Collection<? extends Lijek>) Lijek.lijek(Lijek.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }
    private void populateTableOdjel() {
        try {
            this.tblOdjeli.getItems().setAll((Collection<? extends Odjel>) Odjel.odjel(Odjel.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }
    private void populateTableSale() {
        try {
            this.tblSale.getItems().setAll((Collection<? extends Operacija>) Operacija.sale(Operacija.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }
    @FXML
    public void editUserFirstName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        Integer id=Login.LoggedUser.getId();
        User u = evt.getRowValue();
        String ime=evt.getNewValue();
        if(id==u.getId()){
            u.setFirstName(evt.getNewValue());
            this.hello.setText("Dobrodošli," + "\n"  +ime+" "+ Login.LoggedUser.getLastName());
            u.update();
        }else{
            u.setFirstName(evt.getNewValue());
            u.update();
        }


    }
    @FXML
    public void editUserLastName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        Integer id=Login.LoggedUser.getId();
        User u = evt.getRowValue();
        String ime=evt.getNewValue();
        if(id==u.getId()){
            u.setLastName(evt.getNewValue());
            this.hello.setText("Dobrodošli," + "\n"  +Login.LoggedUser.getFirstName()+" "+ ime);
            u.update();
        }else{
            u.setLastName(evt.getNewValue());
            u.update();
        }

    }
    @FXML
    public void urediUlogu(ActionEvent ev)throws Exception{
        User u = tblKorisnici.getSelectionModel().getSelectedItem();
        if(u==null){
            lblNeoznacenKor.setVisible(true);
            System.out.println("niste oznacili korisnika");
        }else{
            lblNeoznacenKor.setVisible(false);
            if(u.getRole().equals("doktor") || u.getRole().equals("sestra")){
                pnlUloga.setVisible(true);
                pnlKorisnici.setVisible(false);
                gridKorisnici.setVisible(false);
                if(u.getRole().equals("doktor") ){
                    lblDrS.setText("Uredi doktora");
                    lblDrS.setVisible(true);
                    bxSp.setVisible(true);
                    lblSp.setVisible(true);
                }else{
                    lblDrS.setText("Uredi sestru");
                    lblDrS.setVisible(true);
                    bxSp.setVisible(false);
                    lblSp.setVisible(false);
                }
            }else{
                error.setText("ne mogu urediti korisnika koji nije doktor ili sestra");
                error.setVisible(true);
            }
        }
    }
    @FXML
    public void potvrdaUloge(ActionEvent ev) throws Exception{
        User u = tblKorisnici.getSelectionModel().getSelectedItem();
        if(u.getRole().equals("sestra")){
            if(bxUl.getValue().equals("")){
                System.out.println("greska");
                lblPogreska.setVisible(true);
                lblIspravka.setVisible(false);

            }else{
                Sestra ses=Sestra.ussr(u.getId());
                ses.setOdjelFK(Odjel.odfk(this.bxUl.getValue()));
                ses.update();
                lblIspravka.setVisible(true);
                lblPogreska.setVisible(false);
            }
        }else{
            if(bxUl.getValue()==null|| bxSp.getValue()==null){
                System.out.println("greska");
                lblPogreska.setVisible(true);
                lblIspravka.setVisible(false);
            }else{
                if(Inpacijent.moguLiMjenjat(Doktor.usdr(u.getId()).getId())){
                    lblDocImaPac.setVisible(true);
                    System.out.println("prvo mora otpustiti svoje pacijente na odjelu");
                }else if(Terminpregleda.provjeriPostojiDoktorP(Doktor.usdr(u.getId()).getId())){
                    System.out.println("prvo mora otkazati preglede na odjelu");
                }else{
                    Doktor doc=Doktor.usdr(u.getId());
                    doc.setOdjelFK(Odjel.odfk(this.bxUl.getValue()));
                    doc.setSpecijalizacija(bxSp.getValue());
                    doc.update();
                    lblIspravka.setVisible(true);
                    lblPogreska.setVisible(false);
                    lblDocImaPac.setVisible(false);
                }
            }
        }
    }
    @FXML
    public void zatvori(ActionEvent ev)throws Exception{
        pnlKorisnici.setVisible(true);
        pnlUloga.setVisible(false);
        gridKorisnici.setVisible(true);
        lblIspravka.setVisible(false);
        lblPogreska.setVisible(false);
        lblDocImaPac.setVisible(false);
    }
    @FXML
    public void zatvoriPass(ActionEvent ev)throws Exception{
        pnlKorisnici.setVisible(true);
        pnlPass.setVisible(false);
        gridKorisnici.setVisible(true);
        lblUspjehPass.setVisible(false);
        lblNeuspjehPass.setVisible(false);
    }

    @FXML
    public void promjeniLozinku(ActionEvent ev)throws Exception{
        String userName = this.txtNovaLz.getText().toString();
        String korisnik=Login.LoggedUser.getUserName().toString();
        User konacna=User.change(korisnik);
        if(this.txtNovaLz.getText().equals("")){
            lblNeuspjehPass.setVisible(true);
            lblUspjehPass.setVisible(false);
        }else{
            konacna.setPassword(userName);
            konacna.update();
            txtNovaLz.setText("");
            lblUspjehPass.setVisible(true);
            lblNeuspjehPass.setVisible(false);
        }
    }

    @FXML
    public void deleteUser (ActionEvent ev) throws Exception {
        Integer id=Login.LoggedUser.getId();
        User u = tblKorisnici.getSelectionModel().getSelectedItem();
        if(u==null){
            lblNeoznacenKor.setVisible(true);
            error.setVisible(false);
            lblDocPacijenti.setVisible(false);
            System.out.println("oznaci polje");
        }
        else if(id==u.getId()){
            error.setText("ne mogu izbrisati trenutno prijavljenog korisnika");
            error.setVisible(true);
            lblNeoznacenKor.setVisible(false);
        }else{
            if(u.getRole().equals("doktor")){
                if(Inpacijent.moguLiMjenjat(Doktor.usdr(u.getId()).getId()) || Terminoperacija.provjeriPostojiDoktor(Doktor.usdr(u.getId()).getId()) || Terminpregleda.provjeriPostojiDoktorP(Doktor.usdr(u.getId()).getId())){
                    lblDocPacijenti.setVisible(true);
                    lblNeoznacenKor.setVisible(false);
                    error.setVisible(false);
                    System.out.println("doktor ima pacijente ili operacije ili preglede ne mogu izbrisati");
                }else{
                    Doktor doc=Doktor.usdr(u.getId());
                    doc.delete();
                    u.delete();
                    this.populateTableView();
                    error.setVisible(false);
                    lblDocPacijenti.setVisible(false);
                    lblNeoznacenKor.setVisible(false);
                }
            }else if(u.getRole().equals("sestra")){
                Sestra doc=Sestra.ussr(u.getId());
                doc.delete();
                u.delete();
                this.populateTableView();
                error.setVisible(false);
                lblDocPacijenti.setVisible(false);
                lblNeoznacenKor.setVisible(false);
            }else{
                u.delete();
                this.populateTableView();
                error.setVisible(false);
                lblDocPacijenti.setVisible(false);
                lblNeoznacenKor.setVisible(false);
            }

        }
    }
    @FXML
    public void editNaziv (TableColumn.CellEditEvent<Odjel, String> evt) throws Exception {
        Odjel u = evt.getRowValue();
        u.setNaziv(evt.getNewValue());
        u.update();

    }
    @FXML
    public void editBroj (TableColumn.CellEditEvent<Odjel, Integer> evt) throws Exception {
        Odjel u = evt.getRowValue();
        u.setBrKreveta(evt.getNewValue());
        u.update();
    }

    @FXML
    public void deleteOdjel (ActionEvent ev) throws Exception {
        Odjel u = tblOdjeli.getSelectionModel().getSelectedItem();
        if(u==null){
            errorOdjelNe.setVisible(true);
            errorOdjel.setVisible(false);
            System.out.println("molimo odaberite odjel ");
        }else if(Doktor.oddr(u.getId())!=null || Sestra.odsr(u.getId())!=null || Inpacijent.inod(u.getId())!=null){
            errorOdjel.setVisible(true);
            errorOdjelNe.setVisible(false);
        }else{
            u.delete();
            this.populateTableOdjel();
            errorOdjel.setVisible(false);
            errorOdjelNe.setVisible(false);
        }
    }

    @FXML
    public void addOdjelToDatabase(javafx.event.ActionEvent actionEvent) throws Exception {
        String naziv = this.txtNaziv.getText().toString();
        String kreveti=this.txtKrevet.getText();


        if (naziv.equals("") || kreveti.equals("")){
            errorLbl.setText("Morate popuniti sva polja.");
            errorLbl.setVisible(true);
            successLbl.setVisible(false);
        }

        else {
            Odjel u = new Odjel();
            if(Odjel.postojiod(this.txtNaziv.getText())==true){
                errorLbl.setText("odjel vec postoji.");
                errorLbl.setVisible(true);
                txtNaziv.setText("");
                txtKrevet.setText("");
            }
            else{
                u.setNaziv(this.txtNaziv.getText());
                try{
                    u.setBrKreveta(Integer.parseInt(this.txtKrevet.getText()));
                }catch (NumberFormatException e){
                    errorLbl.setText("treba biti broj .");
                    errorLbl.setVisible(true);
                }

                try {
                    u.save();
                    this.populateTableOdjel();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorLbl.setVisible(true);
                    successLbl.setVisible(false);
                }

                errorLbl.setVisible(false);
                successLbl.setVisible(true);
                txtNaziv.setText("");
                txtKrevet.setText("");
            }


        }}
    @FXML
    public void editNazivSale (TableColumn.CellEditEvent<Operacija, String> evt) throws Exception {
        Operacija u = evt.getRowValue();
        u.setNazivSale(evt.getNewValue());
        u.update();
    }
    @FXML
    public void deleteSala (ActionEvent ev) throws Exception {
        Operacija u = tblSale.getSelectionModel().getSelectedItem();
        if(u==null){
            errorSalaNe.setVisible(true);
            errorSa.setVisible(false);
            System.out.println("odaberite salu");
        }
        else if(Terminoperacija.provjeriPostojiSala(u.getId())){
            System.out.println("ne moze se obrisati sala koja se koristi");
            errorSa.setVisible(true);
            errorSalaNe.setVisible(false);
        }else{
            u.delete();
            this.populateTableSale();
            errorSa.setVisible(false);
            errorSalaNe.setVisible(false);
        }
    }
    @FXML
    public void addSalaToDatabase(javafx.event.ActionEvent actionEvent) throws Exception {
        String naziv = this.txtNazivSale.getText().toString();


        if (naziv.equals("")){
            errorSala.setText("Morate popuniti sva polja.");
            errorSala.setVisible(true);
        }

        else {
            Operacija u = new Operacija();
            if(Operacija.postojiop(this.txtNazivSale.getText())==true){
                errorSala.setText("Sala vec postoji.");
                errorSala.setVisible(true);
                txtNazivSale.setText("");
            }
            else{
                u.setNazivSale(this.txtNazivSale.getText());

                try {
                    u.save();
                    this.populateTableSale();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorSala.setText("Molimo popunite sva polja");
                    errorSala.setVisible(true);

                }

                errorSala.setVisible(false);
                txtNazivSale.setText("");
            }


        }}
    @FXML
    public void editNazivLijeka (TableColumn.CellEditEvent<Lijek, String> evt) throws Exception {
        Lijek u = evt.getRowValue();
        u.setNazivLijeka(evt.getNewValue());
        u.update();
    }

    @FXML
    public void editKolicina (TableColumn.CellEditEvent<Lijek, Integer> evt) throws Exception {
        Lijek u = evt.getRowValue();
        u.setKoličina(evt.getNewValue());
        u.update();
    }
    @FXML
    public void editCijena (TableColumn.CellEditEvent<Lijek, Float> evt) throws Exception {
        Lijek u = evt.getRowValue();
        u.setCijena(evt.getNewValue());
        u.update();
    }

    @FXML
    public void deleteLijek (ActionEvent ev) throws Exception {
        Lijek u = tblLijekovi.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("oznacite lijek koji želite izbrisati");
        }else{
            u.delete();
            this.populateTableLijekovi();
        }
    }
    @FXML
    public void addLijekToDatabase(javafx.event.ActionEvent actionEvent) throws Exception {
        String naziv = this.txtNazivLijeka.getText().toString();
        String proiz = this.txtProizvodjac.getText().toString();
        String kolicina=this.txtKolicina.getText();
        String cijena=this.txtCijena.getText();
        //int kolicina = Integer.parseInt(this.txtKolicina.getText());
        //float cijena = Float.parseFloat(this.txtCijena.getText());


        if (naziv.equals("") || kolicina.equals("") || proiz.equals("") || cijena.equals("")){
            errorLijek.setText("Morate popuniti sva polja.");
            errorLijek.setVisible(true);
            this.txtNazivLijeka.setText("");
            this.txtProizvodjac.setText("");
            this.txtKolicina.setText("");
            this.txtCijena.setText("");
        }

        else {
            Lijek u = new Lijek();
            if((Lijek.postLijek(this.txtNazivLijeka.getText(),this.txtProizvodjac.getText()))==true){
                errorLijek.setText("postoji vec u bazi podataka");
                errorLijek.setVisible(true);

            }else{
                u.setNazivLijeka(this.txtNazivLijeka.getText());
                u.setProizvodjac(this.txtProizvodjac.getText());
                try{
                    u.setKoličina(Integer.parseInt(this.txtKolicina.getText()));
                    u.setCijena(Float.parseFloat(this.txtCijena.getText()));
                }catch (NumberFormatException e){
                    System.out.println("unesi broj");
                }

                try {
                    u.save();
                    this.populateTableLijekovi();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorLbl.setVisible(true);
                    successLbl.setVisible(false);
                }

                errorLbl.setVisible(false);
                successLbl.setVisible(true);
                this.txtNazivLijeka.setText("");
                this.txtProizvodjac.setText("");
                this.txtKolicina.setText("");
                this.txtCijena.setText("");
            }

        }}
    @FXML
    public void addUserToDatabase(javafx.event.ActionEvent actionEvent) throws Exception {
        String ime = this.txtIme.getText().toString();
        String prezime = this.txtPrezime.getText().toString();
        String email = this.txtEmail.getText().toString();
        String korime = this.txtKorisIme.getText().toString();
        String lozinka = this.txtLozinka.getText().toString();
        String uloga=this.bxUloga.getValue().toString();
        String specijal=this.bxSpec.getValue();
        String odjell=this.bxOdjel.getValue();

        if (email.equals("") || lozinka.equals("") || ime.equals("") || prezime.equals("") || korime.equals("") || uloga==null || odjell==null){
            errorDod.setText("Morate popuniti sva polja.");
            errorDod.setVisible(true);
        }

        else {
            if(User.postoji(this.txtKorisIme.getText(),this.txtEmail.getText())){
                errorDod.setText("Korisnik već postoji.");
                errorDod.setVisible(true);
            }else{
                if(uloga.equals("doktor")){

                    User u = new User();
                    u.setFirstName(this.txtIme.getText());
                    u.setLastName(this.txtPrezime.getText());
                    u.setEmail(this.txtEmail.getText());
                    u.setPassword(this.txtLozinka.getText());
                    u.setUserName(this.txtKorisIme.getText());
                    u.setRole(this.bxUloga.getValue());
                    try {
                        u.save();
                        Doktor dr=new Doktor();
                        dr.setOdjelFK(Odjel.odfk(odjell));
                        dr.setSpecijalizacija(specijal);
                        dr.setUserFK(u.getId());
                        this.populateTableView();
                        try{
                            dr.save();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            errorDod.setVisible(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorDod.setVisible(true);

                    }

                    errorDod.setVisible(false);

                    txtIme.setText("");
                    txtPrezime.setText("");
                    txtKorisIme.setText("");
                    txtEmail.setText("");
                    txtLozinka.setText("");
                    bxUloga.setValue("doktor");

                }else if(uloga.equals("sestra")){

                    User u = new User();
                    u.setFirstName(this.txtIme.getText());
                    u.setLastName(this.txtPrezime.getText());
                    u.setEmail(this.txtEmail.getText());
                    u.setPassword(this.txtLozinka.getText());
                    u.setUserName(this.txtKorisIme.getText());
                    u.setRole(this.bxUloga.getValue());
                    try {
                        u.save();
                        Sestra sr=new Sestra();
                        sr.setOdjelFK(Odjel.odfk(odjell));
                        sr.setUserFK(u.getId());
                        this.populateTableView();
                        try{
                            sr.save();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            errorDod.setVisible(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorDod.setVisible(true);
                    }

                    errorDod.setVisible(false);
                    txtIme.setText("");
                    txtPrezime.setText("");
                    txtKorisIme.setText("");
                    txtEmail.setText("");
                    txtLozinka.setText("");
                    bxUloga.setValue("doktor");
                    bxSpec.setVisible(true);
                    lblSpec.setVisible(true);
                }else{
                    User u = new User();
                    u.setFirstName(this.txtIme.getText());
                    u.setLastName(this.txtPrezime.getText());
                    u.setEmail(this.txtEmail.getText());
                    u.setPassword(this.txtLozinka.getText());
                    u.setUserName(this.txtKorisIme.getText());
                    u.setRole(this.bxUloga.getValue());
                    try {
                        u.save();
                        this.populateTableView();

                    } catch (Exception e) {
                        e.printStackTrace();
                        errorDod.setVisible(true);
                    }

                    errorDod.setVisible(false);
                    txtIme.setText("");
                    txtPrezime.setText("");
                    txtKorisIme.setText("");
                    txtEmail.setText("");
                    txtLozinka.setText("");
                    bxUloga.setValue("doktor");
                    bxSpec.setVisible(true);
                    lblSpec.setVisible(true);
                }
            }
            }

}
}

