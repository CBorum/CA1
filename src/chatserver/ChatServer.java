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
import java.util.logging.Level;
import java.util.logging.Logger;
import log.Log;

/**
 *
 * @author Borum
 */
public class ChatServer {

    private ServerSocket serverSocket;
    private static boolean keepRunning = true;
    private List<ClientHandler> clients = new ArrayList();

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
                ClientHandler client = new ClientHandler(this, socket);
                clients.add(client);
                client.start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, "IOException in runserver", ex);
        }
    }

    public static void stopServer() {
        keepRunning = false;
    }

    private int findClient(String userName) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getUser().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    public void send(String[] msg, String user) {
        String users = msg[1];
        String[] usersSplit = users.split(",");
        msg[0] = "MESSAGE";
        if (msg[1].equals("*")) {
            for (ClientHandler client : clients) {
                msg[1] = user;
                client.message(msg);
            }
        } else {
            for (ClientHandler client : clients) { //makes sense or stupid?
                if (client.getUser().equals(user)) {
                        msg[1] = "To " + users;
                        client.message(msg);
                }
            }
            for (String user1 : usersSplit) {
                for (ClientHandler client : clients) {
                    if (user1.equals(client.getUser())) {
                        msg[1] = user;
                        client.message(msg);
                    }
                }
            }
            
        }
    }

    public void notifyUsers() {
        String msg = "USERS#";
        for (ClientHandler client : clients) {
            msg += client.getUser() + ',';
        }
        msg = msg.substring(0, msg.length() - 1);
        for (ClientHandler client : clients) {
            client.users(msg);
        }

    }

    public void removeHandler(ClientHandler client) {
        clients.remove(client);
        this.notifyUsers();
    }

}
