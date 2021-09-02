package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User extends Table{
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;

    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String firstName;

    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String lastName;

    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String email;

    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String userName;

    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String password ;

    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
//logiranje na sustav
    public static User login(String userName, String password) throws Exception{
        String sql="SELECT * FROM user WHERE userName=? AND password=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,userName);
        query.setString(2,password);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (User) User.get(User.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    //postoji li već taj korisnik na bazi
    public static boolean postoji(String userName,String posta) throws Exception{
        String sql="SELECT * FROM user WHERE userName=? OR email=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,userName);
        query.setString(2,posta);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    //ispis korisnika
    public static List<?> users(Class cls) throws Exception {
        String SQL = "SELECT * FROM user ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> users = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            users.add(obj);
        }
        return users;
    }
    //promijena lozinke
    public static User change(String id) throws Exception{
        String sql="SELECT * FROM user WHERE userName=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (User) User.get(User.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    public static boolean postojiEm(String posta) throws Exception{
        String sql="SELECT * FROM user WHERE email=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,posta);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }

    public static List<String> nadjiId(List<Doktor> id) throws Exception{
        List<String>korisnici=new ArrayList<>();
        if(id.size()>0){
            for(int i=0;i<id.size();i++){
                String sql="SELECT * FROM user WHERE id=? ";
                PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
                query.setInt(1,id.get(i).userFK);
                ResultSet rs=query.executeQuery();
                while(rs.next()){
                    String ime=(rs.getString("firstName"));
                    String prezime=(rs.getString("lastName"));
                    String korime=(rs.getString("userName"));
                    korisnici.add(ime+" "+prezime+" "+korime);
                }
            }
        }
        return korisnici;
    }
}
