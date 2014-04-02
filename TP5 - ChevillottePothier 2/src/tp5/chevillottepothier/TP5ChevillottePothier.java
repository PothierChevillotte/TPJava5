/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5.chevillottepothier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author florentchevillotte
 */
public class TP5ChevillottePothier {

    static Socket socket;
    /**
     * @param args the command line arguments
     */
    public static String[] tab;

    public static void main(String[] args) throws Exception {
        socket = new Socket("localhost", 4444);
        //Socket socket = new Socket("127.0.0.1", 4444);
        //to get the ip address
        System.out.println((java.net.InetAddress.getLocalHost()).toString());

        //true: it will flush the output buffer
        PrintWriter outSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Thread.sleep(1000);

        System.out.println("Sending Hello to server");
        outSocket.println("Hello");
        System.out.println("Waiting answer from server");
        if ("Hello".equals(inSocket.readLine())) {
            System.out.println("Server is nice :)");

        }
        String pieces = inSocket.readLine();
        System.out.println(pieces);
        TP5ChevillottePothier.tab = pieces.split(",");
        System.out.println(tab[0] + " " + tab[1] + " " + tab[2]);
        new Windows2().setVisible(true);
        System.out.println("Value windows = "+Windows2.value);
        while (Windows2.value != 0) {
            System.out.println("Waiting");
        }
       
        System.out.println("Value interface = "+Windows2.value);
        //attente
        System.out.println("Sending le nom de la pièce au serveur");
         
        System.out.println(outSocket.checkError());
        System.out.println(inSocket.read());
         // on envoie le  nom de la pièce au serveur
        outSocket.println(Windows2.piece);
        System.out.println(inSocket.readLine());
        if ("received".equals(inSocket.readLine())) {
            System.out.println("Sending le nombre de places à réserver");
            outSocket.println(Windows2.place);
            System.out.println("End.");
        }
        //String a = inSocket.readLine();
        Windows2.jLabel7.setText(inSocket.readLine());
        System.out.println(Windows2.name);
        outSocket.println(Windows2.name);
        
    }
}
