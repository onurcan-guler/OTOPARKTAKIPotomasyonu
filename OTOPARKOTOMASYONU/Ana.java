package OTOPARKOTOMASYONU;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingUtilities;

public class Ana{

    static String driver = "com.mysql.jdbc.Driver";
    static String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    static String kullanici = "root";
    static String sifre = "";
    static String kontrol="";

    public static void main(String[] args) {

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from ilk");

            if(rs.next())
                kontrol=Integer.toString(rs.getInt(2));
            if(kontrol.equals("1")){
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Menuler();
                    }
                });
            }
            else{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Ayarlar();
                    }
                });
            }
            sorgu.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
