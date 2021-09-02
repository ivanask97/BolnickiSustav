package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.Pane;
import main.Main;
import model.*;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DoktorCtr implements Initializable {
    @FXML
    private DatePicker dpDatumOp;

    private int pacijentId;
    @FXML
    private Button btnPovPreg,btnOtpusti,btnZakaziOp,btnPacDoc,btnOpDoc,btnPrDoc,btnPromDr,btnUrediDg;
    @FXML
    private Button btnPovBor;
    @FXML
    private Pane pnlPrPodatkeDr;
    @FXML
    private Pane pnlKarton,pnlPreglediDoc;
    @FXML
    private Pane pnlDijagnoza,pnlDijagnoza1,pnlOp;
    @FXML
    private Pane pnlPacijenti;
    @FXML
    private TextArea txtArSimptom,txtArDijagnoza,txtArLijekovi,txtArSimptom1,txtArDijagnoza1,txtArLijekovi1;
    @FXML
    private TextField txtImeDr, txtVrijemePO,txtVrijemeKO,txtBrKartonOp;
    @FXML
    private TextField txtPacijent;
    @FXML
    private TextField txtPrDr;
    @FXML
    private TextField txtEmDr;
    @FXML
    private TextField txtLzDr;
    @FXML
    private Label lblNaslov,lblNeuspjehOtpust,lblUspjehOtpust,lblDijagnozaPost;
    @FXML
    private Label lblImePac,lblDocPregledOtkazan,lblDocPregledNe,lblOtkazatiOpDoc,lblOtkazatiOpNeDoc;
    @FXML
    private Label lblPrezPac;
    @FXML
    private Label lblSpolPac;
    @FXML
    private Label lblMjestoPac;
    @FXML
    private Label lblDatumPac;
    @FXML
    private Label lblGood,lblDocOpNe,lblDocOpDa;
    @FXML
    private Label lblBadMj;
    @FXML
    private Label lblBadEmail;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblPacOd;
    @FXML
    private Label lblPopuni;
    @FXML
    private Label lblNemaPac, lblId;
    @FXML
    private Label lblUspjesnoPr;
    @FXML
    private Label lblNeuspjehPr;
    @FXML
    private TableView<Terminpregleda> tblPreglediDoc;
    @FXML
    private TableView<Pregled> tblPregl;
    @FXML
    private TableView<Boravak> tblBoravak;
    @FXML
    private TableView<Pacijent> tblInPacijenti;
    @FXML
    private TableView<Terminoperacija> tblTermini;
    @FXML
    private TableColumn<Terminoperacija,String>tblClImeDoc,tblClNazivSale,tblClNazivPacijenta;
    @FXML
    private TableColumn<Terminoperacija,Date>tblClDatumOp;
    @FXML
    private TableColumn<Terminoperacija,Time>tblClVrijemePoc,tblClVrijemeKraja;
    @FXML
    private TableColumn<Boravak, String> tblClSimptomPr1, tblClDijagnozaPr1, tblClPropLij1, tblClDatumOd1, tblClDatumPr1;
    @FXML
    private TableColumn<Pregled, String> tblClDatumPr, tblClSimptomPr, tblClDijagnozaPr, tblClPropLij;
    @FXML
    private TableColumn<Pacijent, String> tblClName1;
    @FXML
    private TableColumn<Pacijent, String> tblClLastName1;
    @FXML
    private TableColumn<Pacijent, String> tblClJMBG;
    @FXML
    private TableColumn<Pacijent, String> tblClBrKarton;
    @FXML
    private TableColumn<Terminpregleda, String> tblClImeDocDoc,tblClDatumPreDoc,tblClVrijemePreDoc,tblClNazivPacijentaDoc;
    @FXML
    private ChoiceBox<String> bxOdDr,bxOPsale;
    private String OdOdjel,OppSale;
    private List<String> Opsale = Operacija.opsale();
    private List<String> odjeljenja = Odjel.odjeli();
    private List<Boravak> boravakkk = new ArrayList<>();
    private List<Pregled> pregledd = new ArrayList<>();
    private String pc;
    public DoktorCtr() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lblWelcome.setText("Dobrodošli," + "\n" + Login.LoggedUser.getFirstName() + " " + Login.LoggedUser.getLastName());
        pnlPacijenti.setVisible(true);
        pnlPrPodatkeDr.setVisible(false);
        pnlOp.setVisible(false);
        pnlKarton.setVisible(false);
        pnlDijagnoza.setVisible(false);
        pnlDijagnoza1.setVisible(false);
        pnlPreglediDoc.setVisible(false);
        bxOdDr.getItems().addAll(odjeljenja);
        if(odjeljenja.size()>0){
            bxOdDr.setValue(odjeljenja.get(0));
        }
        bxOdDr.setOnAction(this::getOdjel);
        bxOPsale.getItems().addAll(Opsale);
        if(Opsale.size()>0){
            bxOPsale.setValue(Opsale.get(0));
        }
        bxOPsale.setOnAction(this::getSale);
        this.tblClName1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tblClLastName1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.tblClJMBG.setCellValueFactory(new PropertyValueFactory<>("spol"));
        this.tblClBrKarton.setCellValueFactory(new PropertyValueFactory<>("brKartona"));
        this.populateTableInpacient();
        this.tblClDatumPr1.setCellValueFactory(new PropertyValueFactory<>("datumUlaska"));
        this.tblClSimptomPr1.setCellValueFactory(new PropertyValueFactory<>("simptom"));
        this.tblClDijagnozaPr1.setCellValueFactory(new PropertyValueFactory<>("dijagnoza"));
        this.tblClPropLij1.setCellValueFactory(new PropertyValueFactory<>("lijekovi"));
        this.tblClDatumOd1.setCellValueFactory(new PropertyValueFactory<>("datumIzlaska"));
        this.populateTableBoravak();
        this.tblClDatumPr.setCellValueFactory(new PropertyValueFactory<>("datumPregleda"));
        this.tblClSimptomPr.setCellValueFactory(new PropertyValueFactory<>("simptom"));
        this.tblClDijagnozaPr.setCellValueFactory(new PropertyValueFactory<>("dijagnoza"));
        this.tblClPropLij.setCellValueFactory(new PropertyValueFactory<>("lijekovi"));
        this.populateTablePregled();
        this.tblClImeDoc.setCellValueFactory(new PropertyValueFactory<>("nazivDoktora"));
        this.tblClDatumOp.setCellValueFactory(new PropertyValueFactory<>("datumOperacije"));
        this.tblClVrijemePoc.setCellValueFactory(new PropertyValueFactory<>("vrijemePocetka"));
        this.tblClVrijemeKraja.setCellValueFactory(new PropertyValueFactory<>("vrijemeKraja"));
        this.tblClNazivSale.setCellValueFactory(new PropertyValueFactory<>("nazivOpSale"));
        this.tblClNazivPacijenta.setCellValueFactory(new PropertyValueFactory<>("nazivPacijenta"));
        this.populateTableTerminiOp();
        this.tblClImeDocDoc.setCellValueFactory(new PropertyValueFactory<>("nazivDoktora"));
        this.tblClDatumPreDoc.setCellValueFactory(new PropertyValueFactory<>("datumPregleda"));
        this.tblClVrijemePreDoc.setCellValueFactory(new PropertyValueFactory<>("vrijemePregleda"));
        this.tblClNazivPacijentaDoc.setCellValueFactory(new PropertyValueFactory<>("nazivPacijenta"));
        this.populateTableTerminiPregledaDoc();
    }

    @FXML
    public void getOdjel(ActionEvent event) {
        OdOdjel = bxOdDr.getValue();
    }
    @FXML
    public void getSale(ActionEvent event) {
        OppSale = bxOPsale.getValue();
    }
    @FXML
    private void handleClicks(ActionEvent event) throws Exception {
        if(event.getSource()==btnPacDoc){
           lblPopuni.setVisible(false);
           lblPacOd.setVisible(false);
           lblNeuspjehPr.setVisible(false);
           lblNemaPac.setVisible(false);
           lblUspjesnoPr.setVisible(false);
           lblNeuspjehOtpust.setVisible(false);
           lblUspjehOtpust.setVisible(false);
           lblDijagnozaPost.setVisible(false);
          pnlPacijenti.setVisible(true);
          pnlPrPodatkeDr.setVisible(false);
          pnlOp.setVisible(false);
          pnlKarton.setVisible(false);
          pnlDijagnoza.setVisible(false);
          pnlDijagnoza1.setVisible(false);
          pnlPreglediDoc.setVisible(false);
        }else if(event.getSource()==btnOpDoc){
            lblDocOpDa.setVisible(false);
            lblDocOpNe.setVisible(false);
            lblOtkazatiOpDoc.setVisible(false);
            lblOtkazatiOpNeDoc.setVisible(false);
            pnlPacijenti.setVisible(false);
            pnlPrPodatkeDr.setVisible(false);
            pnlOp.setVisible(true);
            pnlKarton.setVisible(false);
            pnlDijagnoza.setVisible(false);
            pnlDijagnoza1.setVisible(false);
            pnlPreglediDoc.setVisible(false);
        }else if(event.getSource()==btnPrDoc){
            lblDocPregledOtkazan.setVisible(false);
            lblDocPregledNe.setVisible(false);
            pnlPacijenti.setVisible(false);
            pnlPrPodatkeDr.setVisible(false);
            pnlOp.setVisible(false);
            pnlKarton.setVisible(false);
            pnlDijagnoza.setVisible(false);
            pnlDijagnoza1.setVisible(false);
            pnlPreglediDoc.setVisible(true);
        }else if(event.getSource()==btnPromDr){
            lblGood.setVisible(false);
            lblBadMj.setVisible(false);
            lblBadEmail.setVisible(false);
            pnlPacijenti.setVisible(false);
            pnlPrPodatkeDr.setVisible(true);
            pnlOp.setVisible(false);
            pnlKarton.setVisible(false);
            pnlDijagnoza.setVisible(false);
            pnlDijagnoza1.setVisible(false);
            pnlPreglediDoc.setVisible(false);
        }
    }
    @FXML
    public void odjavaDr(ActionEvent ev) {
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
    public void promImeDr(ActionEvent ev) throws Exception {
        String ime = txtImeDr.getText().toString();
        if (ime.equals("")) {
            lblBadMj.setVisible(true);
            lblGood.setVisible(false);
            lblBadEmail.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setFirstName(txtImeDr.getText());
            u.update();
            this.lblWelcome.setText("Dobrodošli," + "\n" + ime + " " + Login.LoggedUser.getLastName());
            lblGood.setVisible(true);
            lblBadEmail.setVisible(false);
            lblBadMj.setVisible(false);
            txtImeDr.setText("");
        }
    }

    @FXML
    public void promPrezimeDr(ActionEvent ev) throws Exception {
        String ime = txtPrDr.getText().toString();
        if (ime.equals("")) {
            lblBadMj.setVisible(true);
            lblGood.setVisible(false);
            lblBadEmail.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setLastName(txtPrDr.getText());
            u.update();
            this.lblWelcome.setText("Dobrodošli," + "\n" + Login.LoggedUser.getFirstName() + " " + ime);
            lblGood.setVisible(true);
            lblBadEmail.setVisible(false);
            lblBadMj.setVisible(false);
            txtPrDr.setText("");
        }
    }

    @FXML
    public void promEmailDr(ActionEvent ev) throws Exception {
        String ime = txtEmDr.getText().toString();
        if (ime.equals("")) {
            lblBadMj.setVisible(true);
            lblBadEmail.setVisible(false);
            lblGood.setVisible(false);
        } else if (User.postojiEm(txtEmDr.getText())) {
            lblBadEmail.setVisible(true);
            lblBadMj.setVisible(false);
            lblGood.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setEmail(txtEmDr.getText());
            u.update();
            lblGood.setVisible(true);
            lblBadEmail.setVisible(false);
            lblBadMj.setVisible(false);
            txtEmDr.setText("");
        }
    }

    @FXML
    public void promLozinkeDr(ActionEvent ev) throws Exception {
        String ime = txtLzDr.getText().toString();
        if (ime.equals("")) {
            lblBadMj.setVisible(true);
            lblGood.setVisible(false);
            lblBadEmail.setVisible(false);
        } else {
            User u = Login.LoggedUser;
            u.setPassword(txtLzDr.getText());
            u.update();
            lblGood.setVisible(true);
            lblBadEmail.setVisible(false);
            lblBadMj.setVisible(false);
            txtLzDr.setText("");
        }
    }

    @FXML
    public void promOdjelaDr(ActionEvent ev) throws Exception {
        String odjell = this.bxOdDr.getValue();
        if (odjell.equals("")) {
            lblBadMj.setVisible(true);
            lblGood.setVisible(false);
            lblBadEmail.setVisible(false);
        } else if(Inpacijent.moguLiMjenjat(Doktor.usdr(Login.LoggedUser.getId()).getId())){
            System.out.println("prvo otpusti svoje pacijente na odjelu");
        }else if(Terminpregleda.provjeriPostojiDoktorP(Doktor.usdr(Login.LoggedUser.getId()).getId())){
            System.out.println("prvo mora otkazati preglede na odjelu");
        }else {
            int u = Login.LoggedUser.getId();
            int idOd = Odjel.odfk(odjell);
            Doktor dr = Doktor.usdr(u);
            dr.setOdjelFK(idOd);
            dr.update();
            this.populateTableInpacient();
            lblGood.setVisible(true);
            lblBadEmail.setVisible(false);
            lblBadMj.setVisible(false);
        }
    }
    @FXML
    public void priminaOdjel(ActionEvent ev) throws Exception {
        String pc = txtPacijent.getText();
        if (pc.equals("")) {
            lblPopuni.setVisible(true);
            lblPacOd.setVisible(false);
            lblNeuspjehPr.setVisible(false);
            lblNemaPac.setVisible(false);
            lblUspjesnoPr.setVisible(false);
        }  else if(Pacijent.upisi(pc)==null){
            System.out.println("taj pacijent ne postoji");
            lblPopuni.setVisible(false);
            lblPacOd.setVisible(false);
            lblNeuspjehPr.setVisible(false);
            lblNemaPac.setVisible(true);
            lblUspjesnoPr.setVisible(false);
        }else {
            Pacijent pac = Pacijent.upisi(pc);
            int pacId = pac.getId();
            int u = Login.LoggedUser.getId();
            int Od = Doktor.usdr(u).getOdjelFK();
            int BrKreveta = Odjel.krevet(Od);
            int zauzeto = Inpacijent.brojkrvt(Od);
            int konacno = BrKreveta - zauzeto;
            if (konacno > 0 && Inpacijent.nadji(pacId) == null) {
                System.out.println(konacno);
                DateFormat da = new SimpleDateFormat("yyyy-MM-dd");
                Inpacijent inpac = new Inpacijent();
                inpac.setOdjelFK(Od);
                inpac.setPacijentFK(pacId);
                inpac.setDoktorFK(Doktor.usdr(u).getId());
                inpac.save();
                Date dater = new Date();
                Boravak bor = new Boravak();
                bor.setDatumUlaska(da.format(dater));
                bor.setDatumIzlaska("");
                bor.setPacijentFK(pacId);
                bor.setSimptom("");
                bor.setLijekovi("");
                bor.setDijagnoza("");
                bor.save();
                lblPopuni.setVisible(false);
                lblPacOd.setVisible(false);
                lblNeuspjehPr.setVisible(false);
                lblNemaPac.setVisible(false);
                lblUspjesnoPr.setVisible(true);
                populateTableInpacient();
                txtPacijent.setText("");
            } else if (konacno == 0) {
                System.out.println("odjel je pun");
                lblPopuni.setVisible(false);
                lblPacOd.setVisible(false);
                lblNeuspjehPr.setVisible(true);
                lblNemaPac.setVisible(false);
                lblUspjesnoPr.setVisible(false);
                txtPacijent.setText("");
            } else if (Inpacijent.nadji(pacId) != null) {
                lblPacOd.setVisible(true);
                lblPopuni.setVisible(false);
                lblNeuspjehPr.setVisible(false);
                lblNemaPac.setVisible(false);
                lblUspjesnoPr.setVisible(false);
                txtPacijent.setText("");
            }
        }

    }

    private void populateTableInpacient() {
        try {
            int u = Login.LoggedUser.getId();
            Doktor dr = Doktor.usdr(u);
            int p = dr.getOdjelFK();
            List<Pacijent> sviUBol = new ArrayList<>();

            List<Integer> odjelipac = Inpacijent.svipac(p,dr.getId());
            if (odjelipac.size() != 0) {
                for (int i = 0; i < odjelipac.size(); i++) {
                    Pacijent pc = Pacijent.primljenibol(odjelipac.get(i));
                    sviUBol.add(pc);
                }
            }
            this.tblInPacijenti.getItems().setAll(sviUBol);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }

    @FXML
    public void otpustiSOdjela(ActionEvent ev) throws Exception {
        int velicina = boravakkk.size();
        Boravak bort = boravakkk.get(velicina - 1);
        if (bort.getDijagnoza().equals("")) {
            System.out.println("ne moze");
            lblNeuspjehOtpust.setText("ne mogu otpustiti pacijenta bez dijagnoze");
            lblNeuspjehOtpust.setVisible(true);
            lblUspjehOtpust.setVisible(false);
        } else if(Terminoperacija.provjeriPostojiPac( Inpacijent.nadji(bort.getPacijentFK()).getId())){
            System.out.println("pacijent ceka operaciju");
            lblNeuspjehOtpust.setText("Pacijent čeka operaciju");
            lblNeuspjehOtpust.setVisible(true);
            lblUspjehOtpust.setVisible(false);
        } else {
            DateFormat da = new SimpleDateFormat("yyyy-MM-dd");
            Date dater = new Date();
            bort.setDatumIzlaska(da.format(dater));
            bort.update();
            Inpacijent in = Inpacijent.nadji(bort.getPacijentFK());
            in.delete();
            this.populateTableBoravak();
            this.populateTableInpacient();
            pnlKarton.setVisible(false);
            pnlPacijenti.setVisible(true);
            lblNeuspjehOtpust.setVisible(false);
            lblUspjehOtpust.setVisible(true);
        }
        this.populateTableInpacient();
    }

    @FXML
    public void pregledBoravak(ActionEvent ev) throws Exception {
        Pacijent u = tblInPacijenti.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("odaberi pacijenta");
        }else{
            pnlPacijenti.setVisible(false);
            pnlKarton.setVisible(true);
            btnPovBor.setVisible(true);
            btnOtpusti.setVisible(true);
            btnPovPreg.setVisible(false);
            btnUrediDg.setVisible(true);
            Date datum = u.getDatumRodjenja();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            lblNaslov.setText("karton br. " + u.getBrKartona());
            lblImePac.setText(u.getFirstName());
            pacijentId = u.getId();
            boravakkk = Boravak.boravciPac(u.getId());
            pregledd=Pregled.preglediPac(u.getId());
            lblPrezPac.setText(u.getLastName());
            lblSpolPac.setText(u.getSpol());
            lblMjestoPac.setText(u.getMjestoRod());
            lblDatumPac.setText(df.format(datum));
            this.populateTableBoravak();
            this.populateTablePregled();
        }
    }

    @FXML
    public void pregledDijagnoza(ActionEvent ev) throws Exception {
         pc = txtPacijent.getText();
        if (pc.equals("")) {
            lblPopuni.setVisible(true);
            lblPacOd.setVisible(false);
            lblNeuspjehPr.setVisible(false);
            lblNemaPac.setVisible(false);
            lblUspjesnoPr.setVisible(false);
        } else if(Pacijent.upisi(pc)==null){
            System.out.println("taj pacijent ne postoji");
            lblPopuni.setVisible(false);
            lblPacOd.setVisible(false);
            lblNeuspjehPr.setVisible(false);
            lblNemaPac.setVisible(true);
            lblUspjesnoPr.setVisible(false);
        }else {
            lblPacOd.setVisible(false);
            lblNeuspjehPr.setVisible(false);
            lblNemaPac.setVisible(false);
            lblUspjesnoPr.setVisible(false);
            lblPopuni.setVisible(false);
            Pacijent u = Pacijent.upisi(pc);
            if (Inpacijent.nadji(u.getId()) == null) {
                boravakkk = Boravak.boravciPac(u.getId());
                pregledd = Pregled.preglediPac(u.getId());
                pnlPacijenti.setVisible(false);
                pnlKarton.setVisible(true);
                btnPovBor.setVisible(false);
                btnOtpusti.setVisible(false);
                btnPovPreg.setVisible(true);
                btnUrediDg.setVisible(false);
                Date datum = u.getDatumRodjenja();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                lblNaslov.setText("karton br. " + u.getBrKartona());
                lblId.setText(Integer.toString(u.getId()));
                lblImePac.setText(u.getFirstName());
                lblPrezPac.setText(u.getLastName());
                lblSpolPac.setText(u.getSpol());
                lblMjestoPac.setText(u.getMjestoRod());
                lblDatumPac.setText(df.format(datum));
                this.populateTableBoravak();
                this.populateTablePregled();
            }else if(Inpacijent.nadji(Pacijent.upisi(pc).getId()).getDoktorFK()!=Doktor.usdr(Login.LoggedUser.getId()).getId()){
                pnlPacijenti.setVisible(false);
                pnlKarton.setVisible(true);
                btnPovBor.setVisible(false);
                btnOtpusti.setVisible(false);
                btnPovPreg.setVisible(false);
                btnUrediDg.setVisible(false);
                Date datum = u.getDatumRodjenja();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                boravakkk = Boravak.boravciPac(u.getId());
                pregledd = Pregled.preglediPac(u.getId());
                lblNaslov.setText("karton br. " + u.getBrKartona());
                lblImePac.setText(u.getFirstName());
                lblPrezPac.setText(u.getLastName());
                lblSpolPac.setText(u.getSpol());
                lblMjestoPac.setText(u.getMjestoRod());
                lblDatumPac.setText(df.format(datum));
                this.populateTableBoravak();
                this.populateTablePregled();
            } else {
                pnlPacijenti.setVisible(false);
                pnlKarton.setVisible(true);
                btnPovBor.setVisible(true);
                btnUrediDg.setVisible(true);
                btnOtpusti.setVisible(true);
                btnPovPreg.setVisible(false);
                Date datum = u.getDatumRodjenja();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                boravakkk = Boravak.boravciPac(u.getId());
                pregledd = Pregled.preglediPac(u.getId());
                lblNaslov.setText("karton br. " + u.getBrKartona());
                lblImePac.setText(u.getFirstName());
                lblPrezPac.setText(u.getLastName());
                lblSpolPac.setText(u.getSpol());
                lblMjestoPac.setText(u.getMjestoRod());
                lblDatumPac.setText(df.format(datum));
                this.populateTableBoravak();
                this.populateTablePregled();
            }

        }
    }

    @FXML
    public void zatvoriPreg(ActionEvent ev) throws Exception {
        pnlKarton.setVisible(false);
        pnlPacijenti.setVisible(true);
    }

    @FXML
    public void zatvoriPreg1(ActionEvent ev) throws Exception {
        pnlKarton.setVisible(true);
        pnlDijagnoza.setVisible(false);
        pnlDijagnoza1.setVisible(false);
    }

    private void populateTableBoravak() {
        try {
            this.tblBoravak.getItems().setAll((boravakkk));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }

    }
    private void populateTablePregled() {
        try {
            this.tblPregl.getItems().setAll((pregledd));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }

    }

    @FXML
    public void postaviDB(ActionEvent ev) throws Exception {
        int velicina = boravakkk.size();
        Boravak bort = boravakkk.get(velicina - 1);
        if (bort.getDijagnoza().equals("")) {
            pnlDijagnoza.setVisible(true);
            pnlKarton.setVisible(false);
            lblDijagnozaPost.setVisible(false);
        } else {
            System.out.println("dijagnoza već postavljena");
            lblDijagnozaPost.setVisible(true);
        }
    }
    @FXML
    public void postaviKonacnuDB(ActionEvent ev) throws Exception {
            int velicina = boravakkk.size();
            Boravak bort = boravakkk.get(velicina - 1);
            String simp=txtArSimptom.getText();
            String dijg=txtArDijagnoza.getText();
            String lij=txtArLijekovi.getText();
            bort.setDijagnoza(dijg);
            bort.setLijekovi(lij);
            bort.setSimptom(simp);
            bort.update();
            this.txtArDijagnoza.setText("");
            this.txtArLijekovi.setText("");
            this.txtArSimptom.setText("");
            this.populateTableBoravak();

    }
    @FXML
    public void postaviDP(ActionEvent ev) throws Exception {
        Pacijent u = Pacijent.upisi(pc);
        String dg=txtArDijagnoza1.getText();
        String sp=txtArSimptom1.getText();
        String lj=txtArLijekovi1.getText();
        if(dg.equals("")||sp.equals("")||lj.equals("")){
            System.out.println("popuni polje");
        }else{
            DateFormat da = new SimpleDateFormat("yyyy-MM-dd");
            Date dater = new Date();
            Pregled p=new Pregled();
            p.setPacijentFK(u.getId());
            p.setDijagnoza(dg);
            p.setLijekovi(lj);
            p.setSimptom(sp);
            p.setDatumPregleda(da.format(dater));
            p.save();
            pregledd.add(p);
            this.populateTablePregled();
            txtArDijagnoza1.setText("");
            txtArSimptom1.setText("");
            txtArLijekovi1.setText("");
        }
    }
    @FXML
    public void postaviPrg(ActionEvent ev) throws Exception {
        pnlKarton.setVisible(false);
        pnlDijagnoza1.setVisible(true);
    }
    @FXML
    public void zakaziOperaciju(ActionEvent ev) throws Exception {
        if(txtVrijemeKO.getText().equals("")|| txtVrijemePO.getText().equals("")||txtBrKartonOp.getText().equals("")|| bxOPsale.getValue()==null|| dpDatumOp.getValue()==null){
            System.out.println("unesite sva polja");
            lblDocOpNe.setText("unesite sva polja");
            lblDocOpNe.setVisible(true);
            lblDocOpDa.setVisible(false);
        }else if(txtVrijemePO.getText().matches("\\d{2}:\\d{2}:\\d{2}")==false||txtVrijemeKO.getText().matches("\\d{2}:\\d{2}:\\d{2}")==false){
         System.out.println("nije dobaar format datuma");
            lblDocOpNe.setText("niste unijeli dobar format datuma");
            lblDocOpNe.setVisible(true);
            lblDocOpDa.setVisible(false);
        }else{
            String vrijeme1=txtVrijemePO.getText();
            String vrijeme2=txtVrijemeKO.getText();
            Time tim=Time.valueOf(vrijeme1);
            Time tim2=Time.valueOf(vrijeme2);
            LocalDate datum=dpDatumOp.getValue();
            Calendar c=Calendar.getInstance();
            c.set(datum.getYear(),datum.getMonthValue()-1,datum.getDayOfMonth(),0,0,0);
            Date datum1=c.getTime();
            String operacijaSala=bxOPsale.getValue();
            int opperId=Operacija.opId(bxOPsale.getValue());
            java.sql.Date dd=new java.sql.Date(datum1.getTime());
            if(Pacijent.upisi(txtBrKartonOp.getText())==null){
                System.out.println("ne postoji takav pacijent");
                lblDocOpNe.setText("ovaj pacijent ne postoji");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }
            else if(Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId())==null){
                System.out.println("pacijent nije na odjelu");
                lblDocOpNe.setText("pacijent nije na odjelu");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }
            else if(Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getOdjelFK()!=Doktor.usdr(Login.LoggedUser.getId()).getOdjelFK()){
                System.out.println("nije na vasem odjelu");
                lblDocOpNe.setText("ne mozete zakazati operaciju pacijentu koji nije na odjelu");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            } else if(Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getDoktorFK()!=Doktor.usdr(Login.LoggedUser.getId()).getId()){
                System.out.println("ne mozete zakazati operaciju pacijentu oji nije vaš");
                lblDocOpNe.setText("unesite sva polja");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.provjeriSve(Doktor.usdr(Login.LoggedUser.getId()).getId(),Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getId(),dd,tim,tim2,opperId)){
                System.out.println("nemeze");
                lblDocOpNe.setText("postoji identicna operacija");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.pokusaji(Doktor.usdr(Login.LoggedUser.getId()).getId(),Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getId(),dd,tim,opperId)){
                System.out.println("ne mogu rezervirati ako je sala zauzeta u to vrijeme izmedju");
                lblDocOpNe.setText("sala je zauzeta u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.provjeriIstiDoc(Doktor.usdr(Login.LoggedUser.getId()).getId(),dd,tim)){
                System.out.println("Imate vec operaciju u to vrijeme");
                lblDocOpNe.setText("imate vec operaciju u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.izmedjuDoc(Doktor.usdr(Login.LoggedUser.getId()).getId(),dd,tim)){
                System.out.println("ne mogu rezervirati ako je dok zauzeta u to vrijeme izmedju");
                lblDocOpNe.setText("imate operaciju u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.provjeriSale(dd,tim,opperId)){
                System.out.println("Ta sala je zauzeta u to vriijeme");
                lblDocOpNe.setText("sala je zauzeta u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.izmedjuSala(dd,tim,opperId)){
                System.out.println("ne mogu rezervirati ako je sala zauzeta u to vrijeme izmedju");
                lblDocOpNe.setText("sala je zauzeta u too vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.provjeriPac(Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getId(),dd,tim)){
                System.out.println("Pacijent je na operaciji u to vriijeme");
                lblDocOpNe.setText("pacijent ima operaciju u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else if(Terminoperacija.izmedjuPac(Inpacijent.nadji(Pacijent.upisi(txtBrKartonOp.getText()).getId()).getId(),dd,tim)){
                System.out.println("ne mogu rezervirati ako je pac zauzeta u to vrijeme izmedju");
                lblDocOpNe.setText("pacijent ima operaciju u to vrijeme");
                lblDocOpNe.setVisible(true);
                lblDocOpDa.setVisible(false);
            }else{
                Pacijent pc=Pacijent.upisi(txtBrKartonOp.getText());
                Inpacijent in=Inpacijent.nadji(pc.getId());
                int pacId=in.getId();
                String imePac=pc.getFirstName()+" "+pc.getLastName();
                User u = Login.LoggedUser;
                String imeDoc=u.getFirstName()+" "+u.getLastName();
                int docId=Doktor.usdr(u.getId()).getId();
                Terminoperacija tr=new Terminoperacija();
                tr.setDatumOperacije(datum1);
                tr.setNazivOpSale(operacijaSala);
                tr.setNazivPacijenta(imePac);
                tr.setVrijemeKraja(tim2);
                tr.setVrijemePocetka(tim);
                tr.setInpacijentFK(pacId);
                tr.setOperacijaFK(opperId);
                tr.setDoktorFK(docId);
                tr.setNazivDoktora(imeDoc);
                tr.save();
                this.populateTableTerminiOp();
                txtVrijemePO.setText("");
                txtVrijemeKO.setText("");
                txtBrKartonOp.setText("");
                lblDocOpDa.setText("uspješno ste zakazali operaciju");
                lblDocOpNe.setVisible(false);
                lblDocOpDa.setVisible(true);
            }
        }

    }
    private void populateTableTerminiOp() { try {
        this.tblTermini.getItems().setAll((Collection<?extends Terminoperacija>) Terminoperacija.termini(Terminoperacija.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    @FXML
    public void otkaziOP(ActionEvent ev) throws Exception {
        Terminoperacija u = tblTermini.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("oznacite koju operaciju zelite otkazati");
            lblOtkazatiOpDoc.setVisible(true);
            lblOtkazatiOpNeDoc.setVisible(false);
        }
        else if(u.getDoktorFK()==Doktor.usdr(Login.LoggedUser.getId()).getId()){
            u.delete();
            this.populateTableTerminiOp();
            lblOtkazatiOpDoc.setVisible(false);
            lblOtkazatiOpNeDoc.setVisible(false);
        }else{
            System.out.println("nemozete izbrisati operaciju kkoja nije vasa.");
            lblOtkazatiOpDoc.setVisible(false);
            lblOtkazatiOpNeDoc.setVisible(true);
        }
    }
    @FXML
    public void urediSOdjela(ActionEvent ev) throws Exception {
        int velicina = boravakkk.size();
        Boravak bort = boravakkk.get(velicina - 1);
        if (bort.getDatumIzlaska().equals("")) {
            pnlDijagnoza.setVisible(true);
            pnlKarton.setVisible(false);
        } else {
            System.out.println("ne moze urediti pacijent otpusten");
        }
        //Date datum=u.getDatumRodjenja();
        //DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        //boolean b=df.format(datum) instanceof String;
        //System.out.println(df.format(datum));
        //System.out.println(b);
    }
    private void populateTableTerminiPregledaDoc() { try {

        this.tblPreglediDoc.getItems().setAll(Terminpregleda.preglediDoc(Doktor.usdr(Login.LoggedUser.getId()).getId()));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    @FXML
    public void otkaziPregled(ActionEvent ev) throws Exception {
        Terminpregleda u = tblPreglediDoc.getSelectionModel().getSelectedItem();
        if(u==null){
            System.out.println("oznacite koji pregled zelite otkazati");
            lblDocPregledOtkazan.setVisible(false);
            lblDocPregledNe.setVisible(true);
        }
        else {
            u.delete();
            this.populateTableTerminiPregledaDoc();
            lblDocPregledOtkazan.setVisible(true);
            lblDocPregledNe.setVisible(false);
        }
    }
}
