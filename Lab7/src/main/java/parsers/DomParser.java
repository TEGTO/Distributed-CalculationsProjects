package parsers;
import myLogger.MyLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomParser <T>
{
    public List<T> parseDOM(File xml, MyHandler handler) throws SAXException, IOException, ParserConfigurationException
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(handler.getRootElName());
            for (int i = 0; i < nList.getLength(); i++)
            {
                Element el = (Element) nList.item(i);
                bypassingNodes(el, handler);
            }
            return handler.getHandledList();
        }
        catch (Exception e)
        {
            MyLogger.printErrorMessage(e.getMessage());
        }
        return null;
    }
    private void bypassingNodes(Node node, MyHandler handler)
    {
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            Map<String, String> attributes = new HashMap<>();
            if (node.getAttributes() != null)
            {
                for (int i = 0; i < node.getAttributes().getLength(); i++)
                    attributes.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getTextContent());
            }
            handler.setField(node.getNodeName(), node.getTextContent(), attributes);
            if (node.getChildNodes() != null)
            {
                for (int i = 0; i < node.getChildNodes().getLength(); i++)
                    bypassingNodes(node.getChildNodes().item(i), handler);
            }
        }
    }
}
