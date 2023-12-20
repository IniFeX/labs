package webapp.ee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Employee Information</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Gender</th><th>Age</th><th>Role</th></tr>");

        int totalAge = 0;
        int totalEmployees = 0;

        try {
            File xmlFile = new File("D:\\БГУИР\\стрWEB\\Marshalling\\DOM\\out\\production\\DOM\\resources\\Employee.xml"); // Путь к вашему файлу Employee.xml
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Employee");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                    String ageStr = element.getElementsByTagName("age").item(0).getTextContent();
                    int age = Integer.parseInt(ageStr);
                    String role = element.getElementsByTagName("role").item(0).getTextContent();

                    // Генерация HTML-кода для вывода информации о сотрудниках
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("<td>" + gender + "</td>");
                    out.println("<td>" + age + "</td>");
                    out.println("<td>" + role + "</td>");
                    out.println("</tr>");

                    // Расчет суммарного возраста
                    totalAge += age;
                    totalEmployees++;
                }
            }

            // Получение среднего возраста
            FindAverageAge findAverageAge = new FindAverageAge();
            double averageAge = findAverageAge.getAge(totalAge, totalEmployees);
            out.println("</table>");
            out.println("<p>Average Age: " + String.format("%.2f", averageAge) + "</p>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }
}
