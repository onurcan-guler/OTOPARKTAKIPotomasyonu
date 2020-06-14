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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

public class AboneDuzenle extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/OtoPark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";


    String idAl = "";

    public AboneDuzenle() throws ParseException {

        setTitle("Abone Düzenle");


        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(6, 2, 5, 5));
        JPanel panelIki = new JPanel(new FlowLayout());


        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));


        MaskFormatter tcFormatter = new MaskFormatter("###########");
        MaskFormatter telFormatter = new MaskFormatter("+90-(###)-###-##-##");

        telFormatter.setPlaceholderCharacter('_');
        tcFormatter.setPlaceholderCharacter('_');

        JLabel tcNoJLabel = new JLabel("T.C. Kimlik No : ", JLabel.RIGHT);
        final JFormattedTextField tcNoField = new JFormattedTextField(tcFormatter);
        JLabel adiJLabel = new JLabel("Adı : ", JLabel.RIGHT);
        final JTextField adiField = new JTextField(15);
        JLabel soyadiJLabel = new JLabel("Soyadı : ", JLabel.RIGHT);
        final JTextField soyadiField = new JTextField(15);
        JLabel telefonJLabel = new JLabel("Telefon : ", JLabel.RIGHT);
        final JFormattedTextField telefonField = new JFormattedTextField(telFormatter);
        JLabel notJLabel = new JLabel("Ek Not : ", JLabel.RIGHT);
        final JTextField notField = new JTextField(15);
        JLabel yenileJLabel = new JLabel("Abonelik Yenile",JLabel.RIGHT);
        final JCheckBox yenileBox = new JCheckBox("Evet");


        final JButton araButton = new JButton("Ara");
        final JButton kaydetButton = new JButton("Kaydet");
        JButton iptalButton = new JButton("İptal");


        araButton.setMnemonic(KeyEvent.VK_A);
        kaydetButton.setMnemonic(KeyEvent.VK_K);
        iptalButton.setMnemonic(KeyEvent.VK_P);



        adiField.setEnabled(false);
        soyadiField.setEnabled(false);
        telefonField.setEnabled(false);
        notField.setEnabled(false);
        yenileBox.setEnabled(false);


        panelBir.add(tcNoJLabel);
        panelBir.add(tcNoField);
        panelBir.add(adiJLabel);
        panelBir.add(adiField);
        panelBir.add(soyadiJLabel);
        panelBir.add(soyadiField);
        panelBir.add(telefonJLabel);
        panelBir.add(telefonField);
        panelBir.add(notJLabel);
        panelBir.add(notField);
        panelBir.add(yenileJLabel);
        panelBir.add(yenileBox);

        panelIki.add(araButton);
        panelIki.add(kaydetButton);
        panelIki.add(iptalButton);


        kaydetButton.setEnabled(false);


        araButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"'");

                    if (rs.next()) {
                        idAl = rs.getString(1);
                        adiField.setText(rs.getString(3));
                        soyadiField.setText(rs.getString(4));
                        telefonField.setText(rs.getString(5));
                        notField.setText(rs.getString(6));


                        kaydetButton.setEnabled(true);
                        araButton.setEnabled(false);

                        adiField.setEnabled(true);
                        soyadiField.setEnabled(true);
                        telefonField.setEnabled(true);
                        notField.setEnabled(true);
                        yenileBox.setEnabled(true);
                    }

                    else{
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);
                    }

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });



        tcNoField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {


                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"'");

                    if (rs.next()) {
                        idAl = rs.getString(1);
                        adiField.setText(rs.getString(3));
                        soyadiField.setText(rs.getString(4));
                        telefonField.setText(rs.getString(5));
                        notField.setText(rs.getString(6));


                        kaydetButton.setEnabled(true);
                        araButton.setEnabled(false);

                        adiField.setEnabled(true);
                        soyadiField.setEnabled(true);
                        telefonField.setEnabled(true);
                        notField.setEnabled(true);
                        yenileBox.setEnabled(true);
                    }

                    else{
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });



        kaydetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {


                int secimKontrol = 0;
                if(yenileBox.isSelected())
                    secimKontrol=1;


                char[] al=adiField.getText().toCharArray();
                char[] al2=soyadiField.getText().toCharArray();

                int alSay=0, al2Say=0;



                for (int i = 0; i < al.length; i++) {
                    if(Character.isLetter(al[i]) || Character.isISOControl(al[i])){

                    }

                    else{
                        alSay++;
                    }
                }

                for (int i = 0; i < al2.length; i++) {
                    if(Character.isLetter(al2[i]) || Character.isISOControl(al2[i])){

                    }

                    else{
                        al2Say++;
                    }
                }

                if(alSay>0){
                    JOptionPane.showMessageDialog(null, "'Adı' kısmını sadece karakter girin.", "Hata", JOptionPane.WARNING_MESSAGE);
                }
                else if(al2Say>0){
                    JOptionPane.showMessageDialog(null, "'Soyad' kısmını sadece karakter girin.", "Hata", JOptionPane.WARNING_MESSAGE);
                }
                else if(tcNoField.getText().equals("           ")||adiField.getText().equals("")||soyadiField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Boş alan bırakmayın ...", "Hata", JOptionPane.WARNING_MESSAGE);
                }

                else{
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                        Statement sorgu = baglan.createStatement();

                        sorgu.executeUpdate("Update abone set tcNo='"+ tcNoField.getText() + "' where id='"+ idAl + "'");
                        sorgu.executeUpdate("Update abone set adi='"+ adiField.getText().trim() + "' where id='" + idAl + "'");
                        sorgu.executeUpdate("Update abone set soyadi='"+ soyadiField.getText().trim() + "' where id='" + idAl+ "'");
                        sorgu.executeUpdate("Update abone set telefon='"+ telefonField.getText() + "' where id='" + idAl+ "'");
                        sorgu.executeUpdate("Update abone set notek='"+ notField.getText() + "' where id='" + idAl + "'");

                        if (secimKontrol==1) {

                            Calendar cal = Calendar.getInstance();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                            String zamanString = sdf.format(cal.getTime());

                            cal.setTime(cal.getTime());
                            cal.add(Calendar.MONTH, 1);
                            String birSonrakiAy = sdf.format(cal.getTime());

                            sorgu.executeUpdate("Update abone set baslamaTarihi='"+ zamanString + "' where id='" + idAl + "'");
                            sorgu.executeUpdate("Update abone set bitisTarihi='"+ birSonrakiAy + "' where id='" + idAl + "'");
                            sorgu.executeUpdate("Update abone set durum=1 where id='" + idAl + "'");

                            JOptionPane.showMessageDialog(null, "Yapılan Değişiklikler ile Abonelik Tarihi de Yenilendi", "Başarılı", JOptionPane.PLAIN_MESSAGE);

                        }
                        else {

                            JOptionPane.showMessageDialog(null, "Değişiklikler Kaydedildi", "Başarılı", JOptionPane.PLAIN_MESSAGE);
                        }
                        sorgu.close();

                        dispose();

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    new AboneDuzenle();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (Exception e) {
                        System.out.println(e);
                    }
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


        kaydetButton.setForeground(Color.BLUE);
        iptalButton.setForeground(Color.RED);
        araButton.setForeground(Color.magenta);


        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);


        panelBir.setBorder(BorderFactory.createTitledBorder("Bilgiler"));
        panelIki.setBorder(BorderFactory.createTitledBorder("İşlem"));

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(385, 310);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

}
