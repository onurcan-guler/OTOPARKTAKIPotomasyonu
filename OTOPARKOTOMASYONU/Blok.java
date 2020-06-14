package OTOPARKOTOMASYONU;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JPanel;

public class Blok extends JPanel{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public Blok(String isim, int aracSayisi, int satir, int sutun) {

        setLayout(new GridLayout(satir,sutun,5,5));

        Arac arac[] = new Arac[aracSayisi];

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from "+isim+"");

            for (int i = 0; i < aracSayisi; i++) {

                while (rs.next()) {
                    String alanBos = rs.getString(2);
                    String alanDolu = rs.getString(3);
                    String durum = rs.getString(4);

                    if(durum.equals("0"))
                        arac[i] = new Arac(alanBos,0);

                    else
                        arac[i] = new Arac(alanDolu,1);
                    add(arac[i]);
                }
            }
            sorgu.close();
        } catch (Exception e2) {
            System.out.println(e2);
        }
    }
}