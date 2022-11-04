package programming3.chatsys.data;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen 759517169@qq.com
 * TextDatabaseTest.java are most originally copied from teacher Maelick Claes (maelick.claes@oulu.fi)
 */

class TextDatabaseTest extends InMemoryDatabaseTest {
//    protected Database db;
    private static final File USER_DB_FILE = new File("test_user_db.txt");
    private static final File MESSAGE_DB_FILE = new File("test_message_db.txt");
    boolean a,b;
    /**
     * @since InMemoryDatabaseTest
     */
    @Override
    protected void initDatabase() {
        a = MESSAGE_DB_FILE.delete();
        b = USER_DB_FILE.delete();
//        a = MESSAGE_DB_FILE.delete();
//        b = USER_DB_FILE.delete();
//        System.out.println(a);
//        System.out.println(b);
        //Warning:(26, 22) Result of 'File.delete()' is ignored
        db1 = new TextDatabase(USER_DB_FILE, MESSAGE_DB_FILE);
    }

    @Test
    @Override
    public void close() {
            assertEquals(0, this.db1.getNumberUsers());
            this.testRegister();
            this.testAddMessage();
            //Why cannot find the method register() since I already extends InMemoryDatabaseTest extends
            //oooooooooo!!!! it's testRegister!!!!!!!!!!!!
            assertEquals(2, this.db1.getNumberUsers());
            super.close();
            Database freshDb = new TextDatabase(USER_DB_FILE, MESSAGE_DB_FILE);
            System.out.println(freshDb.getNumberUsers());
            assertEquals(this.db1.getUser("andychen"), freshDb.getUser("andychen"));
            assertEquals(this.db1.getUser("andrew"), freshDb.getUser("andrew"));
            assertEquals(this.db1.getNumberUsers(), freshDb.getNumberUsers());
    }
}
