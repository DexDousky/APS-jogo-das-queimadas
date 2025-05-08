import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet {
    private BufferedImage sheet;
    private Map<String, Sprite> sprites;

    public SpriteSheet(String xmlPath) throws Exception {
        sprites = new HashMap<>();
        loadFromXML(xmlPath);
    }

    private void loadFromXML(String xmlPath) throws Exception {
        File xmlFile = new File(xmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        String imagePath = doc.getDocumentElement().getAttribute("imagePath");
        sheet = ImageIO.read(new File(imagePath));

        NodeList nodeList = doc.getElementsByTagName("sprite");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String name = element.getAttribute("name");
            int x = Integer.parseInt(element.getAttribute("x"));
            int y = Integer.parseInt(element.getAttribute("y"));
            int width = Integer.parseInt(element.getAttribute("width"));
            int height = Integer.parseInt(element.getAttribute("height"));
            sprites.put(name, new Sprite(name, x, y, width, height));
        }
    }

    public BufferedImage getSprite(String name) {
        Sprite sprite = sprites.get(name);
        if (sprite == null) return null;
        return sheet.getSubimage(0, 0, 0, 0);
        
    //sheet.getSubimage(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());   
    }
}
