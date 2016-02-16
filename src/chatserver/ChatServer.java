/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Borum
 */
public class ChatServer{
    
    private ServerSocket serverSocket;
    private boolean keepRunning = true;
    
    public static void main(String[] args) {
        try {
            Log.setLogFile("LogFile.txt", "ServerLog");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Starting the Server");
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            new ChatServer().runServer(ip, port);
        } finally {
            Log.closeLogger();
        }
    }
    
    private void runServer(String ip, int port) {
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Server listening on: " + port + ", bound to: " + ip);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept();
                Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Connected to a client");
                new ClientHandler(socket).start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, "IOException in runserver", ex);
        }
    }
    
    private void stopServer() {
        keepRunning = false;
    }
    
}
