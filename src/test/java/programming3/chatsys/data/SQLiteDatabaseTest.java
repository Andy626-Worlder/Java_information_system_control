package programming3.chatsys.data;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen
 */
class SQLiteDatabaseTest extends InMemoryDatabaseTest {
    private final File DB_FILE = new File("test_db.sqlite");

    @Override
    protected void initDatabase() {
        DB_FILE.delete();
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE.getPath());
//            connection.createStatement().execute("DROP TABLE user");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        db1 = new SQLiteDatabase("jdbc:sqlite:" + DB_FILE.getPath());
    }

    @Test
    @Override
    public void close() {
        assertEquals(0, this.db1.getNumberUsers());
        this.testRegister();
        assertEquals(2, this.db1.getNumberUsers());
        super.close();
        Database freshDb = new SQLiteDatabase("jdbc:sqlite:" + DB_FILE.getPath());
        System.out.println(freshDb.getNumberUsers());
        assertEquals(2, freshDb.getNumberUsers());
        assertEquals(user1, freshDb.getUser("johndoe"));
        assertEquals(user2, freshDb.getUser("jane"));
        freshDb.close();
    }
}