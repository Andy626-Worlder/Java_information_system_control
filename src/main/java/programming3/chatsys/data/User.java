package programming3.chatsys.data;

import com.sun.xml.internal.bind.v2.TODO;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author Andy Chen (759517169@qq.com)
 * @version 3.0
 */
public class User {
    private String userName;
    private String fullName;
    private String password;
    private int lastReadId;


    /**
     * @param userName the user name of the User.
     * @param fullName the full name of the User.
     * @param password the password of the User.
     */
    public User(String userName, String fullName, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    //A constructor taking a JSONObject as a parameter (i.e.
    //User(JSONObject jsonObjet) that will initialize an object with the information provided in the
    //JSONObject
    public User(JSONObject jsonObject) {
        this.userName = jsonObject.getString("username");
        this.fullName = jsonObject.getString("fullname");
        this.password = jsonObject.getString("password");
    }

    //A public method “JSONObject toJSON()” that converts a User and ChatMessage to a JSONObject
    //The JSON objects used by this new constructor and this new method are formatted as follow:
    //• {username:<username>, password:<password>, fullname:<fullname>} for a users\

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", this.userName);
        jsonObject.put("fullname", this.fullName);
        jsonObject.put("password", this.password);
        return jsonObject;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    public int getLastReadId() {
        return lastReadId;
    }

    public void setLastReadId(int lastReadId) {
        this.lastReadId = lastReadId;
    }


    /**
     * To implement the format method, simply concatenate the different attributes of the object
     * and separate them with tabulations (“\t”).
     */
    public String format(){
//        String formatted = this.userName + "\t" + this.fullName + "\t" + this.password + "\t" + this.lastReadId;
//        return formatted;
          return this.userName + "\t" + this.fullName + "\t" + this.password + "\t" + this.lastReadId;

    }
    //    To implement the parse method,
    //    you can use the split method of the class String using the tabulation as split separator
    //    tabulation = 制表
    //    split() 方法根据匹配给定的正则表达式来拆分字符串。
    //    public String[] split(String regex, int limit)
    //    regex = regular expression ; limit = 次数

    //My Own Code:
//    public void parse(String formatted){
//        String[] donKnowWheterRight1 = formatted.split("\t");
//           TODO
//    }
    //Teacher's code:

    /**
     * use the split method of the class String using the tabulation as split separator
     * @param s
     */
//    private String userName;
//    private String fullName;
//    private String password;
//    private int lastReadId;
    public void parse(String s) {
        String[] split = s.split("\t");
        if (split.length == 4) {
            this.userName = split[0];
            this.fullName = split[1];
            this.password = split[2];
            this.lastReadId = Integer.parseInt(split[3]);
        } else {
            throw new IllegalArgumentException("The String to parse does not contain enough tabulations and cannot be parsed");
        }
    }

    /**
     * Also add a constructor to both classes that take a formatted String as a parameter and calls the parse method.
     * @param formatted
     */
    public User(String formatted) {
//        String donKnowWheterRight2 = formatted1.parse// 这里突然不会怎么调用 parse 了
        this.parse(formatted);
    }


}
