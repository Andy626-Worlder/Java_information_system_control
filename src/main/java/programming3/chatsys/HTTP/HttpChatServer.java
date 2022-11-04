package programming3.chatsys.HTTP;


import com.sun.net.httpserver.HttpServer;
import programming3.chatsys.HTTP.RecentMessagesHandler;
import programming3.chatsys.HTTP.UserHandler;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * The majority structure is teacher's code, I implemented a few methods to connect other handlers.
 *
 * @author 陈新元 Andy Chen (759517169@qq.com)
 * @version 3.0
 */

public class HttpChatServer {
    private final int port;
    private final Database database;
    private final String protocol;
    // It will need two class attributes: one for the
    //Database object and one for the HttpServer object (imported from the package com.sun.net.httpserver).



    /**
     *     Create a constructor that takes the Database object as parameter and the port on which to run the server.
     *     Initialize the HttpServer inside the constructor.
     * @param port
     * @param database
     */
    public HttpChatServer(int port, Database database) {
        this.port = port;
        this.database = database;
        this.protocol = "text";
    }

    /**
     * @param port
     * @param database
     * @param protocol
     */
    public HttpChatServer(int port, Database database, String protocol) {
        this.port = port;
        this.database = database;
        this.protocol = protocol;
    }



    /**
     *      Also add a public
     *     method start to start the server.
     * @throws IOException
     */
    public void start() throws IOException {
        //add a simple handler that just prints some information about the HTTP request.

        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        if (this.protocol.equals("json")) {
            server.createContext("/user/", new JSONUserHandler(this));
        } else {
            server.createContext("/user/", new UserHandler(this));
        }
        if (this.protocol.equals("json")) {
            server.createContext("/recent/", new JSONRecentMessagesHandler((this)));
        } else {
            server.createContext("/recent/", new RecentMessagesHandler(this));
        }
        if (this.protocol.equals("json")) {
            server.createContext("/unread/", new JSONUnreadMessagesHandler(this));
        } else {
            server.createContext("/unread/", new UnreadMessagesHandler(this));
        }
        server.createContext("/message/", new MessageHandler(this));
        server.start();
        System.out.println("Server is running");

    }


    //Implement the different methods that the handlers will need to call on the server: register(User user),
    //authenticate(User user), getUnreadMessages(String userName), getRecentMessages(int n) and
    //addMessage(String userName, String message). All of these methods are straightforward to implement as they
    //simply forward the call to the Database object.
    public boolean register(User user) {
        return this.database.register(user);
    }


    public List<ChatMessage> getRecentMessages(int n) {
        return this.database.getRecentMessages(n);
    }

    //Need to implement here myself

    /**
     * @param user
     * @return
     */
    public boolean authenticate(User user) {
        return this.database.authenticate(user.getUserName(), user.getPassword());
    }

    /**
     * @param username
     * @param message
     * @return
     */
    public ChatMessage addMessage(String username, String message) {
        return this.database.addMessage(username, message);
    }

    /**
     * @param username
     * @return
     */
    public List<ChatMessage> getUnreadMessages(String username) {
        return this.database.getUnreadMessages(username);
    }
}
