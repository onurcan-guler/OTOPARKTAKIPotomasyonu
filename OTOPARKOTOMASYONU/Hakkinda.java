package OTOPARKOTOMASYONU;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hakkinda extends JDialog{
    public Hakkinda() {

        setTitle("Hakkında");

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\OTOPark\\images\\favicon.ico"));

        JPanel panel = new JPanel(new GridLayout(11,1,5,5));

        JLabel hakkinda = new JLabel("Bütün açık ve kapalı otoparklara hitap eden bir otopark yönetim programıdır.");
        JLabel hakkinda2 = new JLabel("Kolay kullanımlı arayüz ile kullanıcıya çabuk öğrenme ve kullanım kolaylığı ");
        JLabel hakkinda3 = new JLabel("avanatjı sağlamaktadır.");
        JLabel hakkinda4 = new JLabel("-Program Özellikleri ;");
        JLabel hakkinda5 = new JLabel("•Plakaya göre araç giriş çıkışı");
        JLabel hakkinda6 = new JLabel("•Abonelik Sistemi, abone ekleme, düzenleme, silme");
        JLabel hakkinda7 = new JLabel("•Abone Listesi");
        JLabel hakkinda8 = new JLabel("•Aracın nerede tutulduğu");
        JLabel hakkinda9 = new JLabel("•Saatte göre ücretlerini belirlenmesi");
        JLabel hakkinda10 = new JLabel("•Park ücretine iç-dış temizlik ücretini ekleyebilme");
        JLabel hakkinda11 = new JLabel("•Personele gözükmemesi gereken bölümler gizli");

        panel.add(hakkinda);
        panel.add(hakkinda2);
        panel.add(hakkinda3);
        panel.add(hakkinda4);
        panel.add(hakkinda5);
        panel.add(hakkinda6);
        panel.add(hakkinda7);
        panel.add(hakkinda8);
        panel.add(hakkinda9);
        panel.add(hakkinda10);
        panel.add(hakkinda11);

        panel.setBorder(BorderFactory.createTitledBorder("Bilgilendirme"));

        panel.setBackground(Color.CYAN);

        add(panel);
        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setSize(460, 295);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
}
