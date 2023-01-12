package programming3.chatsys.data;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Andy Chen (759517169@qq.com)
 * @version 3.0
 */
public class ChatMessage {
    private int id;
    private String message;
    private String userName;
    private Timestamp timestamp;
//    String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);

    /**
     * Creates a ChatMessage.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param timestamp time at which this ChatMessage was sent
     * @param message message of this ChatMessage
     */
    public ChatMessage(int id, String userName, Timestamp timestamp, String message) {
        this.init(id, userName, timestamp, message);
    }

    /**
     * Creates a ChatMessage.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param timestamp time at which this ChatMessage was sent
     * @param message message of this ChatMessage
     */
    public ChatMessage(int id, String userName, long timestamp, String message) {
        this.init(id, userName, new Timestamp(timestamp), message);
    }

    /**
     * Creates a ChatMessage using the current time as timestamp.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param message message of this ChatMessage
     * @throws IllegalArgumentException If the message contains a line feed (\n) character.
     */
    public ChatMessage(int id, String userName, String message) {
        this.init(id, userName, new Timestamp(System.currentTimeMillis()), message);
    }



    /**
     *     A constructor taking a JSONObject as a parameter (i.e. ChatMessage(JSONObject jsonObject) and
     *     User(JSONObject jsonObjet) that will initialize an object with the information provided in the
     *     JSONObject
     * @param jsonObject
     */
    public ChatMessage(JSONObject jsonObject) {
        this.id = jsonObject.getInt("id");
        this.userName = jsonObject.getString("username");
        this.timestamp = new Timestamp(jsonObject.getLong("time"));
        this.message = jsonObject.getString("message");
    }

    /**
     * @param id
     * @param userName
     * @param timestamp
     * @param message
     */
    private void init(int id, String userName, Timestamp timestamp, String message) {
        if (message.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("message contains a line feed");
        }
        this.id = id;
        this.userName = userName;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }



    /**
     *     A public method “JSONObject toJSON()” that converts a User and ChatMessage to a JSONObject
     *     The JSON objects used by this new constructor and this new method are formatted as follow:
     *     • {username:<username1>, time:<timestamp1>, message:<message1>} for a chat messages
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",this.id);
        jsonObject.put("username", this.userName);
        jsonObject.put("timestamp", this.timestamp.getTime());
        jsonObject.put("message", this.message);
        return jsonObject;
    }


    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return id == that.id &&
                Objects.equals(message, that.message) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, userName, timestamp);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    /**
     * Modify your implementation of the Database so that each operations check that the inputs follow the
     * following constraints:
     * • The username only contains ASCII letters (a-z and A-Z), numbers (0-9) and the underscore (_) and
     * hyphen (-) characters
     * • The full name only contains letters (including non ASCII letters) and space characters
     * • The password can only contain spaces as blank characters.
     * • The chat message does not contain tabulation (\t) and line feed characters (\n)
     * Throw exceptions (e.g. InvalidArgumentException) if any of the inputs does not match these constraints.
     * Write unit tests making sure that these special cases are handled properly
     * @param userName
     * @return boolean
     * @throws IllegalArgumentException
     */
    public String registerConstrain(String userName) throws IllegalArgumentException{
            String reg = "^(\\d+[A-Za-z]+[A-Za-z0-9]*)|([A-Za-z]+\\d+[A-Za-z0-9]*)$";
            if (userName.matches(reg)) {
                return "This matched the constraints of the username";
            } else {
                return "This doesn't match the constraints of the username";
            }
    }


    public String format(){
        return this.id + "\t" + this.message +"\t" + this.userName + "\t" + this.timestamp;
    }
    //Before being able to save the database to text files,
    //it would be convenient to convert individual User and
    //ChatMessage objects to and from the text format defined previously.
    //add to both classes a method for formatting these objects and
    // a method for parsing them

//    public void parse(String s){
//        String[] donKnowWhetherRight2 = s.split("\t");
////        TODO
//    }
    /**
     * use the split method of the class String using the tabulation as split separator
     * @param s
     */
//    private int id;
//    private String message;
//    private String userName;
//    private Timestamp timestamp;
    public void parse(String s) {
        String[] split = s.split("\t");
        if (split.length == 4) {
            this.id = Integer.parseInt(split[0]);
            this.message = split[1];
            this.userName = split[2];
            this.timestamp = Timestamp.valueOf(split[3]); // I used to transferred Timestamp to String.
        } else {
            throw new IllegalArgumentException("The String to parse does not contain enough tabulations and cannot be parsed");
        }
    }


    /**
     * Also add a constructor to both classes that take a formatted String as a parameter and calls the parse method.
     * @param formatted
     */    public ChatMessage(String formatted) {
        this.parse(formatted);
    }


}
