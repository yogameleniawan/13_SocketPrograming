package pkg13_socketprograming;

import java.io.*;
import java.net.*;
import java.util.*;

public class EmailClient {

    private static String strHost;
    private static InetAddress host;
    private static final int PORT = 50000;
    private static BufferedReader userEntry;    
    private static BufferedReader msg;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String args[]) {
        userEntry = new BufferedReader(new InputStreamReader(System.in));        
        msg = new BufferedReader(new InputStreamReader(System.in));

        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID Not Found");
            System.exit(1);
        }
        run();
    }

    private static void doSend() throws
            IOException {
           Scanner sc = new Scanner(System.in);
           System.out.println("Enter 1-line message : ");
           String message = sc.nextLine();
           out.println(message);
    }

    private static void doRead() throws IOException {
        int msgLength = Integer.parseInt(in.readLine());
        System.out.println("Jumlah pesan : " + msgLength);
        for (int i = 1; i <= msgLength; i++) {
            System.out.println(i+" : "+in.readLine());
        }
        
    }

    private static void run() {
        Socket link = null;
        try {
            link = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);
            String message = "";
            do {
                String name = "", sendRead = "";
                
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter (Name Send/Read) : ");
                message = sc.nextLine();
                out.println(message);
                String delimiter = " ";
                do {
                    
                    if (!message.equals("close")) {
                        StringTokenizer st = new StringTokenizer(message, delimiter);
                        while (st.hasMoreElements()) {
                            name = st.nextToken().toString();
                            sendRead = st.nextToken().toString();
                        }
                    }
                } while (name.equals("Andi") && name.equals("Budi"));
                if (!message.equals("close")) {
                    if (name.equals("Andi")) {
                        if (sendRead.equals("send")) {                          
                            doSend();
                        } else if (sendRead.equals("read")) {
                            doRead();
                        }
                    } else if (name.equals("Budi")) {
                        if (sendRead.equals("send")) {
                            doSend();
                        } else if (sendRead.equals("read")) {
                            doRead();
                        }
                    }
                }
            } while (!message.equals("close"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("closing connection");
                link.close();
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
