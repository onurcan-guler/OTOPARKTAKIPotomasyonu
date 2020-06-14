package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AyarlariSifirla extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public AyarlariSifirla(){
        setTitle("Ayarlar Sıfırlansın mı?");

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());

        JButton sifirlaButton = new JButton("Sıfırla");
        JButton iptalButton = new JButton("İptal");

        JLabel acıklamaJLabel = new JLabel("Veritabanındaki bütün bölümler ve alanlar silinecek. Emin Misiniz ???");

        sifirlaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from ayarlar");
                    while(rs.next()){
                        String al = rs.getString(2);
                        try {
                            Class.forName(driver);
                            Connection baglan2 = DriverManager.getConnection(adres,kullanici, sifre);
                            Statement sorgu2 = baglan2.createStatement();
                            sorgu2.executeUpdate("drop table "+al+"");
                            sorgu2.close();
                        } catch (Exception e2) {
                            System.out.println(e2);
                        }
                    }
                    sorgu.close();
                } catch (Exception e3) {
                    System.out.println(e3);
                }

                try {
                    Class.forName(driver);
                    Connection baglan2 = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu2 = baglan2.createStatement();
                    sorgu2.executeUpdate("truncate table ayarlar");
                    sorgu2.executeUpdate("truncate table ilk");
                    sorgu2.executeUpdate("truncate table sifre");
                    sorgu2.executeUpdate("Update yerlestir set durum='0'");
                    sorgu2.close();

                    JOptionPane.showMessageDialog(null, "Veritabanı Ayarları Sıfırlandı ... Program Kapanacak ...","Uyarı",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);

                } catch (Exception e2) {
                    System.out.println(e2);
                }
            }
        });
        iptalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        sifirlaButton.setForeground(Color.red);
        iptalButton.setForeground(Color.blue);

        panelBir.add(acıklamaJLabel);
        panelIki.add(sifirlaButton);
        panelIki.add(iptalButton);

        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Soru"));

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(440, 140);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}
