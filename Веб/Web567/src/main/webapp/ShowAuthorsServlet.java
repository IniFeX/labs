package webapp;

import webapp.ee.DataBaseClassses.Author;
import webapp.ee.DataBaseClassses.AuthorsDAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAuthorsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorsDAO authorsDAO = new AuthorsDAO();
        List<Author> authors = authorsDAO.getAllAuthors();

        request.setAttribute("authors", authors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/authors.jsp");
        dispatcher.forward(request, response);
    }
}
