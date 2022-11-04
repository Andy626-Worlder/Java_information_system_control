package programming3.chatsys.HTTP.protocol;

import org.json.JSONArray;
import org.json.JSONObject;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 3.0
 */

public class JSONProtocol {
    private BufferedReader reader;
    private BufferedWriter writer;

    //The text containing the objects to parse will be obtained through a Reader object, and the text to generate will
    //be sent to a Writer. Add these two objects as class attributes and add a constructor to initialize them.

    public JSONProtocol(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public JSONProtocol(BufferedReader reader) {
        this.reader = reader;
    }

    public JSONProtocol(BufferedWriter writer) {
        this.writer = writer;
    }





    public User readUser() throws IOException {
        String json = "";
        String line;
        while ((line = this.reader.readLine()) != null) {
            json += line;
        }
        return new User(new JSONObject(json));
    }

    /**
     * @param user
     * @throws IOException
     */
    public void writeUser(User user) throws IOException {
        JSONObject json = user.toJSON();
        //Here related to User.java
        json.write(this.writer);
        this.writer.flush();
    }

    /**
     * A method readMessages to do the opposite: reads a JSONObject from the Reader and converts it to
     * ChatMessage object and returns it
     * @return chatMessage
     * @throws IOException
     */
    public List<ChatMessage> readMessages() throws  IOException {
        String json = "";
        String line;
        List<ChatMessage> chatMessage = new ArrayList<>();
        while ((line = this.reader.readLine()) != null) {
            json += line;
        }
        JSONArray jsonArray = new JSONArray(json);
//        JSONObject jsonObject = jsonArray.getJSONObject(0);
        for (int i = 0; i < jsonArray.getJSONObject(1).getJSONArray("messages").length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(1).getJSONArray("messages").getJSONObject(i);
            ChatMessage chatMessage1 = new ChatMessage(jsonObject);
            chatMessage.add(chatMessage1);
        }
        return chatMessage;
    }

    /**
     * A method writeMessages that given a List of ChatMessage object creates a JSONObject and writes in in
     * the Writer, e.g.:
     * [type:messages, messages:{“id”: 0, “message”: “This is a chat message”, “username”: “johndoe”,
     * “timestamp”: 1604656487868}, messages:{“id”: 1, “message”: “This is a second chat message”,
     * “username”: “johndoe”, “timestamp”: 1604656587868}]
     * (The timestamp value is the number of milliseconds since 1970 representing the time when the
     * message was posted. You can get it from a Timestamp object using the method “getTime”.)
     * @param messages
     * @throws IOException
     */
    public void writeMessages(List<ChatMessage> messages) throws IOException {
        JSONArray jsonArray = new JSONArray();
        //Here related to ChatMessage.java
//        jsonArray.put(0,"ChatMessages");
        for(int i=0;i<messages.size();i++){
            JSONObject json = messages.get(i).toJSON();
            jsonArray.put(json);
        }
        JSONObject json = new JSONObject();
        json.put("messages",jsonArray);
        json.write(this.writer);
        this.writer.flush();
        this.writer.close();
    }
}
