package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //todo error when server disconnected
        // TODO: 20.10.16 do EXIT command 
        // write your code here
//        int port;
//        if(args.length > 0)
//             port = Integer.parseInt(args[0]);
//        else {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Enter port");
//            port = Integer.parseInt(scanner.nextLine());
//        }
//        Client client = new Client("127.0.0.1", port);
//        client.connect();

             int port = 1994;
        while (true) {
            new Client("127.0.0.1", port).connect();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Reconnect? [Y/N]");
            String ans = scanner.nextLine().toUpperCase();
            if(ans.equals("N"))
                break;

        }

    }
    static class test implements  Runnable
    {
        DataInputStream in = null;

        public test(DataInputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            func(in);
        }

        void func(DataInputStream in)
        {
            while(true) {

                try{
                    String line =in.readLine();
                    System.out.println(line);
                }catch (IOException E)
                {
                    System.out.println(E);
                    break;
                }
                System.out.println("hello");
            }
        }
    }

}
