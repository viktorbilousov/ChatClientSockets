package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Client client = new Client("127.0.0.1", 4444);
        client.connect();
    }
}
