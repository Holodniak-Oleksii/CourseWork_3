package mechanics;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import network.ClientGame;
import network.ServerGame;

/**
 * Клас який перевіряє на зіткнення астероїди і куля
 * @author Arsak
 */
public class Check implements Runnable {

    /**
     * Масив у якому зберігаються всі кулі 
     */
    public ArrayList<Bullet> bul = new ArrayList<>();

    /**
     * Масив у якому зберігаються всі астероїди
     */
    public ArrayList<Aster> aster = new ArrayList<>();
    JPanel can; // панель на якій робим дії (spaceX)
    JPanel earth;
    public int HP = 500;
    int en_x;
    int en_y;
    int pl_x;
    int pl_y;

    /**
     * Лічильник збитиг обєктів
     */
    public int count = 0; // вот лічильник який виводиться у лейбелі
    boolean add_ast = false;
    boolean collision = false;
    /**
     * Метод який створює потік
     */
    public void go() {
        Thread myThread = new Thread(this);
        myThread.setName("Перевірка");
        myThread.start();
    }

    /**
     * Метод який добавляє астрерохд до перевірки
     * @param a Астреїд
     * @param p Панель на якій астероїд
     * @param c Панель із планетою
     */
    public void add_ast(Aster a, JPanel p, JPanel c) {
        aster.add(a);
        can = p;
        earth = c;
        add_ast = true;
    }

    /**
     * Метод який добавляє кулю у перевірку
     * @param b куля
     */
    public void add_bullet(Bullet b) {
        bul.add(b);
    }

    /**
     * Метод якйи перевіряє зіткунення для гри по мережі
     * @param X_Enemy коодтината Х ворога
     * @param Y_Enemy координати Y ворога
     * @param c панель на якій відрисовується
     */
    public void Collision(int X_Enemy, int Y_Enemy, JPanel c) {
        en_x = X_Enemy;
        can = c;
        en_y = Y_Enemy;
        collision = true;
    }

    /**
     * Метод який перевіряє чи є зіткнення
     */
    @Override
    public void run() {
        Font font = new Font("Century Gothic", Font.BOLD, 20);
        JLabel counter = new JLabel();
        while (true) {
            if (Aster.plus == 0) {
                Thread.currentThread().interrupt();
                break;
            }
            if (add_ast == true) {
                collision = false;
                for (int f = 0; f < aster.size(); f++) {
                    Aster star = aster.get(f);
                    for (int i = 0; i < bul.size(); i++) {
                        Bullet ball = bul.get(i);
                        // перевірка на зіткнення кулі і астероїда
                        if (ball.X >= star.X && ball.X <= star.X + 100 && ball.Y >= star.Y && ball.Y <= star.Y + 100) {
                            ball.aim = false;
                            star.aim = false;
                            count++;
                            Blast g = new Blast(can, (int) star.X, (int) star.Y);
                            g.go();
                            counter.setFont(font);
                            counter.setBounds(750, 10, 250, 40);
                            counter.setText("Збитих об'єктів: " + count);
                            counter.setForeground(Color.decode("#81b214"));
                            can.add(counter);
                            aster.remove(star);
                            bul.remove(ball);

                        }
                    }
                    // перевірка на зіткнення астероїда і планети
                    if (star.X >= 340 && star.X <= 500 && star.Y >= 145 && star.Y <= 305) {
                        star.aim = false;
                        HP -= 50;
                        Blast g = new Blast(earth, 0, 0);
                        g.go();
                        aster.remove(star);
                    }
                    if (HP <= 0) {
                        can.remove(counter);
                    }
                }
            }
            if (collision == true) {

                add_ast = false;
                for (int i = 0; i < bul.size(); i++) {
                    Bullet ball = bul.get(i);
                    if (ball != null) {
                        if (en_x != 96) {
                            // перевірка на зіткнення кулі і астероїда
                            if (ball.X >= en_x && ball.X <= en_x + 50 && ball.Y >= en_y && ball.Y <= en_y + 50) {
                                ball.aim = false;
                                ClientGame.GO_blast = true;
                                ServerGame.go_blast = true;
                                Blast g = new Blast(can, en_x - 35, en_y - 35);
                                g.go();
                                
                                bul.remove(ball);
                                can.repaint();
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(30);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); //відновлення статусу перерваний* 
                System.out.println("перевірка заснула ((( ");
            }
        }
    }
}
