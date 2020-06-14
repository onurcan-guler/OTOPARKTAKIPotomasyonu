package OTOPARKOTOMASYONU;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ucretlendirme extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public Ucretlendirme() {

        setTitle("Ücretlendirme");

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new GridLayout(2, 1));
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());
        JPanel panelUc = new JPanel(new GridLayout(2, 2, 5, 5));
        JPanel panelDort = new JPanel(new FlowLayout());
        JPanel panelBes = new JPanel(new GridLayout(2, 2, 5, 5));
        JPanel panelAlti = new JPanel(new FlowLayout());

        JLabel saatlerJLabel = new JLabel("Saatler Arası : ", JLabel.RIGHT);
        final JComboBox saatlerBox = new JComboBox();

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from otopark_fiyat");
            saatlerBox.addItem("");
            while(rs.next()){
                saatlerBox.addItem(rs.getString(2));
            }
            sorgu.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        saatlerBox.setToolTipText("Saatler Arası");

        JLabel ucretJLabel = new JLabel("Ücreti : ", JLabel.RIGHT);
        final JTextField ucretField = new JTextField(5);

        JLabel temizlikJLabel = new JLabel("Temizlik : ", JLabel.RIGHT);
        final JComboBox temizlikBox = new JComboBox();
        temizlikBox.addItem("");
        temizlikBox.addItem("İç Temizlik");
        temizlikBox.addItem("Dış Temizlik");
        temizlikBox.addItem("İç-Dış Temizlik");

        JLabel ucretJLabel2 = new JLabel("Ücreti : ", JLabel.RIGHT);
        final JTextField ucretField2 = new JTextField(5);


        JButton kaydetButton = new JButton("Kaydet");
        JButton kaydetButton2 = new JButton("Kaydet");

        panelUc.add(saatlerJLabel);
        panelUc.add(saatlerBox);
        panelUc.add(ucretJLabel);
        panelUc.add(ucretField);

        panelDort.add(kaydetButton);

        panelBes.add(temizlikJLabel);
        panelBes.add(temizlikBox);
        panelBes.add(ucretJLabel2);
        panelBes.add(ucretField2);

        panelAlti.add(kaydetButton2);

        saatlerBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from otopark_fiyat where saatler='"+saatlerBox.getSelectedItem()+"'");

                    if(rs.next())
                        ucretField.setText(rs.getString(3));

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        temizlikBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from temizlik_fiyat where temizlik='"+temizlikBox.getSelectedItem()+"'");

                    if(rs.next())
                        ucretField2.setText(rs.getString(3));

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        kaydetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if(saatlerBox.getSelectedItem().equals("")||ucretField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Boş alan bırakmayın ...", "Hata", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                        Statement sorgu = baglan.createStatement();

                        sorgu.executeUpdate("Update otopark_fiyat set ucret='"+ ucretField.getText() + "' where saatler='"+ saatlerBox.getSelectedItem().toString() + "'");
                        sorgu.close();

                        JOptionPane.showMessageDialog(null, "Kaydedildi ...", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                        dispose();

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new Ucretlendirme();
                            }
                        });

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        kaydetButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if(temizlikBox.getSelectedItem().toString().equals("")||ucretField2.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Boş alan bırakmayın ...", "Hata", JOptionPane.WARNING_MESSAGE);
                }

                else{
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                        Statement sorgu = baglan.createStatement();

                        sorgu.executeUpdate("Update temizlik_fiyat set ucret='"+ ucretField2.getText() + "' where temizlik='"+ temizlikBox.getSelectedItem().toString() + "'");
                        sorgu.close();

                        JOptionPane.showMessageDialog(null, "Kaydedildi ...", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                        dispose();

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new Ucretlendirme();
                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        add(panelAna);

        panelAna.add(panelBir);
        panelBir.add(panelUc);
        panelBir.add(panelDort);

        panelAna.add(panelIki);
        panelIki.add(panelBes);
        panelIki.add(panelAlti);

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);
        panelUc.setBackground(Color.CYAN);
        panelDort.setBackground(Color.CYAN);
        panelBes.setBackground(Color.CYAN);
        panelAlti.setBackground(Color.CYAN);

        kaydetButton.setForeground(Color.blue);
        kaydetButton2.setForeground(Color.blue);

        panelBir.setBorder(BorderFactory.createTitledBorder("OtoPark Fiyatlandırma"));
        panelIki.setBorder(BorderFactory.createTitledBorder("Temizlik Fiyatlandırma"));

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(270, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}

