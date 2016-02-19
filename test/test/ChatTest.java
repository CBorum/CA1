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
    public static void setUpClass() throws IOException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String args[] = new String[]{"localhost", "9999"};
                ChatServer.main(args);
            }
        }).start();
        Thread.sleep(1000);
    }

    @AfterClass
    public static void tearDownClass() {
        ChatServer.stopServer();
    }

//    @Test
//    public void send() throws IOException, InterruptedException {
//        ChatClient client = new ChatClient();
//        
//        Thread t1 = new Thread(client);
//        
//        t1.start();
//        
//        client.connect("localhost", 9999);
//        client.send("SEND#*#Hello");
//        assertEquals("MESSAGE#null#Hello", client.receive());
//        client.stop();
//        t1.join();
////        client.addObserver(new Observer() {
////            @Override
////            public void update(Observable o, Object arg) {
////                assertEquals("MESSAGE#null#Hello", arg);
////            }
////
////        });
////        Thread recieve = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                System.out.println("Hejmeddig");
////                client.receive();
////            }
////        });
////        recieve.start();
////        System.out.println("nejnej");
////        recieve.join();
//    }

//    @Test
//    public void user() throws IOException {
//        ChatClient client = new ChatClient();
//        client.connect("localhost", 9999);
//        client.send("USER#Test");
//        assertEquals("USERS#Test", client.receive());
//        client.stop();
//    }

    @Test
    public void logout() throws IOException, InterruptedException {
        ChatClient client = new ChatClient();
        ChatClient client2 = new ChatClient();
        
        client.connect("localhost", 9999);
        client2.connect("localhost", 9999);
        
        
        Thread t1 = new Thread(client);
        Thread t2 = new Thread(client2);
        
        t1.start();
        t2.start();
        
        client.send("USER#Test");
        client2.send("USER#Test2");
        client.send("SEND#*#Hello");
        client2.stop();
        t2.join();
        assertEquals("USERS#Test", client.receive());

        client.stop();

        t1.join();
        
    }
}
