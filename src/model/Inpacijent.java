package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Inpacijent extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @ForeignKey(table = "Odjel", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int odjelFK;

    @ForeignKey(table = "Pacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int pacijentFK;
    @ForeignKey(table = "Doktor", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int doktorFK;

    public int getId() {
        return id;
    }

    public int getOdjelFK() {
        return odjelFK;
    }

    public void setOdjelFK(int odjelFK) {
        this.odjelFK = odjelFK;
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
    //računa broj slobodnih kreveta na traženom odjelu
    public static int brojkrvt(int userName) throws Exception{
        String sql="SELECT * FROM inpacijent WHERE odjelFK=?";
        int brKreveta=0;
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,userName);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
           brKreveta++ ;
        }return brKreveta;
    }
    //traži pacijenta na odjellu sa stranim ključem pacijenta
    public static Inpacijent nadji(int userName) throws Exception{
        String sql="SELECT * FROM Inpacijent WHERE pacijentFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,userName);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Inpacijent)Inpacijent.get(Inpacijent.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    public static List<Integer> sviOdjeli(int userName) throws Exception{
        List<Integer>ukupno=new ArrayList<>();
        String sql="SELECT * FROM Inpacijent WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,userName);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            ukupno.add(rs.getInt("pacijentFK"));
        }return ukupno;
    }
    public static List<Integer> svipac(int userName,int ime) throws Exception{
        List<Integer>ukupno=new ArrayList<>();
        String sql="SELECT * FROM Inpacijent WHERE odjelFK=? AND doktorFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,userName);
        query.setInt(2,ime);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            ukupno.add(rs.getInt("pacijentFK"));
        }return ukupno;
    }
    //provjera imali pacijenata od određenog doktora
    public static boolean moguLiMjenjat(int userName) throws Exception{
        String sql="SELECT * FROM Inpacijent WHERE doktorFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,userName);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static Inpacijent inod(int id) throws Exception{
        String sql="SELECT * FROM inpacijent WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Inpacijent) Inpacijent.get(Inpacijent.class,rs.getInt(1));
        }else{
            return null;
        }
    }
}
