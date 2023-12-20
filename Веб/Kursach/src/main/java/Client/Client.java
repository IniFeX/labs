package Client;


import java.io.*;
import java.net.*;

public class Client {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Client(){
        try {
            this.socket = new Socket("localhost", 12345);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void start() {
        System.out.println("Connected to server: localhost");

        // Получаем потоки для обмена данными с сервером

        // Запускаем поток для приема сообщений от сервера
        Thread receiveThread = new Thread(this::receiveMessages);
        receiveThread.start();

        // Теперь у вас есть outputStream и inputStream для отправки и получения данных с сервера
        // Реализуйте логику взаимодействия с сервером с использованием этих потоков
    }

    public void sendMessage(Object message) {
        try {
            out.writeObject(message);
            System.out.println("что-то получено ");
            //out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object receiveMessages() {
        try {
            while (true) {
                Object receivedMessage = in.readObject();
                // Обработайте полученное сообщение
                System.out.println("Received message from server: " + receivedMessage);
                return receivedMessage;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Добавьте другие методы и логику для взаимодействия с сервером
}
