/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mental_blackjack;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Arguments are args[0] = localhost or IP of host. args[1] = number of players.
 * Only accessed if the host.
 *
 * @author Finnius
 */
class Server {

    private static String recv;
    final static String quit = "quit";
    static ArrayList<Socket> players = new ArrayList<Socket>();
    static ArrayList<String> ips = new ArrayList<String>();
    static int numPlayers = 0;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Incorrect Args. Should be 1 String of the host's IP or 'localhost'");
            System.exit(0);
        }

        if (args[0].equals("localhost")) {

            try {
                ServerSocket serverSocket = new ServerSocket(62400);
                numPlayers = Integer.parseInt(args[1]);
                players.add(new Socket(InetAddress.getLocalHost().getHostAddress(), 62400));
                while (players.size() < numPlayers) {
                    Socket clientSocket = serverSocket.accept();
                    players.add(clientSocket);
                    ips.add(clientSocket.getInetAddress().getHostAddress());
                }
                //inform players
                //send ips to all players
                for (Socket clientSocket : players) {
                    PrintWriter bytesWritten = new PrintWriter(clientSocket.getOutputStream(), true);
                    bytesWritten.println(ips.toString());
                }
                //all players now have the ips. Game can begin
                /*
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
                 bytesWritten.println(InetAddress.getLocalHost().getHostAddress());
                 }
                 */
            } catch (IOException e) {
            }
        } else {
            try {
                //connecting to host
                Socket socket = new Socket(args[0], 62400);
                while (true) {
                    PrintWriter bytesWritten = new PrintWriter(socket.getOutputStream(), true);

                    bytesWritten.println(InetAddress.getLocalHost().getHostAddress() + " has connected");

                    BufferedReader bytesRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    recv = bytesRead.readLine();
                    recv = recv.substring(1, recv.length());
                    recv = recv.substring(0, recv.length() - 1);
                    String[] temp = recv.split(",");
                    for (int i = 0; i < temp.length; i++) {
                        ips.add(temp[i]);
                        players.add(new Socket(temp[i], 62400));
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //At this point host and clients are equal and play can begin
        System.out.println("Players areeeeee:");
        for (Socket temp : players) {
            System.out.println("Player" + temp.toString());
        }
    }

    public void broadcast() {
        for (Socket player : players) {
            try {
                PrintWriter bytesWritten = new PrintWriter(player.getOutputStream(), true);
                bytesWritten.println(InetAddress.getLocalHost().getHostName() + " has connected");
            } catch (Exception e) {
                System.out.println("fuck");
            }
        }
    }
}
