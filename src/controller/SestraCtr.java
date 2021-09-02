package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import main.Main;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class SestraCtr implements Initializable {
    @FXML
    private Button btnPacSs,btnPregSs,btnPromSs;
    @FXML
    private Pane pnlPacSs,pnlPregledSs,pnlKartonSs,pnlPromjenaSs,pnlLijekoviSs;
    @FXML
    private DatePicker dpTerminSs;

    @FXML
    private Label lblWelcomeSr,lblGoodSs,lblBadMjSs,lblBadEmailSs,lblImePacSs,lblPrezimePacSs,lblSpolPacSs,lblMjestoPacSs,lblDatumPacSs,lblNaslovPac;
    @FXML
    private Label lblPopisLjSs,lblSestraKPNO,lblSestraNoPac,lblSestraPNOd,lblSestraTerminGreska,lblSestraTerminIspravno,lblOtkazatiSestra;
    @FXML
    private TextArea txtArLijekovi;
    @FXML
    private TextField txtImeSs,txtPrezimeSs,txtEmailSs,txtLozinkaSs,txtBrojKrSs,txtKartonTerminSs;
    @FXML
    private TableView<Boravak> tblBoravakSs;
    @FXML
    private TableView<Pacijent> tblInPacijentSs;
    @FXML
    private TableView<Pregled> tblPreglediSs;
    @FXML
    private TableView<Terminpregleda> tblTerminiPr;
    @FXML
    private TableColumn<Terminpregleda, String> tblClImeDocSs,tblClDatumPreSs,tblClVrijemePreSs,tblClNazivPacijentaSs;
    @FXML
    private TableColumn<Pacijent, String> tblClName1Ss,tblClLastName1Ss,tblClJMBGSs,tblClBrKartonSs;
    @FXML
    private TableColumn<Boravak, String> tblClSimptomPr1Ss, tblClDijagnozaPr1Ss, tblClPropLij1Ss, tblClDatumOd1Ss, tblClDatumPr1Ss;
    @FXML
    private TableColumn<Pregled, String> tblClDatumPrSs, tblClSimptomPrSs, tblClDijagnozaPrSs, tblClPropLijSs;
    @FXML
    private ChoiceBox<String>bxOdSs,bxDoktoriTerminSs,bxTerminSatSs;
    private List<String> odjeljenja = Odjel.odjeli();
    private String [] sati= {"09:00:00","09:30:00","10:00:00","10:30:00","11:00:00","11:30:00","12:00:00","12:30:00","13:00:00","13:30:00","14:00:00","14:30:00","15:00:00","15:30:00","16:00:00","16:30:00","17:00:00","17:30:00","18:00:00"};
    private String OdOdjel,karton,OdSati,OdDoktori;
    private List<String> doktoriOdjel=User.nadjiId(Doktor.sviDokt(Sestra.ussr(Login.LoggedUser.getId()).getOdjelFK()));
    //private List<String>doktoriImena=User.imenadok(doktoriOdjel);
    public SestraCtr() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lblWelcomeSr.setText("Dobrodošli," + "\n" + Login.LoggedUser.getFirstName() + " " + Login.LoggedUser.getLastName());
        pnlPacSs.setVisible(true);
        pnlPromjenaSs.setVisible(false);
        pnlKartonSs.setVisible(false);
        pnlPregledSs.setVisible(false);
        bxOdSs.getItems().addAll(odjeljenja);
        if(odjeljenja.size()>0){
            bxOdSs.setValue(odjeljenja.get(0));
        }
        bxOdSs.setOnAction(this::getOdjel);
        bxTerminSatSs.getItems().addAll(sati);
        bxTerminSatSs.setValue(sati[0]);
        bxTerminSatSs.setOnAction(this::getSati);

        bxDoktoriTerminSs.getItems().addAll(doktoriOdjel);
        if(doktoriOdjel.size()>0){
            bxDoktoriTerminSs.setValue(doktoriOdjel.get(0));
        }
        bxDoktoriTerminSs.setOnAction(this::getDoktori);

        this.tblClName1Ss.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tblClLastName1Ss.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.tblClJMBGSs.setCellValueFactory(new PropertyValueFactory<>("spol"));
        this.tblClBrKartonSs.setCellValueFactory(new PropertyValueFactory<>("brKartona"));
        this.populateTableInpacientSs();
        this.tblClDatumPr1Ss.setCellValueFactory(new PropertyValueFactory<>("datumUlaska"));
        this.tblClSimptomPr1Ss.setCellValueFactory(new PropertyValueFactory<>("simptom"));
        this.tblClDijagnozaPr1Ss.setCellValueFactory(new PropertyValueFactory<>("dijagnoza"));
        this.tblClPropLij1Ss.setCellValueFactory(new PropertyValueFactory<>("lijekovi"));
        this.tblClDatumOd1Ss.setCellValueFactory(new PropertyValueFactory<>("datumIzlaska"));
        this.tblClDatumPrSs.setCellValueFactory(new PropertyValueFactory<>("datumPregleda"));
        this.tblClSimptomPrSs.setCellValueFactory(new PropertyValueFactory<>("simptom"));
        this.tblClDijagnozaPrSs.setCellValueFactory(new PropertyValueFactory<>("dijagnoza"));
        this.tblClPropLijSs.setCellValueFactory(new PropertyValueFactory<>("lijekovi"));

        this.tblClImeDocSs.setCellValueFactory(new PropertyValueFactory<>("nazivDoktora"));
        this.tblClDatumPreSs.setCellValueFactory(new PropertyValueFactory<>("datumPregleda"));
        this.tblClVrijemePreSs.setCellValueFactory(new PropertyValueFactory<>("vrijemePregleda"));
        this.tblClNazivPacijentaSs.setCellValueFactory(new PropertyValueFactory<>("nazivPacijenta"));
        this.populateTableTerminiPregleda();

}
    @FXML
    private void handleClicks(ActionEvent event) throws Exception {
        if(event.getSource()==btnPacSs){
            lblSestraKPNO.setVisible(false);
            lblSestraNoPac.setVisible(false);
            lblSestraPNOd.setVisible(false);
            lblOtkazatiSestra.setVisible(false);
            pnlPacSs.setVisible(true);
            pnlPromjenaSs.setVisible(false);
            pnlKartonSs.setVisible(false);
            pnlPregledSs.setVisible(false);
        }else if(event.getSource()==btnPregSs){
            lblSestraTerminIspravno.setVisible(false);
            lblSestraTerminGreska.setVisible(false);
            pnlPacSs.setVisible(false);
            pnlPromjenaSs.setVisible(false);
            pnlKartonSs.setVisible(false);
            pnlPregledSs.setVisible(true);
        }else if(event.getSource()==btnPromSs){
            lblBadEmailSs.setVisible(false);
            lblGoodSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            pnlPacSs.setVisible(false);
            pnlPromjenaSs.setVisible(true);
            pnlKartonSs.setVisible(false);
            pnlPregledSs.setVisible(false);
        }
    }
    @FXML
    public void getOdjel(ActionEvent event) {
        OdOdjel = bxOdSs.getValue();
    }
    @FXML
    public void getSati(ActionEvent event) {
        OdSati = bxTerminSatSs.getValue();
    }
    @FXML
    public void getDoktori(ActionEvent event) {
        OdDoktori=bxDoktoriTerminSs.getValue();
    }
    @FXML
    public void odjavaSr (ActionEvent ev){
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
    @FXML
    public void promImeSr(ActionEvent ev) throws Exception {
        String ime = txtImeSs.getText().toString();
        if (ime.equals("")) {
            lblBadMjSs.setVisible(true);
            lblGoodSs.setVisible(false);
            lblBadEmailSs.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setFirstName(txtImeSs.getText());
            u.update();
            this.lblWelcomeSr.setText("Dobrodošli," + "\n" + ime + " " + Login.LoggedUser.getLastName());
            lblGoodSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            txtImeSs.setText("");
        }
    }
    @FXML
    public void promPrezimeSs(ActionEvent ev) throws Exception {
        String ime = txtPrezimeSs.getText().toString();
        if (ime.equals("")) {
            lblBadMjSs.setVisible(true);
            lblGoodSs.setVisible(false);
            lblBadEmailSs.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setLastName(txtPrezimeSs.getText());
            u.update();
            this.lblWelcomeSr.setText("Dobrodošli," + "\n" + Login.LoggedUser.getFirstName() + " " + ime);
            lblGoodSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            txtPrezimeSs.setText("");
        }
    }
    @FXML
    public void promEmailSs(ActionEvent ev) throws Exception {
        String ime = txtEmailSs.getText().toString();
        if (ime.equals("")) {
            lblBadMjSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblGoodSs.setVisible(false);
        } else if (User.postojiEm(txtEmailSs.getText())) {
            lblBadEmailSs.setVisible(true);
            lblBadMjSs.setVisible(false);
            lblGoodSs.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setEmail(txtEmailSs.getText());
            u.update();
            lblGoodSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            txtEmailSs.setText("");
        }
    }
    @FXML
    public void promLozinkeSs(ActionEvent ev) throws Exception {
        String ime = txtLozinkaSs.getText().toString();
        if (ime.equals("")) {
            lblBadMjSs.setVisible(true);
            lblGoodSs.setVisible(false);
            lblBadEmailSs.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setPassword(txtLozinkaSs.getText());
            u.update();
            lblGoodSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            txtLozinkaSs.setText("");
        }
    }
    @FXML
    public void promOdjelaSs(ActionEvent ev) throws Exception {
        String odjell = this.bxOdSs.getValue();
        if (odjell==null) {
            lblBadMjSs.setVisible(true);
            lblGoodSs.setVisible(false);
            lblBadEmailSs.setVisible(false);
        } else {
            int u = Login.LoggedUser.getId();
            int idOd = Odjel.odfk(odjell);
            Sestra ss = Sestra.ussr(u);
            ss.setOdjelFK(idOd);
            ss.update();
            this.populateTableInpacientSs();
            lblGoodSs.setVisible(true);
            lblBadEmailSs.setVisible(false);
            lblBadMjSs.setVisible(false);
            this.populateTableTerminiPregleda();
        }
    }
    @FXML
    public void pregledKarton(ActionEvent ev) throws Exception {
         karton=txtBrojKrSs.getText();
        if(karton.equals("")){
            System.out.println("unesite karton");
            lblSestraKPNO.setVisible(true);
            lblSestraNoPac.setVisible(false);
            lblSestraPNOd.setVisible(false);
        }else if(Pacijent.upisi(karton)==null){
            System.out.println("pacijent ne postoji");
            lblSestraKPNO.setVisible(false);
            lblSestraNoPac.setVisible(true);
            lblSestraPNOd.setVisible(false);
        }else{
            Pacijent u = Pacijent.upisi(karton);
            pnlPacSs.setVisible(false);
            pnlKartonSs.setVisible(true);
            Date datum = u.getDatumRodjenja();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            lblNaslovPac.setText("karton br. " + u.getBrKartona());
            lblImePacSs.setText(u.getFirstName());
            lblPrezimePacSs.setText(u.getLastName());
            lblSpolPacSs.setText(u.getSpol());
            lblMjestoPacSs.setText(u.getMjestoRod());
            lblDatumPacSs.setText(df.format(datum));
            this.populateTableBoravakSs();
            this.populateTablePregledSs();
            lblSestraKPNO.setVisible(false);
            lblSestraNoPac.setVisible(false);
            lblSestraPNOd.setVisible(false);
        }
    }
    private void populateTableBoravakSs() {
        try {
            this.tblBoravakSs.getItems().setAll((Boravak.boravciPac(Pacijent.upisi(karton).getId())));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }

    }
    private void populateTablePregledSs() {
        try {
            this.tblPreglediSs.getItems().setAll((Pregled.preglediPac(Pacijent.upisi(karton).getId())));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }

    }
    private void populateTableInpacientSs() {
        try {
            int u = Login.LoggedUser.getId();
            Sestra ss = Sestra.ussr(u);
            int p = ss.getOdjelFK();
            List<Pacijent> sviUBol = new ArrayList<>();
            List<Integer> odjelipac = Inpacijent.sviOdjeli(p);
            if (odjelipac.size() != 0) {
                for (int i = 0; i < odjelipac.size(); i++) {
                    Pacijent pc = Pacijent.primljenibol(odjelipac.get(i));
                    sviUBol.add(pc);
                }
            }
                this.tblInPacijentSs.getItems().setAll(sviUBol);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }
    @FXML
    public void pregledKarton1(ActionEvent ev) throws Exception {
        Pacijent u = tblInPacijentSs.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("odaberi pacijenta");
            lblSestraKPNO.setVisible(false);
            lblSestraNoPac.setVisible(false);
            lblSestraPNOd.setVisible(true);
        }else{
            karton=u.getBrKartona();
            pnlPacSs.setVisible(false);
            pnlKartonSs.setVisible(true);
            Date datum = u.getDatumRodjenja();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            lblNaslovPac.setText("karton br. " + u.getBrKartona());
            lblImePacSs.setText(u.getFirstName());
            lblPrezimePacSs.setText(u.getLastName());
            lblSpolPacSs.setText(u.getSpol());
            lblMjestoPacSs.setText(u.getMjestoRod());
            lblDatumPacSs.setText(df.format(datum));
            this.populateTableBoravakSs();
            this.populateTablePregledSs();
            lblSestraKPNO.setVisible(false);
            lblSestraNoPac.setVisible(false);
            lblSestraPNOd.setVisible(false);
        }
    }
    @FXML
    public void pregledLijekova(ActionEvent ev) throws Exception {
        Pacijent u = tblInPacijentSs.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("odaberi pacijenta");
            lblSestraPNOd.setVisible(true);
        }else{
            lblSestraPNOd.setVisible(false);
            int pacId=u.getId();
            List<Boravak>sviBoravci=Boravak.boravciPac(pacId);
            int velicina=sviBoravci.size();
            System.out.println(velicina);
            String lijekovi=sviBoravci.get(velicina-1).getLijekovi();
            System.out.println(lijekovi);
            pnlLijekoviSs.setVisible(true);
            txtArLijekovi.setText(lijekovi);
        }
    }
    public void zakaziPregled(ActionEvent ev) throws Exception {
        if(dpTerminSs.getValue()==null || bxDoktoriTerminSs.getValue()==null || bxTerminSatSs.getValue()==null || txtKartonTerminSs.getText().equals("")){
            System.out.println("unesite sva polja");
            lblSestraTerminGreska.setText("unesite sva polja");
            lblSestraTerminGreska.setVisible(true);
            lblSestraTerminIspravno.setVisible(false);
        }else{
            String vrijeme1=bxTerminSatSs.getValue();
            Time tim=Time.valueOf(vrijeme1);
            LocalDate datum=dpTerminSs.getValue();
            Calendar c=Calendar.getInstance();
            c.set(datum.getYear(),datum.getMonthValue()-1,datum.getDayOfMonth(),0,0,0);
            Date datum1=c.getTime();
            String doktor=bxDoktoriTerminSs.getValue();
            String [] str=doktor.split(" ");
            int velicina=str.length;
            String usernm=str[velicina-1];
            int dcId=Doktor.usdr(User.change(usernm).getId()).getId();
            java.sql.Date dd=new java.sql.Date(datum1.getTime());
            if(Pacijent.upisi(txtKartonTerminSs.getText())==null){
                lblSestraTerminGreska.setText("ne postoji takav pacijent");
                lblSestraTerminGreska.setVisible(true);
                lblSestraTerminIspravno.setVisible(false);
                System.out.println("ne postoji takav pacijent");
            }else if(Inpacijent.nadji(Pacijent.upisi(txtKartonTerminSs.getText()).getId())!=null){
                System.out.println("pacijent je na odjelu ne moze imati pregled");
                lblSestraTerminGreska.setText("pacijent je na odjelu ne moze imati pregled");
                lblSestraTerminGreska.setVisible(true);
                lblSestraTerminIspravno.setVisible(false);
            }else if(Terminpregleda.provjeriSvePr(dcId,Pacijent.upisi(txtKartonTerminSs.getText()).getId(),dd,tim)){
                System.out.println("nemeze");
                lblSestraTerminGreska.setText("postoji identična operacija");
                lblSestraTerminGreska.setVisible(true);
                lblSestraTerminIspravno.setVisible(false);
            }else if(Terminpregleda.provjeriIstiDocPr(dcId,dd,tim)){
                System.out.println("doktor Ima vec pregled u to vrijeme");
                lblSestraTerminGreska.setText("taj doktor ima vec pregled u to vrijeme");
                lblSestraTerminGreska.setVisible(true);
                lblSestraTerminIspravno.setVisible(false);
            }else if(Terminpregleda.provjeriPacPr(Pacijent.upisi(txtKartonTerminSs.getText()).getId(),dd,tim)){
                System.out.println("Pacijent je na pregledu u to vriijeme");
                lblSestraTerminGreska.setText("Pacijent je na pregledu u to vriijeme");
                lblSestraTerminGreska.setVisible(true);
                lblSestraTerminIspravno.setVisible(false);
            }else{
                System.out.println(dcId);
                Pacijent pc=Pacijent.upisi(txtKartonTerminSs.getText());
                String imePac=pc.getFirstName()+" "+pc.getLastName();
                Terminpregleda tp=new Terminpregleda();
                tp.setDatumPregleda(dd);
                tp.setVrijemePregleda(tim);
                tp.setDoktorFK(dcId);
                tp.setNazivDoktora(str[0]+" "+str[1]);
                tp.setNazivPacijenta(Pacijent.upisi(txtKartonTerminSs.getText()).getFirstName()+" "+Pacijent.upisi(txtKartonTerminSs.getText()).getLastName());
                tp.setPacijentFK(Pacijent.upisi(txtKartonTerminSs.getText()).getId());
                tp.save();
                this.populateTableTerminiPregleda();
                lblSestraTerminGreska.setVisible(false);
                lblSestraTerminIspravno.setText("pregled je zakazan");
                lblSestraTerminIspravno.setVisible(true);
                txtKartonTerminSs.setText("");
            }
        }
    }
    private void populateTableTerminiPregleda() { try {
        this.tblTerminiPr.getItems().setAll((Collection<?extends Terminpregleda>) Terminpregleda.terminiPr(Terminpregleda.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    @FXML
    public void otkaziPregledTer(ActionEvent ev) throws Exception {
        Terminpregleda u = tblTerminiPr.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("oznacite koju operaciju zelite otkazati");
            lblOtkazatiSestra.setText("Odaberite termin koji želite otkazati!");
            lblOtkazatiSestra.setVisible(true);
        }
        else if(Doktor.drid(u.getDoktorFK()).getOdjelFK()==Sestra.ussr(Login.LoggedUser.getId()).getOdjelFK()){
            u.delete();
            this.populateTableTerminiPregleda();
            lblOtkazatiSestra.setVisible(false);
        }else{
            System.out.println("nemozete izbrisati pregled koji nije vas.");
            lblOtkazatiSestra.setText("Ne možete otkazati termin koji nije vaš!");
            lblOtkazatiSestra.setVisible(true);
        }
    }
    @FXML
    public void zatvoriLijekove(ActionEvent ev) throws Exception {
        pnlLijekoviSs.setVisible(false);
    }
    @FXML
    public void zatvoriKartone(ActionEvent ev) throws Exception {
        pnlKartonSs.setVisible(false);
        pnlPacSs.setVisible(true);
    }
}
