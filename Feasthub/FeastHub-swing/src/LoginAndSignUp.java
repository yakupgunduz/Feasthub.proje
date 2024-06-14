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

public class LoginAndSignUp extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private SharedData sharedData;
    private BufferedImage bgImage;

    public LoginAndSignUp(){
        this.sharedData = SharedData.getInstance();
        setTitle("FeastHub"); // pencereBasligi
        setSize(800,500); // pencereBoyutu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kapatmaButonuBelirtme
        try{
        bgImage = ImageIO.read(new File("src/img/background/backgroundLogin.jpg"));
        } catch (IOException e){
            e.printStackTrace();
        }
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (bgImage != null){
                    g.drawImage(bgImage,0,0,getWidth(),getHeight(),this);
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

        GridBagConstraints passwordForgotConstraints = new GridBagConstraints();
        passwordForgotConstraints.insets = new Insets(10, 10, 10, 10);
        passwordForgotConstraints.gridx = 1;
        passwordForgotConstraints.gridy = 2;
        passwordForgotConstraints.anchor = GridBagConstraints.CENTER;

        GridBagConstraints loginButtonConstraints = new GridBagConstraints();
        loginButtonConstraints.insets = new Insets(10, 10, 10, 10);
        loginButtonConstraints.gridx = 0;
        loginButtonConstraints.gridy = 2;
        loginButtonConstraints.anchor = GridBagConstraints.NORTHEAST;

        GridBagConstraints signUpButtonConstraints = new GridBagConstraints();
        signUpButtonConstraints.insets = new Insets(10, 10, 10, 10);
        signUpButtonConstraints.gridx = 2;
        signUpButtonConstraints.gridy = 2;
        signUpButtonConstraints.anchor = GridBagConstraints.NORTHWEST;

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

        JLabel passwordForgotLabel = new JLabel("Sifremi Unuttum");
        passwordForgotLabel.setForeground(Color.BLUE);
        passwordForgotLabel.setFont(new Font("Consolas",Font.BOLD,15));
        passwordForgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Database database = new Database();

        passwordForgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginAndSignUp.this, "İpucu: "+database.getParolaIpucu(usernameField.getText()));
            }
        });
        panel.add(passwordForgotLabel,passwordForgotConstraints);

        JButton loginButton = new JButton("Giris");
        loginButton.setOpaque(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if(database.validateUser(username,password)){
                    sharedData.setUsername(usernameField.getText());
                    sharedData.setNameSurname(database.getAdSoyadByKullaniciAdi(username));
                    sharedData.setAddress(database.getAdresByKullaniciAdi(username));
                    sharedData.setTelno(database.getTelNoByKullaniciAdi(username));
                    Menu menu = new Menu();
                    dispose();
                    menu.display();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Kullanıcı Adı veya Şİfre Hatalı");
                }
            }
        });
        panel.add(loginButton, loginButtonConstraints);

        JButton signUpButton = new JButton("Kaydol");
        signUpButton.setOpaque(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Acik olan pencereyi kapatir
                new SignUp() {
                    @Override
                    protected Database createDatabase() {
                        return null;
                    }
                }.setVisible(true); // diger sinifin arayüzüne gecer
            }
        });
        panel.add(signUpButton,signUpButtonConstraints);


        add(panel);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}