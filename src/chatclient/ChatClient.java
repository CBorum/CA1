/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import chatserver.ClientHandler;
import log.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocol.ProtocolStrings;

/**
 *
 * @author Borum
 */
public class ChatClient extends Observable implements Runnable {

    Socket socket;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;

    public void connect(String address, int port) throws UnknownHostException, IOException {
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    }

    public void send(String msg) {
        output.println(msg);
    }

    public void stop() {
        output.println(ProtocolStrings.LOGOUT);
    }

    public String receive() {
        String msg = input.nextLine();
        if (msg.equals(ProtocolStrings.LOGOUT)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setChanged();
        notifyObservers(msg);
        return msg;

    }

    @Override
    public void run() {
        String msg = input.nextLine();
        if (msg.equals(ProtocolStrings.LOGOUT)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setChanged();
        notifyObservers(msg);
    }

}
