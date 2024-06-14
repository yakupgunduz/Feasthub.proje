import javax.imageio.ImageIO;
import javax.swing.*;
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

public class Dessert extends JFrame {
    JMenuBar menuBar;
    JMenu urunler;
    JMenuItem yemekler, atistirmaliklar, menuler, icecekler;
    JLabel totalPriceLabel;
    private SharedData sharedData;
    private BufferedImage bgImage;

    public Dessert() {
        this.sharedData = SharedData.getInstance();
        setTitle("FeastHub"); // Pencere başlığı
        setSize(850, 500); // Pencere boyutu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma butonu davranışı

        try {
            bgImage = ImageIO.read(new File("src/img/background/menuTatlilar.jpg"));
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
        urunler = new JMenu("Ürünler");

        yemekler = new JMenuItem("Yemekler");
        yemekler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Food().setVisible(true);
            }
        });

        atistirmaliklar = new JMenuItem("Atıştırmalıklar");
        atistirmaliklar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Snack().setVisible(true);
            }
        });

        menuler = new JMenuItem("Menüler");
        menuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Menu().setVisible(true);
            }
        });

        icecekler = new JMenuItem("İçecekler");
        icecekler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Drink().setVisible(true);
            }
        });

        urunler.add(yemekler);
        urunler.add(atistirmaliklar);
        urunler.add(menuler);
        urunler.add(icecekler);

        menuBar.add(urunler);

        // Promosyon Kodları menüsü
        JLabel promosyonKodlariLabel = new JLabel("  Promosyon Kodları");
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

        addMenuItem(menuPanel, "src/img/dessert/d1.png", "Künefe", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d2.png", "Cheesecake", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d3.png", "Trileçe", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d4.png", "Tiramisu", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d5.png", "Magnolya", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d6.png", "Mozaik Pasta", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d7.png", "Brownie", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d8.png", "Supangle", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d9.png", "Baklava", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d10.png", "Kadayıf", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d11.png", "Dondurma", 10.50);
        addMenuItem(menuPanel, "src/img/dessert/d12.png", "Sütlaç", 10.50);

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.setOpaque(false); // Scroll panelin arka planının görünmesini sağlar
        scrollPane.getViewport().setOpaque(false); // Scroll panelin görünüm alanının arka planının görünmesini sağlar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        verticalScrollBar.setUnitIncrement(10);

        mainPanel.setLayout(new BorderLayout()); // Layout belirle
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(mainPanel); // mainPanel pencereye eklenmeli

        setLocationRelativeTo(null);
    }

    private void addMenuItem(JPanel panel, String imagePath, String itemName, double price) {
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

    public void display() {
        setVisible(true);
        setResizable(false);
    }
}
