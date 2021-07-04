package mechanics;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Клас який рісує вибух на фреймові
 * @author Arsak
 */
public class Blast extends JLabel implements Runnable {

    private int acrossX, acrossY;
    private JPanel can;
    int i = 0;
    
    /**
     * Метод констуктор який задає потрібні зміні бля відрисовки вибуха
     * @param g Панель на якій відбувається вибух
     * @param X Координата X для вибуху
     * @param Y Координата Н для вибуху
     */
    public Blast(JPanel g, int X, int Y) {
        acrossX = X;
        acrossY = Y;
        can = g;
    }

    /**
     * Метод який створює потік
     */
    public void go() {
        Thread my = new Thread(this);
        my.setName("Вибух");
        my.start();
    }

    /**
     * Метод ля відрисовки вибуха(відрисовка задається за допомогою цикла який 
     * перебирає спрайт)
     */
    @Override
    public void run() {
        this.setLocation(acrossX, acrossY);
        this.setSize(120, 120);
        
        this.setOpaque(false);

        while (true) {
            if (Aster.plus == 0) {
                Thread.currentThread().interrupt();
                 break;
            }
            i++;
            ImageIcon go = new ImageIcon("res\\anim\\h" + i + ".png");
            this.setIcon(go);
            this.setLocation(acrossX, acrossY);
            can.add(this);
            this.setVisible(true);
            if (i > 16) {
                can.remove(this);
                break;
            }

            try {
                Thread.sleep(30);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); //відновлення статусу перерваний* 
                System.out.println("Куля заснула ((( ");
            }
        }
    }
}
