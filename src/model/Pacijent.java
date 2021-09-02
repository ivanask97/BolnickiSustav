package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Pacijent extends Table{
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String firstName;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String lastName;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String spol;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String JMBG;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String brKartona;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String mjestoRod;
    @Entity(type= "DATETIME")
    Date datumRodjenja;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getBrKartona() {
        return brKartona;
    }

    public void setBrKartona(String brKartona) {
        this.brKartona = brKartona;
    }

    public String getMjestoRod() {
        return mjestoRod;
    }

    public void setMjestoRod(String mjestoRod) {
        this.mjestoRod = mjestoRod;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public static Pacijent upisi(String userName) throws Exception{
        String sql="SELECT * FROM pacijent WHERE brKartona=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,userName);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Pacijent) Pacijent.get(Pacijent.class,rs.getInt(1));
        }else{
            return null;
        }
    }

    public static Pacijent primljenibol(int novi1)throws Exception{
        String sql="SELECT * FROM pacijent WHERE id=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,novi1);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Pacijent) Pacijent.get(Pacijent.class,rs.getInt(1));
        }else{
            return null;
        }
    }
}
