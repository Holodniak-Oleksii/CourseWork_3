package fight;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import mechanics.Aster;
import network.ClientGame;
import network.ServerGame;
import tools.Draw;
import tools.JButtonCircle;
import tools.JNiceField;

/**
 * Даний клас призначений для відтворення головного меню програми, яке у свою
 * чергу допомагає користувачу використовувати весь функціонал програми
 * @author Arsak
 */
public class CoolMenu extends JFrame {

    static int wave,que;; // частота появлення астероїдів
    /**
     * Зміна у який зберігаться порт
     */
    public static int PORT;

    /**
     * Зміна у який зберігаться IP-адрес
     */
    public static String IP_Adress;
//    mySQL SQL;
    DefaultTableModel model = new DefaultTableModel();

    /**
     * Метод конструктор у якому задається всі основні компоненти навігації 
     * за допомогою циклу заповнюється таблиця із рейтингом крім цього взалежносьті
     * від вибору користувача задаються фото для певної локації 
     */
    public CoolMenu() {
        Game g = new Game();
        Font f = new Font("Century Gothic", Font.ITALIC, 18);        
        // Створюємо ТБ 
//        SQL = new mySQL(
//                "127.0.0.1",
//                "3306",
//                "space",
//                "root",
//                "",
//                "admin_of_space"
//        );
//        String rez = SQL.Conect();
//        String data_table = SQL.GetCount();
//        String[] columnsHeader = new String[]{"Імя користувача", "Кількість збитих об'єктів", "Ранг"};
//        String[] words = data_table.split("\n");
//
//        String separate = "";
//        for (String word : words) {
//            separate += word;
//        }
//        String[] data = separate.split("@");
//        String[][] array = new String[data.length / 3][3];
//        int count = 0;
//
//        for (int i = 0; i < data.length / 3; i++) {
//            for (int J = 0; J < 3; J++) {
//                array[i][J] = data[count];
//                count++;
//            }
//        }
//        model.setDataVector(array, columnsHeader); // створюємо модель таблиці
//
//        JTable table = new JTable() {
//            private static final long serialVersionUID = 1L;
//
//            /**
//             * дана дія домож уникнути редагування яйачейок при двойному кліку
//             */
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        ;
//        };
//
//        table.setModel(model);
//        table.setRowHeight(10);
//        table.getColumn("Ранг").setCellRenderer(new CellRenderer());
//        Box contents = new Box(BoxLayout.Y_AXIS);
//        contents.setBackground(new Color(22, 33, 62));
//        contents.setBounds(100, 50, 600, 330);
//        contents.setVisible(false);
//        ViewTable(table, contents);

        JPanel boxesPanel = new JPanel();

        ButtonGroup group = new ButtonGroup();
        JRadioButton easyBox = new JRadioButton("Легкий");
        JRadioButton normBox = new JRadioButton("Нормальний");
        JRadioButton hardBox = new JRadioButton("Важкий");

        easyBox.setBackground(new Color(200, 20, 45));
        normBox.setBackground(new Color(200, 20, 45));
        hardBox.setBackground(new Color(200, 20, 45));

        easyBox.setForeground(Color.white);
        normBox.setForeground(Color.white);
        hardBox.setForeground(Color.white);

        easyBox.setFont(f);
        normBox.setFont(f);
        hardBox.setFont(f);

        easyBox.setFocusPainted(false);
        normBox.setFocusPainted(false);
        hardBox.setFocusPainted(false);

        group.add(easyBox);
        group.add(normBox);
        group.add(hardBox);

        boxesPanel.add(easyBox);
        boxesPanel.add(normBox);
        boxesPanel.add(hardBox);

        boxesPanel.setBounds(225, 370, 350, 40);
        boxesPanel.setBackground(new Color(152, 15, 34, 90));

        boxesPanel.setVisible(false);
        JLabel jL = new JLabel();
        File file = new File("res\\ico\\icon.jpeg");
        try {
            setIconImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(CoolMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // створюємо рисунки для елементів
        ImageIcon b_blue = new ImageIcon("res\\control\\b1.png");
        ImageIcon b_red = new ImageIcon("res\\control\\b0.png");

        ImageIcon b_earth1 = new ImageIcon("res\\control\\Earth1.png");
        ImageIcon b_mars1 = new ImageIcon("res\\control\\Mars1.png");

        ImageIcon b_earth2 = new ImageIcon("res\\control\\Earth2.png");
        ImageIcon b_mars2 = new ImageIcon("res\\control\\Mars2.png");

        Image image = Toolkit.getDefaultToolkit().createImage("res\\gif\\view.gif");
        ImageIcon imageIcon = new ImageIcon(image);
        imageIcon.setImageObserver(jL);
        jL.setIcon(imageIcon);


        Toolkit too = Toolkit.getDefaultToolkit(); // інструмент за замвчуванням
        Dimension dim = too.getScreenSize(); //  Dimension біліотека для визначення розміру чогось тут екрана у dim розмір екрану
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // фігня яка закриває фрейм при натиску на хрестик

        setBounds(dim.width / 2 - 403, dim.height / 2 - 244, 806, 488); // це для розташування фрейма по центру
        setTitle("Menu"); // назва

        Font fot = new Font("Century Gothic", Font.ITALIC, 16);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(228, 0, 23, 90));
        panel.setBounds(100, 50, 600, 330);
        panel.setVisible(false);

        JTextArea Info = new JTextArea();
        Info.setBounds(80, 10, 650, 420);
        Info.setLineWrap(true);
        Font text = new Font("Century Gothic", Font.ITALIC, 12);
        Info.setFont(text);
        Info.setForeground(Color.black);
        Info.setText(ReadFile("res\\info\\IamI.txt"));
        Info.setVisible(false);
        Info.setEditable(false);
        Info.setCursor(null);
        Info.setFocusable(false);
        Info.setBackground(new Color(65, 105, 225, 98));
        Info.setForeground(Color.white);

        JNiceField port = new JNiceField();
        port.setFont(fot);
        port.setBounds(203, 150, 400, 50);
        Registration.placeholder(port, "Введіть номер кіманти для підключення");
        port.setVisible(false);

        JNiceField IP = new JNiceField();
        IP.setFont(fot);
        IP.setBounds(203, 230, 400, 50);
        Registration.placeholder(IP, "Введіть IP адрес");
        IP.setVisible(false);

        JButton along = new JButton("Одиночна гра");
        JPanel along1 = new JPanel();

        JButton go_Net_game = new JButton("Гра по мережі");
        JPanel go_Net_game_panel = new JPanel();

        JButton rating = new JButton("Рейтинг");
        JPanel p3 = new JPanel();

        JButton exit = new JButton("Вихід");
        JPanel exit_panel = new JPanel();

        JButtonCircle b5 = new JButtonCircle("і");
        JButton create_room = new JButton("Створити кімнату");
        JPanel create_room_panel = new JPanel();
        JButton join = new JButton("Приєднатися до гри");

        JPanel join_panel = new JPanel();
        join_panel.setVisible(false);
        create_room.setVisible(false);
        create_room_panel.setVisible(false);
        join.setVisible(false);
        // кнопки локацій
        JButton Button_mars = new JButton();
        JButton Button_earth = new JButton();
        // задаємо властивості компонентам
        Button_mars.setIcon(b_mars1);
        Button_mars.setBounds(425, 100, 300, 210);
        Button_mars.setBorderPainted(false);
        Button_mars.setFocusPainted(false);
        Button_mars.setContentAreaFilled(false);
        Button_mars.setVisible(false);

        Button_earth.setBounds(75, 100, 300, 210);
        Button_earth.setBorderPainted(false);
        Button_earth.setFocusPainted(false);
        Button_earth.setContentAreaFilled(false);
        Button_earth.setVisible(false);
        Button_earth.setIcon(b_earth1);
        // лейбл виходу
        JLabel go_out = new JLabel();
        go_out.setIcon(b_blue);
        go_out.setBounds(20, 380, 50, 50);
        go_out.setOpaque(false);
        go_out.setVisible(false);

        go_out.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                join_panel.setVisible(false);
                create_room.setVisible(false);
                create_room_panel.setVisible(false);
                join.setVisible(false);
  //              contents.setVisible(false);
                Button_mars.setVisible(false);
                Button_earth.setVisible(false);
                boxesPanel.setVisible(false);
                along.setVisible(true);

                Info.setVisible(false);

                go_Net_game.setVisible(true);
                rating.setVisible(true);
                exit.setVisible(true);
                exit.setEnabled(true);
                b5.setVisible(true);

                along1.setVisible(true);
                go_Net_game_panel.setVisible(true);
                p3.setVisible(true);
                exit_panel.setVisible(true);
                port.setVisible(false);
                IP.setVisible(false);
                go_out.setVisible(false);
                panel.setVisible(false);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                go_out.setIcon(b_red);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                go_out.setIcon(b_blue);
            }
        });
        Button_earth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean buttonGroup1 = false;
                Enumeration<AbstractButton> allRadioButton = group.getElements();
                while (allRadioButton.hasMoreElements()) {
                    JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
                    if (temp.isSelected()) {
                        buttonGroup1 = true;

                        setVisible(false);
                        Aster.plus = 1;
                        g.inoprishlenzi = "d";
                        g.fon = "res\\gif\\space.gif";
                        g.planet = "res\\gif\\earth.gif";
                        g.boat = "res\\img\\spaceship.png";
                        g.bullshit = 1;
                        g.game();

                        break;
                    }
                }
                if (!buttonGroup1) {
                    JOptionPane.showMessageDialog(null, "Будь ласка вибери рівень складності перш ніж починати гру");
                }

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Button_earth.setIcon(b_earth2);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Button_earth.setIcon(b_earth1);
            }
        });

        Button_mars.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean buttonGroup1 = false;
                Enumeration<AbstractButton> allRadioButton = group.getElements();
                while (allRadioButton.hasMoreElements()) {
                    JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
                    if (temp.isSelected()) {
                        setVisible(false);
                        Aster.plus = 1;
                        g.inoprishlenzi = "f";
                        g.fon = "res\\gif\\space_around_mars.gif";
                        g.planet = "res\\gif\\Mars.gif";
                        g.boat = "res\\img\\mars-spaceship.png";
                        g.bullshit = 2;
                        g.game();
                        buttonGroup1 = true;
                        break;
                    }
                }
                if (!buttonGroup1) {
                    JOptionPane.showMessageDialog(null, "Будь ласка вибери рівень складності перш ніж починати гру");
                }

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Button_mars.setIcon(b_mars2);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Button_mars.setIcon(b_mars1);
            }
        });

        along.addActionListener((ActionEvent e) -> {
    //        contents.setVisible(false);
            Button_mars.setVisible(true);
            Button_earth.setVisible(true);
            boxesPanel.setVisible(true);
            along.setVisible(false);
            go_Net_game.setVisible(false);
            rating.setVisible(false);
            exit.setVisible(false);
            b5.setVisible(false);
            along1.setVisible(false);
            go_Net_game_panel.setVisible(false);
            p3.setVisible(false);
            exit_panel.setVisible(false);
            go_out.setVisible(true);

            panel.setVisible(false);
        });
        go_Net_game.addActionListener((ActionEvent e) -> {
      //      contents.setVisible(false);
            along.setVisible(false);
            go_Net_game.setVisible(false);
            rating.setVisible(false);
            exit.setVisible(false);
            b5.setVisible(false);

            join_panel.setVisible(true);
            create_room.setVisible(true);
            create_room_panel.setVisible(true);
            join.setVisible(true);

            Button_mars.setVisible(false);
            Button_earth.setVisible(false);
            along1.setVisible(false);
            boxesPanel.setVisible(false);

            go_Net_game_panel.setVisible(false);
            p3.setVisible(false);
            exit_panel.setVisible(false);
            go_out.setVisible(true);
            panel.setVisible(false);
            port.setVisible(true);
            IP.setVisible(true);

        });

        rating.addActionListener((ActionEvent e) -> {
        //    contents.setVisible(true);
            along.setVisible(false);
            go_Net_game.setVisible(false);
            rating.setVisible(false);
            exit.setVisible(false);
            b5.setVisible(false);
            boxesPanel.setVisible(false);
            along1.setVisible(false);
            go_Net_game_panel.setVisible(false);
            p3.setVisible(false);
            exit_panel.setVisible(false);
            Button_mars.setVisible(false);
            Button_earth.setVisible(false);
            go_out.setVisible(true);
            panel.setVisible(true);

        });
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        b5.addActionListener((ActionEvent e) -> {
            Info.setVisible(true);
            panel.setVisible(false);

          //  contents.setVisible(false);
            along.setVisible(false);
            go_Net_game.setVisible(false);
            rating.setVisible(false);
            exit.setVisible(false);
            b5.setVisible(false);
            boxesPanel.setVisible(false);
            along1.setVisible(false);
            go_Net_game_panel.setVisible(false);
            p3.setVisible(false);
            exit_panel.setVisible(false);
            Button_mars.setVisible(false);
            Button_earth.setVisible(false);
            go_out.setVisible(true);

        });

        join.addActionListener((ActionEvent e) -> {
            try {
                PORT = Integer.valueOf(port.getText());
                IP_Adress = IP.getText();
                ClientGame k = new ClientGame();
                k.goGame();
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Неправильно введені данні", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        create_room.addActionListener((ActionEvent e) -> {
            try {
                PORT = Integer.valueOf(port.getText());
                IP_Adress = IP.getText();
                ServerGame h = new ServerGame();
                h.goGame();
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Неправильно введені данні", "Помилка", JOptionPane.ERROR_MESSAGE);
            }

        });

        // задаємо частоту появлення астероїдів
        easyBox.addItemListener((ItemEvent e) -> {
            wave = 800;
            que = 800;
        });

        normBox.addItemListener((ItemEvent e) -> {
            wave = 600;
            que = 600;
        });

        hardBox.addItemListener((ItemEvent e) -> {
            wave = 400;
            que = 400;
        });

        along1.setBounds(30, 50, 260, 70);
        along.setBounds(30, 50, 260, 70);

        go_Net_game_panel.setBounds(30, 140, 260, 70);
        go_Net_game.setBounds(30, 140, 260, 70);

        p3.setBounds(30, 230, 260, 70);
        rating.setBounds(30, 230, 260, 70);

        exit_panel.setBounds(30, 320, 260, 70);
        exit.setBounds(30, 320, 260, 70);

        b5.setBounds(740, 400, 40, 40);
        b5.setBorderPainted(false);
        b5.setFocusPainted(false);
        b5.setContentAreaFilled(false);
        b5.setForeground(Color.white);
        b5.setFont(f);
        create_room.setBounds(410, 30, 300, 70);
        create_room_panel.setBounds(410, 30, 300, 70);

        join_panel.setBounds(80, 30, 300, 70);
        join.setBounds(80, 30, 300, 70);

        Draw vis = new Draw();
        vis.paint(join, join_panel);
        vis.paint(create_room, create_room_panel);
        vis.paint(along, along1);
        vis.paint(go_Net_game, go_Net_game_panel);
        vis.paint(rating, p3);
        vis.paint(exit, exit_panel);

        add(port);
        add(along1);
        add(along);
        add(go_Net_game);
        add(go_Net_game_panel);
        add(IP);
        add(Button_mars);
        add(Button_earth);
        add(p3);
        add(rating);
        add(create_room_panel);
        add(create_room);
        add(join_panel);
        add(join);
        add(exit_panel);
        add(Info);
        add(exit);
    //    add(contents);
        add(b5);
        add(go_out);
        add(panel);
        add(boxesPanel);
        add(jL);

        setVisible(true);
        setResizable(false); // для блокування маштабування
    }

    /**
     * Функція для читаня файлів
     * @param name шлях за яким потрібно прочитати файл-txt
     * @return rezult прочитаний рядок
     */
    public String ReadFile(String name) {
        String rezult = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(name), "UTF-8"));
            while (in.ready()) {
                rezult += in.readLine() + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rezult;
    }

    /**
     * Метод який задає певні властивості для таблиці такі як колір scroll heder...
     * @param table таблиця яку потрібно редагувати
     * @param contents коробка на якій задається таблиця
     */
    public void ViewTable(JTable table, Box contents) {
        JTableHeader heder = table.getTableHeader();
        heder.setBackground(new Color(233, 69, 96));
        heder.setForeground(Color.black);
        heder.setFont(new Font("Tahome", Font.BOLD, 14));
        
        ((DefaultTableCellRenderer) heder.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        table.setBackground(new Color(22, 33, 62));
        table.setForeground(new Color(233, 69, 96));
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setGridColor(Color.red);
        table.setShowVerticalLines(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(22, 33, 62));
        scroll.setBorder(new LineBorder(new Color(26, 26, 46), 10)); // це і
        contents.add(scroll);
        contents.setBorder(new LineBorder(new Color(228, 0, 23, 90), 10)); // це стоврює та закрашує бордер таким чином ми можемо зробити цікавий "візерунок"
    }
}


