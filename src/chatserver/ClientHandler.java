/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.Log;

/**
 *
 * @author Borum
 */
public class ClientHandler extends Observable implements Runnable{

    private Socket socket;
    private Scanner input;
    private PrintWriter writer;
    private boolean keepRunning = true;
    private String user;

    public ClientHandler(Socket socket) {
        
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
        writer.println("Please enter your username:");
        user(new String[]{"USER", input.nextLine()});
        while (keepRunning) {
            String[] message = input.nextLine().split("#");
            switch (message[0]) {
                case "USER":
                    user(message);
                    break;
                case "SEND":
                    send(message);
                    break;
                case "LOGOUT":
                    logout();
                    break;
            }
            System.out.println("setting changed notify");
            setChanged();
            notifyObservers();
        }
    }
    
    private void user(String[] message) {
        try {
            this.user = message[1];
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} connected", this.user);
            //report this user joined
            
<<<<<<< HEAD
            
            
=======
>>>>>>> server
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    private void send(String[] message) {
        try {
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} to " + message[1] + ": " + message[2], user);
            
            //handle send message to specific
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }

    public String getUser() {
        return user;
    }
    
    
    
    private void logout() {
        keepRunning = false;
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "{0} has disconnected", user);
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //report this user disconnected
    }

}
