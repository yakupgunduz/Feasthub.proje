import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    static String dburl = "jdbc:postgresql://localhost:5432/Feasthub";
    static String user = "postgres";
    static String password = "1234";
    Connection connection = null;

    public Database(){
        try{
            connection = DriverManager.getConnection(dburl,user,password);
            System.out.println("Bağlanıldı");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void registerUser(String kullaniciAdi, String sifre, String ipucu, String adSoyad, String adres, String telNo) {
        String sql = "INSERT INTO uyeler (Kullanici_adi, Sifre, ipucu, Ad_Soyad, Adres, Tel_No) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Sorgudaki soru işareti gelen kısımlara indexlerine göre otomatik olarak değerleri atar
            pstmt.setString(1, kullaniciAdi);
            pstmt.setString(2, sifre);
            pstmt.setString(3, ipucu);
            pstmt.setString(4, adSoyad);
            pstmt.setString(5, adres);
            pstmt.setString(6, telNo);
            pstmt.executeUpdate();
            System.out.println("Kayıt başarılı!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public boolean validateUser(String kullaniciAdi, String sifre) {
        String sql = "SELECT Sifre FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Sifre");
                return storedPassword.equals(sifre);
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Kullanıcı doğrulama sırasında bir hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public boolean isKullaniciAdiExists(String kullaniciAdi) {
        String sql = "SELECT kullanici_adi FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Kullanıcı adı mevcutsa true döner
        } catch (SQLException e) {
            System.out.println("Kullanıcı adı kontrolü sırasında bir hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public String getAdSoyadByKullaniciAdi(String kullaniciAdi) {
        String sql = "SELECT Ad_Soyad FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Ad_Soyad");
            } else {
                return null; // Kullanıcı bulunamadı
            }
        } catch (SQLException e) {
            System.out.println("Ad Soyad alınırken bir hata oluştu: " + e.getMessage());
            return null;
        }
    }

    public String getTelNoByKullaniciAdi(String kullaniciAdi) {
        String sql = "SELECT Tel_No FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Tel_No");
            } else {
                return null; // Kullanıcı bulunamadı
            }
        } catch (SQLException e) {
            System.out.println("Tel No alınırken bir hata oluştu: " + e.getMessage());
            return null;
        }
    }

    public String getAdresByKullaniciAdi(String kullaniciAdi) {
        String sql = "SELECT Adres FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Adres");
            } else {
                return null; // Kullanıcı bulunamadı
            }
        } catch (SQLException e) {
            System.out.println("Adres alınırken bir hata oluştu: " + e.getMessage());
            return null;
        }
    }

    public String getParolaIpucu(String kullaniciAdi) {
        String sql = "SELECT Ipucu FROM uyeler WHERE kullanici_adi = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Ipucu");

            } else {
                return null; // Kullanıcı adı bulunamadı
            }
        } catch (SQLException e) {
            System.out.println("İpucu alınırken bir hata oluştu: " + e.getMessage());
            return null;
        }
    }

        public String getUrunAciklama(String urunIsmi) {
        String sql = "SELECT Aciklama FROM Urunler WHERE Isim = ?";
        String aciklama = "";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, urunIsmi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                aciklama = rs.getString("Aciklama");
            }
        } catch (SQLException e) {
            System.out.println("Açıklama alınırken bir hata oluştu: " + e.getMessage());
        }

        return aciklama;
    }

    public List<String> getKampanyaBilgileri() {
        String sql = "SELECT kampanya_kodu, aciklama, indirim_orani FROM Kampanyalar";
        List<String> kampanyaBilgileri = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String kampanyaKodu = rs.getString("kampanya_kodu");
                String aciklama = rs.getString("aciklama");
                String indirimOrani = rs.getString("indirim_orani");
                kampanyaBilgileri.add(kampanyaKodu + " - " + aciklama + " - " + indirimOrani + "%");
            }
        } catch (SQLException e) {
            System.out.println("Kampanya bilgileri alınırken bir hata oluştu: " + e.getMessage());
        }

        return kampanyaBilgileri;
    }

    public Integer getIndirimOrani(String kampanyaKodu) {
        String query = "SELECT indirim_orani FROM kampanyalar WHERE kampanya_kodu = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kampanyaKodu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("indirim_orani");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPromosyonKoduValid(String kampanyaKodu) {
        String query = "SELECT EXISTS (SELECT 1 FROM kampanyalar WHERE kampanya_kodu = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kampanyaKodu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
