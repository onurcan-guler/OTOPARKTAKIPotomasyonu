package OTOPARKOTOMASYONU;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AboneListesi extends JDialog{

    //veritabanına bağlanmak için sabit değerler
    String driver = "com.mysql.jdbc.Driver";
    String adres = "jdbc:mysql://localhost/OtoPark";
    String kullanici = "root";
    String sifre = "";

    final DefaultTableModel model = new DefaultTableModel();

    public AboneListesi() {
        setTitle("Abone Listesi");

        JPanel panelAna = new JPanel(new GridLayout());

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        final JTable tablo = new JTable(model);

        model.addColumn("TC Kimlik No");
        model.addColumn("Adı");
        model.addColumn("Soyadı");
        model.addColumn("Telefon");
        model.addColumn("Ek Not");
        model.addColumn("Başlama Tarihi");
        model.addColumn("Bitiş Tarihi");
        model.addColumn("Durum");


        JScrollPane pane = new JScrollPane(tablo);

        try {
            Class.forName(driver);
            Connection baglan = DriverManager.getConnection(adres, kullanici,sifre);
            Statement sorgu = baglan.createStatement();
            ResultSet rs = sorgu.executeQuery("select * from abone");

            while (rs.next()) {
                model.addRow(new Object[] { rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7),rs.getDate(8),rs.getInt(9) });
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        tablo.setAutoCreateRowSorter(true);


        panelAna.setBorder(BorderFactory.createTitledBorder("Bilgiler"));


        panelAna.setBackground(Color.CYAN);

        add(panelAna);
        panelAna.add(pane);

        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(800, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

}