package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Gecmis extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public Gecmis() {
        setTitle("Geçmiş");

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

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        listeleButton.setForeground(Color.BLUE);

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
                    ResultSet rs = sorgu.executeQuery("select distinct ay from yerlestir where yil='"+yilBox.getSelectedItem()+"'");//veritabanından seçilen yıla göre ay bilgilerin çekilmesi

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
                    ResultSet rs = sorgu.executeQuery("select distinct gun from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"'");//veritabanından seçilen yıla ve ay göre gün bilgilerin çekilmesi

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
                pencere.setTitle("Geçmiş Kayıtlar");

                pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

                JPanel panelAna = new JPanel(new GridLayout());

                final DefaultTableModel model = new DefaultTableModel();

                final JTable tablo = new JTable(model);
                model.addColumn("TC Kimlik No");
                model.addColumn("Plaka");
                model.addColumn("Marka");
                model.addColumn("Model");
                model.addColumn("Renk");
                model.addColumn("Konum");
                model.addColumn("Zaman");
                model.addColumn("Temizlik");
                model.addColumn("Not");

                JScrollPane pane = new JScrollPane(tablo);//tabloya scroll ayarlama

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                    Statement sorgu = baglan.createStatement();

                    if(ayBox.getSelectedItem().equals("")&&gunBox.getSelectedItem().equals("")){
                        ResultSet rs = sorgu.executeQuery("select * from yerlestir where yil='"+yilBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            model.addRow(new Object[] { rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10) });
                        }
                    }

                    else if(gunBox.getSelectedItem().equals("")){
                        ResultSet rs = sorgu.executeQuery("select * from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            model.addRow(new Object[] { rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10) });
                        }
                    }
                    else{
                        ResultSet rs = sorgu.executeQuery("select * from yerlestir where yil='"+yilBox.getSelectedItem()+"' and ay='"+ayBox.getSelectedItem()+"' and gun='"+gunBox.getSelectedItem()+"'");

                        while (rs.next()) {
                            model.addRow(new Object[] { rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10) });
                        }
                    }
                    tablo.setAutoCreateRowSorter(true);

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

                pencere.add(panelAna);
                panelAna.add(pane);

                panelAna.setBorder(BorderFactory.createTitledBorder("Bilgiler"));

                panelAna.setBackground(Color.CYAN);

                pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
                pencere.setSize(720, 250);
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

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}
