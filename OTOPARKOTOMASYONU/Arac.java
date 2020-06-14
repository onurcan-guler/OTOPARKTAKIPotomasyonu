package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Arac extends JButton {

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";
    String konumString="";
    String idString="";
    String otoUcret="";
    String temizlik="";

    Calendar cal = Calendar.getInstance();

    public Arac(final String adi, int durum) {
        setText(adi);

        if (durum == 0)
            setBackground(Color.green);

        else
            setBackground(Color.red);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if(getBackground().toString().equals("java.awt.Color[r=0,g=255,b=0]")){

                    final JDialog pencere5 = new JDialog();

                    pencere5.setTitle("OTO Yerleştirme");

                    pencere5.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

                    JPanel panelAna = new JPanel(new FlowLayout());
                    JPanel panelBir = new JPanel(new GridLayout(9,2,5,5));

                    panelAna.setBackground(Color.CYAN);
                    panelBir.setBackground(Color.CYAN);

                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm - dd MMMMM yyyy");

                    String zamanString = sdf.format(cal.getTime());

                    SimpleDateFormat yilCevir = new SimpleDateFormat("yyyy");
                    SimpleDateFormat ayCevir = new SimpleDateFormat("MM");
                    SimpleDateFormat gunCevir = new SimpleDateFormat("dd");
                    SimpleDateFormat saatCevir = new SimpleDateFormat("H");
                    SimpleDateFormat dakikaCevir = new SimpleDateFormat("mm");

                    final int yil = Integer.parseInt(yilCevir.format(cal.getTime()));
                    final int ay = Integer.parseInt(ayCevir.format(cal.getTime()));
                    final int gun = Integer.parseInt(gunCevir.format(cal.getTime()));
                    final int saat = Integer.parseInt(saatCevir.format(cal.getTime()));
                    final int dakika = Integer.parseInt(dakikaCevir.format(cal.getTime()));

                    MaskFormatter plaka = null;
                    try {
                        plaka = new MaskFormatter("*********");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    MaskFormatter tcFormatter = null;
                    try {
                        tcFormatter = new MaskFormatter("###########");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    JLabel tcNoJLabel = new JLabel("T.C Kimlik No : ",JLabel.RIGHT);
                    final JFormattedTextField tcNoField = new JFormattedTextField(tcFormatter);
                    JLabel plakaJLabel = new JLabel("Plaka : ",JLabel.RIGHT);
                    final JFormattedTextField plakaField = new JFormattedTextField(plaka);
                    JLabel markaJLabel = new JLabel("Marka : ",JLabel.RIGHT);
                    final JTextField markaField = new JTextField(15);
                    JLabel modelJLabel = new JLabel("Model : ",JLabel.RIGHT);
                    final JTextField modelField = new JTextField(15);
                    JLabel renkJLabel = new JLabel("Renk : ",JLabel.RIGHT);
                    final JComboBox renkBox = new JComboBox();

                    JLabel konumJLabel = new JLabel("Konum : ",JLabel.RIGHT);
                    final JLabel konumLabel = new JLabel(adi);
                    JLabel zamanJLabel = new JLabel("Geldiği Saat/Tarih : ",JLabel.RIGHT);
                    final JLabel zamanField = new JLabel();
                    JLabel temizlikJLabel = new JLabel("Temizlik : ", JLabel.RIGHT);
                    final JComboBox temizlikBox = new JComboBox();
                    temizlikBox.addItem("");
                    temizlikBox.addItem("İç Temizlik");
                    temizlikBox.addItem("Dış Temizlik");
                    temizlikBox.addItem("İç-Dış Temizlik");
                    JLabel notJLabel = new JLabel("Not : ",JLabel.RIGHT);
                    final JTextField notField = new JTextField(15);

                    JButton aracYerlestirButton = new JButton("Araç Girişini Yap");

                    zamanField.setText(zamanString);
                    renkBox.setEditable(true);

                    aracYerlestirButton.setForeground(Color.BLUE);
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                        Statement sorgu = baglan.createStatement();

                        renkBox.addItem("");

                        ResultSet rs3 = sorgu.executeQuery("select * from renkler order by renk asc");
                        while(rs3.next()){
                            renkBox.addItem(rs3.getString(2));
                        }
                        sorgu.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    plakaField.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {

                            boolean var;

                            try {
                                Class.forName(driver);
                                Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu = baglan.createStatement();
                                ResultSet rs = sorgu.executeQuery("select * from yerlestir where plaka='"+plakaField.getText()+"'");

                                if(rs.next())
                                    var=true;
                                else
                                    var=false;

                                if(var==true){
                                    try {
                                        ResultSet rs2 = sorgu.executeQuery("select * from yerlestir where plaka='"+plakaField.getText()+"'");
                                        if(rs2.next()){
                                            markaField.setText(rs2.getString(4));
                                            modelField.setText(rs2.getString(5));
                                            renkBox.setSelectedItem(rs2.getString(6));
                                        }
                                        JOptionPane.showMessageDialog(null, "Geçmiş Araç Bilgileri Girildi.", "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
                                        sorgu.close();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }

                                else
                                    JOptionPane.showMessageDialog(null, "Bu araç daha önce giriş yapmamış.", "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);//uyarı penceresi

                                sorgu.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    });

                    aracYerlestirButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {

                            boolean var = false;
                            boolean giris = false;

                            try {
                                Class.forName(driver);
                                Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                                Statement sorgu = baglan.createStatement();
                                ResultSet rs = sorgu.executeQuery("select * from yerlestir where plaka='"+plakaField.getText()+"' and durum='1'");

                                if(rs.next()){
                                    giris=true;
                                }
                                sorgu.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if(giris==true){
                                JOptionPane.showMessageDialog(null, "Bu araç zaten giriş yapmış ...", "Hata", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                if(plakaField.getText().equals("")||markaField.getText().equals("")||modelField.getText().equals("")||renkBox.getSelectedItem().toString().equals("")||konumLabel.getText().equals("")){
                                    JOptionPane.showMessageDialog(null, "Boş alan bırakmayın ...", "Hata", JOptionPane.WARNING_MESSAGE);
                                }
                                else{
                                    if(tcNoField.getText().equals("           ")){
                                        try {
                                            Class.forName(driver);
                                            Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                                            Statement sorgu = baglan.createStatement();

                                            sorgu.executeUpdate("insert into yerlestir (tcNo,plaka,marka,model,renk,konum,zaman,temizlik,notEk,durum,yil,ay,gun,saat,dakika) values ('"+tcNoField.getText()+"','"+plakaField.getText()+"','"+markaField.getText()+"','"+modelField.getText()+"','"+renkBox.getSelectedItem().toString()+"','"+konumLabel.getText()+"','"+zamanField.getText()+"','"+temizlikBox.getSelectedItem().toString()+"','"+notField.getText()+"','"+1+"','"+Integer.toString(yil)+"','"+Integer.toString(ay)+"','"+Integer.toString(gun)+"','"+Integer.toString(saat)+"','"+Integer.toString(dakika)+"')");

                                            String tabloString = "";

                                            try {
                                                Class.forName(driver);
                                                Connection baglan3 = DriverManager.getConnection(adres,kullanici, sifre);
                                                Statement sorgu3 = baglan3.createStatement();

                                                ResultSet rs4 = sorgu3.executeQuery("select * from ayarlar");
                                                while(rs4.next()){
                                                    String al = rs4.getString(2);

                                                    try {
                                                        Class.forName(driver);
                                                        Connection baglan2 = DriverManager.getConnection(adres,kullanici, sifre);
                                                        Statement sorgu2 = baglan2.createStatement();

                                                        ResultSet rs = sorgu2.executeQuery("select * from "+al+" where alanlar='"+konumLabel.getText()+"'");

                                                        if(rs.next()){
                                                            tabloString=al;
                                                        }
                                                        sorgu2.close();
                                                    } catch (Exception e2) {
                                                        System.out.println(e2);
                                                    }
                                                }
                                                sorgu3.close();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }

                                            sorgu.executeUpdate("Update "+tabloString+" set durum='1' where alanlar='"+konumLabel.getText()+"'");
                                            sorgu.executeUpdate("Update "+tabloString+" set plaka='"+plakaField.getText()+"' where alanlar='"+konumLabel.getText()+"'");

                                            setText(plakaField.getText());
                                            setBackground(Color.red);

                                            JOptionPane.showMessageDialog(null, "Araç Girişi Yapıldı.", "Kayıt Başarılı", JOptionPane.INFORMATION_MESSAGE);
                                            sorgu.close();
                                            pencere5.dispose();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }

                                    else{
                                        try {
                                            Class.forName(driver);
                                            Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                                            Statement sorgu = baglan.createStatement();
                                            ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"' and durum=1");
                                            
                                            if(rs.next())
                                                var=true;
                                            else
                                                var=false;

                                            if(var==true){
                                                try {

                                                    sorgu.executeUpdate("insert into yerlestir (tcNo,plaka,marka,model,renk,konum,zaman,temizlik,notEk,durum,yil,ay,gun,saat,dakika) values ('"+tcNoField.getText()+"','"+plakaField.getText()+"','"+markaField.getText()+"','"+modelField.getText()+"','"+renkBox.getSelectedItem().toString()+"','"+konumLabel.getText()+"','"+zamanField.getText()+"','"+temizlikBox.getSelectedItem().toString()+"','"+notField.getText()+"','"+1+"','"+Integer.toString(yil)+"','"+Integer.toString(ay)+"','"+Integer.toString(gun)+"','"+Integer.toString(saat)+"','"+Integer.toString(dakika)+"')");

                                                    String tabloString = "";

                                                    try {
                                                        Class.forName(driver);
                                                        Connection baglan3 = DriverManager.getConnection(adres,kullanici, sifre);
                                                        Statement sorgu3 = baglan3.createStatement();

                                                        ResultSet rs4 = sorgu3.executeQuery("select * from ayarlar");

                                                        while(rs4.next()){

                                                            String al = rs4.getString(2);

                                                            try {
                                                                Class.forName(driver);
                                                                Connection baglan2 = DriverManager.getConnection(adres,kullanici, sifre);
                                                                Statement sorgu2 = baglan2.createStatement();

                                                                ResultSet rs5 = sorgu2.executeQuery("select * from "+al+" where alanlar='"+konumLabel.getText()+"'");

                                                                if(rs5.next()){
                                                                    tabloString=al;
                                                                }
                                                                sorgu2.close();
                                                            } catch (Exception e2) {
                                                                System.out.println(e2);
                                                            }
                                                        }
                                                        sorgu3.close();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    sorgu.executeUpdate("Update "+tabloString+" set durum='1' where alanlar='"+konumLabel.getText()+"'");
                                                    sorgu.executeUpdate("Update "+tabloString+" set plaka='"+plakaField.getText()+"' where alanlar='"+konumLabel.getText()+"'");
                                                    setText(plakaField.getText());
                                                    setBackground(Color.red);

                                                    JOptionPane.showMessageDialog(null, "Araç Girişi Yapıldı.", "Kayıt Başarılı", JOptionPane.INFORMATION_MESSAGE);

                                                    sorgu.close();
                                                    pencere5.dispose();
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null, "Abone Bulunamadı", "Hata", JOptionPane.ERROR_MESSAGE);
                                            }
                                            sorgu.close();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    panelBir.add(tcNoJLabel);
                    panelBir.add(tcNoField);
                    panelBir.add(plakaJLabel);
                    panelBir.add(plakaField);
                    panelBir.add(markaJLabel);
                    panelBir.add(markaField);
                    panelBir.add(modelJLabel);
                    panelBir.add(modelField);
                    panelBir.add(renkJLabel);
                    panelBir.add(renkBox);
                    panelBir.add(konumJLabel);
                    panelBir.add(konumLabel);
                    panelBir.add(zamanJLabel);
                    panelBir.add(zamanField);
                    panelBir.add(temizlikJLabel);
                    panelBir.add(temizlikBox);
                    panelBir.add(notJLabel);
                    panelBir.add(notField);
                    pencere5.add(panelAna);
                    panelAna.add(panelBir);
                    panelAna.add(aracYerlestirButton);

                    panelBir.setBorder(BorderFactory.createTitledBorder("Bilgiler"));

                    pencere5.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
                    pencere5.setSize(380, 375);
                    pencere5.setLocationRelativeTo(null);
                    pencere5.setVisible(true);
                    pencere5.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                }
                else {
                    final JDialog pencere = new JDialog();
                    pencere.setTitle("Araç Çıkış");

                    pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

                    JPanel panelAna = new JPanel(new GridLayout());
                    JPanel panelBir = new JPanel(new GridLayout(11,2,5,5));
                    JPanel panelIki = new JPanel(new FlowLayout());

                    JLabel tcNoJLabel = new JLabel("T.C Kimlik No : ",JLabel.RIGHT);
                    final Label tcNoField = new Label();
                    JLabel plakaJLabel = new JLabel("Plaka : ",JLabel.RIGHT);
                    final Label plakaField = new Label();
                    JLabel markaJLabel = new JLabel("Marka : ",JLabel.RIGHT);
                    final Label markaField = new Label();
                    JLabel modelJLabel = new JLabel("Model : ",JLabel.RIGHT);
                    final Label modelField = new Label();
                    JLabel renkJLabel = new JLabel("Renk : ",JLabel.RIGHT);
                    final Label renkField = new Label();
                    JLabel konumJLabel2 = new JLabel("Konum : ",JLabel.RIGHT);
                    final Label konumField = new Label();
                    JLabel zamanJLabel = new JLabel("Geldiği Tarih : ",JLabel.RIGHT);
                    final Label zamanField = new Label();
                    JLabel notJLabel = new JLabel("Not : ",JLabel.RIGHT);
                    final Label notField = new Label();
                    JLabel ucretiJLabel = new JLabel("Ücreti : ",JLabel.RIGHT);
                    final Label ucretiField = new Label();
                    JLabel temizlikUcretiJLabel = new JLabel("Temizlik Ücreti : ", JLabel.RIGHT);
                    final Label temizlikUcretiField = new Label();
                    JLabel toplamUcretJLabel = new JLabel("Toplam Ücret : ",JLabel.RIGHT);
                    final JLabel toplamUcretField = new JLabel();

                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();

                    SimpleDateFormat yilCevir = new SimpleDateFormat("yyyy");
                    SimpleDateFormat ayCevir = new SimpleDateFormat("MM");
                    SimpleDateFormat gunCevir = new SimpleDateFormat("dd");
                    SimpleDateFormat saatCevir = new SimpleDateFormat("H");
                    SimpleDateFormat dakikaCevir = new SimpleDateFormat("mm");

                    final int yil = Integer.parseInt(yilCevir.format(cal.getTime()));
                    final int ay = Integer.parseInt(ayCevir.format(cal.getTime()));
                    final int gun = Integer.parseInt(gunCevir.format(cal.getTime()));
                    final int saat = Integer.parseInt(saatCevir.format(cal.getTime()));
                    final int dakika = Integer.parseInt(dakikaCevir.format(cal.getTime()));

                    String yilAl = null;
                    String ayAl = null;
                    String gunAl = null;
                    String saatAl = null;
                    String dkAl = null;
                    JButton aracCikisButton = new JButton("Araç Çıkış");
                    JButton iptalButton = new JButton("İptal");
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                        Statement sorgu = baglan.createStatement();
                        ResultSet rs = sorgu.executeQuery("select * from yerlestir where plaka='"+getText()+"' && durum=1");

                        if(rs.next()){
                            idString = rs.getString(1);
                            tcNoField.setText(rs.getString(2));
                            plakaField.setText(rs.getString(3));
                            markaField.setText(rs.getString(4));
                            modelField.setText(rs.getString(5));
                            renkField.setText(rs.getString(6));
                            konumField.setText(rs.getString(7));
                            zamanField.setText(rs.getString(8));
                            temizlik = rs.getString(9);
                            notField.setText(rs.getString(10));
                            yilAl=rs.getString(12);
                            ayAl=rs.getString(13);
                            gunAl=rs.getString(14);
                            saatAl=rs.getString(15);
                            dkAl=rs.getString(16);
                        }
                        calendar1.set(Integer.parseInt(yilAl), Integer.parseInt(ayAl), Integer.parseInt(gunAl), Integer.parseInt(saatAl), Integer.parseInt(dkAl));
                        calendar2.set(yil, ay, gun, saat, dakika);
                        long milliseconds1 = calendar1.getTimeInMillis();
                        long milliseconds2 = calendar2.getTimeInMillis();
                        long diff = milliseconds2 - milliseconds1;
                        long dk = diff / (60 * 1000);
                        long gun2 = diff / (24 * 60 * 60 * 1000);

                        if (temizlik.equals("İç Temizlik")) {
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from temizlik_fiyat where id='1'");
                                if(rs2.next()){
                                    temizlikUcretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if (temizlik.equals("Dış Temizlik")) {
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from temizlik_fiyat where id='2'");

                                if(rs2.next()){
                                    temizlikUcretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if (temizlik.equals("İç-Dış Temizlik")) {
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from temizlik_fiyat where id='3'");
                                if(rs2.next()){
                                    temizlikUcretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else {
                            temizlikUcretiField.setText("0");
                        }
                        if(!tcNoField.getText().equals("           ")){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='10'");

                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk<=30){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='1'");

                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=31&&dk<=60){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='2'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=61&&dk<=120){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='3'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=121&&dk<=180){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='4'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=181&&dk<=240){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='5'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=241&&dk<=300){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='6'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=301&&dk<=360){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='7'");
                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=361&&dk<=10080){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='8'");

                                if(rs2.next()){
                                    otoUcret=rs2.getString(3);
                                }
                                long sonuc;
                                if(gun2==0){
                                    sonuc=Integer.parseInt(otoUcret)*1;
                                }
                                else{
                                    sonuc=Integer.parseInt(otoUcret)*gun2;
                                }
                                ucretiField.setText(Long.toString(sonuc));
                                baglan2.close();

                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else if(dk>=10081&&dk<=43199){
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='9'");
                                if(rs2.next()){
                                    otoUcret=rs2.getString(3);
                                }
                                long sure=dk/10080;

                                long sonuc=Integer.parseInt(otoUcret)*sure;
                                ucretiField.setText(Long.toString(sonuc));
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        else{
                            try {
                                Class.forName(driver);
                                Connection baglan2 = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu2 = baglan2.createStatement();
                                ResultSet rs2 = sorgu2.executeQuery("select * from otopark_fiyat where id='10'");

                                if(rs2.next()){
                                    ucretiField.setText(rs2.getString(3));
                                }
                                baglan2.close();
                            } catch (Exception e2) {
                                System.out.println(e2);
                            }
                        }
                        float toplamSonuc = Float.parseFloat(ucretiField.getText())+Float.parseFloat(temizlikUcretiField.getText());
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        String toplamFormatted = nf.format(Double.parseDouble(Float.toString(toplamSonuc)));

                        toplamUcretField.setText(toplamFormatted);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    aracCikisButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            SimpleDateFormat sdf = new SimpleDateFormat("H:mm - dd MMMMM yyyy");
                            String zamanString2 = sdf.format(cal.getTime());
                            try {
                                Class.forName(driver);
                                Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                                Statement sorgu = baglan.createStatement();

                                String tabloString = "";
                                try {
                                    Class.forName(driver);
                                    Connection baglan3 = DriverManager.getConnection(adres,kullanici, sifre);
                                    Statement sorgu3 = baglan3.createStatement();

                                    ResultSet rs4 = sorgu3.executeQuery("select * from ayarlar");


                                    while(rs4.next()){

                                        String al = rs4.getString(2);


                                        try {
                                            Class.forName(driver);
                                            Connection baglan2 = DriverManager.getConnection(adres,kullanici, sifre);
                                            Statement sorgu2 = baglan2.createStatement();

                                            ResultSet rs = sorgu2.executeQuery("select * from "+al+" where alanlar='"+konumField.getText()+"'");
                                            if(rs.next()){
                                                tabloString=al;
                                            }
                                            sorgu2.close();
                                        } catch (Exception e2) {
                                            System.out.println(e2);
                                        }
                                    }
                                    sorgu3.close();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                sorgu.executeUpdate("Update "+tabloString+" set durum='0' where alanlar='"+konumField.getText()+"'");
                                sorgu.executeUpdate("Update yerlestir set durum='"+0+"' where id='"+idString+"'");
                                sorgu.executeUpdate("Update yerlestir set temizlikUcret='"+temizlikUcretiField.getText()+"' where id='"+idString+"'");
                                sorgu.executeUpdate("Update yerlestir set otoUcret='"+ucretiField.getText()+"' where id='"+idString+"'");
                                sorgu.executeUpdate("Update yerlestir set toplamUcret='"+toplamUcretField.getText()+"' where id='"+idString+"'");
                                sorgu.executeUpdate("Update yerlestir set cikisZaman='"+zamanString2+"' where id='"+idString+"'");

                                JOptionPane.showMessageDialog(null, "Araç Çıkışı Yapıldı.","Başarılı",JOptionPane.INFORMATION_MESSAGE);

                                setText(konumField.getText());
                                konumField.setText(getText());
                                setBackground(Color.GREEN);
                                pencere.dispose();

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    });

                    iptalButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            pencere.dispose();
                        }
                    });

                    panelBir.add(tcNoJLabel);
                    panelBir.add(tcNoField);
                    panelBir.add(plakaJLabel);
                    panelBir.add(plakaField);
                    panelBir.add(markaJLabel);
                    panelBir.add(markaField);
                    panelBir.add(modelJLabel);
                    panelBir.add(modelField);
                    panelBir.add(renkJLabel);
                    panelBir.add(renkField);
                    panelBir.add(konumJLabel2);
                    panelBir.add(konumField);
                    panelBir.add(zamanJLabel);
                    panelBir.add(zamanField);
                    panelBir.add(notJLabel);
                    panelBir.add(notField);
                    panelBir.add(ucretiJLabel);
                    panelBir.add(ucretiField);
                    panelBir.add(temizlikUcretiJLabel);
                    panelBir.add(temizlikUcretiField);
                    panelBir.add(toplamUcretJLabel);
                    panelBir.add(toplamUcretField);

                    panelIki.add(aracCikisButton);
                    panelIki.add(iptalButton);

                    pencere.add(panelAna);
                    panelAna.add(panelBir);
                    panelAna.add(panelIki);

                    panelAna.setBackground(Color.CYAN);
                    panelBir.setBackground(Color.CYAN);
                    panelIki.setBackground(Color.CYAN);

                    aracCikisButton.setForeground(Color.red);
                    iptalButton.setForeground(Color.blue);

                    panelBir.setBorder(BorderFactory.createTitledBorder("Bilgiler"));
                    panelIki.setBorder(BorderFactory.createTitledBorder("İşlem"));

                    pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
                    pencere.setSize(600, 280);
                    pencere.setLocationRelativeTo(null);
                    pencere.setVisible(true);
                    pencere.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

                }

            }
        });

    }



}