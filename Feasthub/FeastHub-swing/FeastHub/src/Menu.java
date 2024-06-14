import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Menu extends JFrame {

    JMenuBar menuBar;
    JMenu urunler;
    JMenuItem yemekler, atistirmaliklar, tatlilar, icecekler;
    JLabel totalPriceLabel;
    JLabel promosyonKodlariLabel;
    private SharedData sharedData;
    private BufferedImage bgImage;

    public Menu() {
        this.sharedData = SharedData.getInstance(); // Singleton Sınıfı Nesnesi Cagirilir
        setTitle("FeastHub"); // Pencere başlığı
        setSize(850, 500); // Pencere boyutu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma butonu davranışı

        try {
            bgImage = ImageIO.read(new File("src/img/background/menuMenuler.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            // Hata Durumunda: Düzeltme mesajı yazdırın veya varsayılan bir resim kullanın
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        menuBar = new JMenuBar();

        // Ürünler menüsü
        urunler = new JMenu("Ürünler  ");
        urunler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        yemekler = new JMenuItem("Yemekler");
        yemekler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Food food = new Food();
                food.display();
            }
        });

        atistirmaliklar = new JMenuItem("Atıştırmalıklar");
        atistirmaliklar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Snack snack = new Snack();
                snack.display();
            }
        });

        tatlilar = new JMenuItem("Tatlılar");
        tatlilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dessert dessert = new Dessert();
                dessert.display();
            }
        });

        icecekler = new JMenuItem("İçecekler");
        icecekler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Drink drink = new Drink();
                drink.display();
            }
        });

        urunler.add(yemekler);
        urunler.add(atistirmaliklar);
        urunler.add(tatlilar);
        urunler.add(icecekler);

        menuBar.add(urunler);

        // Promosyon Kodları menüsü
        promosyonKodlariLabel = new JLabel("  Promosyon Kodları");
        promosyonKodlariLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        promosyonKodlariLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame promosyonFrame = new JFrame();
                promosyonFrame.setTitle("Promosyon Kodları");
                promosyonFrame.setResizable(false);
                promosyonFrame.setSize(450, 300);

                JPanel promosyonPanel = new JPanel();
                promosyonPanel.setLayout(new BoxLayout(promosyonPanel, BoxLayout.Y_AXIS));

                // Burada veritabanından promosyon kodlarını çekip bir ArrayList'e atıyoruz.

                Database database = new Database();
                ArrayList<String> promosyonKodlariBilgileri = (ArrayList<String>) database.getKampanyaBilgileri();

                for (String kod : promosyonKodlariBilgileri){
                    JLabel kodLabel = new JLabel(kod+" ");
                    promosyonPanel.add(kodLabel);
                }

                JScrollPane promosyonScrollPane = new JScrollPane(promosyonPanel);
                promosyonFrame.add(promosyonScrollPane);
                promosyonFrame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
                promosyonFrame.setVisible(true);
            }
        });

        menuBar.add(promosyonKodlariLabel);

        totalPriceLabel = new JLabel("Tutar: ₺" + sharedData.getTotalPrice() + "  ");
        totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Sağa hizala
        totalPriceLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Farenin üzerine gelince el işareti göster
        totalPriceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new OrderDetails().setVisible(true);
            }
        });

        JPanel pricePanel = new JPanel(new BorderLayout()); // Tutarı içeren panel
        pricePanel.add(Box.createHorizontalStrut(30), BorderLayout.LINE_START); // Sağa yaslanmasını sağlamak için 30 piksel boşluk ekle
        pricePanel.add(totalPriceLabel, BorderLayout.CENTER); // Tutar labelini panele ekle

        menuBar.add(pricePanel); // Tutarı içeren paneli menü çubuğuna ekle

        setJMenuBar(menuBar);

        JPanel menuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        menuPanel.setOpaque(false); // Arkaplanın üzerine gelmesini sağlar

        addMenuItem(menuPanel, "src/img/menu/m1.png", "Köfte+Tavuk Burger ", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m2.png", "Double Köfte + Tavuk Burger", 12.50);
        addMenuItem(menuPanel, "src/img/menu/m3.png", "Double Çedarlı Köfte Burger", 18.50);
        addMenuItem(menuPanel, "src/img/menu/m4.png", "Çıtır Kova", 12.50);
        addMenuItem(menuPanel, "src/img/menu/m5.png", "Triple Burger", 14.50);
        addMenuItem(menuPanel, "src/img/menu/m6.png", "Double Cheeseburger", 13.50);
        addMenuItem(menuPanel, "src/img/menu/m7.png", "İkili Pizza Menü", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m8.png", "Hatay Usulü Tavuk Dürüm", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m9.png", "Eko Çıtır", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m10.png", "Eko Tavuk", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m11.png", "İkili Fırsat Çıtır", 10.50);
        addMenuItem(menuPanel, "src/img/menu/m12.png", "Çıtır Kanat", 10.50);

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar(); // dikey konumdaki scroll bar nesensi oluşturduk üzerinden özellikler ekleyebilmek için
        scrollPane.setOpaque(false); // Scroll panelin arka planının görünmesini sağlar
        scrollPane.getViewport().setOpaque(false); // Scroll panelin görünüm alanının arka planının görünmesini sağlar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        verticalScrollBar.setUnitIncrement(10); // scroll barın hızını ayarladık


        mainPanel.setLayout(new BorderLayout()); // Layout belirle
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(mainPanel); // mainPanel pencereye eklenmeli

        setLocationRelativeTo(null);
    }

    private void addMenuItem(JPanel panel, String imagePath,String itemName, double price) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            JLabel nameLabel = new JLabel(itemName);
            JLabel priceLabel = new JLabel("₺" + price);
            JButton addButton = new JButton("+");

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(imageLabel, BorderLayout.CENTER);

            Database database = new Database();
            // HTML kullanarak Hint bileşeninin genişliğini ayarladık çünkü mevcut kütüphanede böyle bir ayar yok.
            imageLabel.setToolTipText("<html><p style='width:75px; font-family: Consolas; font-weight: bold;'>"+database.getUrunAciklama(itemName)+"</p></html>");

            // Ürün adı ve fiyatını içeren alt panel
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alt panelin düzeni
            bottomPanel.setOpaque(false); // Arkaplanın üzerine gelmesini sağlar
            bottomPanel.add(nameLabel); // Ürün adını alt panele ekler
            bottomPanel.add(priceLabel); // Fiyatı alt panele ekler
            bottomPanel.add(addButton); // Butonu alt panele ekler

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Butona tıklandığında fiyatı artır
                    sharedData.addToTotalPrice(price);
                    sharedData.addProduct(itemName,price);
                    totalPriceLabel.setText("Tutar: ₺" + sharedData.getTotalPrice() + "  ");
                    System.out.println(nameLabel.getText() + " " + priceLabel.getText());
                    System.out.println(sharedData.getAddedProductMap());

                }
            });

            itemPanel.add(bottomPanel, BorderLayout.SOUTH);

            itemPanel.setOpaque(false); // Arkaplanın üzerine gelmesini sağlar
            panel.add(itemPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTotalPriceLabel(Double totalPrice){
        totalPriceLabel.setText(totalPrice.toString());
    }

    public void display() {
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.display();
    }
}