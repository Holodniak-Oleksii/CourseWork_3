package tools;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Клас який відрисовує певні властивості для кнопки і панелі
 * @author Arsak
 */
public class Draw {

    /** 
     * Метод який робить кнопки на говоному меню
     * @param button кнопка якій потрібно задати властивості
     * @param panel панель якій потрібно задати властивості
     */
    public void paint(JButton button, JPanel panel) {

        Font f = new Font("Century Gothic", Font.ITALIC, 22);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.white);
        button.setFont(f);

        panel.setBackground(new Color(228, 0, 23, 90));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(255, 0, 92, 90));

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(228, 0, 23, 90));
            }
        });
    }

}