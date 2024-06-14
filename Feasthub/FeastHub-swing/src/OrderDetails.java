import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class OrderDetails extends JFrame {
    private SharedData sharedData;
    private JPanel mainPanel; // Ana paneli sınıf düzeyinde tanımladık
    private JPanel payPanel; // Ödeme panelini de sınıf düzeyinde tanımladık

    Database database = new Database();
    String adSoyad;
    String telNo;
    String adres;


    public OrderDetails() {
        this.sharedData = SharedData.getInstance();
        this.adSoyad = database.getAdSoyadByKullaniciAdi(sharedData.getUsername());
        this.telNo = database.getTelNoByKullaniciAdi(sharedData.getUsername());
        this.adres = database.getAdresByKullaniciAdi(sharedData.getUsername());
        setTitle("Sipariş Detayları");
        setSize(350, 400);
        setUndecorated(true);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        for (Map.Entry<String, Double> entry : sharedData.getAddedProductMap().entrySet()) {
            textArea.append(entry.getKey() + ": " + entry.getValue() + "₺\n");
        }


        JScrollPane scrollPane = new JScrollPane(textArea);

        // Alt panel oluşturduk
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // Dikey hizaladık

        JLabel totalLabel = new JLabel("Toplam Tutar: " + sharedData.getTotalPrice() + "₺");
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortaya hizaladık

        // Ödeme panelini oluşturduk
        payPanel = new JPanel(new BorderLayout());
        payPanel.setLayout(new BoxLayout(payPanel, BoxLayout.Y_AXIS)); // Dikey hizaladık

        // payPanel için üstten 20 piksel boşluk ekleyen bir boş kenarlık oluşturduk
        payPanel.setBorder(new EmptyBorder(10, 0, 50, 0));

        JLabel paymentMethodLabel = new JLabel("Ödeme Yöntemi");
        paymentMethodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        payPanel.add(paymentMethodLabel);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Sola hizalı FlowLayout kullandık

        JRadioButton rb1 = new JRadioButton("Nakit");
        JRadioButton rb2 = new JRadioButton("Kredi/Banka Kartı");

        // Radio button'ları bir ButtonGroup'a ekledik
        ButtonGroup paymentMethodsGroup = new ButtonGroup();
        paymentMethodsGroup.add(rb1);
        paymentMethodsGroup.add(rb2);

        radioPanel.add(rb1);
        radioPanel.add(rb2);
        payPanel.add(radioPanel);

        JTextPane kvkkTextPane = new JTextPane();
        kvkkTextPane.setContentType("text/html");
        kvkkTextPane.setEditable(false);
        kvkkTextPane.setText("<html>"
                + "<p style='font-family: Arial; font-weight: bold; color: red;'>KVKK Uyarısı:</p>"
                + "<p style='font-style: italic; font-weight: 500;'>"
                + "Kişisel verilerinizin gizliliği bizim için <span style='color: blue;'>son derece önemlidir.</span><br>"
                + "Bu nedenle, yemek siparişi sırasında verdiğiniz kişisel bilgilerinizi gizli tutuyoruz.<br>"
                + "Sadece siparişinizi işlemek ve size hizmet sunmak amacıyla kullanıyoruz.<br>"
                + "Kişisel verilerinizi hiçbir koşulda üçüncü şahıslarla paylaşmıyoruz<br>"
                + "ve sadece belirtilen amaçlar doğrultusunda kullanıyoruz.<br>"
                + "Veri güvenliğiniz için gerekli önlemleri alıyor<br>"
                + "ve verilerinizi koruma altına alıyoruz.<br>"
                + "Herhangi bir konuda soru veya endişeniz varsa,<br>"
                + "lütfen bizimle iletişime geçmekten çekinmeyin."
                + "</p></html>");

        JLabel kvkkText = new JLabel("<html><u>Kvkk metnini okudum, onaylıyorum.</u></html>");
        kvkkText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kvkkText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // KVKK metni içeren JTextArea'nın bulunduğu yeni bir pencere aç
                JFrame kvkkFrame = new JFrame();
                kvkkFrame.setTitle("KVKK Metni");
                kvkkFrame.setResizable(false);
                kvkkFrame.setSize(450, 300);

                JPanel kvkkFramePanel = new JPanel(new BorderLayout());
                kvkkFramePanel.add(new JScrollPane(kvkkTextPane), BorderLayout.CENTER);

                kvkkFrame.add(kvkkFramePanel);
                kvkkFrame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
                kvkkFrame.setVisible(true);
            }
        });

        JPanel kvkkPanel = new JPanel();
        JRadioButton rb3 = new JRadioButton();
        kvkkPanel.add(rb3,BorderLayout.WEST);
        kvkkPanel.add(kvkkText,BorderLayout.EAST);
        payPanel.add(kvkkPanel);

        JLabel totalLabelPayPanel = new JLabel("Toplam Tutar: " + sharedData.getTotalPrice() + "₺");
        totalLabelPayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        payPanel.add(totalLabelPayPanel);

        JPanel promosyonPanel = new JPanel();
        JLabel promoKodLabel = new JLabel("<html><p style='font-family: Arial; font-weight: bold;'>Promosyon Kodu :</p></html>");
        JTextField promokodField = new JTextField();
        JButton confrimPromoButton = new JButton("Uygula");
        confrimPromoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(database.isPromosyonKoduValid(promokodField.getText())){
                    int indirimOrani = database.getIndirimOrani(promokodField.getText());
                    double indirimliFiyat = sharedData.getTotalPrice() - ((sharedData.getTotalPrice()*indirimOrani)/100);
                    sharedData.setTotalPrice(indirimliFiyat);
                    JOptionPane.showMessageDialog(null,"%"+indirimOrani+" İndirim Uygulandı");
                    totalLabelPayPanel.setText("Toplam Tutar: "+ sharedData.getTotalPrice() + "₺");
                    payPanel.revalidate();
                    payPanel.repaint();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Girilen indirim kodu tanımsız");
                }

            }
        });
        promokodField.setPreferredSize(new Dimension(75,20));
        promosyonPanel.add(promoKodLabel,BorderLayout.WEST);
        promosyonPanel.add(promokodField,BorderLayout.CENTER);
        promosyonPanel.add(confrimPromoButton,BorderLayout.EAST);
        payPanel.add(promosyonPanel);

        JButton confirmButton = new JButton("Onayla");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rb3.isSelected()){
                    if (rb1.isSelected()) {
                        System.out.println("Ödeme Nakit Olacak");
                        JOptionPane.showMessageDialog(null, "Ödeme Nakit Olacak");
                        JOptionPane.showMessageDialog(null,urunlerVeToplamTutar());
                        sharedData.setTotalPrice(0.0);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    } else if (rb2.isSelected()) {
                        System.out.println("Ödeme Kart ile Olacak");
                        JOptionPane.showMessageDialog(null, "Ödeme Kart Olacak");
                        JOptionPane.showMessageDialog(null,urunlerVeToplamTutar());
                        sharedData.setTotalPrice(0.0);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ödeme Yöntemlerinden Birini Seçin");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Kvkk metnini okuyup, onaylayın.");
                }
            }
        });
        payPanel.add(confirmButton);

        JButton payButton = new JButton("Öde");
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortaya hizala
        payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ödeme işlemleri burada yapılacak
                getContentPane().removeAll();
                getContentPane().add(payPanel);
                setSize(300, 200);
                revalidate();
                repaint();
                setLocationRelativeTo(null); // Pencereyi ekranın ortasına yeniden konumlandır
            }
        });

        JButton continueButton = new JButton("Devam Et");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortaya hizala
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        bottomPanel.add(totalLabel);
        bottomPanel.add(Box.createVerticalStrut(10)); // 10 piksel boşluk
        bottomPanel.add(payButton);
        bottomPanel.add(Box.createVerticalStrut(5)); // 5 piksel boşluk
        bottomPanel.add(continueButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER); // JTextArea'yı merkezi alana ekledik
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Alt paneli alt alana ekledik

        add(mainPanel); // Ana paneli pencereye ekledik
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public String urunlerVeToplamTutar() {
        StringBuilder message = new StringBuilder();
        message.append("Ürünler:\n");
        for (Map.Entry<String, Double> entry : sharedData.getAddedProductMap().entrySet()) {
            message.append(entry.getKey()).append(": ").append(entry.getValue()).append("₺\n");
        }
        message.append("==================================================\n");
        message.append("Toplam Tutar: ").append(sharedData.getTotalPrice()).append("₺\n");
        message.append("Ad Soyad: ").append(adSoyad).append("\n");
        message.append("Telefon Numarası: ").append(telNo).append("\n");
        message.append("Adres: ").append(adres).append("\n");
        return message.toString();
    }

}