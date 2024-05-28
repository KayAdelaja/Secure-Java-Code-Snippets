import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.XMLConstants;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SecureXMLEntity {

    // Secure method to parse XML with entity expansion prevention
    public Document parseXML(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(xmlFile);
    }

    // Validate XML against a schema
    public boolean validateXMLSchema(File xmlFile, File schemaFile) throws SAXException, IOException, ParserConfigurationException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();

        Document document = parseXML(xmlFile);
        try {
            validator.validate(new DOMSource(document));
            return true;
        } catch (SAXException e) {
            System.out.println("XML validation error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SecureXMLEntity secureXMLEntity = new SecureXMLEntity();
        File xmlFile = new File("example.xml");
        File schemaFile = new File("example.xsd");

        try {
            Document document = secureXMLEntity.parseXML(xmlFile);
            System.out.println("XML parsed successfully.");

            boolean isValid = secureXMLEntity.validateXMLSchema(xmlFile, schemaFile);
            System.out.println("XML is valid against schema: " + isValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
