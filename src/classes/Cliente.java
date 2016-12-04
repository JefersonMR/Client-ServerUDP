/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author Jeferson
 */
public class Cliente {
     private static InetAddress host;
    private static final int PORT = 4221;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    private static Scanner userEntry;

    public static void main(String[] args) {

        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx) {
            JOptionPane.showMessageDialog(null, "ID do host não encontrado!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        try {
            datagramSocket = new DatagramSocket();
            userEntry = new Scanner(System.in);
            String message = "", response = "";
            do {
               System.out.print("Menssagem: ");
                message = userEntry.nextLine();
                
                if (!message.equals("***FECHAR***")) {
                    outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);

                    datagramSocket.send(outPacket);
                    buffer = new byte[256];
                    inPacket = new DatagramPacket(buffer, buffer.length);
                    datagramSocket.receive(inPacket);
                    response = new String(inPacket.getData(), 0, inPacket.getLength());

                    System.out.println("\nSERVER> " + response);
                }
            } while (!message.equals("***FECHAR***"));
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
           System.out.println(
                   "\n* Fechando Conexão... *");
            datagramSocket.close();
        }
    }
}
