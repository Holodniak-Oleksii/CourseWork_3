package fight;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import mechanics.Aster;
import mechanics.Bullet;
import mechanics.Check;
import sql.mySQL;

/**
 * Клас який створює одиночну гру 
 * @author Arsak
 */
public class Game {

    static int mouseX, mouseY; // координати миші 
    String inoprishlenzi; // тип астероїдів які летять
    JPanel spaceX; // панель на якій все відтворюється
    ImageIcon as; // зображення астероїдів
    double theta; // кут поворту корабля
    int H = 215; // координати гравця y
    Point from; // точка із якої летять
    Point to; // точка до якої летять
    Point tot; // координати миші 
    Timer t; // таймер repaint()
    int bullshit;
    Timer exit;
    String fon;
    int W = 410; // Координати гравця Х
    String boat;
    String planet;
    static Aster ast;
    static Bullet bullet;
    JPanel life = new JPanel();
    boolean shot = true; // зміна стрійльби
    JFrame frame = new JFrame(); // головний фрейм

 // mySQL SQL;

    /**
     * Метод який створює ігровий цикл для одиночної гри, у ньому задаються 
     * всі необхідні панелі із зображеннями, постріли, створення астероїдів
     * і позакінченню гри додаються бали у базу даних
     */
    public void game() {
//        SQL = new mySQL(
//                "127.0.0.1",
//                "3306",
//                "space",
//                "root",
//                "",
//                "admin_of_space"
//        );
//        String rez = SQL.Conect();

        // клас який перевіряє на зіткнення
        Check check = new Check();
        check.go();

        Toolkit too = Toolkit.getDefaultToolkit(); // клас інстументів
        Dimension dim = too.getScreenSize();

        JLabel repeat = new JLabel();
        repeat.setBounds(375, 375, 100, 100);
        repeat.setOpaque(false);
        repeat.setVisible(false);

        JLabel home = new JLabel();
        home.setBounds(475, 375, 100, 100);
        home.setVisible(false);
        home.setOpaque(false);

        frame.setLayout(null); // стравжні координати
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBounds(dim.width / 2 - 480, dim.height / 2 - 285, 960, 570); // це для розташування фрейма по центру
        frame.setTitle("SpaceIN");

        // ========================= задаємо фото ==============================
        ImageIcon player = new ImageIcon(fon);
        Image imgPlayer = player.getImage();

        ImageIcon earth = new ImageIcon(planet);
        Image people = earth.getImage();

        ImageIcon ship = new ImageIcon(boat);
        Image Imgplayer = ship.getImage();

        ImageIcon rokcet = new ImageIcon("res\\gif\\elon.gif");
        Image elon = rokcet.getImage();

        ImageIcon game = new ImageIcon("res\\img\\game_over.png");
        Font font = new Font("Century Gothic", Font.BOLD, 20);

        ImageIcon repeat_blue = new ImageIcon("res\\control\\repeat2.png");
        ImageIcon repeat_red = new ImageIcon("res\\control\\repeat.png");

        ImageIcon home_green = new ImageIcon("res\\control\\home.png");
        ImageIcon home_red = new ImageIcon("res\\control\\home2.png");

        repeat.setIcon(repeat_blue);
        home.setIcon(home_green);

        // =================== створюємо панель на кожний обєкт ================
        life.setBounds(230, 20, check.HP, 20);
        life.setBackground(Color.green);
        life.setVisible(true);

        JLabel game_over = new JLabel();
        game_over.setIcon(game);
        game_over.setBounds(240, 100, 480, 230);
        game_over.setVisible(false);

        JLabel count = new JLabel();
        count.setFont(font);
        count.setBounds(380, 325, 250, 40);
        count.setVisible(false);

        spaceX = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(imgPlayer, 0, 0, null);
            }
        };

        // панель респекту
        JPanel Musk = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(elon, 0, 0, null);
            }
        };

        // наш світ
        JPanel grand = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(people, 20, 20, null);
            }
        };

        JPanel spaceship = new JPanel() {
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
                g2d.drawImage(Imgplayer, 7, 7, null); //    
            }
        };

        // створємо особливий курсок коли мишка на панелі
        Image im = too.getImage("res\\img\\targit.png"); // кладе фото на курос 
        Cursor c = too.createCustomCursor(im, new Point(spaceX.getX(), spaceX.getY()), "img");

        // задаємо властивості для кожної понелі
        spaceX.setBounds(0, 0, 960, 570);
        spaceX.setOpaque(true);
        spaceX.setVisible(true);
        spaceX.setCursor(c);

        grand.setBounds(400, 205, 120, 120);
        grand.setOpaque(false);
        grand.setVisible(true);
        grand.setCursor(c);

        spaceship.setBounds(W, H, 75, 75);
        spaceship.setOpaque(false);
        spaceship.setVisible(true);
        spaceship.setCursor(c);

        Musk.setBounds(820, 440, 100, 78);
        Musk.setOpaque(false);
        Musk.setVisible(false);

        repeat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                String info = SQL.GetInfo(Registration.USR);
  //              int jazz = Integer.parseInt(info) + check.count;
