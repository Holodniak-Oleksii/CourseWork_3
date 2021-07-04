package mechanics;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tools.Sound;

/**
 * Клас для відображення кулі на екрані
 * @author Arsak
 */
public class Bullet extends JPanel implements Runnable {

    private Point toXY;
    private Point fromXY;
    private JPanel canvas;
    private JFrame fr;

    /**
     * Координьати кулі
     */
    public double X = 0, Y = 0;
    int crossX, crossY;
    Check c = new Check();

    /**
     * Зміна яка допомагає зупинити потік
     */
    public boolean aim = true;

    /**
     *Зміна яка допомагає зупинити потік
     */
    public static boolean targit = true;

    /**
     *Зміна яка допомагає зупинити потік
     */
    public static boolean goul = true;

    /**
     * Метод який задає потрібні компоненти для кулі
     * @param from Точна із якої летить куля
     * @param to точка до якої летить куля
     * @param pnl панель на якій летить куля
     * @param frame Фрейм на якому відорбажаться кулі
     */
    public void Bullet(Point from, Point to, JPanel pnl, JFrame frame) {
        fromXY = from;
        toXY = to;
        canvas = pnl;
        fr = frame;
    }
    /**
     * Метод який запускає потік який рухає астрероїд по екрану
     */
    public void go() {
        Thread x = new Thread(this);
        x.start();
    }
    /**
     * Метод який створює потік який рухає кулю також зупиняється коли 
     *куля виходить за поле чи збивається
     */
    public void run() {
        // формула для вичеслення правильного напрямку і координат для снаряду
        double deltaX = fromXY.getX() - toXY.getX();
        double deltaY = fromXY.getY() - toXY.getY();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double xVelocity = 1;
        double yVelocity = 1;

        Sound loud = new Sound(new File("res\\sound\\shoot.wav"));
        Sound noise = new Sound(new File("res\\sound\\kosmicheskiy_vzryiv.wav"));
        if (magnitude != 0) {
            xVelocity = deltaX / magnitude;
            yVelocity = deltaY / magnitude;
            this.setLocation((int) fromXY.getX(), (int) fromXY.getY());
            X = (int) fromXY.getX();
            Y = (int) fromXY.getY();
            loud.play();
            loud.setVolume(0.7f);
            this.setSize(30, 30);
            this.setVisible(true);
            this.setOpaque(false);
            targit = true;
            aim = true;
            goul = true;
            canvas.add(this);
            while (true) {
                if (Aster.plus == 0) {
                    Thread.currentThread().interrupt();
                    break;
                }
                // швіидкість переміщення
                X -= 40 * xVelocity;
                Y -= 40 * yVelocity;
                crossX = (int) X;
                crossY = (int) Y;
                this.setLocation((int) X, (int) Y);
                // якщо вийде за межі поля то удаляємо

                if (X > 960 || X < 0) {
                    c.bul.remove(this);
                    break;
                }
                if (Y > 570 || Y < 0) {
                    c.bul.remove(this);
                    break;
                }
                if (aim == false) {
                    noise.setVolume(0.6f);
                    noise.play();
                    break;
                }
                if (targit == false) {
                    noise.setVolume(0.6f);
                    noise.play();
                    break;
                }
                if (goul == false) {
                    noise.setVolume(0.6f);
                    noise.play();
                    break;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); //відновлення статусу перерваний* 
                    System.out.println("Куля заснула ((( ");
                }

            }
            canvas.remove(this);
        }
    }

    protected void paintComponent(Graphics g) {
        ImageIcon Snarad = new ImageIcon("res\\img\\ball.png");
        fr.repaint();
        super.paintComponent(g);
        Graphics2D projectile = (Graphics2D) g;
        projectile.drawImage(Snarad.getImage(), 10, 10, null);
    }

    /**
     * @return the toXY
     */
    public Point getToXY() {
        return toXY;
    }

    /**
     * @param toXY the toXY to set
     */
    public void setToXY(Point toXY) {
        this.toXY = toXY;
    }

    /**
     * @return the frmXY
     */
    public Point getFromXY() {
        return fromXY;
    }

    /**
     * @param frmXY the frmXY to set
     */
    public void setFromXY(Point frmXY) {
        this.fromXY = frmXY;
    }

    /**
     * @return the canvas
     */
    public JPanel getCanvas() {
        return canvas;
    }

    /**
     * @param canvas the canvas to set
     */
    public void setCanvas(JPanel canvas) {
        this.canvas = canvas;
    }
}
