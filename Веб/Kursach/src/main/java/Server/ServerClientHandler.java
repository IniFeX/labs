package Server;

import java.io.*;
import java.net.Socket;

public class ServerClientHandler implements Runnable {
    private final Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
   // private final DatabaseHandler databaseHandler;

    public ServerClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Пример: Ждем от клиента команду
            String clientCommand = (String) inputStream.readObject();

            // Пример: В зависимости от команды, делегируем обработку базе данных
//            switch (clientCommand) {
//                case "GET_USER":
//                    // Пример: Клиент запрашивает данные о пользователе
//                    String username = (String) inputStream.readObject();
//                    User user = databaseHandler.getUserByUsername(username);
//
//                    // Отправляем результат клиенту
//                    outputStream.writeObject(user);
//                    break;
//
//                case "SAVE_USER":
//                    // Пример: Клиент отправляет данные пользователя для сохранения
//                    User newUser = (User) inputStream.readObject();
//                    databaseHandler.saveUser(newUser);
//
//                    // Подтверждение клиенту
//                    outputStream.writeObject("User saved successfully");
//                    break;
//
//                // Другие команды...
//
//                default:
//                    // Неизвестная команда
//                    outputStream.writeObject("Unknown command");
//            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
