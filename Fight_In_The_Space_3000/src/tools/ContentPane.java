package tools;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Клас для створення ранелі яка зможе відображатися на прозорому фреймі
 * @author Arsak
 */
public class ContentPane extends JPanel {
    
    /**
     * Конструктор у якому задаємо прозорість панелі
     */
    public ContentPane() {
        
        setOpaque(false);
        
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
        g2d.setColor(getBackground());
        g2d.fill(getBounds());
        g2d.dispose();

    }

}