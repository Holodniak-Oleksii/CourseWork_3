package tools;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * Клас який допомгає відрбразити не прозору панель на прозорому фреймові
 * @author Arsak
 */
public class ShowPanel extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit Kit = Toolkit.getDefaultToolkit();
        Image logo = Kit.getImage("res\\anim\\2.gif"); // потрібно для того щоб гіфка сильно не мигала
        Image underLogo = Kit.getImage("res\\anim\\2.png");

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(underLogo, 300, 100, this);
        g2d.drawImage(logo, 300, 100, this);

    }
}
