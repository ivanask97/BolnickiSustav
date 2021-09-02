package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Terminpregleda extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "DATETIME")
    Date datumPregleda;
    @Entity(type= "TIME")
    Time vrijemePregleda;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String nazivPacijenta;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String nazivDoktora;
    @ForeignKey(table = "Pacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int pacijentFK;
    @ForeignKey(table = "Doktor", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int doktorFK;

    public int getId() {
        return id;
    }

    public Date getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(Date datumPregleda) {
        this.datumPregleda = datumPregleda;
    }

    public Time getVrijemePregleda() {
        return vrijemePregleda;
    }

    public void setVrijemePregleda(Time vrijemePregleda) {
        this.vrijemePregleda = vrijemePregleda;
    }

    public String getNazivPacijenta() {
        return nazivPacijenta;
    }

    public void setNazivPacijenta(String nazivPacijenta) {
        this.nazivPacijenta = nazivPacijenta;
    }

    public String getNazivDoktora() {
        return nazivDoktora;
    }

    public void setNazivDoktora(String nazivDoktora) {
        this.nazivDoktora = nazivDoktora;
    }

    public int getPacijentFK() {
        return pacijentFK;
    }

    public void setPacijentFK(int pacijentFK) {
        this.pacijentFK = pacijentFK;
    }

    public int getDoktorFK() {
        return doktorFK;
    }

    public void setDoktorFK(int doktorFK) {
        this.doktorFK = doktorFK;
    }
    public static List<?> terminiPr(Class cls) throws Exception {
        String SQL = "SELECT * FROM terminpregleda ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> terminipr = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            terminipr.add(obj);
        }
        return terminipr;
    }
    public static boolean provjeriSvePr(int docFk,int InpFK,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminpregleda WHERE pacijentFK=?  AND doktorFK=? AND vrijemePregleda=? AND datumPregleda=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,InpFK);
        query.setInt(2,docFk);
        query.setTime(3,vr1);
        query.setDate(4, (java.sql.Date) dat);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean provjeriIstiDocPr(int docFk,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminpregleda WHERE doktorFK=? AND vrijemePregleda=? AND datumPregleda=?";
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
    public static boolean provjeriPacPr(int InpFK,Date dat,Time vr1) throws Exception{
        String sql="SELECT * FROM terminpregleda WHERE pacijentFK=? AND vrijemePregleda=? AND datumPregleda=?";
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
    public static List<Terminpregleda> preglediDoc(int docId) throws Exception{
        List<Terminpregleda> terminidoc=new ArrayList<>();
        String sql="SELECT * FROM terminpregleda WHERE doktorFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,docId);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            terminidoc.add((Terminpregleda) Terminpregleda.get(Terminpregleda.class,rs.getInt(1)));
        }return terminidoc;
    }
    public static List<Terminpregleda> preglediOdjel(int docId) throws Exception{
        List<Terminpregleda> terminidoc=new ArrayList<>();
        String sql="SELECT * FROM terminpregleda WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,docId);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            terminidoc.add((Terminpregleda) Terminpregleda.get(Terminpregleda.class,rs.getInt(1)));
        }return terminidoc;
    }
    public static boolean provjeriPostojiDoktorP(int pacid) throws Exception{
        String sql="SELECT * FROM terminpregleda WHERE doktorFK=?";
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
