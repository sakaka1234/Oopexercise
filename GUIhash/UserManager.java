package GUIhash;


import org.mindrot.jbcrypt.BCrypt;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class UserManager {
    private Map<String, String> users = new HashMap<>();

    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        users.put(username, hashed);
        return true;
    }

    public boolean login(String username, String password) {
        if (!users.containsKey(username)) return false;
        return BCrypt.checkpw(password, users.get(username));
    }

    public void exportToXML(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("users");
        doc.appendChild(root);

        for (String username : users.keySet()) {
            Element user = doc.createElement("user");

            // Tạo phần tử cho tên người dùng
            Element name = doc.createElement("username");
            name.setTextContent(username);
            user.appendChild(name);

            // Tạo phần tử cho mật khẩu đã băm
            Element hashedPassword = doc.createElement("hashedPassword");
            hashedPassword.setTextContent(users.get(username));
            user.appendChild(hashedPassword);

            root.appendChild(user);
        }

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.transform(new DOMSource(doc), new StreamResult(new File(path)));
    }


    public List<String> importFromXML(String path) throws Exception {
        List<String> importedUsers = new ArrayList<>();
        File file = new File(path);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        if (!doc.getDocumentElement().getNodeName().equals("users"))
            throw new Exception("XML không hợp lệ!");

        NodeList list = doc.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); i++) {
            Element user = (Element) list.item(i);
            String username = user.getElementsByTagName("username").item(0).getTextContent();
            importedUsers.add(username);
        }

        return importedUsers;
    }

    public Set<String> getAllUsernames() {
        return users.keySet();
    }
}