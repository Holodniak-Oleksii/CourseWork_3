package tools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Клас для відорадення загрузки
 * @author Arsak
 */
public class Loading extends JFrame {

    /**
     * Конструктор загрузочного меню
     */
    public Loading() {
        ShowPanel panel = new ShowPanel();
        panel.setBounds(480, 285, 960, 110);
        panel.setOpaque(false);
        setContentPane(new ContentPane());
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        Toolkit too = Toolkit.getDefaultToolkit();
        Dimension dim = too.getScreenSize();

        setBounds(dim.width / 2 - 480, dim.height / 2 - 285, 960, 570);
        setUndecorated(true);
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        File file = new File("res\\ico\\icon.jpeg");
        try {
            setIconImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(Loading.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timer timer = new Timer(8, (ActionEvent e) -> {
            repaint();
        });
        timer.start();
        add(panel);
        setVisible(true);

    }

}
