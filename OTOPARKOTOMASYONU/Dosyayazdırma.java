package OTOPARKOTOMASYONU;


import java.io.*;

public class Dosyayazdırma {
    public static void main(String[] args) throws IOException {
        String str = "id:1,kadi:oto44,sifre,444444,adi:Onurcan,soyadi:GÜLER,tc:44444444444,tel:444 232344,araba rengi:turuncu,araba modeli:BMW,giriş saati:10.00,çıkış saati:11.00,araç giriş tarihi:04.04.2004";

        File file = new File("OTOPARKTAKİP.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write(str);
        bWriter.close();


        FileReader fileReader = new FileReader(file);
        String line;

        BufferedReader br = new BufferedReader(fileReader);

        while ((line = br.readLine()) != null) {

            System.out.println(line);

        }

        br.close();


    }
}