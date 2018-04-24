package pl.edu.pwr.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SummaryXML {

    private Document document;
    private String[] contractHeadersValues;
    private String[] contractGeneralValues;
    private String[] contractDetailsValues;
    private String[] contractDetailsHeaders;

    public SummaryXML(String[] hValues, String[] gValues, String[] dValues, String[] dHeaders) {
        contractHeadersValues = hValues;
        contractGeneralValues = gValues;
        contractDetailsValues = dValues;
        contractDetailsHeaders = dHeaders;
    }

    private void createContractSumarizationXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();

            Element rootElement = document.createElement("CONTRACT");
            document.appendChild(rootElement);

            Element headers = document.createElement("HEADERS");
            rootElement.appendChild(headers);
            for (int i = 0; i < Constants.CONTRACT_HEADERS.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_HEADERS[i]);
                elem.appendChild(document.createTextNode(contractHeadersValues[i]));
                headers.appendChild(elem);
            }

            Element general = document.createElement("GENERAL");
            rootElement.appendChild(general);

            for (int i = 0; i < Constants.CONTRACT_GENERAL.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_GENERAL[i]);
                elem.appendChild(document.createTextNode(contractGeneralValues[i]));
                general.appendChild(elem);
            }

            Element details = document.createElement("DETAILS");
            rootElement.appendChild(details);

            for (int i = 0; i < contractDetailsHeaders.length; i++){
                Element elem = document.createElement(contractDetailsHeaders[i]);
                elem.appendChild(document.createTextNode(contractDetailsValues[i]));
                details.appendChild(elem);
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public void writeXMLtoFile() throws TransformerException {
        createContractSumarizationXML();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("/Users/nieop/Desktop/mgr/xml/summary.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
        System.out.println("File saved!");
    }
}
