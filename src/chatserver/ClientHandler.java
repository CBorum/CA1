/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.Log;

/**
 *
 * @author Borum
 */
public class ClientHandler extends Thread {
    
    private ChatServer server;
    private Socket socket;
    private Scanner input;
    private PrintWriter writer;
    private boolean keepRunning = true;
    private String user;
    
    public ClientHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            input = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
//        writer.println("Please enter your username:");
//        user(new String[]{"USER", input.nextLine()});
        try {
            while (keepRunning) {
                String msg = input.nextLine();
                if (msg.equals("LOGOUT#")) {
                    logout();
                }
                String[] message = msg.split("#");
                switch (message[0]) {
                    case "USER":
                        user(message);
                        break;
                    case "SEND":
                        send(message);
                        break;
                }
            }
        } catch (NoSuchElementException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, user + " force closed connection", ex);
            server.removeHandler(this);
        }
    }
    
    public void message(String[] msg) {
        writer.println(msg[0] + "#" + msg[1] + "#" + msg[2]);
    }
    
    private void user(String[] message) {
        try {
            this.user = message[1];
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} connected", this.user);
            //report this user joined
            server.notifyUsers();
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(String[] message) {
        try {
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} to " + message[1] + ": " + message[2], user);
            server.send(message, this.getUser());
            //handle send message to specific
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getUser() {
        return user;
    }
    
    public void users(String msg) {
        writer.println(msg);
    }
    
    private void logout() {
        keepRunning = false;
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} has disconnected", user);
        
        server.removeHandler(this);
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //report this user disconnected
    }
    
}
