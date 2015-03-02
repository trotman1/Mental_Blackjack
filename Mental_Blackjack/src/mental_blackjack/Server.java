/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mental_blackjack;

import java.util.*;
import java.net.*;
import java.io.*;


class Server {

    private static String recv;
    final static String quit = "quit";

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Incorrect Args. Should be 1 String of the host's IP or 'localhost'");
            System.exit(0);
        }
        if (args[0].equals("localhost")) {

            try {
                ServerSocket socket = new ServerSocket(62400);
                Socket clientSocket = socket.accept();
                while (true) {

                    BufferedReader bytesRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    recv = bytesRead.readLine();
                    if (recv.equals(quit)) {
                        System.out.print("Client manually closed connection.\n");
                        System.exit(0);
                    } else {
                        System.out.println("CLIENT: " + recv);
                    }

                    String sendTo;
                    PrintWriter bytesWritten = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.print("Message: ");
                    Scanner user_input = new Scanner(System.in);
                    sendTo = user_input.next();
                    bytesWritten.println(sendTo);
                }
            } catch (IOException e) {
            }
        }
        else {
            try {
            String sendTo;
            Socket socket = new Socket(args[1], 62400);
            while (true) {
                PrintWriter bytesWritten = new PrintWriter(socket.getOutputStream(), true);
                System.out.print("Message: ");
                Scanner user_input = new Scanner(System.in);
                sendTo = user_input.next();
                if (sendTo.equals(quit)) {
                    bytesWritten.println("quit");
                    System.out.print("Quiting now.\n");
                    Thread.sleep(1000);
                    System.exit(0);
                } else {
                    bytesWritten.println(sendTo);
                }

                BufferedReader bytesRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                recv = bytesRead.readLine();
                System.out.println("SERVER: " + recv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        }
    }
}
