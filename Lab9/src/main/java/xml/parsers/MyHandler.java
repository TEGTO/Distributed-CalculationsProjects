package xml.parsers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Map;

public class MyHandler <T> extends DefaultHandler
{
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {throw new UnsupportedOperationException("Not implemented yet.");};
    @Override
    public void startDocument() throws SAXException {throw new UnsupportedOperationException("Not implemented yet.");};
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {throw new UnsupportedOperationException("Not implemented yet.");};
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {throw new UnsupportedOperationException("Not implemented yet.");};
    public void setField(String propName, String propData, Map<String, String> attributes) {throw new UnsupportedOperationException("Not implemented yet.");};
    public List<T> getHandledList() {throw new UnsupportedOperationException("Not implemented yet.");};
    public String getRootElName() {throw new UnsupportedOperationException("Not implemented yet.");}
}


