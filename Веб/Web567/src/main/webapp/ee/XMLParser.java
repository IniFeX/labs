package webapp.ee;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {
    public void parseEmployeesXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
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
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    String role = element.getElementsByTagName("role").item(0).getTextContent();

                    // Вывод информации о сотрудниках
                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Gender: " + gender);
                    System.out.println("Age: " + age);
                    System.out.println("Role: " + role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
