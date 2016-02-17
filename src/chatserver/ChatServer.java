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
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Observable;
import java.util.Observer;
>>>>>>> server
import java.util.logging.Level;
import java.util.logging.Logger;
import log.Log;

/**
 *
 * @author Borum
 */
<<<<<<< HEAD
public class ChatServer {

    private ServerSocket serverSocket;
    private boolean keepRunning = true;
    private List<ClientHandler> clients = new ArrayList();

=======
public class ChatServer implements Observer{
    
    private ServerSocket serverSocket;
    private boolean keepRunning = true;
    private ClientHandler ch;
    
>>>>>>> server
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
<<<<<<< HEAD
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                client.start();
=======
                ch = new ClientHandler(socket);
                new Thread(new ClientHandler(socket)).start();
                ch.addObserver(this);
>>>>>>> server
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, "IOException in runserver", ex);
        }
    }

    private void stopServer() {
        keepRunning = false;
    }

<<<<<<< HEAD
    private int findClient(String userName) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getUser().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

=======
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update method");
    }
    
>>>>>>> server
}
