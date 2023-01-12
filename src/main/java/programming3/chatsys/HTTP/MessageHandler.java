package programming3.chatsys.HTTP;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.DatabaseAccessException;
import programming3.chatsys.data.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @version 3.0
 * @author 陈新元 Andy Chen (759517169@qq.com)
 */

public class MessageHandler extends AbstractHandler {
    private final HttpChatServer server;

    public MessageHandler(HttpChatServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("POST")) {
            User u = parseUser(exchange);
            handleMessage(exchange,u);
        }else {
            sendResponse(exchange,405,"Request method should be POST");
        }
    }


    /**
     * @param exchange
     * @param user
     */
    private void handleMessage(HttpExchange exchange,User user){
        try{
            if (this.server.authenticate(user)){
                String message = this.parseMessage(exchange);
                try{
                    this.server.addMessage(user.getUserName(),message);
                    sendResponse(exchange,201,"Message is successfully add.");
                } catch (DatabaseAccessException e){
                    sendResponse(exchange,500,"2-Database cannot be accessed-2.");
                }
            } else {
                sendResponse(exchange,401,"the userName doesn't correspond to an existing user");
            }
        } catch (DatabaseAccessException e){
            sendResponse(exchange,500,"1-Database cannot be accessed-1.");
        }
    }

    /**
     * @param exchange
     */
    private String parseMessage(HttpExchange exchange){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            String line = reader.readLine();
            System.out.println(line);
            return line;
        } catch (IOException e) {
            sendResponse(exchange, 400, "Cannot read from request body.");
        } catch(NullPointerException e) {
            sendResponse(exchange, 400, "No response body provided.");
        }
        return null;
    }

}
