package programming3.chatsys.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @version 3.0
 * @author 陈新元 Andy Chen
 */
public class SQLiteDatabase implements Database {
    private final Connection connection;
    private int lastId = 0;

    /**
     * @param url
     */
    public SQLiteDatabase(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            this.createUsersTable();
            this.createMessagesTable();
            lastId = getNumberMessages();
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @throws SQLException
     */
    private void createUsersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS user (\n" +
                "id integer PRIMARY KEY,\n" +
                "username text UNIQUE NOT NULL,\n" +
                "fullname text NOT NULL,\n" +
                "password text NOT NULL,\n" +
                "last_read_id integer DEFAULT 0\n" +
                ");";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
    }

    /**
     * @throws SQLException
     */
    private void createMessagesTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS chatmessage (\n" +
                "id integer PRIMARY KEY,\n" +
                "user integer NOT NULL,\n" +
                "time integer NOT NULL,\n" +
                "message text NOT NULL\n" +
                ");";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
    }

    /**
     * @param user the user to add.
     * @return
     */
    @Override
    public boolean register(User user) {
        String query = "INSERT INTO user(username, fullname, password) VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                return false;
            } else {
                throw new DatabaseAccessException(e);
            }
        }
    }


    @Override
    public int getNumberUsers() {
        String query = "SELECT COUNT(*) FROM user";
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            return statement.getResultSet().getInt(1);
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param userName The user's username.
     * @return
     */
    @Override
    public User getUser(String userName) {
        String query = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new User(
                        result.getString("username"),
                        result.getString("fullname"),
                        result.getString("password")
                );
            } else {
                throw new IllegalArgumentException(userName + " is not a registered user");
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param userName the name of the user.
     * @param password the password of the user.
     * @return
     */
    @Override
    public boolean authenticate(String userName, String password) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
            ResultSet result = statement.getResultSet();
            return result.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param userName user who sends the message.
     * @param message the message to add.
     * @return
     */
    @Override
    public ChatMessage addMessage(String userName, String message) {
        String query = "INSERT INTO chatmessage(user, time, message) " +
                "SELECT id, ?, ? FROM user WHERE username = ?";
        try {
            this.lastId++;
            ChatMessage chatMessage = new ChatMessage(this.lastId, userName, message);
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setLong(1, chatMessage.getTimestamp().getTime());
            statement.setString(2, message);
            statement.setString(3, userName);
            statement.execute();
            return chatMessage;
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public int getNumberMessages() {
        String query = "SELECT COUNT(*) FROM chatmessage";
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            return statement.getResultSet().getInt(1);
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param n the number of chat messages to read.
     * @return
     */
    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        String query = "SELECT * FROM (SELECT chatmessage.id, username, time, message FROM chatmessage, user " +
                "WHERE user.id = user ORDER BY chatmessage.id DESC LIMIT ?) ORDER BY id ASC";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, n);
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<ChatMessage> messages = new LinkedList<>();
            while (result.next()) {
                ChatMessage message = new ChatMessage(result.getInt(1),
                        result.getString(2),
                        result.getLong(3),
                        result.getString(4));
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param userName user.
     * @return
     */
    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        String query = "SELECT chatmessage.id, u1.username, time, message FROM chatmessage, user u1, user u2 " +
                "WHERE u1.id = user AND u2.username = ? AND u2.last_read_id < chatmessage.id";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<ChatMessage> messages = new LinkedList<>();
            while (result.next()) {
                ChatMessage message = new ChatMessage(result.getInt(1),
                        result.getString(2),
                        result.getLong(3),
                        result.getString(4));
                messages.add(message);
            }
            update(userName);
            return messages;
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * @param userName
     */
    public void update(String userName) {
        String query = "UPDATE user SET last_read_id = ? WHERE username = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, this.lastId);
            statement.setString(2, userName);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public void close() {

    }
}
