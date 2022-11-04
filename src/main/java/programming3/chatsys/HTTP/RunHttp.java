package programming3.chatsys.HTTP;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen (759517169@qq.com)
 */
import programming3.chatsys.data.Database;
import programming3.chatsys.data.InMemoryDatabase;
import programming3.chatsys.data.SQLiteDatabase;

import java.io.IOException;

public class RunHttp {
    public static void main(String[] args) {
//        Database db = new InMemoryDatabase();
        Database db = new SQLiteDatabase("jdbc:sqlite:sqlite.db");
        HttpChatServer server = new HttpChatServer(88, db, "json");//GUI
        HttpChatServer server1 = new HttpChatServer(87, db); // TO test the GUI

        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Server could not be started.");
            e.printStackTrace();
        }
        try {
            server1.start();
        } catch (IOException e) {
            System.out.println("Server could not be started.");
            e.printStackTrace();
        }
    }
}

