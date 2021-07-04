package network;

import fight.CoolMenu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Клас який реалізує Сервер
 *
 * @author Arsak
 */
public class Server implements Runnable {

    /**
     * Зміна у яку передається інформація із мережі
     */
    public String Info;
    ServerSocket ss;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;

    /**
     * Метод який запускає потік
     */
    public void go() {
        Thread y = new Thread(this);
        y.setName("Переписка2");
        y.start();
    }

    /**
     * У цьому методі створюємо зєнання, також передаємо і дістаємо данні
     */
    @Override
    public void run() {
        String massage = "";
        try {
            ss = new ServerSocket(CoolMenu.PORT);
            s = ss.accept();

            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                massage = din.readUTF();
                setLoc(massage);
            }

        } catch (Exception e) {
        }
    }

    /**
     * Метод яеий надсилає повідрмлення
     *
     * @param msg_out рядок який потрібно надіслати
     *
     */
    public void setInfo(String msg_out) throws IOException {
        try {
            dout.writeUTF(msg_out);
        } catch (java.net.SocketException ex) {

        }
    }

    /**
     * метод якйи задає зміній Info іформацію із мережі
     *
     * @param o
     */
    public void setLoc(String o) {
        Info = o;
    }

    /**
     * Метод який зупиняє зєднання
     */
    public void Stop() throws IOException {
        dout.flush();
        dout.close();
        din.close();
        s.close();
        ss.close();
        Thread.currentThread().interrupt();
    }
}
