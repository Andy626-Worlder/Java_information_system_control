package programming3.chatsys.data;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;

/**
 @author Andy Chen (759517169@qq.com)
 @version 3.0
 */
public class TextDatabase extends InMemoryDatabase {
    private File userTxtFile;
    private File chatMessageTxtFile;
//    These two text files will be saved as
//    private attributes of TextDatabase of type File.

    /**
     * @param userTxtFile
     * @param chatMessageTxtFile
     */
    public TextDatabase(File userTxtFile,File chatMessageTxtFile) {
        this.userTxtFile = userTxtFile;
        this.chatMessageTxtFile = chatMessageTxtFile;

        this.readUsers();
        this.readChatMessages();
    }

    @Override
    public void close() {
        this.saveUsers();
        this.saveChatMessages();
    }

    /**
     * 1.Open the text file
     * 2.Create a BufferedWriter to write in the text file
     * 3.For each User object in the list of users,
     * we write the formatted object to the BufferedWriter and also
     * add a line feed (“\r\n”) (回车”（Carriage Return）和“换行”（Line Feed）)
     * to put each entry on a separate line 每次换行
     * 4.We flush the BufferedWriter and close it
     */
    private void saveUsers() {
        System.out.println("Here is what is saved in user.txt");
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(this.userTxtFile))) {
            for (User u : this.users) {
                // Here I faced with an error that I used 'private' instead of 'protected' in InMemoryDatabase.
                // which I also forgot about the extends relationship above.
                writer.write(u.format() + "\r\n");
                //writeFile("userTxtFile",);//Need a User Object which stuck me for quite a while
            }
//            writer.close();
        } catch (IOException e) {
            throw new DatabaseAccessException("Can't write anything in the user.txt" + this.userTxtFile, e);
        }
    }


        //If equipped each with a try-catch, would be to complicate to read.
        /*
        BufferedWriter writer =
                null;
        try {
            writer = new BufferedWriter(new FileWriter(this.userTxtFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (User u: this.users) {
            try {
                writer.write(u.format() + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */


    /**
     * need to be able to read them from files when we load a database:
     * 1. We create a BufferedReader instead of a BufferedWriter
     * 2. We iterator over the lines of the file rather than the User objects  (line by line not by Objects)
     * 3. We create User objects from the formatted line read from the file and add it to the database
     *
     * @throws IOException
     */
    private void readUsers()  {
        System.out.println("Here we are reading from user.txt");
        //1. reader -> writer
        if (this.userTxtFile.exists()) {
            try (BufferedReader reader =
                         new BufferedReader(new FileReader(this.userTxtFile))) {
                while (true) {
                    //2. lines rather objects.
                    String lines = reader.readLine();
                    // Here to make sure each line will be included in the loop
                    // 3.1 Read formatted line from file.
                    if (lines != null) {
                        User u = new User(lines);
                        this.users.add(u);
                        //3.2 Judge whether to add to database.
                    } else {
                        break;
                        // Break at where is vital : loop or switch.
                        //switch problem: options cannot be managed.
                    }
                }
            } catch (IOException e) {
                throw new DatabaseAccessException("Cannot access user DB file" + this.userTxtFile, e);
            }
        }
    }


    /**
     * Self implemented on read/save ChatMessages.
     */
    private void saveChatMessages() {
        System.out.println("Here is what is saved in chatMessages.txt");
        try {
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(this.chatMessageTxtFile));
            for (ChatMessage chatM : this.messages) {
                writer.write(chatM.format() + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new DatabaseAccessException("Can't write anything to the chatMessages.txt" + this.chatMessageTxtFile, e); // + this.chatMessageTxtFile, e
        }
    }


    /**
     * Self implemented on read/save ChatMessages.
     */
    private void readChatMessages() {
        System.out.println("Here we are reading from user.txt");
        if (this.chatMessageTxtFile.exists()) {
            try (BufferedReader reader =
                         new BufferedReader(new FileReader(this.chatMessageTxtFile))) {
                while (true) {
                    String lines = reader.readLine();
                    if (lines != null) {
                        ChatMessage chatM = new ChatMessage(lines);
                        this.messages.add(chatM);
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                throw new DatabaseAccessException("Can't read anything from the chatMessages.txt" + chatMessageTxtFile, e);
            }
        }
    }

    /**
     * modify the close method so:
     * It first saves the User objects to a file
     * Then it saves the ChatMessage objects to a different file
     */

}

