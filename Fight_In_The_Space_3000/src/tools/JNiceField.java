package tools;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Arsak
 * Клас задопомогою якого ми можемо зробити красиві заокруглені JTextField
 */
public class JNiceField extends JTextField{
    
        @Override protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
          int w = getWidth() - 1;
          int h = getHeight() - 1;
          Graphics2D g2 = (Graphics2D) g.create();
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2.setPaint(UIManager.getColor("TextField.background"));
          g2.fillRoundRect(0, 0, w, h, h, h);
          g2.setPaint(Color.GRAY);
          g2.drawRoundRect(0, 0, w, h, h, h);
          g2.dispose();
        }
        super.paintComponent(g);
      }
      /**
       * Обновлення
       */
      @Override public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
      }
    }