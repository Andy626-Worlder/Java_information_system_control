package programming3.chatsys.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.sql.Timestamp;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen 759517169@qq.com
 * notes: Some of the Commented lines are my first test on InMemoryDatabase before creating
 * TextDatabaseTest, but in oder not forgetting, I saved it there(not going to break the readability)
 * @since
 */

class InMemoryDatabaseTest {

//    private InMemoryDatabase db;  //引用方法
    protected User user1;
    protected User user2;
    protected User user3;
    protected User user4;
    protected User user5;
    protected User user6;
    protected Database db1;

    @BeforeEach
    void setUp() {//will be called every time before every test
//        db = new InMemoryDatabase();
//        db.register(new User("AndyChen", "Andy Chen", "correctpassword"));
//        db.addMessage("AndyChen","Hello Andy!");//this will return chatMessage

        user1 = new User("andychen", "Andy Chen", "thepassword");
        user2 = new User("andrew", "Andrew Chen", "andrewpassword");
        user3 = new User("andychen", "Andy Chen", "thepassword");
        user4 = new User("andychen", "Andy Chen", "anotherpassword");
        user5 = new User("andychen", "Andy Chen 2", "anotherpassword");
        user6 = user1;
        this.initDatabase();
    }

    protected void initDatabase() {
        db1 = new InMemoryDatabase();
    }

    @AfterEach
    void tearDown() {//will be called every time after every test
//        db.close(); //释放关闭db并释放内存
        db1.close();
    }


//    @Test
//    void testNewDatabase(){
//        int numberMessages = db.getNumberMessages();
//        int numberUsers = db.getNumberUsers();
//        assertEquals(numberMessages,numberUsers);{ // Don't know how to use the method
//            // -- maybe used it right.(don't have to be a void/ public / static
//            if (numberMessages == 1 && numberUsers == 1){
//                System.out.println("Right");
//                return;
//            }else {
//                throw new AssertionFailedError();
//            }
//        }
//    }

//    @Test
//    void testGetRecentMessages(){
//        System.out.println(db.getRecentMessages(1));
//        System.out.println(db.getRecentMessages(99999));
//        //Don't  know why the higher value has the same result.
//    }

    @Test
    void testRegister(){
//        db.register(new User("Andy Chen", "AndyChen","correctpassword"));
//        System.out.println(db.register(new User("Andy Chen", "AndyChen","correctpassword")));
//        db1.register(user1);
        assertTrue(db1.register(user1));
//        db1.register(user2);
        assertTrue(db1.register(user2));
        assertFalse(db1.register(user3));
        assertFalse(db1.register(user4));
        assertFalse(db1.register(user5));
        assertFalse(db1.register(user6));
    }


    @Test
    void testGetNumberUsers() {
        assertEquals(0, db1.getNumberUsers());
        db1.register(user1);
        assertEquals(1, db1.getNumberUsers());
        db1.register(user2);
        assertEquals(2, db1.getNumberUsers());
        db1.register(user3);
        assertEquals(2, db1.getNumberUsers());
        db1.register(user4);
        assertEquals(2, db1.getNumberUsers());
        db1.register(user5);
        assertEquals(2, db1.getNumberUsers());
        db1.register(user6);
        assertEquals(2, db1.getNumberUsers());
    }


    @Test
    void testGetUser() {
        db1.register(user1);
        db1.register(user2);
        assertEquals(user1, db1.getUser("andychen"));
        assertEquals(user2, db1.getUser("andrew"));
//        assertThrows(IllegalArgumentException.class, () -> db1.getUser("andy"));
        assertThrows(IllegalArgumentException.class, () -> {
            db1.getUser("andy");
        });
    }

    @Test
    void testAuthenticate() {// To distinguish from InMemoryDatabase's authenticate
//        //db.register(new User("AndyChen", "Andy Chen", "123456")); //构造器引入数值
//        assertTrue(db.authenticate("AndyChen", "correctpassword"));
//        assertFalse(db.authenticate("AndChen", "wrongpassword"));
//        assertFalse(db.authenticate("AndrewChen", "password"));
        db1.register(user1);
        assertTrue(db1.authenticate("andychen", "thepassword"));
        assertFalse(db1.authenticate("andychen", "wrongpassword"));
        assertFalse(db1.authenticate("andy", "thepassword"));
        }



    @Test
    void testAddMessage(){
//        db.addMessage("AndyChen","Hello Again !");
//        db1.register(user1);
//        db1.addMessage("andychen","Hello Again !");
//        ChatMessage c1 = db1.addMessage();
        db1.register(user1);
        db1.register(user2);
        db1.addMessage("andychen","Hello,andychen");
        ChatMessage c1 = db1.addMessage("andychen","Hello");
        ChatMessage c2 = db1.addMessage("andychen","Hello!");
        assertEquals(c1.getMessage(),"Hello");
        assertEquals(c2.getMessage(),"Hello!");
        Timestamp t1 = c1.getTimestamp();
        Timestamp t2 = c2.getTimestamp();
        assertEquals(t1.compareTo(t2),0);
        //Here to test.
//        System.out.println(db1.addMessage("AndyChen","Hello Again !")););
//        System.out.println(db1.addMessage("AndyChen","Hello Again !"));
    }


    @Test
    void getRecentMessages() {
//        System.out.println(db1.getRecentMessages(1));
//        System.out.println(db1.getRecentMessages(99999));
    }

    @Test
    void getUnreadMessages() {
    }

    @Test
    void close() {
//        this.db.close();
        this.db1.close();
    }


    // To make the test more reliable and trust-worthy, added one wrong input.
//    @Test
//    void testAddMessageInvalidUser(){
//        db.addMessage("AndrewChen", "Test Invalid");
//    }
}