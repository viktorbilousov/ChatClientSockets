package com.company;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by belldell on 17.10.16.
 */
public class Client implements Runnable {
    private String serverIP;
    private int serverPort = 0 ;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;

    private String name = "";
    private String status = "I love world";

    //BufferedReader keyboard = null;
  //  private  InputStreamReader reader = null;

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean connect()
    {
        Socket socket = null;
        Thread thread = null;

        try {
            System.out.println("try connect to ip=" + serverIP + " port=" +serverPort);
            InetAddress ipAddress = InetAddress.getByName(serverIP);
            socket = new Socket(ipAddress, serverPort);
            System.out.println("Connected to server!");


           // DataInputStream in = new DataInputStream(sin);
            String line= "";
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            if(!initNameServer(in))
                return false;

            thread =  new Thread(new WriteMessage());
            thread.start();

            while (true) {
                Message m = (Message) in.readObject();
                System.out.println(m);
            }
        } catch (Exception x) {
            System.out.println("Server disconnect!");
            System.out.println("Press any key!");
            return false;
        }
        finally {
            try {
                thread.interrupt();
                System.in.reset();
                in.close();
                out.close();
                socket.close();
                System.out.println("out");

            }catch (Exception E){
                //System.out.println(E);
            }
        }

    }

    // TODO: 19.10.16 end and check initNameServer
    private boolean initNameServer(ObjectInputStream in) throws Exception
    {
        String line = "";
        String enterName = "";
        Scanner sc = new Scanner(System.in);


        try {
                 line = (String) in.readObject();; // ждем пока сервер отошлет строку текста.
                if(!line.equals(ServerMessage.NAME_REQUEST))
                    throw new Exception("Error init name");
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter name :");
                line = keyboard.readLine();
                enterName = new String(line);
                out.writeObject(line);
                out.flush();
                line = (String)in.readObject();
                if(line.equals(ServerMessage.NAME_REQUEST_ASSEPT))
                    break;
                else if (line.equals(ServerMessage.NAME_REQUEST_ERROR))
                    System.out.println("Error name");
                else
                    System.out.println("Error server answer");
            }
        }catch (Exception E)
        {
            System.out.println(E);
            System.out.println("Error init");
            return false;
        }
        name = enterName;
        return true;
    }

    private void writeMessageThr()
    {
        try {
            String line = "";
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            while(!Thread.interrupted()) {
                line = keyboard.readLine();
                if(line == null)
                    continue;

                Message m = new Message(line, name, status);
                out.writeObject(m);
                out.flush();
            }
        }
        catch (Exception x) {
            System.out.println("writer disconnect");
        }
    }

    @Override
    public void run() {
        connect();
    }

    class WriteMessage implements Runnable{
        @Override
        public void run() {
            writeMessageThr();
        }
    }



    private static final class ServerMessage {
        public final static String DISCONNECT = "Disconnect message";
        public final static String NAME_REQUEST = "Wait name";
        public final static String NAME_REQUEST_ERROR = "Error name";
        public final static String NAME_REQUEST_ASSEPT = "Accepted name";

    }
}
