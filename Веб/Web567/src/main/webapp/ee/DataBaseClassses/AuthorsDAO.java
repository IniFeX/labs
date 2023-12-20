package webapp.ee.DataBaseClassses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorsDAO {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        // Установить подключение к базе данных
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/laba4", "root", "123123")) {
            // Выполнить запрос к базе данных
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Authors");

            // Обработка результатов запроса
            while (resultSet.next()) {
                Author author = new Author(resultSet.getInt("AuthorID"), resultSet.getString("firstName"), resultSet.getString("lastName"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибок подключения или запроса к базе данных
        }

        return authors;
    }
}