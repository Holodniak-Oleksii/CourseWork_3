package sql;

/**
 *
 * @author Arsak Клас який спілкується із MySQL
 */
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class mySQL {

    private final String dbUrl = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=Cp1251";//рядок для зв’язку з БД дуже старий спосіб

    private String user = "root";//КОРИСТУВАЧ
    private String password = "";//ПАРОЛЬ
    private String Host = "127.0.0.1";
    private String Port = "3306";
    private String DB = "jdbc_test";
    private final String CP = "utf8";

    private Connection conn = null; //обєкт що представляє зєднання з БД
    private String tbl = "test";//ТАБЛИЦЯ З ЯКОЮ БУДЕМО ПРАЦЮВАТИ
    private Statement s = null;

    /**
     * Метод конструктор який задає відповідні значення
     * @param SERVER сервер
     * @param PORT порт
     * @param DB база даних
     * @param USR імя коритувача
     * @param PAS пароль
     * @param TBL таблиця
     */
    public mySQL(String SERVER, String PORT, String DB, String USR, String PAS, String TBL) {
        this.user = USR;
        this.password = PAS;
        this.Host = SERVER;
        this.Port = PORT;
        this.DB = DB;
        if (TBL != null) {
            tbl = TBL;
        }
    }

    /**
     * функція для зв'язку із БД
     *
     * @return Дані про успіше чи неуспішне приєднання
     */
    public String Conect() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(this.Host);
        dataSource.setDatabaseName(this.DB);
        dataSource.setPort(Integer.parseInt(this.Port));

        try {
            dataSource.setServerTimezone("UTC");
        } catch (SQLException ex) {
            System.out.println("Err on setting Timezone :\n" + ex.toString());
        }
        try {
            dataSource.setCharacterEncoding(CP);
        } catch (SQLException ex) {
            System.out.println("Err on setting CP :\n" + ex.toString());
        }
        try {
            conn = dataSource.getConnection();
            return "Ok";
        } catch (SQLException ex) {
            System.out.println("Err on getting connection :\n" + ex.toString());
        }
        return "not Ok";
    }

    /**
     * @param info Метод який отримує інформацію із певної таблиці і певного
     * стовпчика
     * @return rez Дані із таблиці у вигляді великого рядка
     */
    public String GetInfo(String info) {
        String rez = "Помилка!!!";
        try {
            if (s == null) {
                s = conn.createStatement();
            }
            ResultSet r;
            r = s.executeQuery(
                    "SELECT `experience` FROM `admin_of_space` WHERE `name` = '" + info + "'");

            rez = "";
            while (r.next()) {
                rez += r.getString(1);
            }

        } catch (SQLException e) {
            return rez;
        }
        return rez;
    }

    /**
     * Метод який вставлеє інформацію у таблицю admin_of_space
     * @param id1 імя
     * @param id2 пароль
     * @param id3 доствід
     * @param id4 ранг
     */
    public void insert(String id1, String id2, String id3, String id4) {
        try {
            String mySQLquery;
            mySQLquery
                    = "INSERT INTO `" + tbl + "` "
                    + "(`name`, `password`, `experience`, `rang`)"
                    + "VALUES (?, ?, ?, ?);";
            PreparedStatement X = (PreparedStatement) conn.prepareStatement(mySQLquery);
            X.setString(1, id1);
            X.setString(2, id2);
            X.setString(3, id3);
            X.setString(4, id4);
            X.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Перевірте введені дані");
        }
    }

    /**
     * Функція яка Встановлює ранг 
     * @param id1 досвід
     * @param id2 імя
     */
    public void insertRANG(String id1, String id2) {
        try {
            String mySQLquery;
            mySQLquery
                    = "UPDATE admin_of_space SET experience = '" + id2 + "' WHERE name = '" + id1 + "';";
            PreparedStatement X = (PreparedStatement) conn.prepareStatement(mySQLquery);
            X.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Перевірте введені дані" + e);
        }
    }

    /**
     * Метод який пднімає рівень гравця
     * @param count рахунок балів
     * @param user імя користувача
     */
    public void UP_Level(int count, String user) {
        try {
            
            if (count >= 100 && count < 300) {
                String mySQLquery;
                mySQLquery = "UPDATE `admin_of_space` SET `rang` = 'res\\\\rang\\\\Даймьо.png' WHERE `name` = '" + user + "';";
                PreparedStatement X = (PreparedStatement) conn.prepareStatement(mySQLquery);
                X.execute();
            }
            if (count >= 300 && count < 500) {
                String mySQLquery;
                mySQLquery = "UPDATE `admin_of_space` SET `rang` = 'res\\\\rang\\\\Сьоґун.png' WHERE `name` = '" + user + "';";
                PreparedStatement X = (PreparedStatement) conn.prepareStatement(mySQLquery);
                X.execute();
            }
            if (count >= 500) {
                String mySQLquery;
                mySQLquery = "UPDATE `admin_of_space` SET `rang` = 'res\\\\rang\\\\Тенно.png' WHERE `name` = '" + user + "';";
                PreparedStatement X = (PreparedStatement) conn.prepareStatement(mySQLquery);
                X.execute();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Перевірте введені дані" + e);
        }
    }

    /**
     * Метод якиій дістає дані із admin_of_space відсортовані по полю experience
     * @return дані із БД
     */
    public String GetCount() {
        String rez = "Помилка!!!";
        int i;
        try {
            if (s == null) {
                s = conn.createStatement();
            }
            ResultSet r;
            r = s.executeQuery(
                    "SELECT `name`, `experience`, `rang` FROM admin_of_space ORDER BY experience DESC"
            );
            ResultSetMetaData colonki = r.getMetaData();
            i = colonki.getColumnCount();
            rez = "";
            while (r.next()) {
                rez += "\n";
                for (int j = 1; j <= i; j++) {
                    rez += r.getString(j) + "@";
                }
            }
        } catch (SQLException e) {
            return rez;
        }
        return rez;
    }

    /**
     * @return conn обєкт що представляє зєднання з БД
     */
    public Connection getCon() {
        return conn;
    }

    /**
     * закриття звязку із БД
     */
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
