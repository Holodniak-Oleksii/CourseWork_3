package mechanics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Клас який створює Астероїди
 * @author Arsak
 */
public class Aster extends JPanel implements Runnable {

    private Point ToXY;
    private Point FromXY;
    private JPanel Canvas;
    private ImageIcon Img;
    private JFrame fr;
    Thread y;

    /**
     * зміна яка допамагає зупиняти потік
     */
    public static int plus = 1;

    /**
     * Координати астероїда
     */
    public double X = 0, Y = 0;
    Check c = new Check();

    /**
     * зміна яка допамагає зупиняти потік
     */
    public boolean aim = true;

    /**
     * Метод який задає потрібні компоненти для астероїда
     * @param From Точна із якої летить астероїд
     * @param to точка до якої летить астероїд
     * @param pnl панель на якій відображається астероїд 
     * @param picture катинка астрероїда
     * @param frame фрейм на якому потрбіно відтворити рух астрероїда
     */
    public void Aster(Point From, Point to, JPanel pnl, ImageIcon picture, JFrame frame) {
        FromXY = From;
        ToXY = to;
        Canvas = pnl;
        Img = picture;
        fr = frame;
    }

    /**
     * Метод який запускає потік який рухає астрероїд по екрану
     */
    public void go() {
        y = new Thread(this);
        y.start();
    }

    /**
     * Метод який створює потік який рухає астероїд також зупиняється коли 
     * астероїд виходить за поле чи збивається
     */
    @Override
    public void run() {

        double deltaX = FromXY.getX() - ToXY.getX();
        double deltaY = FromXY.getY() - ToXY.getY();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double xVelocity = 1;
        double yVelocity = 1;

        if (magnitude != 0) {
            xVelocity = deltaX / magnitude;
            yVelocity = deltaY / magnitude;
            this.setLocation((int) FromXY.getX(), (int) FromXY.getY());
            X = (int) FromXY.getX();
            Y = (int) FromXY.getY();
            this.setSize(100, 100);
            this.setVisible(true);
            this.setOpaque(false);
            aim = true;
            Canvas.add(this);

            while (true) {

                if (plus == 0) {
                    Thread.currentThread().interrupt();
                     break;
                }
                // швіидкість переміщення
                X -= 4 * xVelocity;
                Y -= 4 * yVelocity;

                this.setLocation((int) X, (int) Y);
                // якщо вийде за межі поля то удаляємо
                if (X > 960 || X < 0) {
                    c.aster.remove(this);
                    break;
                }
                if (Y > 570 || Y < 0) {
                    c.aster.remove(this);
                    break;
                }
                if (aim == false) {
                    break;
                }
                try {
                    Thread.sleep(30);

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); //відновлення статусу перерваний* 
                    System.out.println("Астероїд заснув((( ");
                }
            }

            Canvas.remove(this);
        }
    }
    protected void paintComponent(Graphics g) {
        Image aster = Img.getImage();
        fr.repaint();
        super.paintComponent(g);
        Graphics2D projectile = (Graphics2D) g;
        projectile.drawImage(aster, 0, 0, null);
    }

    /**
     * функція яка закриває потік
     */
    public void Stop() {
        plus = 0;
    }

    /**
     *  
     * @return the toXY
     */
    public Point getToXY() {
        return ToXY;
    }

    /**
     * @param toXY the toXY to set
     */
    public void setToXY(Point toXY) {
        this.ToXY = toXY;
    }

    /**
     * повертає координату Х
     * @return кордината Х
     */
    public int getX() {
        return (int) X;
    }

    /**
     * задає координату Х
     * @param X координата Х
     */
    public void setX(int X) {
        this.X = X;
    }

    /**
     * повртає координату Y
     * @return координата Y
     */
    public int getY() {
        return (int) Y;
    }
    /**
     * задає координату Y
     * @param Y координата Y
     */
    public void setY(int Y) {
        this.Y = Y;
    }

    /**
     * 
     * @return Зображення
     */
    public ImageIcon getimg() {
        return Img;
    }

    /**
     *
     * @param img Зображення
     */
    public void setimg(ImageIcon img) {
        this.Img = img;
    }

    /**
     * @return the frmXY
     */
    public Point getFromXY() {
        return FromXY;
    }

    /**
     * @param frmXY the frmXY to set
     */
    public void setFromXY(Point frmXY) {
        this.FromXY = frmXY;
    }

    /**
     * @return the canvas
     */
    public JPanel getCanvas() {
        return Canvas;
    }

    /**
     * @param canvas the canvas to set
     */
    public void setCanvas(JPanel canvas) {
        this.Canvas = canvas;
    }
}
