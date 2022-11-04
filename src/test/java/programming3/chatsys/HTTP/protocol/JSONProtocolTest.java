package programming3.chatsys.HTTP.protocol;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The User read/write are teacher's code
 * The Message:
 * @author 陈新元 Andy Chen
 * @version 3.0
 */
class JSONProtocolTest {
    private final JSONObject jsonJohn = new JSONObject("{username:johndoe, fullname:\"John Doe\", password:thepassword}");
    private final JSONObject jsonJane = new JSONObject("{username:jane, fullname:\"Jane Doe\", password:janespassword}");

    private final User john = new User("johndoe", "John Doe", "thepassword");
    private final User jane = new User("jane", "Jane Doe", "janespassword");

    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private final ChatMessage chatMessage1 = new ChatMessage(0,"johndoe",new Timestamp(1604656487),"This is a chat message");
    private final ChatMessage chatMessage2 = new ChatMessage(1,"jane",new Timestamp(1604934958),"This is not a chat message");
    {
        chatMessageList.add(chatMessage1);
        chatMessageList.add(chatMessage2);
        //which can be asserted into the read/write messages
    }
    private final JSONArray jsonArray = new JSONArray("[" +
            "{\"type\":\"messages\"}," +
            "{\"messages\":" +
            "[" +
            "{\"id\":0,\"message\":\"This is a chat message\",\"username\":\"johndoe\",\"time\":1604656487}," +
            "{\"id\":1,\"message\":\"This is not a chat message\",\"username\":\"jane\",\"time\":1604934958}" +
            "]" +
            "}" +
            "]");
    @Test
    void readMessages() throws IOException{
        JSONProtocol protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonArray.toString())));
        assertEquals(chatMessageList,protocol.readMessages());

        protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonArray.toString())));
        assertEquals(chatMessageList, protocol.readMessages());
    }

    @Test
    void writeMessages() throws IOException {
//        //You can initialize the Reader and Writer of the JSONProtocol
//        //with a StringWriter and StringWriter to read and write from Java Strings directly
//        StringWriter string = new StringWriter();
//        JSONProtocol protocol = new JSONProtocol(new BufferedWriter(string));
//        protocol.writeMessages(chatMessageList);
////        protocol.writeUser(john);
//        assertEquals(chatMessage1.toString(), string.toString());
//
//        string = new StringWriter();
//        protocol = new JSONProtocol(new BufferedWriter(string));
//        protocol.writeMessages(chatMessageList);
////        protocol.writeUser(jane);
//        assertEquals(chatMessage2.toString(), string.toString());
        StringWriter string = new StringWriter();
        JSONProtocol protocol = new JSONProtocol(new BufferedWriter(string));
        protocol.writeMessages(chatMessageList);
        JSONArray jsonArray = new JSONArray();

//        jsonArray.put(0,"ChatMessages");
//        for (int i = 0; i < chatMessageList.size(); i++) {
//            JSONObject json = chatMessageList.get(i).toJSON();
//            jsonArray.put(json);
//        }
        jsonArray.put(0,"ChatMessages");
        chatMessageList.stream().map(ChatMessage::toJSON).forEach(jsonArray::put);
        assertEquals(jsonArray.toString(), string.toString());

    }

    @Test
    void readUser() throws IOException {
        JSONProtocol protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonJohn.toString())));
        assertEquals(john, protocol.readUser());

        protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonJane.toString())));
        assertEquals(jane, protocol.readUser());
    }

    @Test
    void writeUser() throws IOException {
        //You can initialize the Reader and Writer of the JSONProtocol
        //with a StringWriter and StringWriter to read and write from Java Strings directly
        StringWriter string = new StringWriter();
        JSONProtocol protocol = new JSONProtocol(new BufferedWriter(string));
        protocol.writeUser(john);
        assertEquals(jsonJohn.toString(), string.toString());

        string = new StringWriter();
        protocol = new JSONProtocol(new BufferedWriter(string));
        protocol.writeUser(jane);
        assertEquals(jsonJane.toString(), string.toString());
    }



}