package programming3.chatsys.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen 759517169@qq.com
 * UserTest.java are most originally copied from teacher Maelick Claes (maelick.claes@oulu.fi)
 */
class UserTest {

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;

    @BeforeEach
    void setUp() {
        user1 = new User("andychen", "Andy Chen", "thepassword");
        user2 = new User("andrew", "Andrew Chen", "andrewpassword");
        user3 = new User("andychen", "Andy Chen", "thepassword");
        user4 = new User("andychen", "Andy Chen", "anotherpassword");
        user5 = new User("andychen", "Andy Chen 2", "anotherpassword");
        user6 = user1;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testEquals() {
        assertSame(user1, user6);
        assertNotSame(user1, user3);
        assertEquals(user3, user1);
        assertNotEquals(user2, user1);
        assertEquals(user4, user1);
        assertEquals(user5, user1);
        assertEquals(user6, user1);
    }

    @Test
    void testFormat() {
        assertEquals("andychen\tAndy Chen\tthepassword\t0", user1.format());
    }

    @Test
    void testParse() {
        user1.parse("andychen\tAndy Chen\tthepassword\t0");
        assertEquals("andychen", user1.getUserName());
        assertEquals("Andy Chen", user1.getFullName());
//        assertThrows(IllegalArgumentException.class, () -> {
//            user1.parse("andychen\tAndy Chen");
//        });
        assertThrows(IllegalArgumentException.class, () -> user1.parse("andychen\tAndy Chen"));
    }
}