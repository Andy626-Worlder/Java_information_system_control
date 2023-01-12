package programming3.chatsys.HTTP;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.DatabaseAccessException;
import programming3.chatsys.data.User;

import java.io.*;

/**
 * The majority is the teacher's code.
 * @version 3.0
 * @author 陈新元 Andy Chen (759517169@qq.com)
 */

public class UserHandler extends AbstractHandler {
    private final HttpChatServer server;

    public UserHandler(HttpChatServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("POST")) {
            User user = parseUser(exchange);
            if (user != null) {
                handleUser(exchange, user);
            }
        } else {
            sendResponse(exchange, 405, "Request method should be POST");
        }
    }

    /**
     * @param exchange
     */
    @Override
    protected User parseUser(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            String line = reader.readLine();
            System.out.println(line);
            String[] split = line.split("\t", 3);
            if (split.length < 3) {
                String response = "User information in request body was not formatted properly\n";
                response += "User should be formatted as <username>\\t<fullname>\\t<password>";
                sendResponse(exchange, 400, "Request method should be POST");
            } else {
                return new User(split[0], split[1], split[2]);
            }
        } catch (IOException e) {
            sendResponse(exchange, 400, "Cannot read from request body.");
        } catch(NullPointerException e) {
            sendResponse(exchange, 400, "No response body provided.");
        }
        return null;
    }

    /**
     * @param exchange
     * @param user
     */
    private void handleUser(HttpExchange exchange, User user) {
        System.out.println("User information received from client: " + user);
        try {
            if (this.server.register(user)) {
                sendResponse(exchange, 201, "User " + user.getUserName() + " was successfully registered");
            } else {
                sendResponse(exchange, 400, "User " + user.getUserName() + " cannot be registered (e.g. username already taken)");
            }
        } catch(DatabaseAccessException e) {
            sendResponse(exchange, 500, "Database cannot be accessed.");
        }
    }
}
