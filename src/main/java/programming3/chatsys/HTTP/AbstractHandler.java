package programming3.chatsys.HTTP;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @code <Notice that there are certain parts that are similar between each sequence diagram:
 *  1•
 *  MessageHandler and UnreadMessagesHandler both require to authenticate a user and parse
 *  username and password from the URI parameters.
 *  2•
 *  Both UnreadMessagesHandler and RecentMessagesHandler require to send a list of formatted
 *  messages in the response body.
 *  3•
 *  All handlers require sending a response.
 * @version 3.0
 * @author 陈新元 Andy Chen (759517169@qq.com)
 */
public abstract class AbstractHandler implements HttpHandler {
    // The AbstractHandler should implement the interface HttpHandler but define the handle method as abstract.
    public abstract void handle(HttpExchange exchange);

    /**
     * void sendResponse(HttpExchange exchange, int code, String response) that sends a response message
     * with a given code using the provided HttpExchange object
     * (i.e. writes the response message inside the response body OutputStream).
     * @param exchange
     * @param responseCode
     * @param response
     */
    protected void sendResponse(HttpExchange exchange, int responseCode, String response) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()))) {
            exchange.sendResponseHeaders(responseCode, 0);
            writer.write(response);
        } catch (IOException e) {
            System.out.println("Couldn't send response to client.");
        }
        exchange.close();
    }

    /**
     * String login(HttpExchange exchange) that parses the URI parameters from the exchange message and
     * returns the username of the user if successfully authenticated
     * @param exchange
     * @return
     */
    protected User parseUser(HttpExchange exchange){
        String line = exchange.getRequestURI().toString();
        System.out.println(line);
        String[] split = line.split("/", 3);
        String[] newsplit = split[2].replaceAll("\\?username=","").replaceAll("password=","").split("&",2);
        if (newsplit.length < 2){
            sendResponse(exchange,400,"Parsed URI should be formatted by /message/ ?username=<username> & password=<password>");
        }else {
            return new User(newsplit[0],"",newsplit[1]);
        }
        return null;
    }

    /**
     * void sendMessages(HttpExchange exchange, List<ChatMessage> messages) that formats a list of
     * ChatMessage and sends it as a response with the HTTP code 200.
     * @param exchange
     * @param messages
     */
    protected void sendMessages(HttpExchange exchange, List<ChatMessage> messages) {
        String response = "";
        for (ChatMessage m: messages) {
            response += m.getId() + "\t" + m.getUserName() + "\t";
            response += m.getMessage() + "\t" + m.getTimestamp().getTime() + "\r\n";
        }
        this.sendResponse(exchange, 200, response);
    }
}

