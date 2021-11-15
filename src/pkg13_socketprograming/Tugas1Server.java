/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg13_socketprograming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Pamungkas
 */
public class Tugas1Server {
    private static ServerSocket servSock;
    private static final int PORT = 1234;

    public static void main(String args[]) {
        System.out.println("Opening Port.....\n");
        try {
            servSock = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Unable to attach to port"); 
            System.exit(1);
}
do {
            run();
        } while (true);
    }

    private static void run() {
        Socket link = null;
        try {
            link = servSock.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            int numMessages = 0;
            String message = in.readLine();
            while (!message.equals("close")) {
                System.out.println("Message received " +numMessages +" : "+ message);
                numMessages++;
                String reverse = new StringBuffer(message).reverse().toString();
                out.println("Message " + numMessages + " : "
                        + reverse);
                message = in.readLine();
            }
            out.println(numMessages + " message received.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("*********Closing Connection * * * *"); 
                link.close();
}
catch (IOException e) {
                System.out.println("Unable to disconnect");
                System.exit(1);
            }
        }
    }
}
