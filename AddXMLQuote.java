package quotes;

import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class AddXMLQuote {
    public void addQuoteToXML(String Newquote, String author) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("quotes/quotes.xml");
        Element root = document.getDocumentElement();

        Collection<Quote> quotes = new ArrayList<Quote>();
        quotes.add(new Quote());

        for (Quote quote : quotes) {
            Element newQuoteToBeAdded = document.createElement("quote"); //new quote(node) to be added

            Element name = document.createElement("quote-text");//quote section
            name.appendChild(document.createTextNode(Newquote));
            newQuoteToBeAdded.appendChild(name);

            Element port = document.createElement("author");//author section
            port.appendChild(document.createTextNode(author));
            newQuoteToBeAdded.appendChild(port);

            root.appendChild(newQuoteToBeAdded);//add to quote-list
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("quotes/quotes.xml");
        transformer.transform(source, result);//overwrite with new file/result
    }

}