package programming3.chatsys.HTTP;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.HTTP.protocol.JSONProtocol;
import programming3.chatsys.data.ChatMessage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class JSONRecentMessagesHandler extends RecentMessagesHandler {

    public JSONRecentMessagesHandler(HttpChatServer server) {
        super(server);
    }

    /**
     * @param exchange
     * @param messages
     */
    @Override
    public void sendMessages(HttpExchange exchange, List<ChatMessage> messages) {
        StringWriter stringWriter = new StringWriter();
        JSONProtocol protocol = new JSONProtocol(new BufferedWriter(stringWriter));
        try {
            protocol.writeMessages(messages);
            sendResponse(exchange, 200, stringWriter.toString());
        } catch (IOException e) {
            sendResponse(exchange, 500, "sever error");
        }
    }
}