//                SQL.UP_Level(jazz, Registration.USR);
//                SQL.insertRANG(Registration.USR, Integer.toString(jazz));

                Game g = new Game();
                frame.dispose();
                ast.Stop();

                if (bullshit == 2) {
                    Aster.plus = 1;
                    g.inoprishlenzi = "f";
                    g.fon = "res\\gif\\space_around_mars.gif";
                    g.planet = "res\\gif\\Mars.gif";
                    g.boat = "res\\img\\mars-spaceship.png";
                    g.bullshit = 2;
                    g.game();
                }
                if (bullshit == 1) {
                    Aster.plus = 1;
                    g.inoprishlenzi = "d";
                    g.fon = "res\\gif\\space.gif";
                    g.planet = "res\\gif\\earth.gif";
                    g.boat = "res\\img\\spaceship.png";
                    g.bullshit = 1;
                    g.game();
                }

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                repeat.setIcon(repeat_red);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                repeat.setIcon(repeat_blue);
            }
        });
        // вертаємся до дому
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                String info = SQL.GetInfo(Registration.USR);
//                int jazz = Integer.parseInt(info) + check.count;
//                SQL.UP_Level(jazz, Registration.USR);
//                SQL.insertRANG(Registration.USR, Integer.toString(jazz));
                frame.dispose();
                ast.Stop();
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

        // переміщаємо корабель на натиск клавіш
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A && W >= 0) {
                    W -= 8;
                    spaceship.setBounds(W, H, 75, 75);

                }
                if (e.getKeyCode() == KeyEvent.VK_D && W <= 885) {
                    W += 8;
                    spaceship.setBounds(W, H, 75, 75);

                }
                if (e.getKeyCode() == KeyEvent.VK_W && H >= 0) {
                    H -= 8;
                    spaceship.setBounds(W, H, 75, 75);
                }
                if (e.getKeyCode() == KeyEvent.VK_S && H <= 475) {
                    H += 8;
                    spaceship.setBounds(W, H, 75, 75);
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
                    from = new Point(W + 12, H + 12);
                    tot = new Point(mouseX - 30, mouseY - 30);
                    bullet = new Bullet();
                    bullet.Bullet(from, tot, spaceX, frame);
                    check.add_bullet(bullet);
                    bullet.go();
                }
            }
        });

        // обновляємо фрейм кожні 8мс
        Timer timer = new Timer(8, (ActionEvent e) -> {
            frame.repaint();
            life.setBounds(230, 20, check.HP, 20);
            // перевірка на поразку
            if (check.HP <= 0) {
                repeat.setVisible(true);
                home.setVisible(true);
                game_over.setVisible(true);
                count.setForeground(Color.decode("#81b214"));
                count.setText("Збитих об'єктів: " + check.count);
                count.setVisible(true);
                shot = false;
                frame.remove(spaceship);
                
                if (check.count >= 20) {
                    Musk.setVisible(true);
                }
            }
        });
        addMouseMotionListener(frame);
        timer.start();

        // видодим кожні wave мс астероїд
        t = new Timer(CoolMenu.wave, (ActionEvent e) -> {
            frame.repaint();
            int cho = (int) (Math.random() * 4);
            if (cho == 1) {
                int Y = (int) (Math.random() * 600);
                to = new Point(0, Y);
            }
            if (cho == 2) {
                int X = (int) (Math.random() * 1000);
                to = new Point(X, 0);
            }
            if (cho == 3) {
                int X = (int) (Math.random() * 600);
                to = new Point(X, 600);
            }
            if (cho == 0) {
                int Y = (int) (Math.random() * 600);
                to = new Point(1000, Y);
            }
            int choise = (int) (Math.random() * 4);

            as = new ImageIcon("res\\img\\" + inoprishlenzi + choise + ".png");
            from = new Point(420, 225);
            ast = new Aster();
            ast.Aster(to, from, spaceX, as, frame);
            check.add_ast(ast, spaceX, grand);
            ast.go();
        });
        t.start();

        File file = new File("res\\ico\\icon.jpeg");
        try {
            frame.setIconImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.add(home);
        frame.add(repeat);
        frame.add(Musk);
        frame.add(count);
        frame.add(game_over);
        frame.add(life);
        frame.add(spaceship);
        frame.add(grand);
        frame.add(spaceX);
        frame.setVisible(true);
    }

    /**
     * Метод для відстежування кусора на фреймові
     * @param frame фрейм на якому відстежуємо курсор
     */
    public static void addMouseMotionListener(JFrame frame) {
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

}
