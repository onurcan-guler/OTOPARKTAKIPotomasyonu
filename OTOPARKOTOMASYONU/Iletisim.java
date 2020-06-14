package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Iletisim extends JDialog{
    public Iletisim() {

        setTitle("İletişim");

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panel = new JPanel(new FlowLayout());

        JLabel yazilimciLabel = new JLabel("SDRL Yazılım -");
        JLabel kisilerLabel = new JLabel("Onur ASLAN");
        JLabel egitimLabel = new JLabel("Hassa MYO - Bilgisayar Teknolojisi II");
        JLabel iletisimEmailLabel = new JLabel("E-mail: onuraslan07@gmail.com    ");
        JLabel webLabel = new JLabel("Web: www.aslanakvaryum.com            ");
        JLabel iletisimTelLabel = new JLabel("Tel: 0 538 488 82 46");

        panel.add(yazilimciLabel);
        panel.add(kisilerLabel);
        panel.add(egitimLabel);
        panel.add(iletisimEmailLabel);
        panel.add(webLabel);
        panel.add(iletisimTelLabel);

        panel.setBackground(Color.CYAN);
        add(panel);
        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
}