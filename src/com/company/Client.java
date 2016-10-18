package com.company;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by belldell on 17.10.16.
 */
public class Client {
    private String name;
    private Socket socket;
    private String serverIP;
    private int serverPort = 0 ;

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean connect()
    {

        try {
            System.out.println("try connect to ip=" + serverIP + " port=" +serverPort);
            InetAddress ipAddress = InetAddress.getByName(serverIP);
            socket = new Socket(ipAddress, serverPort);
            System.out.println("Connected to server!");

            InputStream sin = socket.getInputStream();
            DataInputStream in = new DataInputStream(sin);
            String line= "";

            new Thread(new WriteMessage()).start();

            while (true) {
                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                System.out.println(line);
            }
        } catch (Exception x) {
            x.printStackTrace();
            return false;
        }
    }

    private void writeMessageThr()
    {
        try {
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);
            String line = "";
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                line = keyboard.readLine();
                System.out.println("Sending this line to the server...");
                out.writeUTF(line);
                out.flush();
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    class WriteMessage implements Runnable{
        @Override
        public void run() {
            writeMessageThr();
        }
    }
}
