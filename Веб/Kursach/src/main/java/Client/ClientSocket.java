package Client;

import Client.DBClass.User;

import java.io.*;
import java.net.*;

public class ClientSocket {
    private User user;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket();

    private ClientSocket() {
        try {
            socket = new Socket("localhost", 5555);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeClient() {
        try {
            in.close();
            out.close();
            socket.close();
            in = null;
            out = null;
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //private int FlightId = -1;

    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }


    public Socket getSocket() {
        return socket;
    }


    public BufferedReader getInStream() {
        return in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


//    public int getFlightId() {
//        return FlightId;
//    }

//    public void setFlightId(int flightId) {
//        FlightId = flightId;
//    }
    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}
