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
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class AboneBul extends JDialog{

    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/otopark?useUnicode=true&characterEncoding=UTF-8";
    String kullanici = "root";
    String sifre = "";

    public AboneBul() throws ParseException {

        setTitle("Abone Bul");

        JPanel panelAna = new JPanel(new FlowLayout());
        JPanel panelBir = new JPanel(new FlowLayout());
        JPanel panelIki = new JPanel(new FlowLayout());


        MaskFormatter tcFormatter = new MaskFormatter("###########");
        final JFormattedTextField tcNoField = new JFormattedTextField(tcFormatter);
        tcNoField.setColumns(12);
        tcFormatter.setPlaceholderCharacter('_');

        JButton bulButton = new JButton("Bul");


        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));


        bulButton.setMnemonic(KeyEvent.VK_B);

        panelBir.add(tcNoField);

        panelIki.add(bulButton);


        panelAna.setBackground(Color.CYAN);
        panelBir.setBackground(Color.CYAN);
        panelIki.setBackground(Color.CYAN);


        bulButton.setForeground(Color.BLUE);

     
        tcNoField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                boolean var = false;

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"'");

                    if (rs.next())
                        var = true;

                    else
                        var = false;


                    if (var==true) {
                        dispose();
                        Bul(tcNoField.getText());
                    }

                    else
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);

                    sorgu.close();

                } catch (Exception e) {
                    System.out.println(e);
                }



            }
        });



        bulButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                boolean var = false;

                try {
                    Class.forName(driver);
                    Connection baglan = DriverManager.getConnection(adres,kullanici,sifre);
                    Statement sorgu = baglan.createStatement();
                    ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcNoField.getText()+"'");

                    if (rs.next())
                        var = true;

                    else
                        var = false;


                    if (var==true) {
                        dispose();
                        Bul(tcNoField.getText());
                    }

                    else
                        JOptionPane.showMessageDialog(null, "Abone Bulunamadı","Uyarı",JOptionPane.WARNING_MESSAGE);//uyarı penceresi

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


    final DefaultTableModel model = new DefaultTableModel();

    public void Bul(final String tcAl){
        final JDialog pencere = new JDialog();
        pencere.setTitle("Abone Bilgileri");


        pencere.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panel = new JPanel(new FlowLayout());
        JPanel panelAna = new JPanel(new GridLayout(6,2,5,5));

        JLabel noJLabel = new JLabel("No : ");
        Label noLabel = new Label();
        JLabel tcJLabel = new JLabel("T.C. Kimlik : ");
        Label tcLabel = new Label();
        JLabel adJLabel = new JLabel("Adı : ");
        Label adLabel = new Label();
        JLabel soyadJLabel = new JLabel("Soyadı : ");
        Label soyadLabel = new Label();
        JLabel telJLabel = new JLabel("Tel : ");
        Label telLabel = new Label();
        JLabel notJLabel = new JLabel("Not : ");
        Label notLabel = new Label();


        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from abone where tcNo='"+tcAl+"'");

            if (rs.next()) {
                noLabel.setText(rs.getString(1));
                tcLabel.setText(rs.getString(2));
                adLabel.setText(rs.getString(3));
                soyadLabel.setText(rs.getString(4));
                telLabel.setText(rs.getString(5));
                notLabel.setText(rs.getString(6));
            }

            sorgu.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        panelAna.add(noJLabel);
        panelAna.add(noLabel);
        panelAna.add(tcJLabel);
        panelAna.add(tcLabel);
        panelAna.add(adJLabel);
        panelAna.add(adLabel);
        panelAna.add(soyadJLabel);
        panelAna.add(soyadLabel);
        panelAna.add(telJLabel);
        panelAna.add(telLabel);
        panelAna.add(notJLabel);
        panelAna.add(notLabel);

        panel.add(panelAna);
        pencere.add(panel);


        panelAna.setBackground(Color.CYAN);
        panel.setBackground(Color.CYAN);

        panelAna.setBorder(BorderFactory.createTitledBorder("Bilgiler"));

        pencere.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        pencere.setSize(310, 240);
        pencere.setLocationRelativeTo(null);
        pencere.setVisible(true);
        pencere.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
}