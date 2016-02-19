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
import static java.lang.Thread.sleep;
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
    
    static String testString = "";
    
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

    @Test
    public void send() throws IOException, InterruptedException {
        testString = "";
        ChatClient client = new ChatClient();
        
        client.connect("localhost", 9999);
        
        Thread t1 = new Thread(client);
        
        t1.start();
        
        client.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                testString = (String) arg;
                ChatClient cl = (ChatClient) o;
                client.stop();
            }
        });
        
        client.send("SEND#*#Hello");
        
        t1.join();
        
        assertEquals("MESSAGE#null#Hello", testString);
    }

    @Test
    public void user() throws IOException, InterruptedException {
        testString = "";
        ChatClient client = new ChatClient();
        client.connect("localhost", 9999);
        
        Thread t1 = new Thread(client);
        
        t1.start();
        
        client.send("USER#Test");
        
        client.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                testString = (String) arg;
                ChatClient cl = (ChatClient) o;
                client.stop();
            }
        });
        
        t1.join();
        
        assertEquals("USERS#Test", testString);
    }

    @Test
    public void logout() throws IOException, InterruptedException {
        testString = "";
        
        ChatClient client = new ChatClient();
        ChatClient client2 = new ChatClient();
        
        client.connect("localhost", 9999);
        client2.connect("localhost", 9999);     
        
        Thread t1 = new Thread(client);
        Thread t2 = new Thread(client2);
        
        t1.start();
        t2.start();
        
        client.send("USER#Test");
        
        sleep(1000);
        
        client.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
                if (arg.equals("USERS#Test")) {
                    testString = (String) arg;
                    ChatClient cl = (ChatClient) o;
                    client.stop();
                }
            }
        });
        
        client2.send("USER#Test2");
        client2.stop();

        t1.join(2000);
        
        assertEquals("USERS#Test", testString);
        
    }
}
