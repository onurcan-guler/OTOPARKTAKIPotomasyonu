package OTOPARKOTOMASYONU;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ayarlar extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public Ayarlar(){

        setTitle("Ayarlar");

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(3,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JLabel bolumJLabel = new JLabel("Bölüm İsmi :");
        final JTextField bolumField = new JTextField(10);
        JLabel alanJLabel = new JLabel("Alan Sayısı :");
        final JTextField alanField = new JTextField(10);
        JLabel sutunJLabel = new JLabel("Sutün Sayısı :");
        final JTextField sutunField = new JTextField(10);

        JButton ileriButton = new JButton("İleri");

        ileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    sorgu.executeUpdate("insert into ayarlar (bolumAd,alanSayisi,sutunSayisi) values ('"+bolumField.getText()+"','"+alanField.getText()+"','"+sutunField.getText()+"')");
                    sorgu.close();
                } catch (Exception e2) {
                    System.out.println(e2);
                }
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    sorgu.executeUpdate("create table "+ bolumField.getText()+ " (id int NOT NULL AUTO_INCREMENT, alanlar text, plaka text, durum int, PRIMARY KEY ( `id` ))");
                    sorgu.executeUpdate("ALTER TABLE `"+ bolumField.getText()+ "` CHANGE `alanlar` `alanlar` TEXT CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ");
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    String plakaString = "Plaka Yer Alacak";
                    for (int i = 1; i <= Integer.parseInt(alanField.getText()); i++) {
                        sorgu.executeUpdate("insert into "+ bolumField.getText()+ " (alanlar,plaka,durum) values ('"+bolumField.getText()+i+"','"+plakaString+"','0')");
                    }
                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ayarDevam();
                    }
                });
            }
        });

        panelBir.add(bolumJLabel);
        panelBir.add(bolumField);
        panelBir.add(alanJLabel);
        panelBir.add(alanField);
        panelBir.add(sutunJLabel);
        panelBir.add(sutunField);

        panelIki.add(ileriButton);

        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Ayarlar"));

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(270, 190);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    public void ayarDevam() {
        final JDialog pencere = new JDialog();
        pencere.setTitle("Ayarlar Tamamlansın mı?");

        JPanel panelAna = new JPanel(new GridLayout(2,1));
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel aciklamaJLabel = new JLabel("Yeni bölüm eklemek için 'Devam' basın.");
        JLabel aciklama2JLabel = new JLabel("Ayarların tamamlanması için 'Bitir' basın.");

        JButton devamButton = new JButton("Devam");
        JButton bitirButton = new JButton("Bitir");

        panelBir.add(aciklamaJLabel);
        panelBir.add(aciklama2JLabel);

        panelIki.add(devamButton);
        panelIki.add(bitirButton);

        pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        bitirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pencere.dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ySifre();
                    }
                });
            }
        });
        devamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pencere.dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Ayarlar();
                    }
                });
            }
        });
        pencere.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelAna.setBorder(BorderFactory.createTitledBorder("Tamamlansın mı?"));

        pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pencere.setSize(270, 150);
        pencere.setLocationRelativeTo(null);
        pencere.setVisible(true);
        pencere.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    public void ySifre() {

        dispose();

        final JDialog pen = new JDialog();
        pen.setTitle("Açılış Şifresi - Yönetici");

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(3,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel bolumJLabel = new JLabel("Programın Yönetici",JLabel.RIGHT);
        JLabel aciklamaJLabel = new JLabel("Şifresini Girin !!");
        JLabel sifreJLabel = new JLabel("Şifre :");
        final JTextField sifreField = new JTextField(10);
        JLabel sifreTekrarJLabel = new JLabel("Şifreyi Tekrarla :");
        final JTextField sifreTekrarField = new JTextField(10);

        JButton ileriButton = new JButton("İleri");

        pen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        ileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if(sifreField.getText().equals(sifreTekrarField.getText())&&!sifreField.getText().equals("")&&!sifreTekrarField.getText().equals("")){
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                        Statement sorgu = baglan.createStatement();
                        sorgu.executeUpdate("insert into sifre (gSifre) values ('"+sifreField.getText()+"')");
                        sorgu.close();
                        pen.dispose();
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                kSifre();
                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Girilen Şifreler birbiri ile uyumlu değil. Tekrar Şifreyi girin ...", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBir.add(bolumJLabel);
        panelBir.add(aciklamaJLabel);
        panelBir.add(sifreJLabel);
        panelBir.add(sifreField);
        panelBir.add(sifreTekrarJLabel);
        panelBir.add(sifreTekrarField);

        panelIki.add(ileriButton);

        pen.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Ayarlar"));

        pen.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pen.setSize(270, 190);
        pen.setLocationRelativeTo(null);
        pen.setVisible(true);
        pen.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    public void kSifre() {
        dispose();

        final JDialog pen2 = new JDialog();
        pen2.setTitle("Açılış Şifresi - Normal Kullanıcı");

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(3,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel bolumJLabel = new JLabel("Programın Normal Kullanıcı",JLabel.RIGHT);
        JLabel aciklamaJLabel = new JLabel("Şifresini Girin !!");
        JLabel sifreJLabel = new JLabel("Şifre :");
        final JTextField sifreField = new JTextField(10);
        JLabel sifreTekrarJLabel = new JLabel("Şifreyi Tekrarla :");
        final JTextField sifreTekrarField = new JTextField(10);

        JButton ileriButton = new JButton("Bitti");

        pen2.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));
        ileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if(sifreField.getText().equals(sifreTekrarField.getText())&&!sifreField.getText().equals("")&&!sifreTekrarField.getText().equals("")){

                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
                        Statement sorgu = baglan.createStatement();
                        sorgu.executeUpdate("insert into sifre (gSifre) values ('"+sifreField.getText()+"')");

                        sorgu.executeUpdate("insert into ilk (durum) values ('1')");

                        sorgu.close();

                        pen2.dispose();

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new Menuler();
                            }
                        });

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Girilen Şifreler birbiri ile uyumlu değil. Tekrar Şifreyi girin ...", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBir.add(bolumJLabel);
        panelBir.add(aciklamaJLabel);
        panelBir.add(sifreJLabel);
        panelBir.add(sifreField);
        panelBir.add(sifreTekrarJLabel);
        panelBir.add(sifreTekrarField);
        panelIki.add(ileriButton);
        pen2.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Ayarlar"));

        pen2.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pen2.setSize(350, 185);
        pen2.setLocationRelativeTo(null);
        pen2.setVisible(true);
        pen2.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}


