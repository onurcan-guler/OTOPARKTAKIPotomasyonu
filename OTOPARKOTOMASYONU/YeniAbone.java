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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class YeniAbone extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/OtoPark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public YeniAbone() throws ParseException{

        setTitle("Yeni Abone Kayıt");

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(5,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        final Calendar cal = Calendar.getInstance();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ImageIcon kaydetIcon = new ImageIcon("C:\\OTOPark\\images\\0.gif");
        ImageIcon iptalIcon = new ImageIcon("C:\\OTOPark\\images\\1.gif");

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

        JButton kaydetButton = new JButton("Kaydet",kaydetIcon);
        JButton iptalButton = new JButton("İptal",iptalIcon);

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

        panelIki.add(kaydetButton);
        panelIki.add(iptalButton);

        kaydetButton.setMnemonic(KeyEvent.VK_K);
        iptalButton.setMnemonic(KeyEvent.VK_P);

        kaydetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                boolean var=false;

                char[] al=adiField.getText().toCharArray();
                char[] al2=soyadiField.getText().toCharArray();

                int alSay=0, al2Say=0;

                String zamanString = sdf.format(cal.getTime());

                cal.setTime(cal.getTime());
                cal.add(Calendar.MONTH, 1);

                String birSonrakiAy = sdf.format(cal.getTime());
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"'");

                    if(rs.next()){
                        var=true;
                    }
                    if(var==true){
                        JOptionPane.showMessageDialog(null, "Bu T.C. Kimlik No Daha Önceden Kaydedilmiş ...", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

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
                        else if(tcNoField.getText().equals("___________")||adiField.getText().equals("")||soyadiField.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "Boş alan bırakmayın ...", "Hata", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                sorgu2.executeUpdate("insert into abone (tcNo,adi,soyadi,telefon,notek,baslamaTarihi,bitisTarihi,durum) values ('"+tcNoField.getText()+"','"+adiField.getText()+"','"+soyadiField.getText()+"','"+telefonField.getText()+"','"+notField.getText()+"','"+zamanString+"','"+birSonrakiAy+"','1')");
                                sorgu2.close();

                                JOptionPane.showMessageDialog(null, "Kayıt Başarılı", "Başarılı", JOptionPane.PLAIN_MESSAGE);

                                dispose();

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
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

        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Bilgiler"));
        panelIki.setBorder(BorderFactory.createTitledBorder("İşlem"));

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(380, 265);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}
