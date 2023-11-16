package parsers;
import myLogger.MyLogger;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator
{
    public static boolean validate(String xmlFile, String xsdFile)
    {
        try
        {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            return true;
        }
        catch (Exception ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            return false;
        }
    }
}
