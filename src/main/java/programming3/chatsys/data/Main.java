package programming3.chatsys.data;

//import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.*;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        //1
//        User user1 = new User("johndoe", "John Doe", "thepassword");
//        User user2 = new User("jane", "Jane Doe", "janespassword");
//        User user3 = new User("johndoe", "John Doe", "thepassword");
//        User user4 = new User("johndoe", "John Doe", "anotherpassword");
//        User user5 = new User("johndoe", "John Doe 2", "anotherpassword");
//        User user6 = user1;

//2
//        System.out.println(user1);
//        System.out.println(user2);
//        System.out.println(user3);
//        System.out.println(user4);
//        System.out.println(user5);
//        System.out.println(user6);
//
//        System.out.println(user1 == user6);
//        System.out.println(user1 == user3);
//        System.out.println(user1.equals(user3));
//        System.out.println(user1.equals(user2));
//        System.out.println(user1.equals(user4));
//        System.out.println(user1.equals(user5));
//        System.out.println(user1.equals(user6));

//        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
//        server.createContext("/", new HttpHandler() {
//            @Override
//            public void handle(HttpExchange exchange) throws IOException {
//                System.out.println("Requested URI: " + exchange.getRequestURI());
//                System.out.println("Client: " + exchange.getRemoteAddress());
//                System.out.println("Request method: " + exchange.getRequestMethod());
//                System.out.println("Request headers: " +
//                        exchange.getRequestHeaders().entrySet());
//                BufferedReader reader = new BufferedReader(new
//                        InputStreamReader(exchange.getRequestBody()));
//                System.out.println("Request body: ");
//                for (String line = reader.readLine(); line != null; line =
//                        reader.readLine()) {
//                    System.out.println(line);
//                }
//                System.out.println("Sending response: " + response);
//                exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
//                BufferedWriter writer = new BufferedWriter(new
//                        OutputStreamWriter(exchange.getResponseBody()));
//                writer.write("Hello World!");
//                writer.flush();
//                writer.close();
//                System.out.println();
//            }
//        });
//        server.start();
//

//1
//        Database db = new InMemoryDatabase();
//        System.out.println(db);
//        System.out.println(db.register(user1));
//        System.out.println(db.register(user2));
//        System.out.println(db.register(user3));
//        System.out.println(db.register(user4));
//        System.out.println(db.register(user5));
//        System.out.println(db.register(user6));
//        System.out.println(db);
//
//        System.out.println(db.getNumberUsers());
//
//        System.out.println(db.getUser("johndoe"));
//        System.out.println(db.getUser("jane"));
//        try {
//            System.out.println(db.getUser("john"));
//        } catch(IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(db.authenticate("johndoe", "thepassword"));
//        System.out.println(db.authenticate("johndoe", "wrongpassword"));
//        System.out.println(db.authenticate("john", "thepassword"));
    }
}
