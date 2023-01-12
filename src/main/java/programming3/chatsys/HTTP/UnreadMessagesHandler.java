package programming3.chatsys.HTTP;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.HTTP.AbstractHandler;
import programming3.chatsys.HTTP.HttpChatServer;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.DatabaseAccessException;
import programming3.chatsys.data.User;

import java.util.List;

/**
 *  @version 3.0
 *  @author 陈新元 Andy Chen (759517169@qq.com)
 */


public class UnreadMessagesHandler extends AbstractHandler {
    private final HttpChatServer server;

    public UnreadMessagesHandler(HttpChatServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("GET")) {
            User user = parseUser(exchange);
            handleUnreadMessages(exchange,user);
        } else{
            sendResponse(exchange, 405, "Request method should be GET");
        }
    }
    private void handleUnreadMessages(HttpExchange exchange, User user){
        try{
            if (this.server.authenticate(user)){
                try {
                    List<ChatMessage> ch = this.server.getUnreadMessages(user.getUserName());
                    sendMessages(exchange,ch);
                    sendResponse(exchange,201,"get the UnreadMessages");
                } catch (DatabaseAccessException e){
                    sendResponse(exchange,500,"Database cannot be accessed.");
                }
            } else {
                sendResponse(exchange,401,"the userName doesn't correspond to an existing user");
            }
        } catch (DatabaseAccessException e){
            sendResponse(exchange,500,"1-Database cannot be accessed-1.");
        }
    }
}
