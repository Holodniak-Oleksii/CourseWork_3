package network;

import fight.CoolMenu;
import fight.Game;
import fight.Registration;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import mechanics.Blast;
import mechanics.Bullet;
import mechanics.Check;
import sql.mySQL;

/**
 * Клас який відображає строну сервера
 *
 * @author Arsak
 */
public class ServerGame {

    int W = 450; // Координати гравця Х
    int H = 285; // координати гравця y
    int W_E = 450; // Координати  ворога Х
    int H_E = 285; // координати ворога y
    int mouseX_ENEMY, mouseY_ENEMY; // координати миші для врга
    static int mouseX, mouseY; // координати миші 
    /**
     * змніа яка відстежує постріл
     */
    public static boolean go_shooter = false;
    /**
     * зміна яка відстежує вибух
     */
    public static boolean go_blast = false;
    boolean shot = true; // зміна стрійльби
    Point from; // точка із якої летять
    Point to; // точка до якої летять
    Point tot; // координати миші 
    Bullet bullet_E;
    Bullet bullet_Enemy;
    int count = 0;
    double theta;
    double theta_S;
    JPanel spaceX;
    int co = 0;
    int HP_P = 275;
    int HP_X = 590;
    //mySQL SQL;

    /**
     * Функція яка задає логіку гри для строни сервера
     */
    public void goGame() {

        Server m = new Server();
        m.go();
        Check check = new Check();
        check.go();

//        SQL = new mySQL(
//                "127.0.0.1",
//                "3306",
//                "space",
//                "root",
//                "",
//                "admin_of_space"
//        );
//        String rez = SQL.Conect();

        Toolkit too = Toolkit.getDefaultToolkit(); // інструмент за замвчуванням
        Dimension dim = too.getScreenSize(); //  Dimension біліотека для визначення розміру чогось тут екрана у dim розмір екрану

        JFrame frame = new JFrame(); // свтворюємо фрейм (фрейм = форма)
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // фігня яка закриває фрейм при натиску на хрестик
        frame.setTitle("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // фігня яка закриває фрейм при натиску на хрестик
        File file = new File("res\\ico\\icon.jpeg");
        try {
            frame.setIconImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frame.setBounds(dim.width / 2 - 480, dim.height / 2 - 285, 960, 570); // це для розташування фрейма по центру
        frame.setLayout(null);

        ImageIcon game = new ImageIcon("res\\img\\game_over.png");
        Font font = new Font("Century Gothic", Font.BOLD, 28);

        ImageIcon home_green = new ImageIcon("res\\control\\home.png");
        ImageIcon home_red = new ImageIcon("res\\control\\home2.png");

        ImageIcon s = new ImageIcon("res\\img\\spaceship.png");
        Image Imgplayer = s.getImage();

        ImageIcon player = new ImageIcon("res\\gif\\jupiter.gif");
        Image imgPlayer = player.getImage();

        ImageIcon sas = new ImageIcon("res\\img\\mars-spaceship.png");
        Image player_against = sas.getImage();

        ImageIcon hp = new ImageIcon("res\\control\\health_line_enemy.png");
        Image health = hp.getImage();

        ImageIcon hp_w = new ImageIcon("res\\control\\health_line.png");
        Image health_w = hp_w.getImage();

        ImageIcon my_life = new ImageIcon("res\\control\\health.png");
        Image health_for_all = my_life.getImage();

        JLabel game_over = new JLabel();
        game_over.setIcon(game);
        game_over.setBounds(240, 100, 480, 230);
        game_over.setVisible(false);

        JLabel coun = new JLabel();
        coun.setFont(font);
        coun.setBounds(400, 325, 250, 40);
        coun.setVisible(false);

        JLabel home = new JLabel();
        home.setBounds(420, 375, 100, 100);
        home.setVisible(false);
        home.setOpaque(false);
        home.setIcon(home_green);

        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (HP_P < 0) {
//                    String info = SQL.GetInfo(Registration.USR);
//                    int jazz = Integer.parseInt(info) - 50;
//                    SQL.UP_Level(jazz, Registration.USR);
//                    SQL.insertRANG(Registration.USR, Integer.toString(jazz));
                    frame.dispose();
                } else {
//                    String info = SQL.GetInfo(Registration.USR);
//                    int jazz = Integer.parseInt(info) + 50;
//                    SQL.UP_Level(jazz, Registration.USR);
//                    SQL.insertRANG(Registration.USR, Integer.toString(jazz));
                    frame.dispose();
                }
                try {
                    m.Stop();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                CoolMenu menu = new CoolMenu();

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                home.setIcon(home_red);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                home.setIcon(home_green);
            }
        });

        JPanel life_player = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(health_w, 0, 0, null);
            }
        };
        life_player.setBounds(590, 10, 350, 80);
        life_player.setOpaque(false);
        life_player.setVisible(true);

        JPanel hp_player = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(health_for_all, 0, 0, null);
            }
        };
        hp_player.setBounds(HP_X, 40, HP_P, 21);
        hp_player.setOpaque(false);
        hp_player.setVisible(true);

        JPanel hp_enemy = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(health_for_all, 0, 0, null);
            }
        };
        hp_enemy.setBounds(85, 40, 275, 21);
        hp_enemy.setOpaque(false);
        hp_enemy.setVisible(true);

        JPanel life_enemy = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(health, 0, 0, null);
            }
        };
        life_enemy.setBounds(10, 10, 350, 80);
        life_enemy.setOpaque(false);
        life_enemy.setVisible(true);

        JPanel ship = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // фомула для вичеслня повороту в бік миші
                double x = mouseX - W;
                double y = mouseY - H;
                theta = Math.toDegrees(Math.atan2(x, y));
                if (theta < 0.0) {
                    theta += 360.0;
                }
                g2d.rotate(Math.toRadians(-theta), 30, 30); // поворот на певний градус
                g2d.drawImage(Imgplayer, 7, 7, null);
            }
        };

        JPanel enemy = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // фомула для вичеслня повороту в бік миші
                double x = mouseX_ENEMY - W_E;
                double y = mouseY_ENEMY - H_E;
                theta_S = Math.toDegrees(Math.atan2(x, y));
                if (theta_S < 0.0) {
                    theta_S += 360.0;
                }
                g2d.rotate(Math.toRadians(-theta_S), 30, 30); // поворот на певний градус
                g2d.drawImage(player_against, 7, 7, null);
            }
        };

        spaceX = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(imgPlayer, 0, 0, null);
            }
        };

        Image im = too.getImage("res\\img\\targit.png"); // кладе фото на курос 
        Cursor c = too.createCustomCursor(im, new Point(spaceX.getX(), spaceX.getY()), "img");

        spaceX.setBounds(0, 0, 960, 570);
        spaceX.setOpaque(true);
        spaceX.setVisible(true);
        spaceX.setCursor(c);

        ship.setOpaque(false);
        ship.setVisible(true);
        ship.setCursor(c);

        enemy.setOpaque(false);
        enemy.setVisible(true);
        enemy.setCursor(c);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (count < 1) {
                    String send = W + "@" + H + "@" + mouseX + "@" + mouseY + "@" + 0 + "@" + co + "@" + HP_P;

                    try {
                        m.setInfo(send);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    count++;
                }
                if (e.getKeyCode() == KeyEvent.VK_A && W >= 0) {
                    W -= 8;
                    ship.setBounds(W, H, 75, 75);

                }
                if (e.getKeyCode() == KeyEvent.VK_D && W <= 885) {
                    W += 8;
                    ship.setBounds(W, H, 75, 75);

                }
                if (e.getKeyCode() == KeyEvent.VK_W && H >= 0) {
                    H -= 8;
                    ship.setBounds(W, H, 75, 75);

                }
                if (e.getKeyCode() == KeyEvent.VK_S && H <= 475) {
                    H += 8;
                    ship.setBounds(W, H, 75, 75);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
        // стрільба
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (shot == true) {
                    go_shooter = true;
                    from = new Point(W + 12, H + 12);
                    tot = new Point(mouseX - 30, mouseY - 30);
                    bullet_E = new Bullet();
                    bullet_E.Bullet(from, tot, spaceX, frame);
                    bullet_E.go();
                    check.add_bullet(bullet_E);
                }
            }
        });
        Timer mytimer = new Timer(8, (ActionEvent e) -> {
            frame.repaint();
            spaceX.repaint();
        });
        mytimer.start();
        Timer timer = new Timer(30, (ActionEvent e) -> {
            frame.repaint();
            
            if (m.Info != null) {
                if (go_blast == true) {
                    
                    co++;
                    if (go_shooter == false) {
                        
                        String send = W + "@" + H + "@" + mouseX + "@" + mouseY + "@" + 0 + "@" + co + "@" + HP_P;
                        try {
                            m.setInfo(send);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {
                        String send = W + "@" + H + "@" + mouseX + "@" + mouseY + "@" + 1 + "@" + co + "@" + HP_P;
                        
                        try {
                            m.setInfo(send);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        go_shooter = false;
                        go_blast = false;
                    }
                } else {
                    co = 0;
                    if (go_shooter == false) {
                        String send = W + "@" + H + "@" + mouseX + "@" + mouseY + "@" + 0 + "@" + co + "@" + HP_P;
                        try {
                            m.setInfo(send);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {
                        String send = W + "@" + H + "@" + mouseX + "@" + mouseY + "@" + 1 + "@" + co + "@" + HP_P;
                        
                        try {
                            m.setInfo(send);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        go_shooter = false;
                    }
                }

                String f = m.Info;
                //System.out.println(f + "   Sr");
                String[] go = f.split("@");
                enemy.setBounds(Integer.valueOf(go[0]), Integer.valueOf(go[1]), 75, 75);
                W_E = Integer.valueOf(go[0]);
                H_E = Integer.valueOf(go[1]);
                
                check.Collision(W_E, H_E, spaceX);
                
                mouseX_ENEMY = Integer.valueOf(go[2]);
                mouseY_ENEMY = Integer.valueOf(go[3]);
                if (Integer.valueOf(go[4]) == 1) {
                    from = new Point(W_E + 12, H_E + 12);
                    tot = new Point(mouseX_ENEMY - 30, mouseY_ENEMY - 30);
                    bullet_Enemy = new Bullet();
                    bullet_Enemy.Bullet(from, tot, spaceX, frame);
                    bullet_Enemy.go();
                    
                }
                if (Integer.valueOf(go[5]) == 1) {
                    HP_P -= 10;
                    HP_X += 10;
                    hp_player.setBounds(HP_X, 40, HP_P, 21);
                    Blast g = new Blast(spaceX, W - 35, H - 35);
                    Bullet.goul = false;
                    g.go();
                }
                //System.out.println(Integer.valueOf(go[6]) + " CL");
                hp_enemy.setBounds(85, 40, Integer.valueOf(go[6]), 21);
                if (HP_P <= 0) {
                    home.setVisible(true);
                    game_over.setVisible(true);
                    coun.setForeground(Color.red);
                    coun.setText("Поразка");
                    coun.setVisible(true);
                    shot = false;
                    frame.remove(ship);
                    frame.remove(enemy);
                }
                if (Integer.valueOf(go[6]) <= 0) {
                    
                    home.setVisible(true);
                    game_over.setVisible(true);
                    coun.setForeground(Color.decode("#81b214"));
                    coun.setText("Перемога!");
                    coun.setVisible(true);
                    shot = false;
                    frame.remove(ship);
                    frame.remove(enemy);
                }
                
            }
        });
        addMouseMotionListener(frame);
        timer.start();

        ship.setBounds(W, H, 75, 75);
        enemy.setBounds(W_E, H_E, 75, 75);

        enemy.setVisible(true);
        ship.setVisible(true);
        frame.add(home);
        frame.add(coun);
        frame.add(game_over);
        frame.add(hp_player);
        frame.add(life_player);
        frame.add(hp_enemy);
        frame.add(life_enemy);
        frame.add(ship);

        frame.add(enemy);
        frame.add(spaceX);
        frame.setVisible(true);
        frame.setResizable(false); // для блокування маштабування

    }
    /**
     * Метод для відстежування кусора на фреймові
     * @param frame фрейм на якому відстежуємо курсор
     */
    public void addMouseMotionListener(JFrame frame) {
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
}
