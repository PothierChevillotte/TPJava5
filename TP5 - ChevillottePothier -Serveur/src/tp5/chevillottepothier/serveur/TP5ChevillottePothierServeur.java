/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5.chevillottepothier.serveur;

/**
 *
 * @author florentchevillotte
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import static java.sql.Types.NULL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TP5ChevillottePothierServeur {

    public static String place;
    public static String pieces;
    public static String name;
    public static boolean good = false;

    public static void main(String[] args) throws IOException {
        new TP5ChevillottePothierServeur().begin(4444);
    }

    ServerSocket serverSocket;
    Connection conn;

    public void begin(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        try {
            
          
           Class.forName("com.mysql.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://localhost:8889/piecestheatre";
                conn = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (true) {
            System.out.println("Waiting for clients to connect on port " + port + "...");
            new ProtocolThread(serverSocket.accept()).start();
            //Thread.start() calls Thread.run()
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;

        public ProtocolThread(Socket socket) {
            System.out.println("Accepting connection from " + socket.getInetAddress() + "...");
            this.socket = socket;
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("Expecting Hello from client...");
                //sleep(5000);
                if ("Hello".equals(in_socket.readLine())) {
                    System.out.println("Client is nice :) Let's be polite...");
                    out_socket.println("Hello");
                }

                getPieces();
                out_socket.println("Hello");
                getValueClient();
                UpdatePlaces(place, pieces);
                //out_socket.println("La réservation a bien été effectuée ");
                name = in_socket.readLine();
                System.out.println("Name = " + name);
                int place_int = Integer.parseInt(place);
                // System.out.println("On a lancé le getValueClient");
                if (good == true) {
                    setResa(name, pieces, place_int);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("Closing connection.");
                    socket.close();
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

        private void getPieces() {
            String query = "SELECT nom_pieces FROM chevillotte";
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                String s1 = "";
                while (rs.next()) {
                    String s = rs.getString("nom_pieces");
                    s1 = s + "," + s1;
                }
                System.out.println(s1);
                out_socket.println(s1);
                return;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        }

        private void UpdatePlaces(String nb_users, String nom) {
            int nb_users2 = Integer.parseInt(nb_users);
            System.out.print("[Performing UPDATE] ... ");
            try {
                Statement st = conn.createStatement();
                String query = "SELECT places FROM chevillotte WHERE nom_pieces='" + nom + "'";
                try {
                    Statement st1 = conn.createStatement();
                    ResultSet rs = st1.executeQuery(query);
                    //  while (rs.next())
                    rs.next();
                    int p = rs.getInt("places");
                    int pmod = p - nb_users2;
                    if (pmod >= 0) {
                        out_socket.println("La réservation a bien été effectuée");
                        System.out.println("Pmod =" + pmod);
                        st.executeUpdate("UPDATE chevillotte SET places=" + pmod + " WHERE nom_pieces='" + nom + "'");
                        System.out.println(p + "   ");
                        good = true;
                    } else {
                        out_socket.println("La réservation a échoué. Places restantes = " + p);
                    }

                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }

                System.out.println("La base de données a été mise à jour");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        private void getValueClient() throws IOException {
            //new ProtocolThread(serverSocket.accept()).start();
            //System.out.println(this.in_socket.readLine());
           /* while (in_socket.readLine()="") {
             getValueClient();System.out.println("on est dans le while");
             }*/
            System.out.println("okgetValueClient");

            pieces = this.in_socket.readLine();
            System.out.println("pieces = " + pieces);
            out_socket.println("received");
            place = in_socket.readLine();
            System.out.println(place);
            System.out.println(pieces);
        }

        private void setResa(String nom, String nom_piece, int nb_place) {
            System.out.println("ok");
            String query = "INSERT INTO reservation " + "VALUES ('" + nom + "','" + nom_piece + "'," + nb_place + ")";
            try {
                Statement st = conn.createStatement();
                st.executeUpdate(query);
                System.out.println("BDD reservation a été mis à jour !");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        }
    }
}