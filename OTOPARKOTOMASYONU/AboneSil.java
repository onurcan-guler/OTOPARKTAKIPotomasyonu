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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

public class AboneSil extends JDialog {

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark";
    String kullanici = "root";
    String sifre = "";

    public AboneSil() throws ParseException {

        setTitle("Abone Sil");//pencere başlığı

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        MaskFormatter tcFormatter = new MaskFormatter("###########");
        final JFormattedTextField tcNoField = new JFormattedTextField(tcFormatter);
        tcNoField.setColumns(12);
        tcFormatter.setPlaceholderCharacter('_');

        JButton silButton = new JButton("Sil");

        panelBir.add(tcNoField);

        panelIki.add(silButton);

        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);

        silButton.setForeground(Color.red);

        silButton.setMnemonic(KeyEvent.VK_S);
        tcNoField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                boolean var = false;

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='" + tcNoField.getText() + "'");
                    if (rs.next())
                        var = true;
                    else
                        var = false;

                    if (var==true) {
                        dispose();
                        Sil(tcNoField.getText());
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);//uyarı penceresi

                    sorgu.close();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        silButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                boolean var = false;

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='" + tcNoField.getText() + "'");

                    if (rs.next())
                        var = true;
                    else
                        var = false;

                    if (var==true) {
                        dispose();
                        Sil(tcNoField.getText());
                    }

                    else
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);

                    sorgu.close();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });
        panelBir.setBorder(BorderFactory.createTitledBorder("T.C. Kimlik No"));
        panelIki.setBorder(BorderFactory.createTitledBorder("İşlem"));

        add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(255, 110);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    public void Sil(final String tcAl) {
        final JDialog pencere = new JDialog();
        pencere.setTitle("Abone Silinsin mi?");


        pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panelAna = new JPanel(new GridLayout(2, 1));
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());

        JLabel adiJLabel = new JLabel();
        JLabel soyadiJLabel = new JLabel();

        JButton evetButton = new JButton("Evet");
        JButton hayirButton = new JButton("Hayır");

        evetButton.setMnemonic(KeyEvent.VK_E);
        hayirButton.setMnemonic(KeyEvent.VK_H);

        adiJLabel.setForeground(new Color(255, 0, 0));
        soyadiJLabel.setForeground(new Color(255, 0, 0));
        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);
        hayirButton.setForeground(Color.BLUE);
        evetButton.setForeground(Color.RED);

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='" + tcAl + "'");
            if (rs.next()) {
                adiJLabel.setText(rs.getString(3));
                soyadiJLabel.setText(rs.getString(4));
            }

            sorgu.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        panelBir.add(adiJLabel);
        panelBir.add(soyadiJLabel);
        panelIki.add(evetButton);
        panelIki.add(hayirButton);
        evetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici, sifre);
                    Statement sorgu = baglan.createStatement();
                    sorgu.executeUpdate("delete from abone where tcNo='" + tcAl + "'");//veritabanından abone silinir
                    sorgu.close();
                    JOptionPane.showMessageDialog(null, "Abone Silindi ...", "Başarılı", JOptionPane.PLAIN_MESSAGE);//uyarı penceresi
                    pencere.dispose();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        hayirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pencere.dispose();
            }
        });

        pencere.add(panelAna);
        panelAna.add(panelBir);
        panelAna.add(panelIki);
        panelBir.setBorder(BorderFactory.createTitledBorder("Abone"));
        panelIki.setBorder(BorderFactory.createTitledBorder("İşlem"));
        pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pencere.setSize(240, 160);
        pencere.setLocationRelativeTo(null);
        pencere.setVisible(true);
        pencere.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}
