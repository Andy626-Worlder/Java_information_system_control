package programming3.chatsys.HTTP;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.HTTP.AbstractHandler;
import programming3.chatsys.HTTP.HttpChatServer;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.DatabaseAccessException;

import java.util.List;

/**
 * Teacher's code mostly.
 * @version 3.0
 * @author 陈新元 Andy Chen (759517169@qq.com)
 */
public class RecentMessagesHandler extends AbstractHandler {
    private final HttpChatServer server;

    public RecentMessagesHandler(HttpChatServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("GET")) {
            try {
                sendMessages(exchange, parseN(exchange));
            } catch(NumberFormatException e) {
                sendResponse(exchange, 400, "Number of chat messages should be provided in URL");
            }
        } else {
            sendResponse(exchange, 405, "Request method should be GET");
        }
    }

    private void sendMessages(HttpExchange exchange, int n) {
        if (n > 0) {
            List<ChatMessage> messages = this.server.getRecentMessages(n);
            try {
                sendMessages(exchange, messages);
            // Database Access Exception
            } catch(DatabaseAccessException e) {
                sendResponse(exchange, 500, "Database cannot be accessed.");
            }
            // n < 0 or parsing error.
        } else {
            sendResponse(exchange, 400, "Number of chat messages should be > 0");
        }
    }

    public void sendMessages(HttpExchange exchange, List<ChatMessage> messages) {
        String response = "";
        for (ChatMessage message: messages) {
            response += message.getId() + "\t" + message.getUserName() + "\t";
            response += message.getMessage() + "\t" + message.getTimestamp().getTime() + "\r\n";
        }
        this.sendResponse(exchange, 200, response);
    }

    //n:= parseN(HttpExchange exchange)
    private int parseN(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String context = exchange.getHttpContext().getPath();
        return Integer.parseInt(path.substring(context.length()));
    }
}
