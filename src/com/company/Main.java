package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //todo error when server disconnected
	// write your code here
        int port;
        if(args.length > 0)
             port = Integer.parseInt(args[0]);
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter port");
            port = Integer.parseInt(scanner.nextLine());
        }
        Client client = new Client("127.0.0.1", port);
        client.connect();
    }
}
