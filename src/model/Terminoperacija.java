package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Terminoperacija extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "DATETIME")
    Date datumOperacije;
    @Entity(type= "TIME")
    Time vrijemePocetka;
    @Entity(type= "TIME")
    Time vrijemeKraja;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String nazivPacijenta;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String nazivDoktora;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String nazivOpSale;
    @ForeignKey(table = "Inpacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int inpacijentFK;
    @ForeignKey(table = "Operacija", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int operacijaFK;
    @ForeignKey(table = "Doktor", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int doktorFK;

    public int getId() {
        return id;
    }

    public Date getDatumOperacije() {
        return datumOperacije;
    }

    public void setDatumOperacije(Date datumOperacije) {
        this.datumOperacije = datumOperacije;
    }

    public Time getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Time vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Time getVrijemeKraja() {
        return vrijemeKraja;
    }

    public void setVrijemeKraja(Time vrijemeKraja) {
        this.vrijemeKraja = vrijemeKraja;
    }

    public int getInpacijentFK() {
        return inpacijentFK;
    }

    public void setInpacijentFK(int inpacijentFK) {
        this.inpacijentFK = inpacijentFK;
    }

    public int getOperacijaFK() {
        return operacijaFK;
    }

    public void setOperacijaFK(int operacijaFK) {
        this.operacijaFK = operacijaFK;
    }

    public String getNazivPacijenta() {
        return nazivPacijenta;
    }

    public void setNazivPacijenta(String nazivPacijenta) {
        this.nazivPacijenta = nazivPacijenta;
    }

    public String getNazivOpSale() {
        return nazivOpSale;
    }

    public void setNazivOpSale(String nazivOpSale) {
        this.nazivOpSale = nazivOpSale;
    }

    public int getDoktorFK() {
        return doktorFK;
    }

    public void setDoktorFK(int doktorFK) {
        this.doktorFK = doktorFK;
    }

    public String getNazivDoktora() {
        return nazivDoktora;
    }

    public void setNazivDoktora(String nazivDoktora) {
        this.nazivDoktora = nazivDoktora;
    }


    public static List<?> termini(Class cls) throws Exception {
        String SQL = "SELECT * FROM terminoperacija ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> termini = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            termini.add(obj);
        }
        return termini;
    }
    public static boolean provjeriSve(int docFk,int InpFK,Date dat,Time vr1,Time vr2,int opFk) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE inpacijentFK=? AND operacijaFK=? AND doktorFK=? AND vrijemePocetka=? AND vrijemeKraja=? AND datumOperacije=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,InpFK);
        query.setInt(2,opFk);
        query.setInt(3,docFk);
        query.setTime(4,vr1);
        query.setTime(5,vr2);
        query.setDate(6, (java.sql.Date) dat);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriIstiDoc(int docFk,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE doktorFK=? AND vrijemePocetka=? AND datumOperacije=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,docFk);
        query.setTime(2,vr1);
        query.setDate(3, (java.sql.Date) dat);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriSale(Date dat,Time vr1,int opFk) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE operacijaFK=? AND vrijemePocetka=? AND datumOperacije=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,opFk);
        query.setTime(2,vr1);
        query.setDate(3, (java.sql.Date) dat);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriPac(int InpFK,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE inpacijentFK=? AND vrijemePocetka=? AND datumOperacije=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,InpFK);
        query.setTime(2,vr1);
        query.setDate(3, (java.sql.Date) dat);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriPostojiPac(int pacid) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE inpacijentFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,pacid);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriPostojiSala(int pacid) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE operacijaFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,pacid);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean pokusaji(int docFk,int InpFK,Date dat,Time vr1,int opFk) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE inpacijentFK=? AND operacijaFK=? AND doktorFK=? AND datumOperacije=? AND ? BETWEEN vrijemePocetka AND vrijemeKraja ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,InpFK);
        query.setInt(2,opFk);
        query.setInt(3,docFk);
        query.setDate(4, (java.sql.Date) dat);
        query.setTime(5,vr1);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean izmedjuPac(int InpFK,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE inpacijentFK=? AND  datumOperacije=? AND ? BETWEEN vrijemePocetka AND vrijemeKraja ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,InpFK);
        query.setDate(2, (java.sql.Date) dat);
        query.setTime(3,vr1);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean izmedjuSala(Date dat,Time vr1,int opFk) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE operacijaFK=? AND datumOperacije=? AND ? BETWEEN vrijemePocetka AND vrijemeKraja ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,opFk);
        query.setDate(2, (java.sql.Date) dat);
        query.setTime(3,vr1);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean izmedjuDoc(int docFk,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE doktorFK=? AND datumOperacije=? AND ? BETWEEN vrijemePocetka AND vrijemeKraja ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,docFk);
        query.setDate(2, (java.sql.Date) dat);
        query.setTime(3,vr1);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriPostojiDoktor(int pacid) throws Exception{
        String sql="SELECT * FROM terminoperacija WHERE doktorFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,pacid);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
}
