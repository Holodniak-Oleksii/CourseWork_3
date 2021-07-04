package fight;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import sql.mySQL;
import tools.Draw;
import tools.JNiceField;
import tools.Loading;

/**
 * Клас для творення регістраційного вікна
 * @author Arsak
 */
public class Registration extends JFrame {

    static int i = 0;
    static Timer t;
    private Connection con = null;
    int mychoose;
    mySQL SQL;

    /**
     * Індетифікатор користувача 
     */
    public static String USR;

    /**
     * Метод конструктор який задає всі небхідні компоненти для входу чи реєстрації користувача
     */
    public Registration() {

        SQL = new mySQL(
                "127.0.0.1",
                "3306",
                "space",
                "root",
                "",
                "admin_of_space"
        );
        String rez = SQL.Conect();

        // створюємо рисунки для елементів
        ImageIcon b_blue = new ImageIcon("res\\control\\b1.png");
        ImageIcon b_red = new ImageIcon("res\\control\\b0.png");
        Font f = new Font("Century Gothic", Font.ITALIC, 18);
        Font fort = new Font("Century Gothic", Font.ITALIC, 24);

        Toolkit too = Toolkit.getDefaultToolkit(); // інструмент за замвчуванням
        Dimension dim = too.getScreenSize(); //  Dimension біліотека для визначення розміру чогось тут екрана у dim розмір екрану

        JLabel jL = new JLabel();
        Image image = Toolkit.getDefaultToolkit().createImage("res\\gif\\kosm0.gif");
        ImageIcon imageIcon = new ImageIcon(image);
        imageIcon.setImageObserver(jL);
        jL.setIcon(imageIcon);
      
        // лейбл виходу
        JLabel go_out = new JLabel();
        go_out.setIcon(b_blue);
        go_out.setBounds(20, 155, 50, 50);
        go_out.setOpaque(false);
        go_out.setVisible(false);
        
        JLabel privet = new JLabel();
        privet.setForeground(Color.white);
        privet.setFont(fort);
        privet.setVisible(false);

        JNiceField password = new JNiceField();
        password.setFont(f);
        password.setVisible(false);

        JNiceField password_two = new JNiceField();
        password_two.setFont(f);
        password_two.setVisible(false);

        JNiceField admin = new JNiceField();
        admin.setFont(f);
        admin.setVisible(false);

        placeholder(password, "Введи пароль");
        placeholder(password_two, "Повтори пароль");
        placeholder(admin, "Введи логін");
        
        JPanel panel_reg = new JPanel();
        panel_reg.setBounds(225, 100, 250, 70);

        JPanel panel = new JPanel();
        panel.setBounds(225, 200, 250, 70);

        JPanel ok = new JPanel();
        ok.setBounds(570, 300, 100, 50);
        ok.setVisible(false);
        
        JButton choose_reg = new JButton();
        choose_reg.setBounds(225, 100, 250, 70);
        choose_reg.setText("Створити акаунт");

        JButton okay = new JButton("Ок");
        okay.setBounds(570, 300, 100, 50);
        okay.setVisible(false);

        JButton choose = new JButton();
        choose.setBounds(225, 200, 250, 70);
        choose.setText("Авторизуватися");

        
        Draw vis = new Draw();
        vis.paint(okay, ok);
        vis.paint(choose_reg, panel_reg);
        vis.paint(choose, panel);
        
        choose_reg.addActionListener((ActionEvent e) -> {
            mychoose = 1;
            choose_reg.setVisible(false);
            panel_reg.setVisible(false);
            panel.setVisible(false);
            choose.setVisible(false);

            privet.setVisible(true);
            privet.setBounds(250, 20, 200, 50);
            privet.setText("Створіть акаунт");

            password.setBounds(225, 155, 250, 40);
            password.setVisible(true);

            password_two.setBounds(225, 225, 250, 40);
            password_two.setVisible(true);

            admin.setBounds(225, 90, 250, 40);
            admin.setVisible(true);

            ok.setVisible(true);
            okay.setVisible(true);
            go_out.setVisible(true);
        });

        choose.addActionListener((ActionEvent e) -> {
            mychoose = 2;
            choose_reg.setVisible(false);
            panel_reg.setVisible(false);
            panel.setVisible(false);
            choose.setVisible(false);

            privet.setVisible(true);
            privet.setBounds(270, 70, 200, 50);
            privet.setText("Авторизація");

            password.setBounds(225, 205, 250, 40);
            password.setVisible(true);

            admin.setBounds(225, 140, 250, 40);
            admin.setVisible(true);

            ok.setVisible(true);
            okay.setVisible(true);
            go_out.setVisible(true);
        });


        go_out.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choose_reg.setVisible(true);
                panel_reg.setVisible(true);
                panel.setVisible(true);
                choose.setVisible(true);

                privet.setVisible(false);
                password.setVisible(false);
                admin.setVisible(false);
                password_two.setVisible(false);

                ok.setVisible(false);
                okay.setVisible(false);
                go_out.setVisible(false);

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                go_out.setIcon(b_red);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                go_out.setIcon(b_blue);
            }
        });
        okay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mychoose == 2) {
                    con = SQL.getCon();
                    USR = admin.getText();
                    String PWD = "";
                    try {
                        PWD = toHexString(getSHA(password.getText()));
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String sql = "SELECT * FROM `admin_of_space` WHERE `name`=? AND `password`=? ORDER BY `id_admin` ASC LIMIT 0,1";
                    try {
                        PreparedStatement stm = con.prepareStatement(sql);
                        stm.setString(1, USR);
                        stm.setString(2, PWD);
                        ResultSet rez = stm.executeQuery();
                        if (rez.next()) {
                            dispose();
                            // фрейм загрузки
                            Loading lod = new Loading();
                            t = new Timer(50, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    i++;
                                    lod.setVisible(true);
                                    if (i >= 80) {
                                        t.stop();
                                        lod.dispose();
                                        CoolMenu menu = new CoolMenu();
                                    }
                                }
                            });
                            t.start();
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Некоректна пара ім’я/пароль, повторіть ввід", "Помилка", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        System.out.println("error");
                    }
                }              
                if (mychoose == 1) {
                    if (admin.equals("Введи логін") || password.equals("Введи пароль") || password_two.equals("Повтори пароль")) {
                        JOptionPane.showMessageDialog(null, "Заповніть пусті поля!", "Помилка", JOptionPane.ERROR_MESSAGE);
                    } else if (password_two.getText().equals(password.getText())) {
                        try {
                            SQL.insert(admin.getText(), toHexString(getSHA(password.getText())), "0", "res\\rang\\Самурай.png");
                            dispose();
                            // фрейм загрузки
                            Loading lod = new Loading();
                            t = new Timer(50, (ActionEvent e1) -> {
                                i++;
                                lod.setVisible(true);
                                if (i >= 80) {
                                    t.stop();
                                    lod.dispose();
                                    CoolMenu menu = new CoolMenu();
                                }
                            });
                            t.start();
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Паролі не збігаються, повторіть ввід", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        File file = new File("res\\ico\\icon.jpeg");
        try {
            setIconImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // фігня яка закриває фрейм при натиску на хрестик

        setBounds(dim.width / 2 - 350, dim.height / 2 - 200, 700, 400); // це для розташування фрейма по центру
        setTitle("Авторизація"); // назва

        add(panel_reg);
        add(choose_reg);
        add(panel);
        add(choose);
        add(admin);
        add(password);
        add(okay);
        add(ok);
        add(password_two);
        add(privet);
        add(go_out);
        add(jL);
        setVisible(true);
        setLayout(null);
        setResizable(false); // для блокування маштабування
    }

    /**
     * Даний метод вставлює placeholder на вказаний JTextField
     *
     * @param searchText JTextField якому потібно встановити placeholder
     * @param i текст який відображається у JTextField
     */
    public static void placeholder(JTextField searchText, String i) {
        searchText.setForeground(Color.gray);
        searchText.setText(i);
        searchText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchText.getText().equals(i)) {
                    searchText.setText("");
                    searchText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchText.getText().isEmpty()) {
                    searchText.setForeground(Color.GRAY);
                    searchText.setText(i);
                }
            }
        });
    }

    private byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
