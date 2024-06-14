import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SignUp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField forgotPasswordField;
    private JTextField nameSurnameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private SharedData sharedData;
    private BufferedImage bgImage;

    public SignUp() {
        this.sharedData = SharedData.getInstance();
        setTitle("FeastHub"); // pencereBasligi
        setSize(800, 500); // pencereBoyutu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kapatmaButonuBelirtme
        try {
            bgImage = ImageIO.read(new File("src/img/background/backgroundLogin.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new GridBagLayout());

        GridBagConstraints usernameLabelConstraints = new GridBagConstraints();
        usernameLabelConstraints.insets = new Insets(10,10,10,10);
        usernameLabelConstraints.gridx = 0;
        usernameLabelConstraints.gridy = 0;
        usernameLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints usernameFieldConstraints = new GridBagConstraints();
        usernameFieldConstraints.insets = new Insets(10, 10, 10, 10);
        usernameFieldConstraints.gridx = 1;
        usernameFieldConstraints.gridy = 0;
        usernameFieldConstraints.anchor = GridBagConstraints.SOUTHWEST;

        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.insets = new Insets(10, 10, 10, 10);
        passwordLabelConstraints.gridx = 0;
        passwordLabelConstraints.gridy = 1;
        passwordLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints passwordFieldConstraints = new GridBagConstraints();
        passwordFieldConstraints.insets = new Insets(10, 10, 10, 10);
        passwordFieldConstraints.gridx = 1;
        passwordFieldConstraints.gridy = 1;
        passwordFieldConstraints.anchor = GridBagConstraints.SOUTHWEST;

        GridBagConstraints passwordForgotLabelConstraints = new GridBagConstraints();
        passwordForgotLabelConstraints.insets = new Insets(10, 10, 10, 10);
        passwordForgotLabelConstraints.gridx = 0;
        passwordForgotLabelConstraints.gridy = 2;
        passwordForgotLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints passwordForgotFieldConstraints = new GridBagConstraints();
        passwordForgotFieldConstraints.insets = new Insets(10, 10, 10, 10);
        passwordForgotFieldConstraints.gridx = 1;
        passwordForgotFieldConstraints.gridy = 2;
        passwordForgotFieldConstraints.anchor = GridBagConstraints.SOUTHWEST;

        GridBagConstraints signUpButtonConstraints  = new GridBagConstraints();
        signUpButtonConstraints.insets = new Insets(10,10,10,10);
        signUpButtonConstraints.gridx = 1;
        signUpButtonConstraints.gridy = 6;
        signUpButtonConstraints.anchor = GridBagConstraints.CENTER;

        GridBagConstraints nameSurnameFieldConstraints = new GridBagConstraints();
        nameSurnameFieldConstraints.insets = new Insets(10,10,10,10);
        nameSurnameFieldConstraints.gridx = 1;
        nameSurnameFieldConstraints.gridy = 3;
        nameSurnameFieldConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints nameSurnameLabelConstraints = new GridBagConstraints();
        nameSurnameLabelConstraints.insets = new Insets(10,10,10,10);
        nameSurnameLabelConstraints.gridx = 0;
        nameSurnameLabelConstraints.gridy = 3;
        nameSurnameLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints addressFieldConstraints = new GridBagConstraints();
        addressFieldConstraints.insets = new Insets(10,10,10,10);
        addressFieldConstraints.gridx = 1;
        addressFieldConstraints.gridy = 4;
        addressFieldConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints addressLabelConstraints = new GridBagConstraints();
        addressLabelConstraints.insets = new Insets(10,10,10,10);
        addressLabelConstraints.gridx = 0;
        addressLabelConstraints.gridy = 4;
        addressLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints phoneNumberFieldConstraints = new GridBagConstraints();
        phoneNumberFieldConstraints.insets = new Insets(10,10,10,10);
        phoneNumberFieldConstraints.gridx = 1;
        phoneNumberFieldConstraints.gridy = 5;
        phoneNumberFieldConstraints.anchor = GridBagConstraints.SOUTHEAST;

        GridBagConstraints phoneNumberLabelConstraints = new GridBagConstraints();
        phoneNumberLabelConstraints.insets = new Insets(10,10,10,10);
        phoneNumberLabelConstraints.gridx = 0;
        phoneNumberLabelConstraints.gridy = 5;
        phoneNumberLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;

        JLabel nameSurnameLabel = new JLabel("Ad-Soyad:");
        nameSurnameLabel.setForeground(Color.DARK_GRAY);
        nameSurnameLabel.setFont(new Font("Consolas",Font.BOLD,15));
        panel.add(nameSurnameLabel, nameSurnameLabelConstraints);

        nameSurnameField = new JTextField();
        nameSurnameField.setOpaque(false);
        nameSurnameField.setForeground(Color.DARK_GRAY);
        nameSurnameField.setPreferredSize(new Dimension(150,20));
        nameSurnameField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
        panel.add(nameSurnameField,nameSurnameFieldConstraints);

        JLabel usernameLabel = new JLabel("Kullanici Adi:");
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setFont(new Font("Consolas",Font.BOLD,15));
        panel.add(usernameLabel, usernameLabelConstraints);

        usernameField = new JTextField();
        usernameField.setOpaque(false);
        usernameField.setForeground(Color.DARK_GRAY);
        usernameField.setPreferredSize(new Dimension(150, 20));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
        panel.add(usernameField, usernameFieldConstraints);

        JLabel passwordLabel = new JLabel("Sifre:");
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setFont(new Font("Consolas",Font.BOLD,15));
        panel.add(passwordLabel, passwordLabelConstraints);

        passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setForeground(Color.DARK_GRAY);
        passwordField.setPreferredSize(new Dimension(150, 20));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
        panel.add(passwordField, passwordFieldConstraints);

        JLabel forgotPasswordLabel = new JLabel("İpucu:");
        forgotPasswordLabel.setForeground((Color.DARK_GRAY));
        forgotPasswordLabel.setFont(new Font("consolas",Font.BOLD,15));
        panel.add(forgotPasswordLabel,passwordForgotLabelConstraints);

        forgotPasswordField = new JTextField();
        forgotPasswordField.setOpaque(false);
        forgotPasswordField.setForeground(Color.DARK_GRAY);
        forgotPasswordField.setPreferredSize(new Dimension(150,20));
        forgotPasswordField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
        panel.add(forgotPasswordField,passwordForgotFieldConstraints);

        JLabel addressLabel = new JLabel("Adres");
        addressLabel.setForeground(Color.DARK_GRAY);
        addressLabel.setFont(new Font("Consolas",Font.BOLD,15));
        panel.add(addressLabel,addressLabelConstraints);

        addressField = new JTextField();
        addressField.setOpaque(false);
        addressField.setForeground(Color.DARK_GRAY);
        addressField.setPreferredSize(new Dimension(150,20));
        addressField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2,true));
        panel.add(addressField,addressFieldConstraints);

        JLabel phoneNumberLabel = new JLabel("Tel-No");
        phoneNumberLabel.setForeground(Color.DARK_GRAY);
        phoneNumberLabel.setFont(new Font("Consolas",Font.BOLD,15));
        panel.add(phoneNumberLabel,phoneNumberLabelConstraints);

        phoneNumberField = new JTextField();
        phoneNumberField.setOpaque(false);
        phoneNumberField.setForeground(Color.DARK_GRAY);
        phoneNumberField.setPreferredSize(new Dimension(150,20));
        phoneNumberField.setBorder((BorderFactory.createLineBorder(Color.DARK_GRAY,2,true)));
        panel.add(phoneNumberField,phoneNumberFieldConstraints);

        Database database = new Database();

        JButton signUpButton = new JButton("Kaydol");
        signUpButton.setOpaque(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (database.isKullaniciAdiExists(usernameField.getText())) {
                    JOptionPane.showMessageDialog(SignUp.this, "Bu kullanıcı adı zaten mevcut!");
                } else {
                    // Kullanıcı adı mevcut değilse, kullanıcıyı kaydet
                    String kullaniciAdi = usernameField.getText();
                    String sifre = new String(passwordField.getPassword());
                    String ipucu = forgotPasswordField.getText();
                    String adSoyad = nameSurnameField.getText();
                    String adres = addressField.getText();
                    String telNo = phoneNumberField.getText();
                    database.registerUser(kullaniciAdi, sifre, ipucu, adSoyad, adres, telNo);
                    JOptionPane.showMessageDialog(SignUp.this, "Kayıt başarılı!");
                    sharedData.setUsername(kullaniciAdi);
                    dispose();
                    new Menu().setVisible(true);
                }
            }
        });

        panel.add(signUpButton,signUpButtonConstraints);
        add(panel);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected abstract Database createDatabase();
}