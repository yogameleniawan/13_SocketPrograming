package pkg13_socketprograming;

import java.io.*;
import java.net.*;
import java.util.*;

public class EmailServer {

    private static ServerSocket servSock;
    private static final int PORT = 50000;
    private static final int MAX = 10;
    private static String[] mailbox1 = new String[MAX];
    private static String[] mailbox2 = new String[MAX];
    private static int messageInBox1 = 0;
    private static int messageInBox2 = 0;

    public static void main(String args[]) throws IOException {
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

    public static void doSend(String mailbox[], int messageInBox,
            BufferedReader in) throws IOException {
        
        if (messageInBox == MAX) {
            System.out.println("INBOX FULL");
        } else {
            mailbox[messageInBox] = in.readLine();
        }
        System.out.println(" "+mailbox[messageInBox]);
       
    }

    public static void doRead(String mailbox[], int messageInBox, PrintWriter out) throws IOException {
        System.out.println("Message Inbox : " + messageInBox);
        out.println(messageInBox);
        for (int i = 0; i < messageInBox; i++) {
            out.println(mailbox[i]);
        }
    }

    private static void run() throws IOException {
        Socket link = null;

        try {
            link = servSock.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            String message = "", name = "", sendRead = "";
            String delimiter = " ";
            
            do {
                message = in.readLine();
                StringTokenizer st = new StringTokenizer(message, delimiter);
                while (st.hasMoreElements()) {
                    name = st.nextToken().toString();
                    sendRead = st.nextToken().toString();
                }
                if (name.equals("Andi")) {
                    if (sendRead.equals("send")) {
                        System.out.print("Message From Andi :");
                        doSend(mailbox2, messageInBox2, in);
                        if (messageInBox2 < MAX) {
                            messageInBox2++;
                        }
                    } else if (sendRead.equals("read")) {
                        doRead(mailbox1, messageInBox1, out);
                        messageInBox1 = 0;
                    }
                } else if (name.equals("Budi")) {
                    if (sendRead.equals("send")) {
                        System.out.print("Message From Budi :");
                        doSend(mailbox1, messageInBox1, in);
                        if (messageInBox1 < MAX) {
                            messageInBox1++;
                        }
                    } else if (sendRead.equals("read")) {
                        doRead(mailbox2, messageInBox2, out);
                        messageInBox2 = 0;
                    }
                }else{
                    System.out.println("Invalid Format Message");
                }
            } while (!message.equals("close"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("*********Closing Connection * * * *");
                link.close();
            } catch (IOException e) {
                System.out.println("Unable to disconnect");
                System.exit(1);
            }
        }
    }
}
