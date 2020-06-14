package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Kasa extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public Kasa() {
        setTitle("Kasa");

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new GridLayout(2,1,5,5));
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());

        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        JLabel yilJLabel = new JLabel("Yıl : ",JLabel.RIGHT);
        final JComboBox yilBox = new JComboBox();
        JLabel ayJLabel = new JLabel("Ay : ",JLabel.RIGHT);
        final JComboBox ayBox = new JComboBox();
        JLabel gunJLabel = new JLabel("Gün : ",JLabel.RIGHT);
        final JComboBox gunBox = new JComboBox();

        JButton listeleButton = new JButton("Listele");

        listeleButton.setMnemonic(KeyEvent.VK_L);

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select distinct yil from yerlestir");

            yilBox.addItem("");

            while(rs.next()){
                yilBox.addItem(rs.getString(1));
            }
            sorgu.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        yilBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                ayBox.removeAllItems();
                gunBox.removeAllItems();
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select distinct ay from yerlestir where yil='"+yilBox.getSelectedItem()+"'");
                    ayBox.addItem("");
                    while(rs.next()){
                        ayBox.addItem(rs.getString(1));
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        ayBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gunBox.removeAllItems();
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select distinct gun from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"'");

                    gunBox.addItem("");

                    while(rs.next()){
                        gunBox.addItem(rs.getString(1));
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        listeleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                final JDialog pencere = new JDialog();
                pencere.setTitle("Kasa");

                pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

                JPanel panelAna = new JPanel(new FlowLayout());

                JLabel kasaJLabel = new JLabel();
                Label kasaLabel = new Label("Kasadaki Para : ");
                JLabel tlJLabel = new JLabel("TL");

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                    Statement sorgu = baglan.createStatement();

                    if(ayBox.getSelectedItem().toString().equals("")&&gunBox.getSelectedItem().toString().equals("")){
                        ResultSet rs = sorgu.executeQuery("select sum(toplamUcret) from yerlestir where yil='"+yilBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            kasaJLabel.setText(rs.getString(1));
                        }
                    }
                    else if(gunBox.getSelectedItem().toString().equals("")){
                        ResultSet rs = sorgu.executeQuery("select sum(toplamUcret) from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            kasaJLabel.setText(rs.getString(1));
                        }
                    }
                    else{
                        ResultSet rs = sorgu.executeQuery("select sum(toplamUcret) from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"' and gun='"+gunBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            kasaJLabel.setText(rs.getString(1));
                        }
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                panelAna.add(kasaLabel);
                panelAna.add(kasaJLabel);
                panelAna.add(tlJLabel);

                pencere.add(panelAna);

                panelAna.setBackground(Color.CYAN);

                panelAna.setBorder(BorderFactory.createTitledBorder("Bilgiler"));

                pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
                pencere.setSize(200, 110);
                pencere.setLocationRelativeTo(null);
                pencere.setVisible(true);
                pencere.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            }
        });
        panelBir.add(yilJLabel);
        panelBir.add(yilBox);
        panelBir.add(ayJLabel);
        panelBir.add(ayBox);
        panelBir.add(gunJLabel);
        panelBir.add(gunBox);
        panelIki.add(listeleButton);

        panelAna.setBorder(BorderFactory.createTitledBorder("Geçmiş Tarihler"));

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        listeleButton.setForeground(Color.blue);

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}
