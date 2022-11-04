package programming3.chatsys.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen
 */
public class InMemoryDatabase  implements Database{
    protected List<User> users;
    protected List<ChatMessage> messages;
    private int lastId = 0;

    /**
     * Create a new empty InMemoryDatabase.
     */
    public InMemoryDatabase() {
        System.out.println("In InMemoryDatabase constructor");
        users = new LinkedList<>();
        messages = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "InMemoryDatabase{" +
                "users=" + users +
                '}';
    }


    /**
     * @param user the user to add.
     * @return
     */
    @Override
    public boolean register(User user) {
        if (this.contains(user)) {
            return false;
        } else {
            this.users.add(user);
            return true;
        }
    }


    /**
     * Checks whether a User with the same userName is already present in the Database.
     *
     * @param user the User to check for inclusion.
     * @return true if there is already a User with the same userName in the Database.
     */
    private boolean contains(User user) {
        return this.contains(user.getUserName());
    }

    /**
     * Checks whether a User with the same userName is already present in the Database.
     *
     * @param userName the userName to check for inclusion.
     * @return true if there is already a User with the given userName in the Database.
     */
    private boolean contains(String userName) {
        try {
            return this.getUser(userName) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public int getNumberUsers() {
        return this.users.size();
    }

    /**
     * @param userName The user's username.
     * @return
     */
    @Override
    public User getUser(String userName) {
        for (User u: this.users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        throw new IllegalArgumentException(userName + " is not a registered user");
    }


    /**
     * @param userName the name of the user.
     * @param password the password of the user.
     * @return
     */
    @Override
    public boolean authenticate(String userName, String password) {
        try {
            System.out.println("Authenticated !");// To show something in the test. Otherwise will be hard to notice how many true before false.
            return Objects.equals(this.getUser(userName).getPassword(), password);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong Input");
            return false;
        }
    }

    /**
     * @param userName user who sends the message.
     * @param message the message to add.
     * @return
     */
    @Override
    public ChatMessage addMessage(String userName, String message) {
        if (this.contains(userName)) {
            this.lastId++;
            ChatMessage chatMessage = new ChatMessage(this.lastId, userName, message);
            this.messages.add(chatMessage);
            return chatMessage;
        } else {
            throw new IllegalArgumentException("[User] " + userName + " is not registered.");
        }
    }


    @Override
    public int getNumberMessages() {
        return this.messages.size();
    }

    /**
     * @param n the number of chat messages to read.
     * @return
     */
    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        if (n > 0 && n < this.messages.size()) {
            return messages.subList(messages.size() - n, messages.size());//从哪个位置到那个位置（不到最后那个）
        } else {
            return new LinkedList<>(messages);
        }
    }


    /**
     * @param userName user.
     * @return
     */
    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        User user = this.getUser(userName);
        final int lastReadId = user.getLastReadId();
        if (lastReadId == this.lastId) {
            return new LinkedList<>();
        } else {
            int firstUnread = 0;
            for (ChatMessage m : this.messages) {
                firstUnread = m.getId();
                if (firstUnread > lastReadId) {
                    break;
                }
            }
            this.getUser(userName).setLastReadId(this.lastId);
            return this.messages.subList(firstUnread - 1, this.messages.size());
        }
    }

    @Override
    public void close()  {
        /* Save the data into files right here.
        * o This has the advantage of being the simplest solution
        o However, it means we might lose data if our program ends unexpectedly (e.g. unhandled
        exception, computer crash, power outage, etc) or if we forget to close the database
        o Calling the close method can be slow if we have a very big database */
    }
}
