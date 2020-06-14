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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


public class Menuler extends JFrame{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    int durum=2;

    public void Giris() {

        setTitle("OtoPark Takip - SDRL");

        JPanel anaJPanel = new JPanel(new FlowLayout());

        JScrollPane pane = new JScrollPane(anaJPanel);
        JTabbedPane tPane = new JTabbedPane();

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from ayarlar");

            while (rs.next()) {
                tPane.addTab(rs.getString(2), new Blok(rs.getString(2), Integer.parseInt(rs.getString(3)), 0, Integer.parseInt(rs.getString(4))));
            }

            sorgu.close();

        } catch (Exception e2) {
            System.out.println(e2);
        }

        anaJPanel.add(tPane);

        anaJPanel.setBackground(Color.CYAN);

        anaJPanel.setBorder(BorderFactory.createTitledBorder("Durum"));

        final SimpleDateFormat yil = new SimpleDateFormat("yyyy");
        final SimpleDateFormat ay = new SimpleDateFormat("MM");
        final SimpleDateFormat gun = new SimpleDateFormat("dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            Statement sorgu2 = baglan.createStatement();

            ResultSet rs = sorgu.executeQuery("select * from abone where durum=1");

            String suanYilString = yil.format(c1.getTime());
            String suanAyString = ay.format(c1.getTime());
            String suanGunString = gun.format(c1.getTime());

            int a=0;

            while (rs.next()) {

                int id = rs.getInt(1);

                c1.set(Integer.parseInt(suanYilString), Integer.parseInt(suanAyString), Integer.parseInt(suanGunString));

                c2.setTime(rs.getDate(8));

                String suanYilString2 = yil.format(c2.getTime());
                String suanAyString2 = ay.format(c2.getTime());
                String suanGunString2 = gun.format(c2.getTime());
                c2.set(Integer.parseInt(suanYilString2), Integer.parseInt(suanAyString2), Integer.parseInt(suanGunString2));

                if(c1.before(c2)){
                }
                else{
                    sorgu2.executeUpdate("update abone set durum=0 where id='"+id+"'");
                    a=1;
                }
            }
            if(a==1)
                JOptionPane.showMessageDialog(null, "Abonelik süresi dolanların, otomatik olarak abonelikleri iptal edildi ...","Bilgilendirme",JOptionPane.PLAIN_MESSAGE);
            sorgu.close();
        } catch (Exception e2) {
            System.out.println(e2);
        }
        ImageIcon yeniAboneIcon = new ImageIcon("C:\\OTOPark\\images\\new.gif");
        ImageIcon aboneListeIcon = new ImageIcon("C:\\OTOPark\\images\\table.gif");
        ImageIcon duzenleIcon = new ImageIcon("C:\\OTOPark\\images\\actionEdit.gif");
        ImageIcon silIcon = new ImageIcon("C:\\OTOPark\\images\\actionCancel.gif");
        ImageIcon bulIcon = new ImageIcon("C:\\OTOPark\\images\\Cfsearch.gif");
        ImageIcon hakkindaIcon = new ImageIcon("C:\\OTOPark\\images\\about.gif");
        ImageIcon iletisimIcon = new ImageIcon("C:\\OTOPark\\images\\tel.gif");
        ImageIcon cikisIcon = new ImageIcon("C:\\OTOPark\\images\\stop.gif");
        ImageIcon paraIcon = new ImageIcon("C:\\OTOPark\\images\\money.gif");
        ImageIcon kasaIcon = new ImageIcon("C:\\OTOPark\\images\\cash.png");
        ImageIcon sifreIcon = new ImageIcon("C:\\OTOPark\\images\\password.gif");
        ImageIcon ayarlarIcon = new ImageIcon("C:\\OTOPark\\images\\options.gif");
        ImageIcon gecmisIcon = new ImageIcon("C:\\OTOPark\\images\\gec.png");

