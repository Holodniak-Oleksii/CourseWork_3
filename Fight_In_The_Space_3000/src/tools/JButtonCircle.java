package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Клас для створення круглої кнопки
 * @author Arsak
 */
public class JButtonCircle extends JButton {

    /**
     * Конструктор у якому здаються властивості для кнопки
     * @param text текст для кнопки
     */
    public JButtonCircle(String text) {
        this.setText(text);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        Font f = new Font("Century Gothic", Font.ITALIC, 15);
        this.setFont(f);
        this.setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            int w = getWidth() - 1;
            int h = getHeight() - 1;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new Color(228, 0, 23, 90));
            g2.fillRoundRect(0, 0, w, h, h, h);
            g2.setPaint(new Color(228, 0, 23, 90));
            g2.drawRoundRect(0, 0, w, h, h, h);
            g2.dispose();
        }
        super.paintComponent(g);
    }

    /**
     * Обновлення
     */
    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

}
