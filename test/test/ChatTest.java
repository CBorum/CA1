/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import chatclient.ChatClient;
import chatserver.ChatServer;
import chatserver.ClientHandler;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josephinepatrick
 */
public class ChatTest {

    public ChatTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String args[] = new String[]{"localhost", "9999"};
                ChatServer.main(args);
            }
        }).start();
    }

    @AfterClass
    public static void tearDownClass() {
        ChatServer.stopServer();
    }

    @Test
    public void send() throws IOException, InterruptedException {
        Thread.sleep(1000);
        ChatClient client = new ChatClient();
        //   ClientHandler client = new ClientHandler();
        client.connect("localhost", 9999);
        System.out.println("Hejhej");
        client.send("SEND#*#Hello");
        assertEquals("MESSAGE#null#Hello", client.receive());
        
//        client.addObserver(new Observer() {
//            @Override
//            public void update(Observable o, Object arg) {
//                assertEquals("MESSAGE#null#Hello", arg);
//            }
//
//        });
//        Thread recieve = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hejmeddig");
//                client.receive();
//            }
//        });
//        recieve.start();
//        System.out.println("nejnej");
//        recieve.join();//
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