        JMenuBar bar = new JMenuBar();
        JMenu aboneJMenu = new JMenu("Abone");
        JMenu aboneDuzenleJMenu = new JMenu("Abone Düzenle");
        JMenu yerJMenu = new JMenu("OtoPark");
        JMenu programJMenu = new JMenu("Program");
        JMenu yonetimJMenu = new JMenu("Yönetim");

        JMenuItem yeniAboneItem = new JMenuItem("Yeni Abone",yeniAboneIcon);
        JMenuItem aboneListesiItem = new JMenuItem("Abone Listesi",aboneListeIcon);
        JMenuItem aboneDuzenleItem = new JMenuItem("Abone Düzenle",duzenleIcon);
        JMenuItem aboneSilItem = new JMenuItem("Abone Sil",silIcon);
        JMenuItem aboneBulItem = new JMenuItem("Abone Bul",bulIcon);

        JMenuItem ucretlendirmeItem = new JMenuItem("Ücretlendirme",paraIcon);
        JMenuItem gecmisItem = new JMenuItem("Geçmiş",gecmisIcon);

        JMenuItem iletisimItem = new JMenuItem("İletişim",iletisimIcon);
        JMenuItem hakkindaItem = new JMenuItem("Hakkında",hakkindaIcon);
        JMenuItem cikisItem = new JMenuItem("Çıkış",cikisIcon);

        JMenuItem kasaItem = new JMenuItem("Kasa",kasaIcon);
        JMenuItem ayarlarItem = new JMenuItem("Ayarları Sıfırla",ayarlarIcon);
        JMenuItem sifreItem = new JMenuItem("Şifre Değiştir",sifreIcon);

        yeniAboneItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.ALT_MASK));
        aboneListesiItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.ALT_MASK));
        aboneDuzenleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.ALT_MASK));
        aboneSilItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
        aboneBulItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.ALT_MASK));
        ucretlendirmeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,ActionEvent.ALT_MASK));
        kasaItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,ActionEvent.ALT_MASK));
        gecmisItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.ALT_MASK));
        iletisimItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.ALT_MASK));
        hakkindaItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.ALT_MASK));
        cikisItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.ALT_MASK));

        bar.add(aboneJMenu);
        bar.add(yerJMenu);
        if(durum==1)
            bar.add(yonetimJMenu);
        bar.add(programJMenu);

        aboneJMenu.add(yeniAboneItem);
        aboneJMenu.add(aboneListesiItem);
        aboneJMenu.add(aboneDuzenleJMenu);
        aboneDuzenleJMenu.add(aboneDuzenleItem);
        aboneDuzenleJMenu.add(aboneSilItem);
        aboneJMenu.add(aboneBulItem);
        yerJMenu.add(gecmisItem);

        programJMenu.add(hakkindaItem);
        programJMenu.add(iletisimItem);
        programJMenu.addSeparator();
        programJMenu.add(cikisItem);

        yonetimJMenu.add(ayarlarItem);
        yonetimJMenu.add(kasaItem);
        yonetimJMenu.add(sifreItem);
        yonetimJMenu.add(ucretlendirmeItem);

        yeniAboneItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new YeniAbone();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        aboneListesiItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new AboneListesi();
                    }
                });
            }
        });

        aboneDuzenleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new AboneDuzenle();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        aboneSilItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new AboneSil();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        aboneBulItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new AboneBul();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        ucretlendirmeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Ucretlendirme();
                    }
                });
            }
        });

        gecmisItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Gecmis();
                    }
                });
            }
        });

        kasaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Kasa();
                    }
                });
            }
        });

        ayarlarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new AyarlariSifirla();
                    }
                });
            }
        });

        sifreItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        sifreDegis();
                    }
                });
            }
        });

        iletisimItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Iletisim();
                    }
                });
            }
        });

        hakkindaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Hakkinda();
                    }
                });
            }
        });

        cikisItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });


        add(pane);
        setJMenuBar(bar);

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        setSize(500, 450);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }

    public Menuler(){

        final JDialog pen = new JDialog();

        pen.setTitle("Şifre Girin");


        pen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(2,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel bolumJLabel = new JLabel("Şifrenizi",JLabel.RIGHT);
        JLabel aciklamaJLabel = new JLabel("Girin !!");
        JLabel sifreJLabel = new JLabel("Şifre :");

        final JPasswordField sifreField = new JPasswordField(10);

        JButton ileriButton = new JButton("Onayla");

        sifreField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {


                char[] girilenSifre = sifreField.getPassword();
                String cevir = new String(girilenSifre);

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from sifre where gSifre='"+cevir+"'");

                    if(rs.next()){

                        durum=rs.getInt(1);//hangi şifrenin girildiği

                        pen.dispose();//pencereyi kapat

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                //ana pencereyi açar
                                Giris();
                            }
                        });

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Girilen Şifre Yanlış Tekrar Girin ...", "Başarısız" ,JOptionPane.WARNING_MESSAGE);
                    }

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        ileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                char[] girilenSifre = sifreField.getPassword();
                String cevir = new String(girilenSifre);

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from sifre where gSifre='"+cevir+"'");

                    if(rs.next()){
                        durum=rs.getInt(1);
                        pen.dispose();
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                Giris();
                            }
                        });
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Girilen Şifre Yanlış Tekrar Girin ...", "Başarısız" ,JOptionPane.WARNING_MESSAGE);
                    }

                    sorgu.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });
        panelBir.add(bolumJLabel);
        panelBir.add(aciklamaJLabel);
        panelBir.add(sifreJLabel);
        panelBir.add(sifreField);

        panelIki.add(ileriButton);

        pen.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelBir.setBorder(BorderFactory.createTitledBorder("Giriş"));

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        ileriButton.setForeground(Color.blue);

        pen.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pen.setSize(270, 160);
        pen.setLocationRelativeTo(null);
        pen.setVisible(true);
        pen.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    public void sifreDegis() {

        final JDialog pen3 = new JDialog();
        pen3.setTitle("Şifre Değiştir");

        pen3.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new GridLayout(3,2,5,5));
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel bolumJLabel = new JLabel("Hangisinin Şifresi ??",JLabel.RIGHT);
        final JComboBox kullaniciBox = new JComboBox();
        kullaniciBox.addItem("");
        kullaniciBox.addItem("Normal Kullanıcı");
        kullaniciBox.addItem("Yönetici");
        JLabel sifreJLabel = new JLabel("Şifre :");
        final JTextField sifreField = new JTextField(10);
        JLabel sifreTekrarJLabel = new JLabel("Şifreyi Tekrarla :");
        final JTextField sifreTekrarField = new JTextField(10);
        JButton ileriButton = new JButton("Şifreyi Değiştir");
        ileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int a=0;
                if(sifreField.getText().equals(sifreTekrarField.getText())&&!sifreField.getText().equals("")&&!sifreTekrarField.getText().equals("")&&!kullaniciBox.getSelectedItem().equals("")){
                    //seçilen yönetici ise
                    if(kullaniciBox.getSelectedItem().equals("Yönetici"))
                        a=1;
                    else
                        a=2;
                    try {
                        Class.forName(driver);
                        Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                        Statement sorgu = baglan.createStatement();
                        sorgu.executeUpdate("update sifre set gSifre='"+sifreField.getText()+"' where id='"+a+"'");
                        sorgu.close();
                        JOptionPane.showMessageDialog(null, "Şifre Başarıyla Değiştirildi ...", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        pen3.dispose();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Girilen Şifreler birbiri ile uyumlu değil. Tekrar Şifreyi girin ...", "Hata", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panelBir.add(bolumJLabel);
        panelBir.add(kullaniciBox);
        panelBir.add(sifreJLabel);
        panelBir.add(sifreField);
        panelBir.add(sifreTekrarJLabel);
        panelBir.add(sifreTekrarField);

        panelIki.add(ileriButton);

        pen3.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        ileriButton.setForeground(Color.blue);

        panelBir.setBorder(BorderFactory.createTitledBorder("Ayarlar"));

        pen3.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pen3.setSize(280, 205);
        pen3.setLocationRelativeTo(null);
        pen3.setVisible(true);
        pen3.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}

